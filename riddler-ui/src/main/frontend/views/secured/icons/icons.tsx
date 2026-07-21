import {Icon} from "@vaadin/react-components";

// CRITICAL: You must import the iconset for the icons to render
import '@vaadin/icons';
import {useEffect, useState} from "react";
import {UiService} from "Frontend/generated/endpoints";
import {AbacusIcon} from "Frontend/components/ui/icons";
import {Notify} from "Frontend/util";

export default function IconsView() {
    const [icons, setIcons] = useState<string[]>([]);

    useEffect(() => {
        UiService.icons()
            .then(setIcons)
            .catch(err => Notify.error('Could not retrieve the icons {}', err));
    }, []);
    return (
        <div>
            <AbacusIcon/>
            {icons.map((iconName) => (
                <div key={iconName}>
                    <Icon icon={iconName}/>
                    <span>{iconName}</span>
                </div>
            ))}
        </div>
    );
}