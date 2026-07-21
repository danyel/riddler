import BookmarkType from "Frontend/generated/be/riddler/v1/settings/model/BookmarkType";
import {Urls} from "Frontend/util/urls";
import {NavigateFunction} from "react-router";
import {LOGGER} from "./defaultLog";

export class Navigate {
    private static instance: NavigateFunction | null = null;

    static initialize(navigateFn: NavigateFunction) {
        this.instance = navigateFn;
    }

    static to(bookmarkType: BookmarkType, id?: string) {
        if (!this.instance) {
            LOGGER.error("Navigate utility called before initialization!");
            return;
        }
        this.instance(Urls.makePath(bookmarkType, id));
    }

    static path(path: string) {
        if (!this.instance) {
            LOGGER.error("Navigate utility called before initialization!");
            return;
        }
        this.instance(path);
    }
}