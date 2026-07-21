import {useEffect, useState} from "react";
import {InvitationEndpoint, UserEndpoint} from "Frontend/generated/endpoints";
import Invitation from "Frontend/generated/be/riddler/v1/invitation/client/model/Invitation";
import {useParams} from "react-router-dom";
import {Button, FormLayout, HorizontalLayout, TextField} from "@vaadin/react-components";
import FormItem from "Frontend/components/ui/form/form-item.component";
// @ts-ignore
import styles from "Frontend/themes/riddler/common.module.css";
import {useSignal} from "@vaadin/hilla-react-signals";
import {Key} from "lucide-react";
import {ElementStylingTypes} from "Frontend/constant";
import {Notify} from "Frontend/util";

export default function ParticipantPage() {
    const [invitation, setInvitation] = useState<Invitation>();
    const token = useSignal<string>();
    const params = useParams();

    useEffect(() => {
        if (params.token) {
            InvitationEndpoint.findById(params.token)
                .then(setInvitation)
                .catch(err => Notify.error('Could not retrieve the invitation via token {}', err));
        }
    });

    return (
        <HorizontalLayout className={styles.full_width_layout}>
            <FormLayout
                style={{width: '100%'}}
                autoResponsive
                columnWidth="8em"
                expandColumns
                expandFields
            >
                <FormItem children={
                    <TextField
                        label="token"
                        value={token.value}
                        onValueChanged={(e) => (token.value = e.detail.value)}
                        className={styles.text_full}
                    />
                }/>
                <FormItem children={
                    <Button theme={ElementStylingTypes.TERTIARY_ICON} onClick={() => {
                        if (invitation?.id) {
                            UserEndpoint.useToken(invitation.id, token.value)
                                .then(() => {
                                    Notify.success('Logged in!');
                                })
                                .catch(() => Notify.error('Invalid invitation and/or token'));
                        }
                    }}>
                        <Key size={24}/>
                    </Button>
                }/>
            </FormLayout>
        </HorizontalLayout>
    );
}