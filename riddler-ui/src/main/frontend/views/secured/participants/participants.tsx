import Participant from "Frontend/generated/be/riddler/v1/participant/client/model/Participant";
import {Button, FormLayout, HorizontalLayout, TextField} from "@vaadin/react-components";
// @ts-ignore
import styles from "Frontend/themes/riddler/common.module.css";
import {useEffect, useState} from "react";
import {ParticipantAdminEndpoint} from "Frontend/generated/endpoints";
import {useSignal} from "@vaadin/hilla-react-signals";
import {CheckButton, CloseButton, ViewDetailButton} from "Frontend/components/ui/button";
import CreateParticipant from "Frontend/generated/be/riddler/v1/participant/client/model/CreateParticipant";
import BookmarkType from "Frontend/generated/be/riddler/v1/settings/model/BookmarkType";
import RiddlerModal from "Frontend/components/ui/modal/modal";
import FormItem from "Frontend/components/ui/form/form-item.component";
import {Navigate} from "Frontend/util/navigate";
import RiddlerTable from "Frontend/components/ui/table/table";
import {ElementStylingTypes} from "Frontend/constant";
import {Plus} from "lucide-react";
import {Notify} from "Frontend/util";

function ParticipantTable() {
    const [open, setOpen] = useState(false);
    const [participants, setParticipants] = useState<Participant[]>([]);
    const createParticipant = useSignal<CreateParticipant>({});
    const [modalType, setModalType] = useState<ModalType>('NONE');
    type ModalType = 'TOKEN' | 'NONE' | 'ADD';

    useEffect(() => {
        if (open) {
            createParticipant.value.email_address = '';
            createParticipant.value.first_name = '';
            createParticipant.value.last_name = '';
        }
    }, [open]);

    function saveParticipant() {
        const payload: CreateParticipant = {
            email_address: createParticipant.value.email_address,
            first_name: createParticipant.value.first_name,
            last_name: createParticipant.value.last_name,
        }
        ParticipantAdminEndpoint.create(payload)
            .then(() => {
                fetchParticipants();
                setOpen(false);
            })
            .catch(err => Notify.error('Could not create a participant {}', err));
    }

    function fetchParticipants() {
        ParticipantAdminEndpoint.findAll()
            .then(setParticipants)
            .catch(err => Notify.error('Could not retrieve the participants {}', err));
    }

    useEffect(() => {
        fetchParticipants();
    }, []);

    const actionButtons = ({item}: { item: Participant }) => {
        return (
            <>
                <ViewDetailButton onClick={() => {
                    Navigate.to(BookmarkType.PARTICIPANTS, item.id);
                }}/>
            </>
        );
    };

    const closeModal = () => {
        setModalType('NONE')
        setOpen(false);
    }

    const openModal = (modalType: ModalType) => {
        setModalType(modalType);
        setOpen(true);
    }

    const isOpenModal = (mt: ModalType) => {
        return open && modalType === mt;
    }

    return (
        <>

            <HorizontalLayout className={styles.full_width_layout}>
                <div className={styles.menu_bar_layout}>
                    <Button theme={ElementStylingTypes.TERTIARY_ICON} onClick={() => openModal('ADD')}><Plus
                        size={24}/></Button>
                </div>
            </HorizontalLayout>
            <RiddlerModal
                headerTitle="Add participant"
                opened={isOpenModal('ADD')}
                onClosed={closeModal}
                footer={
                    <>
                        <CheckButton onClick={saveParticipant}/>
                        <CloseButton onClick={closeModal}/>
                    </>
                }
                content={
                    <>
                        <FormLayout style={{width: '100%'}}
                                    autoResponsive
                                    columnWidth="8em"
                                    expandColumns
                                    expandFields>
                            <FormItem key={"firstName"}
                                      children={
                                          <TextField
                                              label="First Name"
                                              value={createParticipant.value.first_name}
                                              onValueChanged={(e) => (createParticipant.value.first_name = e.detail.value)}
                                              className={styles.text_full}
                                          />
                                      }/>
                            <FormItem key={"lastName"}
                                      children={
                                          <TextField
                                              label="Last Name"
                                              value={createParticipant.value.last_name}
                                              onValueChanged={(e) => (createParticipant.value.last_name = e.detail.value)}
                                              className={styles.text_full}
                                          />
                                      }/>
                            <FormItem key={"emailAddress"}
                                      children={
                                          <TextField
                                              label="Email"
                                              value={createParticipant.value.email_address}
                                              onValueChanged={(e) => (createParticipant.value.email_address = e.detail.value)}
                                              className={styles.text_full}
                                          />
                                      }/>
                        </FormLayout>

                    </>
                }
            />

            <RiddlerTable
                elements={participants}
                columnNames={
                    [
                        {
                            path: 'first_name',
                            header: "First Name",
                            width: '100px',
                            flexGrow: 1
                        },
                        {
                            path: 'last_name',
                            header: "Last Name",
                            width: '100px',
                            flexGrow: 1
                        },
                        {
                            path: 'email_address',
                            header: "Email",
                            width: '100px',
                            flexGrow: 1
                        }
                    ]
                }
                actionButtons={actionButtons}
                emptyMessage={"No participant found"}
                helperMessage={"Click \"Add New Participant\" to add a new participant."}/>
        </>
    );
}

export default function ParticipantsPage() {
    return (
        <>
            <ParticipantTable/>
        </>
    );
}