export class Collections {
    static putElementAtIndex<T>(object: T, elements: T[], index: number): T[] {
        const retVal = [];
        for (let i = 0; i < elements.length; i++) {
            if (index === i) {
                retVal.push(object);
            } else {
                let mutatedIndex = i;
                if (index > mutatedIndex) {
                    mutatedIndex++;
                }
                retVal.push(elements[mutatedIndex]);
            }
        }

        return retVal;
    }

    static removeElement<T>(elements: T[], predicate: (value: T, index: number, array: T[]) => unknown) {
        return elements.filter(predicate);
    }
}