import {useParams} from "react-router";
import React, {useEffect, useState} from "react";
import {InvitationEndpoint, PublicationsEndpoint, QuestionEndpoint} from "Frontend/generated/endpoints";
import Invitation from "Frontend/generated/be/riddler/v1/invitation/client/model/Invitation";
import {Notify, Strings} from "Frontend/util";
import Publication from "Frontend/generated/be/riddler/v1/publication/client/model/Publication";
import {HorizontalLayout, Select} from "@vaadin/react-components";
import RiddlerTable from "Frontend/components/ui/table/table";
import {BackButton, DeleteButton, PlusButton, SaveButton} from "Frontend/components";
// @ts-ignore
import styles from "Frontend/themes/riddler/common.module.css";
import Question from "Frontend/generated/be/riddler/v1/question/client/model/Question";
import {Navigate} from "Frontend/util/navigate";
import BookmarkType from "Frontend/generated/be/riddler/v1/settings/model/BookmarkType";

interface OptionItem {
    label: string;
    value: string;
}

export default function InvitationPage() {
    const params = useParams();
    const [invitation, setInvitation] = useState<Invitation>();
    const [publication, setPublication] = useState<Publication>();
    const [questions, setQuestions] = useState<OptionItem[]>([]);
    const [fetchedQuestions, setFetchedQuestions] = useState<Question[]>([]);
    const [selectedQuestions, setSelectedQuestions] = useState<OptionItem[]>([]);
    const [selectedQuestion, setSelectedQuestion] = useState<string>(''); // Default to empty string for safe input bindings

    useEffect(() => {
        if (params.invitation_id) {
            QuestionEndpoint.getQuestions()
                .then(setFetchedQuestions)
                .catch(() => Notify.error('Could not fetch questions.'));
        }
    }, [params.invitation_id]);

    useEffect(() => {
        if (fetchedQuestions && params.invitation_id) {
            InvitationEndpoint.findById(params.invitation_id)
                .then(setInvitation)
                .catch(() => Notify.error("No invitation found for {}.", params.invitation_id));
        }
    }, [params.invitation_id, fetchedQuestions]);

    useEffect(() => {
        if (invitation && params.invitation_id) {
            const selectedItems = fetchedQuestions.filter(e => invitation.questions.indexOf(e.id!!) > -1).map(e => {
                return {value: e.id, label: e.title} as OptionItem
            });
            const remainingQuestions = fetchedQuestions.filter(e => invitation.questions.indexOf(e.id!!) == -1).map(e => {
                return {value: e.id, label: e.title} as OptionItem
            });
            setSelectedQuestions(selectedItems);
            setQuestions(remainingQuestions);
            PublicationsEndpoint.findPublicationById(invitation.publication.id)
                .then((publication: Publication) => {
                    setPublication(publication);
                })
                .catch(() => Notify.error('No publication found for {}.', invitation.publication.id));
        }
    }, [invitation]);

    return (
        <>
            <HorizontalLayout className={styles.full_width_layout}>
                <div className={styles.menu_bar_layout}>
                    <BackButton onClick={() => Navigate.to(BookmarkType.PARTICIPANTS, invitation?.participant_id)}/>
                    {
                        invitation?.id && (
                            <SaveButton onClick={() => {
                                InvitationEndpoint.addQuestions(invitation?.id!!, selectedQuestions.map(e => e.value))
                                    .then(() => Notify.success('Successfully added questions to the invitation.'))
                                    .catch(() => Notify.error('Failed to add questions to the invitation.'));
                            }}/>
                        )
                    }
                </div>
            </HorizontalLayout>
            {publication && (
                <div style={{marginBottom: '1em', fontSize: '1.2em', fontWeight: 'bold'}}>
                    {publication.title}
                </div>
            )}
            <div style={{display: 'flex', alignItems: 'flex-end', gap: '1em', marginBottom: '1em'}}>
                {
                    questions.length > 0 && (
                        <Select
                            label="Select Question"
                            items={questions}
                            value={selectedQuestion}
                            onValueChanged={(e) => setSelectedQuestion(e.detail.value)}
                        />
                    )
                }
                {Strings.isNotEmpty(selectedQuestion) && (
                    <PlusButton onClick={() => {
                        const matchedQuestion = questions.find(o => o.value === selectedQuestion);
                        if (matchedQuestion) {
                            if (selectedQuestions.some(q => q.value === matchedQuestion.value)) {
                                Notify.warn("Question is already added to this list.");
                                return;
                            }
                            setSelectedQuestions(prev => {
                                return [...prev, matchedQuestion];
                            });
                            setQuestions(prev => prev.filter(q => q.value !== matchedQuestion.value));
                            setSelectedQuestion('');
                        }
                    }}/>
                )}
            </div>
            <RiddlerTable
                elements={selectedQuestions}
                columnNames={[{path: 'value', width: '200px'}, {path: 'label', width: '200px'}]}
                emptyMessage={"No questions added to this invitation"}
                helperMessage={"Select a question and add it."}
                actionButtons={({item}: { item: OptionItem }) => {
                    return (
                        <>
                            <DeleteButton onClick={() => {
                                setSelectedQuestions(prev => prev.filter(q => q.value !== item.value))
                                setQuestions(prev => {
                                    return [...prev, item];
                                })
                            }}/>
                        </>
                    )
                }}
            />
        </>
    );
}