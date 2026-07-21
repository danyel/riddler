// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';

import {AppLayout} from '@vaadin/react-components/AppLayout.js';
import {Outlet} from 'react-router';
import {VerticalLayout} from "@vaadin/react-components";
import Footer from "Frontend/components/footer/footer.component";

export default function LoginLayout() {
    return (
        <AppLayout>
            <div slot="navbar" className={styles.navbar_container}>
                <h2 className={styles.banner}>Riddler</h2>
            </div>
            <main className={`${styles.content_container} drawer-is-closed`}>
                <VerticalLayout className={styles.content}>
                    <Outlet/>
                </VerticalLayout>
                <Footer/>
            </main>
        </AppLayout>
    );
}