// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';

import {AppLayout} from '@vaadin/react-components/AppLayout.js';
import {Outlet, useNavigate} from 'react-router';
import Navigation from "Frontend/components/navigation/Navigation";
import {VerticalLayout} from "@vaadin/react-components";
import {useEffect, useState} from "react";
import Footer from "Frontend/components/footer/footer.component";
import {SettingsStateProvider, useSettingsState} from "Frontend/views/secured/settings-context-provider";
import {SettingsEndpoint} from "Frontend/generated/endpoints";
import {SideBar} from "Frontend/components";
import {Navigate} from "Frontend/util/navigate";
import {Authorisation} from "Frontend/util/authorisation";
import {useAuth} from "Frontend/auth";
import {Notify} from "Frontend/util";

function MainLayoutContent() {
    const [isOpened, setIsOpened] = useState(true);
    const {setSettings} = useSettingsState();
    const navigate = useNavigate();
    const auth = useAuth();

    useEffect(() => {
        SettingsEndpoint.getSettings()
            .then(setSettings)
            .catch(err => Notify.error('Could not retreive the setting {}', err));
    }, []);

    useEffect(() => {
        Navigate.initialize(navigate);
    }, [navigate]);

    useEffect(() => {
        Authorisation.initialize(auth);
    }, [auth]);

    return (
        <AppLayout primarySection="navbar"
                   drawerOpened={isOpened}
                   onDrawerOpenedChanged={({detail}) => setIsOpened(detail.value)}
        >
            <Navigation noMenu={false}/>
            <section slot={'drawer'} className={styles.side_bar}>
                <SideBar/>
            </section>
            <main className={`${styles.content_container} ${isOpened ? 'drawer-is-open' : 'drawer-is-closed'}`}>
                <VerticalLayout className={styles.content}>
                    <Outlet/>
                    <Footer/>
                </VerticalLayout>
            </main>
        </AppLayout>
    );
}

export default function MainLayout() {
    return (
        <SettingsStateProvider>
            <MainLayoutContent/>
        </SettingsStateProvider>
    )
}