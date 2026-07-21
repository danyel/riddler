import {Button, ButtonProps} from "@vaadin/react-components/Button.js";
import {ReactNode} from "react";
import {CloseIcon, KeyIcon, NewsPaperIcon} from "Frontend/components/ui/icons";
import {ElementStylingTypes} from "Frontend/constant";
import {ArrowLeft, Ban, Glasses, Plus, RefreshCw, Save, Trash2} from "lucide-react";

export function CheckButton(props: ButtonDecorationProps) {
    return (
        <BaseButton
            icon={(<Plus size={24}/>)}
            onClick={props.onClick}
            theme={ElementStylingTypes.TERTIARY_ICON}
            disabled={props.disabled}
        />
    );
}

export function CloseButton(props: ButtonDecorationProps) {
    return (
        <BaseButton
            icon={CloseIcon()}
            onClick={props.onClick}
            theme={ElementStylingTypes.TERTIARY_ICON_RED}
            disabled={props.disabled}
        />
    );
}


export function BackButton(props: ButtonDecorationProps) {
    return (
        <BaseButton
            icon={<ArrowLeft size={24}/>}
            onClick={props.onClick}
            theme={ElementStylingTypes.TERTIARY_ICON_BLUE}
            disabled={props.disabled}
        />
    );
}

export function SaveButton(props: ButtonDecorationProps) {
    return (
        <BaseButton
            icon={(<Save size={24}/>)}
            onClick={props.onClick}
            theme={ElementStylingTypes.TERTIARY_ICON}
            disabled={props.disabled}
        />
    );
}

export function PlusButton(props: ButtonDecorationProps) {
    return (
        <BaseButton
            icon={(<Plus size={24}/>)}
            onClick={props.onClick}
            theme={ElementStylingTypes.TERTIARY_ICON}
            disabled={props.disabled}
        />
    );
}

export function CancelButton(props: ButtonDecorationProps, theme: string = ElementStylingTypes.TERTIARY_ICON) {
    return (
        <BaseButton
            icon={<Ban size={24}/>}
            onClick={props.onClick}
            theme={props.theme}
            disabled={props.disabled}
        />
    );
}

export function ViewDetailButton(props: ButtonDecorationProps) {
    return (
        <BaseButton
            icon={<Glasses/>}
            onClick={props.onClick}
            theme={ElementStylingTypes.TERTIARY_INLINE}
            disabled={props.disabled}
        />
    );
}

export function NewsPaperButton(props: ButtonDecorationProps) {
    return (
        <BaseButton
            icon={NewsPaperIcon()}
            onClick={props.onClick}
            theme={ElementStylingTypes.TERTIARY_INLINE}
            disabled={props.disabled}
        />
    );
}

export function BanButton(props: ButtonDecorationProps) {
    return (
        <BaseButton
            icon={<Ban size={24}/>}
            onClick={props.onClick}
            theme={ElementStylingTypes.TERTIARY_ICON_RED}
            disabled={props.disabled}
        />
    );
}

export function GenerateToken(props: ButtonDecorationProps) {
    return (
        <BaseButton
            icon={KeyIcon()}
            onClick={props.onClick}
            theme={ElementStylingTypes.TERTIARY_INLINE}
            disabled={props.disabled}
        />
    );
}

export function RefreshButton(props: ButtonDecorationProps) {
    return (
        <BaseButton
            icon={<RefreshCw size={24}/>}
            onClick={props.onClick}
            theme={ElementStylingTypes.TERTIARY_ICON}
            disabled={props.disabled}
        />
    );
}

export function DeleteButton(props: ButtonDecorationProps) {
    return (
        <BaseButton
            icon={<Trash2 size={24}/>}
            onClick={props.onClick}
            theme={ElementStylingTypes.TERTIARY_ICON_RED}
            disabled={props.disabled}
        />
    );
}

interface ButtonDecorationProps extends ButtonProps {
    onClick: () => void;
    disabled?: boolean;
}

interface BaseButtonProps extends ButtonProps {
    icon: ReactNode;
    onClick: () => void;
}

function BaseButton({icon, children, ...rest}: BaseButtonProps) {
    return (
        <Button {...rest}>
            {icon}
            {children}
        </Button>
    );
}