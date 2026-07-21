import {createContext, ReactNode, useContext, useState} from "react";
import Settings from "Frontend/generated/be/riddler/v1/settings/model/Settings";

interface SettingsState {
    settings: Settings;
    setSettings: (settings: Settings) => void;
}

const SettingsContext = createContext<SettingsState | undefined>(undefined);

export function SettingsStateProvider({children}: { children: ReactNode }) {
    const [settings, setSettings] = useState<Settings>({username: '', roles: [], bookmarks: []});
    return (
        <SettingsContext.Provider value={{settings, setSettings}}>
            {children}
        </SettingsContext.Provider>
    );
}

export function useSettingsState() {
    const context = useContext(SettingsContext);
    if (!context) {
        throw new Error("useSettingsState must be used within an <SettingsStateProvider>");
    }
    return context;
}
