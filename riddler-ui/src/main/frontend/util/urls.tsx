import BookmarkType from "Frontend/generated/be/riddler/v1/settings/model/BookmarkType";
import {Strings} from "Frontend/util/strings";

export class Urls {
    static makePath(bookmarkType: BookmarkType, id?: string): string {
        if (Strings.isNotUndefined(id)) {
            return `/${bookmarkType.toLocaleLowerCase()}/${id}`;
        }
        return `/${bookmarkType.toLocaleLowerCase()}`;
    }

    static isSamePathForBookmark(path: string, bookmarkType: BookmarkType, id?: string): boolean {
        return path === this.makePath(bookmarkType, id);
    }

    static isSamePath(path?: string, toCompare?: string): boolean {
        return path === toCompare;
    }

    static containsPath(path: string, bookmarkType: BookmarkType): boolean {
        return path.startsWith(`/${bookmarkType.toLocaleLowerCase()}`);
    }

    static isCurrentPage(path: string): boolean {
        return location.hash === path;
    }
}