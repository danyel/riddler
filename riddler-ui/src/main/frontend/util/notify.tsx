import {Notification} from "@vaadin/react-components";
import {ElementStylingTypes} from "Frontend/constant";
import {Strings} from "Frontend/util/strings";
import {LOGGER} from "./defaultLog";

export class Notify {
    static success(message: string, ...data: any[]) {
        Notification.show(Strings.format(message, data), {
            position: 'top-end',
            theme: ElementStylingTypes.SUCCESS
        });
        LOGGER.debug(message, ...data);
    }

    static error(message: string, ...data: any[]) {
        Notification.show(Strings.format(message, data), {
            position: 'top-end',
            theme: ElementStylingTypes.ERROR
        });
        LOGGER.error(message, ...data);
    }

    static warn(message: string, ...data: any[]) {
        Notification.show(Strings.format(message, data), {
            position: 'top-end',
            theme: ElementStylingTypes.WARNING
        });
        LOGGER.warn(message, ...data);
    }
}