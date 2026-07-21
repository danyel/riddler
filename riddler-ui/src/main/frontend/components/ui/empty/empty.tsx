export default function Empty({emptyMessage, helperMessage}: { emptyMessage: string, helperMessage: string }) {
    return (
        <div style={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            height: '200px',
            width: '100%',
            background: 'var(--lumo-contrast-5pct)',
            borderRadius: 'var(--lumo-base-border-radius)',
            border: '1px dashed var(--lumo-contrast-20pct)',
            gap: 'var(--lumo-space-xs)'
        }}>
                <span style={{
                    color: 'var(--lumo-secondary-text-color)',
                    fontWeight: 500,
                    fontSize: 'var(--lumo-font-size-m)'
                }}>
                    {emptyMessage}
                </span>
            <span style={{color: 'var(--lumo-disabled-text-color)', fontSize: 'var(--lumo-font-size-s)'}}>
                    {helperMessage}
                </span>
        </div>
    );
}