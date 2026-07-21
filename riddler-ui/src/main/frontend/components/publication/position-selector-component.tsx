import {ComboBox, ComboBoxCustomValueSetEvent, ComboBoxValueChangedEvent} from "@vaadin/react-components";
import {useEffect, useRef, useState} from "react";
import {Notify} from "Frontend/util";
import Position from "Frontend/generated/be/riddler/v1/publication/client/model/Position";
import {PublicationsEndpoint} from "Frontend/generated/endpoints";

interface PositionSelectorProps {
    selectedPositionId: string | undefined;
    onPositionChange: (positionId: string | undefined) => void;
}

export function PositionSelector({selectedPositionId, onPositionChange}: PositionSelectorProps) {
    const [positions, setPositions] = useState<Position[]>([]);
    const [loading, setLoading] = useState(false);
    const currentTypedText = useRef<string>("");

    useEffect(() => {
        loadPositions();
    }, []);

    const loadPositions = () => {
        PublicationsEndpoint.getPositions()
            .then(setPositions)
            .catch(err => Notify.error('Could not find the positions {}', err));
    };

    const handleBlur = () => {
        const customText = currentTypedText.current.trim();
        processCustomValue(customText);
    };

    const handleSelectExisting = (e: ComboBoxValueChangedEvent) => {
        const selectedValue = e.detail.value;
        onPositionChange(selectedValue || undefined);
        if (selectedValue) {
            currentTypedText.current = "";
        }
    };

    const processCustomValue = (text: string) => {
        if (!text) return;

        const matchedExisting = positions.find(
            (item) => item.position?.toLowerCase() === text.toLowerCase()
        );

        if (matchedExisting) {
            onPositionChange(matchedExisting.id);
            currentTypedText.current = "";
            return;
        }

        setLoading(true);
        PublicationsEndpoint.createPosition({position: text})
            .then((position) => {
                Notify.success("Created new position: {} {}", position.id, position.position);
                setPositions((prev) => [...prev, position]);
                onPositionChange(position.id);
                currentTypedText.current = "";
            })
            .catch((error) => Notify.error("Failed to create new position option: {}", error))
            .finally(() => setLoading(false));
    };

    const handleInputChange = (e: any) => {
        currentTypedText.current = e.target.value || "";
    };

    const handleCreateCustom = (e: ComboBoxCustomValueSetEvent) => {
        const customText = e.detail.trim();
        if (!customText) return;

        setLoading(true);
        PublicationsEndpoint.createPosition({position: customText})
            .then((position) => {
                Notify.success("Created new position: {} {}", position.id, position.position);
                setPositions((prev) => [...prev, position]);
                onPositionChange(position.id);
                currentTypedText.current = "";
            })
            .catch((error) => Notify.error("Failed to create new position option: {}", error))
            .finally(() => setLoading(false));
    };

    return (
        <ComboBox
            label="Experience Position"
            placeholder="Select or type to create..."
            items={positions}
            itemLabelPath="position"
            itemValuePath="id"
            value={selectedPositionId || ''}
            disabled={loading}
            allowCustomValue={true}
            onCustomValueSet={handleCreateCustom}
            onValueChanged={handleSelectExisting}
            onBlur={handleBlur}
            onInput={handleInputChange}
        />
    );
}
