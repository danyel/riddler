import React, {useEffect, useState} from "react";
import {Button, Dialog, HorizontalLayout, TextField} from "@vaadin/react-components";
import {LOGGER, Notify} from "Frontend/util";
import {CategoryEndpoint} from "Frontend/generated/endpoints";
import Category from "Frontend/generated/be/riddler/v1/category/client/model/Category";
import CreateCategory from "Frontend/generated/be/riddler/v1/category/client/model/CreateCategory";
import {useAuth} from "Frontend/auth";
import UpdateCategory from "Frontend/generated/be/riddler/v1/category/client/model/UpdateCategory";
import RiddlerTable from "Frontend/components/ui/table/table";
import {CancelButton, PlusButton} from "Frontend/components";
import {Ban, Pen, Save, Trash2} from "lucide-react";
import {ElementStylingTypes} from "Frontend/constant";
import RiddlerModal from "Frontend/components/ui/modal/modal";
// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';

export default function CategoryManagementPage() {
    const {state} = useAuth();
    const [categories, setCategories] = useState<Category[]>([]);
    const [modalType, setModalType] = useState<ModalType>('NONE');
    const [dialogOpen, setDialogOpen] = useState(false);
    const [selectedCategory, setSelectedCategory] = useState<Category | null>(null);
    const [categoryName, setCategoryName] = useState("");
    const [rawKeywords, setRawKeywords] = useState("");
    const [itemId, setItemId] = useState<string>('');

    type ModalType = 'DELETE' | 'EDIT' | 'NONE';
    const loadData = () => {
        if (state.user) {
            CategoryEndpoint.findAll()
                .then(setCategories)
                .catch(err => Notify.error('Could not find the category {}', err));
        }
    };

    useEffect(() => {
        loadData();
        setModalType("NONE");
    }, []);

    const openEditor = (category: Category | null) => {
        setSelectedCategory(category);
        if (category) {
            setCategoryName(category.name || "");
            const keywordsString = category.keywords.map(k => k.word).join(", ") || "";
            setRawKeywords(keywordsString);
        } else {
            setCategoryName("");
            setRawKeywords("");
        }
        openModal('EDIT');
    };

    const handleSave = () => {
        if (!categoryName.trim()) {
            Notify.error("Category name cannot be left blank.");
            return;
        }

        const keywordList: { id?: string, word: string }[] = rawKeywords.split(",")
            .filter(k => k.length > 0)
            .map(k => {
                return {
                    word: k.trim()
                };
            });

        if (selectedCategory) {
            const updateCategory: UpdateCategory = {
                name: categoryName,
                keywords: keywordList
            };
            LOGGER.debug("Updating Category");
            CategoryEndpoint.update(selectedCategory.id, updateCategory)
                .then(() => {
                    Notify.success("Scanning rule configuration stored successfully!");
                    closeModal();
                    loadData();
                })
                .catch(() => Notify.error("Failed to write rules data changes to backend endpoints."));
        } else {
            LOGGER.debug("Creating Category");
            const categoryPayload: CreateCategory = {
                name: categoryName,
                keywords: keywordList
            };

            CategoryEndpoint.create(categoryPayload)
                .then(() => {
                    Notify.success("Scanning rule configuration stored successfully!");
                    closeModal();
                    loadData();
                })
                .catch(() => Notify.error("Failed to write rules data changes to backend endpoints."));
        }
    };

    const handleDelete = (id: string) => {
        setItemId(id);
        openModal("DELETE");
    };

    const actionRenderer = ({item}: { item: Category }) => (
        <HorizontalLayout theme="spacing-s">
            <Button theme={ElementStylingTypes.TERTIARY_ICON} onClick={() => openEditor(item)}><Pen size={24}/></Button>
            <Button theme={ElementStylingTypes.TERTIARY_ICON_RED} onClick={() => handleDelete(item.id!!)}><Trash2
                size={24}/></Button>
        </HorizontalLayout>
    );

    const keywordsBadgeRenderer = ({item}: { item: Category }) => (
        <HorizontalLayout theme="spacing-xs" style={{flexWrap: 'wrap'}}>
            {item.keywords?.map((k, index) => (
                <span
                    key={index}
                    style={{
                        background: 'var(--lumo-contrast-10pct)',
                        fontSize: 'var(--lumo-font-size-xs)',
                        padding: '2px 6px',
                        borderRadius: '4px',
                        fontFamily: 'var(--lumo-font-family)'
                    }}
                >
                    {k.word}
                </span>
            )) || <span style={{color: 'var(--lumo-disabled-text-color)', fontStyle: 'italic'}}>None</span>}
        </HorizontalLayout>
    );

    const closeModal = () => {
        setModalType('NONE');
        setDialogOpen(false);
    }

    const openModal = (modalType: ModalType) => {
        setModalType(modalType);
        setDialogOpen(true);
    }

    function isOpen(mt: ModalType): boolean {
        return dialogOpen && modalType === mt;
    }

    return (
        <>
            <HorizontalLayout className={styles.full_width_layout}>

                <div className={styles.menu_title_layout}>
                    <h1>CV Category Rules Management</h1>
                    <div className={styles.code}>
                        Define target profile classification tracks and system text scanning keyword anchors
                        dynamically.
                    </div>
                </div>
                <div className={styles.menu_bar_title_layout}>
                    <PlusButton onClick={() => openEditor(null)}/>
                </div>

            </HorizontalLayout>

            <Dialog
                headerTitle={`Remove category?`}
                opened={isOpen('DELETE')}
                onClosed={close}
                footer={
                    <>
                        <Button
                            theme={ElementStylingTypes.TERTIARY_ICON_RED}
                            style={{marginRight: 'auto'}}
                            onClick={() => {
                                CategoryEndpoint.delete(itemId)
                                    .then(() => {
                                        Notify.success("Classification rules scrubbed successfully.");
                                        loadData();
                                        closeModal();
                                    })
                                    .catch(() => Notify.error("Failed to delete category rule dependency."));
                            }}
                        >
                            <Trash2 size={24}/>
                        </Button>
                        <Button theme={ElementStylingTypes.TERTIARY_ICON} onClick={closeModal}>
                            <Ban size={24}/>
                        </Button>
                    </>
                }
            >
                Are you sure you want to permanently delete this classification rule package? This removes it from all
                existing participants.
            </Dialog>
            <RiddlerTable
                elements={categories}
                columnNames={
                    [
                        {
                            path: 'name',
                            header: 'Category Skill Track Name',
                            width: "20%",
                            flexGrow: 0
                        },
                        {
                            path: 'keywords',
                            width: '60%',
                            flexGrow: 1,
                            header: 'Target Scanning Keywords (Matches 2 or more to activate)',
                            renderer: keywordsBadgeRenderer
                        }
                    ]
                }
                actionButtons={actionRenderer}
                emptyMessage={"No category rules found"}
                helperMessage={"Click \"+\" to configure your first parsing track."}/>
            <RiddlerModal
                headerTitle={selectedCategory ? "Edit Skill Classification Rules" : "Create New Skill Classification Rule"}
                opened={isOpen('EDIT')}
                onClosed={() => setDialogOpen(false)}
                footer={
                    <HorizontalLayout theme="spacing" style={{width: '100%', justifyContent: 'flex-end'}}>
                        <Button theme={ElementStylingTypes.TERTIARY_ICON} onClick={handleSave}><Save
                            size={24}/></Button>
                        <CancelButton theme={ElementStylingTypes.TERTIARY_ICON}
                                      onClick={() => setDialogOpen(false)}></CancelButton>
                    </HorizontalLayout>
                }
                content={
                    <>
                        <TextField
                            label="Profile Category Name"
                            placeholder="e.g., Python AI Developer"
                            value={categoryName}
                            onChange={(e) => setCategoryName(e.target.value)}
                            style={{width: '100%'}}
                        />
                        <TextField
                            label="Associated Tracking Keywords"
                            placeholder="Separate tags with commas (e.g., python, pytorch, pandas)"
                            value={rawKeywords}
                            onChange={(e) => setRawKeywords(e.target.value)}
                            style={{width: '100%'}}
                        />
                        <div style={{
                            fontSize: 'var(--lumo-font-size-xs)',
                            color: 'var(--lumo-secondary-text-color)',
                            lineHeight: 'var(--lumo-line-height-s)'
                        }}>
                            * Note: When any participant uploads their CV document, the internal indexing framework
                            parses
                            matching words case-insensitively against the list above.
                        </div>
                    </>
                }
            />
        </>
    );
}
