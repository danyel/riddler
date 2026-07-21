export class Objects {
    static isNotNull(object: any) {
        return !this.isNull(object);
    }

    static isUndefined(object: any) {
        return object === undefined;
    }

    static isNull(object: any) {
        return object === null;
    }

    static isNotUndefined(object: any) {
        return !this.isUndefined(object);
    }

    static isNotNullAndNotUndefined(object: any) {
        return this.isNotNull(object) && this.isNotUndefined(object);
    }

    static getOrElse(object: any, defaultValue: any): any {
        return this.isNotNullAndNotUndefined(object) ? object : defaultValue;
    }

    static isEqual(object: any, other: any) {
        return object === other;
    }

    static isNotEqual(object: any, other: any) {
        return !this.isEqual(object, other);
    }
}