import {Button, Card, HorizontalLayout, Upload, VerticalLayout} from "@vaadin/react-components";
import {ParticipantAdminEndpoint} from "Frontend/generated/endpoints";
// @ts-ignore
import styles from "Frontend/themes/riddler/common.module.css";
import {DynamicBase64Image} from "Frontend/components/ui/image/dynamic-image";
import Participant from "Frontend/generated/be/riddler/v1/participant/client/model/Participant";
import {useParams} from "react-router";
import {ModalType} from "Frontend/views/secured/participants/participant";
import Category from "Frontend/generated/be/riddler/v1/category/client/model/Category";
import {ElementStylingTypes} from "Frontend/constant";
import {File} from "lucide-react";
import {Notify} from "Frontend/util";

export default function ParticipantProfileDetail(
    {
        participant,
        setParticipant,
        openModal
    }:
    {
        participant: Participant,
        setParticipant: (participant: Participant) => void,
        openModal: (id: string, modalType: ModalType) => void,
    }
) {
    const params = useParams();

    const photoUploadI18n = {
        dropFiles: {one: 'Drop photo here...', many: 'Drop photos here...'},
        addFiles: {one: 'Upload Profile Photo', many: 'Upload Profile Photos'}
    };

    const cvUploadI18n = {
        dropFiles: {one: 'Drop CV PDF here...', many: 'Drop CV PDFs here...'},
        addFiles: {one: 'Upload CV Document (PDF)', many: 'Upload CV Documents (PDF)'}
    };

    function getCookie(name: string): string | null {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop()?.split(';').shift() || null;
        return null;
    }

    return (
        <VerticalLayout className={styles.full_width_layout} style={{alignItems: 'stretch'}}>
            <Card style={{width: '100%', padding: 'var(--lumo-space-l)', boxShadow: 'var(--lumo-box-shadow-s)'}}>
                <HorizontalLayout theme="spacing-xl"
                                  style={{alignItems: 'flex-start', width: '100%', flexWrap: 'wrap'}}>
                    <VerticalLayout theme="spacing-m" style={{width: 'auto', alignItems: 'center', minWidth: '160px'}}>
                        <div style={{position: 'relative'}}>
                            <DynamicBase64Image
                                rawBase64={participant?.photo}
                                altText={`${participant?.first_name}'s Profile Photo`}
                                width="130"
                                height="130"
                                style={{
                                    borderRadius: '50%',
                                    border: '3px solid var(--lumo-primary-color-10pct)',
                                    boxShadow: 'var(--lumo-box-shadow-xs)'
                                }}
                            />
                        </div>
                        <Upload
                            accept="image/*"
                            target={`/v1/participants/${params.id!!}/photo`}
                            maxFileSize={20000000}
                            nodrop
                            i18n={photoUploadI18n}
                            onUploadBefore={(e: any) => {
                                const csrfToken = getCookie('XSRF-TOKEN');
                                if (csrfToken) {
                                    e.detail.xhr.setRequestHeader('X-XSRF-TOKEN', csrfToken);
                                }
                            }}
                            onUploadSuccess={() => {
                                ParticipantAdminEndpoint.findById(params.id!!)
                                    .then(setParticipant)
                                    .catch(err => Notify.error('Could not find the participant {}', err));
                            }}
                            style={{scale: '0.9'}}
                        />
                    </VerticalLayout>
                    <VerticalLayout theme="spacing-m" style={{flexGrow: 1, minWidth: '280px'}}>
                        <VerticalLayout theme="spacing-xs" style={{
                            borderBottom: '1px solid var(--lumo-contrast-10pct)',
                            paddingBottom: 'var(--lumo-space-m)',
                            width: '100%'
                        }}>
                            <h2 style={{
                                margin: 0,
                                fontWeight: 600,
                                color: 'var(--lumo-heading-text-color)',
                                fontSize: 'var(--lumo-font-size-xxl)'
                            }}>
                                {participant?.first_name} {participant?.last_name}
                            </h2>
                            <div style={{
                                color: 'var(--lumo-secondary-text-color)',
                                fontSize: 'var(--lumo-font-size-m)',
                                fontFamily: 'var(--lumo-font-family)'
                            }}>
                                {participant?.email_address}
                            </div>
                            {participant?.categories && participant.categories.length > 0 && (
                                <HorizontalLayout
                                    theme="spacing-s"
                                    style={{
                                        flexWrap: 'wrap',
                                        marginTop: 'var(--lumo-space-s)',
                                        marginBottom: 'var(--lumo-space-xs)'
                                    }}
                                >
                                    {participant.categories.map((category: Category) => (
                                        <span
                                            key={`category_${category.id}`}
                                            style={{
                                                background: 'var(--lumo-primary-color-10pct)',
                                                color: 'var(--lumo-primary-text-color)',
                                                fontSize: 'var(--lumo-font-size-xs)',
                                                fontWeight: 'bold',
                                                padding: 'var(--lumo-space-xs) var(--lumo-space-s)',
                                                borderRadius: '20px',
                                                whiteSpace: 'nowrap'
                                            }}
                                        >
                                            {category.name}
                                        </span>
                                    ))}
                                </HorizontalLayout>
                            )}
                        </VerticalLayout>
                        <VerticalLayout theme="spacing-s" style={{
                            width: '100%',
                            background: 'var(--lumo-contrast-5pct)',
                            padding: 'var(--lumo-space-m)',
                            borderRadius: 'var(--lumo-base-border-radius)'
                        }}>
                            <div style={{
                                fontSize: 'var(--lumo-font-size-s)',
                                fontWeight: 600,
                                color: 'var(--lumo-secondary-text-color)',
                                textTransform: 'uppercase',
                                letterSpacing: '0.05em'
                            }}>
                                Curriculum Vitae
                            </div>

                            <HorizontalLayout theme="spacing-m" style={{
                                alignItems: 'center',
                                width: '100%',
                                justifyContent: 'space-between',
                                flexWrap: 'wrap',
                                gap: 'var(--lumo-space-s)'
                            }}>
                                <HorizontalLayout theme="spacing-s" style={{alignItems: 'center'}}>
                                    <Button
                                        key={`view_cv_button_${params.id}`}
                                        theme={ElementStylingTypes.TERTIARY_ICON}
                                        disabled={!participant?.cv}
                                        onClick={() => {
                                            openModal(participant.id, 'CV');
                                        }}
                                    >
                                        <File size={24}/>
                                    </Button>
                                    <span style={{
                                        fontSize: 'var(--lumo-font-size-xs)',
                                        fontWeight: 'bold',
                                        padding: '4px 10px',
                                        borderRadius: 'var(--lumo-base-border-radius)',
                                        background: participant?.cv ? 'var(--lumo-success-color-10pct)' : 'var(--lumo-error-color-10pct)',
                                        color: participant?.cv ? 'var(--lumo-success-text-color)' : 'var(--lumo-error-text-color)'
                                    }}>
                                        {participant?.cv ? "AVAILABLE" : "MISSING"}
                                    </span>
                                </HorizontalLayout>
                                <Upload
                                    accept="application/pdf"
                                    target={`/v1/participants/${params.id!!}/cv`}
                                    maxFileSize={20000000}
                                    nodrop
                                    key={`cv_upload_${params.id}`}
                                    i18n={cvUploadI18n}
                                    onUploadBefore={(e: any) => {
                                        const csrfToken = getCookie('XSRF-TOKEN');
                                        if (csrfToken) {
                                            e.detail.xhr.setRequestHeader('X-XSRF-TOKEN', csrfToken);
                                        }
                                    }}
                                    onUploadSuccess={() => {
                                        ParticipantAdminEndpoint.findById(params.id!!)
                                            .then(setParticipant)
                                            .catch(err => Notify.error('Could not find the participant {}', err));
                                    }}
                                    style={{margin: 0}}
                                />
                            </HorizontalLayout>
                        </VerticalLayout>
                    </VerticalLayout>
                </HorizontalLayout>
            </Card>
        </VerticalLayout>
    );
}
