// @ts-ignore
import styles from "Frontend/themes/riddler/common.module.css";
import Publication from "Frontend/generated/be/riddler/v1/publication/client/model/Publication";

export default function PublicationDetail({publication}: { publication: Publication }) {
    return publication ? (
        <>
            <div style={{colspan: 2, marginTop: 'var(--lumo-space-m)'}}>
                <div style={{
                    fontSize: 'var(--lumo-font-size-s)',
                    fontWeight: 500,
                    color: 'var(--lumo-secondary-text-color)',
                    marginBottom: 'var(--lumo-space-xs)'
                }}>
                    Title
                </div>

                <div className={styles.modal_text_ro}>
                    {publication.title || (
                        <span style={{color: 'var(--lumo-disabled-text-color)', fontStyle: 'italic'}}>
                                        No title available for this publication.
                                    </span>
                    )}
                </div>
            </div>
            <div style={{colspan: 2, marginTop: 'var(--lumo-space-m)'}}>
                <div style={{
                    fontSize: 'var(--lumo-font-size-s)',
                    fontWeight: 500,
                    color: 'var(--lumo-secondary-text-color)',
                    marginBottom: 'var(--lumo-space-xs)'
                }}>
                    Description
                </div>

                <div className={styles.modal_text_area_ro}>
                    {publication.description || (
                        <span style={{color: 'var(--lumo-disabled-text-color)', fontStyle: 'italic'}}>
                                        No description available for this publication.
                                    </span>
                    )}
                </div>
            </div>
            <div style={{colspan: 2, marginTop: 'var(--lumo-space-m)'}}>
                <div style={{
                    fontSize: 'var(--lumo-font-size-s)',
                    fontWeight: 500,
                    color: 'var(--lumo-secondary-text-color)',
                    marginBottom: 'var(--lumo-space-xs)'
                }}>
                    Description
                </div>

                <div className={styles.modal_text_area_ro}>
                    {publication.proposal ||
                        (
                            <span style={{color: 'var(--lumo-disabled-text-color)', fontStyle: 'italic'}}>
                                            No proposal available for this publication.
                                        </span>
                        )
                    }
                </div>
            </div>
        </>
    ) : (<></>);
}