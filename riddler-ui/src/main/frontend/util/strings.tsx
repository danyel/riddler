export class Strings {
    static EMPTY: string = '';

    static isEmpty(value?: string): boolean {
        return this.isNull(value) || this.isUndefined(value);
    }

    static isNotEmpty(value?: string): boolean {
        return this.isNotNull(value) && this.isNotUndefined(value) && value?.trim() !== '';
    }

    static isBlank(value?: string): boolean {
        return this.isNotNull(value) && value?.trim() === '';
    }

    static isNotNull(value?: string): boolean {
        return !this.isNull(value);
    }

    static isNull(value?: string): boolean {
        return !this.isUndefined(value) && value === null;
    }

    static isUndefined(value?: string): boolean {
        return value === undefined
    }

    static isNotUndefined(id?: string) {
        return !this.isUndefined(id);
    }

    static format(template: string, data: any[]): string {
        let index = 0;
        return template.replace(/{}/g, () => {
            if (index < data.length) {
                const item = data[index++];
                return typeof item === 'object' ? JSON.stringify(item) : item;
            }
            return '{}';
        });
    }
}