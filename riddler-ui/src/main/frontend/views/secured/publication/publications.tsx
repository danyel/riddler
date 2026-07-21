import {useEffect, useState} from "react";
import Publication from "Frontend/generated/be/riddler/v1/publication/client/model/Publication";
import {HorizontalLayout, TextArea, TextField, VerticalLayout} from "@vaadin/react-components";
// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css'
import {useSignal} from "@vaadin/hilla-react-signals";
import {CancelButton, CheckButton, PlusButton, ViewDetailButton} from "Frontend/components";
import CreatePublication from "Frontend/generated/be/riddler/v1/publication/client/model/CreatePublication";
import {LevelSelector} from "Frontend/components/publication/level-selector-component";
import {PositionSelector} from "Frontend/components/publication/position-selector-component";
import {Notify} from "Frontend/util";
import {ElementStylingTypes} from "Frontend/constant";
import {PublicationsEndpoint} from "Frontend/generated/endpoints";
import BookmarkType from "Frontend/generated/be/riddler/v1/settings/model/BookmarkType";
import FormItem from "Frontend/components/ui/form/form-item.component";
import {Navigate} from "Frontend/util/navigate";
import RiddlerTable from "Frontend/components/ui/table/table";
import RiddlerModal from "Frontend/components/ui/modal/modal";

const PUBLIC_COLUMN_DEFINITIONS = [
    {
        path: 'title',
        width: '200px',
        flexGrow: 0,
    },
    {
        path: 'description',
        width: '200px',
        flexGrow: 1,
    },
    {
        path: 'position.position',
        width: '200px',
        flexGrow: 0,
        header: 'Position',
    },
    {
        path: 'level.level',
        header: 'Level',
        width: '200px',
        flexGrow: 0,
    }
];

export default function PublicationsPage() {
    const [publications, setPublications] = useState<Publication[]>([]);
    const createPublicationOpened = useSignal(false);
    const createPublication = useSignal<CreatePublication>({
        description: '',
        title: '',
        proposal: '',
        level_id: '',
        position_id: ''
    });

    useEffect(() => {
        if (!createPublicationOpened.value) {
            getPublications();
        }
    }, [createPublicationOpened.value]);

    function getPublications() {
        PublicationsEndpoint.getPublications()
            .then(setPublications)
            .catch(err => Notify.error('Could not retrieve the publications {}', err));
    }

    const actionButtons = ({item}: { item: Publication }) => {
        return (
            <>
                <ViewDetailButton onClick={() => {
                    Navigate.to(BookmarkType.PUBLICATIONS, item.id);
                }}/>
            </>
        );
    };

    return (
        <VerticalLayout className={styles.full_width_layout}>
            <HorizontalLayout className={styles.full_width_layout}>
                <div className={styles.menu_bar_layout}>
                    <PlusButton onClick={() => createPublicationOpened.value = true}/>
                </div>
            </HorizontalLayout>
            <HorizontalLayout className={styles.full_width_layout}>
                <RiddlerModal headerTitle="Add new publication"
                              opened={createPublicationOpened.value}
                              onClosed={() => {
                                  createPublicationOpened.value = false;
                              }}
                              footer={
                                  <>
                                      <CheckButton onClick={() => {
                                          const createPublicationVa = createPublication.value;
                                          PublicationsEndpoint.createPublication(createPublicationVa)
                                              .then((publication) => {
                                                  Notify.success('Publication created successfully');
                                                  setPublications([publication, ...publications]);
                                                  createPublicationOpened.value = false;
                                              })
                                              .catch(err => Notify.error('Failed to save publication: {}', err));
                                      }}/>
                                      <CancelButton theme={ElementStylingTypes.TERTIARY_ICON_RED} onClick={() => {
                                          createPublicationOpened.value = false;
                                      }}/>
                                  </>
                              }
                              content={
                                  <VerticalLayout theme="spacing" style={{alignItems: 'stretch'}}>
                                      <FormItem
                                          children={
                                              <TextField
                                                  label="Title"
                                                  value={createPublication.value.title}
                                                  onValueChanged={(e) => (createPublication.value.title = e.detail.value)}
                                                  className={styles.text_full}
                                              />
                                          }
                                      />
                                      <FormItem
                                          children={
                                              <TextArea
                                                  label="Description"
                                                  value={createPublication.value.description}
                                                  onValueChanged={(e) => (createPublication.value.description = e.detail.value)}
                                                  className={styles.text_area_full}
                                              />
                                          }
                                      />

                                      <FormItem
                                          children={
                                              <TextArea
                                                  label="Proposal"
                                                  value={createPublication.value.proposal}
                                                  onValueChanged={(e) => (createPublication.value.proposal = e.detail.value)}
                                                  className={styles.text_area_full}
                                              />
                                          }
                                      />

                                      <FormItem
                                          children={
                                              <LevelSelector
                                                  selectedLevelId={createPublication.value.level_id}
                                                  onLevelChange={(id) => createPublication.value.level_id = id!!}
                                              />
                                          }
                                      />

                                      <FormItem
                                          children={
                                              <PositionSelector
                                                  selectedPositionId={createPublication.value.position_id}
                                                  onPositionChange={(id) => createPublication.value.position_id = id!!}
                                              />
                                          }
                                      />
                                  </VerticalLayout>
                              }
                />

                <RiddlerTable
                    elements={publications}
                    columnNames={PUBLIC_COLUMN_DEFINITIONS}
                    actionButtons={actionButtons}
                    emptyMessage={"No publication found"}
                    helperMessage={"Click \"+\" to add a new publication."}/>
            </HorizontalLayout>
        </VerticalLayout>
    );
}