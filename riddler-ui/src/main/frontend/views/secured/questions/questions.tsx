import {useEffect, useState} from 'react';
import {FormLayout, HorizontalLayout, Select, TextArea, TextField} from "@vaadin/react-components";
// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';

// CRITICAL: You must import the iconset for the icons to render
import '@vaadin/icons';
import Question from "Frontend/generated/be/riddler/v1/question/client/model/Question";
import CreateQuestion from "Frontend/generated/be/riddler/v1/question/client/model/CreateQuestion";
import {QuestionEndpoint} from "Frontend/generated/endpoints";
import {CloseButton, PlusButton, SaveButton, ViewDetailButton} from "Frontend/components/ui/button";
import QuestionType from "Frontend/generated/be/riddler/v1/question/client/model/QuestionType";
import BookmarkType from "Frontend/generated/be/riddler/v1/settings/model/BookmarkType";
import FormItem from "Frontend/components/ui/form/form-item.component";
import RiddlerTable from "Frontend/components/ui/table/table";
import RiddlerModal from "Frontend/components/ui/modal/modal";
import {Navigate} from "Frontend/util/navigate";
import {Notify} from "Frontend/util";

export default function QuestionsView() {
    const [open, setOpen] = useState(false);
    const [questions, setQuestions] = useState<Question[]>([]);
    const [createQuestion, setCreateQuestion] = useState<CreateQuestion>({
        type: QuestionType.OPEN,
        question: '',
        title: ''
    });
    const dropDown: { label: string, value: QuestionType }[] = [
        {
            label: 'Open',
            value: QuestionType.OPEN
        },
        {
            label: 'Review',
            value: QuestionType.REVIEW
        },
        {
            label: 'Single Choice',
            value: QuestionType.SINGLE_CHOICE
        },
        {
            label: 'Multiple Choice',
            value: QuestionType.MULTIPLE_CHOICE
        }
    ];

    useEffect(() => {
        if (open) {
            // Clear input value when the modal opens
            createQuestion.type = QuestionType.OPEN;
            createQuestion.question = '';
            createQuestion.title = '';
        }
    }, [open]);

    function saveQuestion() {
        const payload: CreateQuestion = {
            question: createQuestion.question,
            type: createQuestion.type,
            title: createQuestion.title,
        };
        QuestionEndpoint.create(payload)
            .then(() => {
                fetchQuestions();
                setOpen(false);
            })
            .catch(err => Notify.error('Could not create the question {}', err));
    }

    function closeIfNotValue(e: CustomEvent<{ value: boolean }>) {
        // Syncs background clicks / ESC keys directly back to parent
        if (!e.detail.value) setOpen(false);
    }

    function fetchQuestions() {
        QuestionEndpoint.getQuestions()
            .then(setQuestions)
            .catch(err => Notify.error('Could not retrieve the questions {}', err));
    }

    useEffect(() => {
        fetchQuestions();
    }, []);

    return (
        <>
            <HorizontalLayout className={styles.full_width_layout}>
                <div className={styles.menu_bar_layout}>
                    <PlusButton onClick={() => setOpen(true)}/>
                </div>
            </HorizontalLayout>

            <RiddlerModal
                headerTitle={'Create question'}
                opened={open}
                onClosed={closeIfNotValue}
                footer={
                    <>
                        <SaveButton onClick={saveQuestion}/>
                        <CloseButton onClick={() => setOpen(false)}/>
                    </>
                }
                content={
                    <FormLayout
                        style={{width: '100%'}}
                        autoResponsive
                        columnWidth="8em"
                        expandColumns
                        expandFields
                    >

                        <FormItem children={<Select label="Type" items={dropDown}
                                                    onValueChanged={(e) => {

                                                        setCreateQuestion(prevState => {
                                                            return {
                                                                ...prevState,
                                                                type: e.detail.value as QuestionType
                                                            }
                                                        });
                                                    }}/>}/>
                        <FormItem key={"title"} children={
                            <TextField value={createQuestion.title}
                                       className={styles.text_full}
                                       onValueChanged={(e) => {
                                           setCreateQuestion(prev => {
                                               return {
                                                   ...prev,
                                                   title: e.detail.value
                                               }
                                           });
                                       }}
                            />}/>
                        <FormItem key={"question"} children={
                            <TextArea value={createQuestion.question}
                                      className={styles.text_area_full}
                                      onValueChanged={(e) => {
                                          setCreateQuestion(prev => {
                                              return {
                                                  ...prev,
                                                  question: e.detail.value
                                              }
                                          });
                                      }}
                            />}/>

                    </FormLayout>
                }/>
            <HorizontalLayout className={styles.div_full}>
                <RiddlerTable
                    elements={questions}
                    columnNames={
                        [
                            {
                                path: 'title',
                                width: '10%'
                            },
                            {
                                path: 'question',
                                width: '10%'
                            },
                            {
                                path: 'type',
                                width: '10%'
                            },
                        ]
                    }
                    actionButtons={({item}: { item: Question }) => {
                        return <ViewDetailButton
                            onClick={() => Navigate.to(BookmarkType.QUESTIONS, item.id)}/>;
                    }}
                    emptyMessage={"No question found"}
                    helperMessage={"Click \"+\" to add a new question."}
                />
            </HorizontalLayout>
        </>
    );
}