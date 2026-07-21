import {ComboBox, ComboBoxCustomValueSetEvent, ComboBoxValueChangedEvent} from "@vaadin/react-components";
import {useEffect, useRef, useState} from "react";
import {Notify} from "Frontend/util";
import Level from "Frontend/generated/be/riddler/v1/publication/client/model/Level";
import {PublicationsEndpoint} from "Frontend/generated/endpoints";

interface LevelSelectorProps {
    selectedLevelId: string | undefined;
    onLevelChange: (levelId: string | undefined) => void;
}

export function LevelSelector({selectedLevelId, onLevelChange}: LevelSelectorProps) {
    const [levels, setLevels] = useState<Level[]>([]);
    const [loading, setLoading] = useState(false);
    const currentTypedText = useRef<string>("");

    useEffect(() => {
        loadLevels();
    }, []);

    const loadLevels = () => {
        PublicationsEndpoint.getLevels()
            .then(setLevels)
            .catch(err => Notify.error('Could not find the levels {}', err));
    };

    const handleBlur = () => {
        const customText = currentTypedText.current.trim();
        processCustomValue(customText);
    };
    const handleSelectExisting = (e: ComboBoxValueChangedEvent) => {
        const selectedValue = e.detail.value;
        onLevelChange(selectedValue || undefined);
        if (selectedValue) {
            currentTypedText.current = "";
        }
    };

    const processCustomValue = (text: string) => {
        if (!text) return;

        const matchedExisting = levels.find(
            (item) => item.level?.toLowerCase() === text.toLowerCase()
        );

        if (matchedExisting) {
            onLevelChange(matchedExisting.id);
            currentTypedText.current = "";
            return;
        }

        setLoading(true);
        PublicationsEndpoint.createLevel({level: text})
            .then((level) => {
                Notify.success("Created level: {} {}", level.id, text);
                setLevels((prev) => [...prev, level]);
                onLevelChange(level.id);
                currentTypedText.current = "";
            })
            .catch((err) => Notify.error("Failed to create new level option: {}", err))
            .finally(() => setLoading(false));
    };

    const handleInputChange = (e: any) => {
        currentTypedText.current = e.target.value || "";
    };

    const handleCreateCustom = async (e: ComboBoxCustomValueSetEvent) => {
        const customText = e.detail.trim();
        if (!customText) return;

        setLoading(true);
        PublicationsEndpoint.createLevel({level: customText})
            .then((level) => {
                Notify.success("Created level: {} {}", level.id, customText);
                setLevels((prev) => [...prev, level]);
                onLevelChange(level.id);
                currentTypedText.current = "";
            })
            .catch((error) => Notify.error("Failed to create new level option: {}", error))
            .finally(() => setLoading(false));
    };

    return (
        <ComboBox
            label="Experience Level"
            placeholder="Select or type to create..."
            items={levels}
            itemLabelPath="level"      // Field name from your LevelEntity
            itemValuePath="id"        // Field value mapped back to your parent model
            value={selectedLevelId || ''}
            disabled={loading}
            allowCustomValue={true}   // 🔥 Critical: Enables typing new records
            onCustomValueSet={handleCreateCustom}
            onValueChanged={handleSelectExisting}
            onBlur={handleBlur}
            onInput={handleInputChange}
        />
    );
}
