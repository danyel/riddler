import React, {useEffect, useState} from "react";
import {QuestionEndpoint, QuestionTypeEndpoint} from "Frontend/generated/endpoints";
import Question from "Frontend/generated/be/riddler/v1/question/client/model/Question";
import OptionTable from "Frontend/components/option/question-table";
import {
    Button,
    Dialog,
    FormItem,
    FormLayout,
    HorizontalLayout,
    Select,
    TextArea,
    TextField,
    VerticalLayout
} from "@vaadin/react-components";
// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';
import QuestionType from "Frontend/generated/be/riddler/v1/question/client/model/QuestionType";
import UpdateQuestion from "Frontend/generated/be/riddler/v1/question/client/model/UpdateQuestion";
import {BackButton, BanButton, RefreshButton, SaveButton} from "Frontend/components/ui/button";
import {ElementStylingTypes} from "Frontend/constant";
import {Notify, Objects} from "Frontend/util";
import BookmarkType from "Frontend/generated/be/riddler/v1/settings/model/BookmarkType";
import {useParams} from 'react-router-dom';
import {Navigate} from "Frontend/util/navigate";
import {Ban, Trash2} from "lucide-react";

export default function QuestionDetailPage() {
    const {id} = useParams<{ id: string }>();
    const [question, setQuestion] = useState<Question>();
    const [updateQuestion, setUpdateQuestion] = useState<UpdateQuestion>({
        question: '',
        type: QuestionType.OPEN,
        title: ''
    });
    const [items, setItems] = useState<{ label: string, value: string }[]>([]);
    const [isDialogOpen, setIsDialogOpen] = useState(false); // Fixed: Replaced raw signal with state

    function open() {
        setIsDialogOpen(true);
    }

    function close() {
        setIsDialogOpen(false);
    }

    function isSame(): boolean {
        if (!question) return true;
        return Objects.isEqual(question.title, updateQuestion.title) &&
            Objects.isEqual(question.question, updateQuestion.question) &&
            Objects.isEqual(question.type, updateQuestion.type);
    }

    function deleteQuestion() {
        if (!id) return;
        QuestionEndpoint.delete(id)
            .then(() => {
                Notify.error('Question "{}" deleted!', question?.title);
                Navigate.to(BookmarkType.QUESTIONS);
            })
            .catch(err => Notify.error('Could not delete the question {}', err));
    }

    const isChoice = (updateQuestion: UpdateQuestion) => {
        return updateQuestion.type == QuestionType.REVIEW || updateQuestion.type !== QuestionType.OPEN;
    }

    useEffect(() => {
        if (!id) return;

        QuestionEndpoint.get(id)
            .then(fetchedQuestion => {
                setQuestion(fetchedQuestion);
                setUpdateQuestion({
                    question: fetchedQuestion.question,
                    type: fetchedQuestion.type,
                    title: fetchedQuestion.title
                });
            })
            .catch(err => Notify.error('Could not retrieve the question {}', err));

        QuestionTypeEndpoint.questionTypes()
            .then((questionTypes: string[]) => {
                setItems(questionTypes.map(e => ({
                    label: `label.${e}`,
                    value: e
                })));
            })
            .catch(err => Notify.error('Could not retrieve the question types {}', err));
    }, [id]);

    return question ? (
        <>
            <HorizontalLayout className={styles.full_width_layout}>
                <div className={styles.menu_bar_layout}>
                    <BackButton onClick={() => Navigate.to(BookmarkType.QUESTIONS)}/>

                    <SaveButton
                        onClick={() => {
                            QuestionEndpoint.update(question.id!!, updateQuestion)
                            QuestionEndpoint.update(question.id!!, updateQuestion)
                                .then(() => {
                                    Notify.success('Question updated: ', updateQuestion.title);
                                    Navigate.to(BookmarkType.QUESTIONS);
                                })
                                .catch(err => Notify.error('Could not update the question {}', err));
                        }}
                        disabled={isSame()}
                    />

                    <RefreshButton
                        onClick={() => setUpdateQuestion({
                            title: question.title,
                            question: question.question,
                            type: question.type
                        })}
                        disabled={isSame()}
                    />

                    <BanButton onClick={open}/>
                </div>
            </HorizontalLayout>

            <Dialog
                headerTitle={`Delete question "${question.title}"?`}
                opened={isDialogOpen}
                onClosed={close}
                footer={
                    <>
                        <Button
                            theme={ElementStylingTypes.TERTIARY_ICON_RED}
                            onClick={deleteQuestion}
                            style={{marginRight: 'auto'}}
                        >
                            <Trash2 size={24}/>
                        </Button>
                        <Button theme={ElementStylingTypes.TERTIARY_ICON} onClick={close}>
                            <Ban size={24}/>
                        </Button>
                    </>
                }
            >
                Are you sure you want to delete this question permanently?
            </Dialog>

            <VerticalLayout className={styles.full_width_layout}>
                <FormLayout style={{width: '100%'}} autoResponsive columnWidth="8em" expandColumns expandFields>
                    <FormItem>
                        <TextField
                            label="Title"
                            value={updateQuestion.title}
                            className={styles.text_full}
                            onValueChanged={(e) => {
                                setUpdateQuestion(prev => ({...prev, title: e.detail.value}));
                            }}
                        />
                    </FormItem>

                    <FormItem>
                        <TextArea
                            label="Question"
                            value={updateQuestion.question}
                            className={styles.text_area_full}
                            onValueChanged={(e) => {
                                setUpdateQuestion(prev => ({...prev, question: e.detail.value}));
                            }}
                        />
                    </FormItem>

                    <FormItem>
                        <Select
                            label="Question Type"
                            items={items}
                            value={updateQuestion.type}
                            onValueChanged={(e) => {
                                const newType = e.detail.value as QuestionType;
                                setUpdateQuestion(prev => ({...prev, type: newType}));
                            }}
                        />
                    </FormItem>
                </FormLayout>

                <HorizontalLayout className={styles.full_width_layout}>
                    {isChoice(updateQuestion) &&
                        <OptionTable question={question}/>}
                </HorizontalLayout>
            </VerticalLayout>
        </>
    ) : null;
}

