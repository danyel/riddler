import {LOGGER} from "./defaultLog";

import {Authentication} from '@vaadin/hilla-react-auth';
import UserInfo from "Frontend/generated/be/riddler/v1/security/client/model/UserInfo";

export class Authorisation {
    private static instance: Authentication<UserInfo> | null = null;
    public static ADMIN = 'ROLE_ADMIN';
    public static USER = 'ROLE_USER';
    public static PARTICIPANT = 'ROLE_PARTICIPANT';

    static initialize(authHook: Authentication<UserInfo>) {
        this.instance = authHook;
    }

    static hasRole(roles: [string, ...string[]]): boolean {
        if (!this.instance) {
            LOGGER.error("Authorisation called before initialization!");
            return false;
        }

        return this.instance.hasAccess({rolesAllowed: roles});
    }
}
