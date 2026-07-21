import {useEffect} from "react";
import {Button, FormLayout, HorizontalLayout, TextArea, VerticalLayout} from "@vaadin/react-components";
// @ts-ignore
import styles from "Frontend/themes/riddler/common.module.css";
import {OptionEndpoint, QuestionEndpoint} from "Frontend/generated/endpoints";
import {useSignal} from "@vaadin/hilla-react-signals";
import {CloseButton, PlusButton, SaveButton} from "Frontend/components/ui/button";
import FormItem from "Frontend/components/ui/form/form-item.component";
import RiddlerModal from "Frontend/components/ui/modal/modal";
import RiddlerTable from "Frontend/components/ui/table/table";
import {Notify} from "Frontend/util";
import Question from "Frontend/generated/be/riddler/v1/question/client/model/Question";
import Option from "Frontend/generated/be/riddler/v1/question/client/model/Option";
import AddOption from "Frontend/generated/be/riddler/v1/question/client/model/AddOption";
import UpdateOption from "Frontend/generated/be/riddler/v1/question/client/model/UpdateOption";
import {Pencil} from "lucide-react";
import {ElementStylingTypes} from "Frontend/constant";

export default function SolutionTable(
    {
        question,
    }:
    {
        question: Question,
    }
) {
    const activeOption = useSignal<Option | null>(null);
    const isModalOpen = useSignal(false);
    const optionsList = useSignal<Option[]>([]);
    const isLoading = useSignal(false);
    const refreshOptionsList = () => {
        isLoading.value = true;
        QuestionEndpoint.get(question.id)
            .then((q) => {
                optionsList.value = q.options;
            })
            .catch((err) => Notify.error('Failed to reload options: {}', err))
            .finally(() => {
                isLoading.value = false;
            });
    };

    useEffect(() => {
        refreshOptionsList();
    }, [question.id]);
    const openCreate = () => {
        activeOption.value = null;
        isModalOpen.value = true;
    };

    const openUpdate = (option: Option) => {
        activeOption.value = option;
        isModalOpen.value = true;
    };
    const optionActions = ({item}: { item: Option }) => {
        return <Button theme={ElementStylingTypes.TERTIARY_ICON} onClick={() => {
            openUpdate(item);
        }}>
            <Pencil size={24}/>
        </Button>;
    };

    return (
        <VerticalLayout className={styles.full_width_layout}>
            <HorizontalLayout className={styles.full_width_layout}>
                <div className={styles.menu_bar_layout}>
                    <PlusButton onClick={openCreate}/>
                </div>
            </HorizontalLayout>
            <OptionDialogModal
                show={isModalOpen.value}
                questionId={question.id!!}
                editingOption={activeOption.value}
                onOptionSaved={refreshOptionsList}
                onClose={() => (isModalOpen.value = false)}
            />
            <HorizontalLayout className={styles.full_width_layout}>
                <RiddlerTable elements={optionsList.value}
                              columnNames={
                                  [
                                      {
                                          path: 'value',
                                          header: 'Option',
                                          width: '240px',
                                          flexGrow: 1,
                                      }
                                  ]
                              }
                              emptyMessage={'No options found'}
                              helperMessage={'Click \"+\" to add a new option.'}
                              actionButtons={optionActions}/>
            </HorizontalLayout>
        </VerticalLayout>
    );
}

function OptionDialogModal(
    {
        show,
        questionId,
        editingOption, // 2. Pass the option being edited (null/undefined if creating)
        onOptionSaved,  // 3. Renamed to match both actions
        onClose
    }:
    {
        show: boolean;
        questionId: string;
        editingOption?: Option | null;
        onOptionSaved: () => void;
        onClose: () => void;
    }
) {
    const optionValue = useSignal('');
    const isEditing = !!editingOption;

    // 4. Pre-fill state when editing, reset when creating
    useEffect(() => {
        if (show) {
            optionValue.value = isEditing ? editingOption.value : '';
        }
    }, [show, editingOption]);

    // 5. Unified Save handler
    const handleSave = () => {
        if (isEditing) {
            const payload: UpdateOption = {
                option_id: editingOption.id,
                value: optionValue.value
            };
            OptionEndpoint.updateOption(payload)
                .then(() => {
                    onOptionSaved();
                    onClose();
                })
                .catch(err => Notify.error('Could not update option: {}', err));
        } else {
            const payload: AddOption = {
                question_id: questionId,
                value: optionValue.value
            };
            OptionEndpoint.addOption(payload)
                .then(() => {
                    onOptionSaved();
                    onClose();
                })
                .catch(err => Notify.error('Could not create option: {}', err));
        }
    };

    return (
        <RiddlerModal
            headerTitle={isEditing ? "Update option" : "Create option"}
            opened={show}
            onClosed={onClose}
            footer={
                <>
                    <SaveButton onClick={handleSave}/>
                    <CloseButton onClick={onClose}/>
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
                    <FormItem children={
                        <TextArea
                            label="Option"
                            value={optionValue.value}
                            onValueChanged={(e) => (optionValue.value = e.detail.value)}
                            className={styles.text_area_full}
                        />
                    }/>
                </FormLayout>
            }
        />
    );
}
