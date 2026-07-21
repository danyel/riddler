// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';
import {DrawerToggle} from "@vaadin/react-components/DrawerToggle";
import {Icon, MenuBar, MenuBarItem, MenuBarItemSelectedEvent} from "@vaadin/react-components";
import {CSSProperties} from "react";
import {useSettingsState} from "Frontend/views/secured/settings-context-provider";
import {Navigate} from "Frontend/util/navigate";
import {LOGGER} from "Frontend/util";

export interface NavigationProperties {
    noMenu: boolean
}

interface CustomMenuItem extends MenuBarItem {
    path?: string;
    children?: CustomMenuItem[];
}

function renderItemComponent(iconName: string, text: string) {
    const iconStyle: CSSProperties = {
        width: '1.25rem',
        height: '1.25rem',
        marginRight: 'var(--vaadin-gap-s)',
    };

    return (
        <div style={{display: 'flex', alignItems: 'center', cursor: 'pointer'}}>
            <Icon icon={`vaadin:${iconName}`} style={iconStyle}/>
            {text !== '' && (text)}
        </div>
    );
}

export default function Navigation(props: NavigationProperties) {
    const {settings} = useSettingsState();
    LOGGER.debug("Navigation settings[{}]", settings);

    const items: CustomMenuItem[] = [
        {
            component: renderItemComponent('cog', settings.username),
            children: [
                {
                    component: renderItemComponent('user', 'Profile'),
                    path: '/profile'
                }
            ],
        }
    ];

    const handleMenuSelection = (event: MenuBarItemSelectedEvent) => {
        const selectedItem = event.detail.value as CustomMenuItem;

        if (selectedItem && selectedItem.path) {
            Navigate.path(selectedItem.path);
        }
    };

    return (
        <div slot="navbar" className={styles.navbar_container}>
            {
                !props.noMenu && (
                    <DrawerToggle/>
                )
            }
            <h2 className={styles.banner}>Riddler</h2>

            <div className={styles.profile_menu}>
                <MenuBar
                    theme="icon"
                    items={items}
                    onItemSelected={handleMenuSelection} // Handles navigation cleanly when any leaf is clicked
                />
            </div>
        </div>
    );
}
