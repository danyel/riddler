import {Dialog} from "@vaadin/react-components";
import {DialogProps} from "@vaadin/react-components/Dialog";
import {ReactNode} from "react";

interface RiddlerModalProps extends DialogProps {
    content: ReactNode;
}

export default function RiddlerModal(props: RiddlerModalProps) {
    return (
        <Dialog
            headerTitle={props.headerTitle}
            opened={props.opened}
            onClosed={props.onClosed}
            footer={props.footer}
        >
            <div style={{
                width: '90vw',
                height: 'calc(90vh - 120px)',
                display: 'flex',
                flexDirection: 'column',
                boxSizing: 'border-box'
            }}>
                {props.content}
            </div>
        </Dialog>
    )
}