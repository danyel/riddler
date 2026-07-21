import {CSSProperties, useEffect, useState} from "react";

interface DynamicBase64ImageProps {
    rawBase64: string | undefined;
    altText?: string;
    width?: string;
    height?: string;
    style?: CSSProperties;
}

export function DynamicBase64Image({
                                       rawBase64,
                                       altText = "Image Preview",
                                       width = "120",
                                       height = "120",
                                       style
                                   }: DynamicBase64ImageProps) {
    const [imageSrc, setImageSrc] = useState<string>("");

    useEffect(() => {
        if (!rawBase64) {
            setImageSrc("");
            return;
        }

        // 1. Scrub spacing
        let cleanBase64 = rawBase64.replace(/\s/g, '');

        // 2. 🔥 Padding Defense Guard: Ensure length is a clean multiple of 4
        while (cleanBase64.length % 4 !== 0) {
            cleanBase64 += '=';
        }

        // 3. Match signatures
        const signatures: Record<string, string> = {
            "/9j/": "image/jpeg",
            "iVBORw0KGgo": "image/png"
        };

        let detectedMimeType = "image/jpeg";
        for (const key in signatures) {
            if (cleanBase64.startsWith(key)) {
                detectedMimeType = signatures[key];
                break;
            }
        }
        setImageSrc(`data:${detectedMimeType};base64,${cleanBase64}`);
    }, [rawBase64]);

    if (!imageSrc) {
        return (
            <div style={{
                width: `${width}px`,
                height: `${height}px`,
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                background: 'var(--lumo-contrast-5pct)',
                borderRadius: 'var(--lumo-base-border-radius)',
                border: '1px solid var(--lumo-contrast-10pct)',
                color: 'var(--lumo-disabled-text-color)',
                fontSize: 'var(--lumo-font-size-s)'
            }}>
                No Image
            </div>
        );
    }

    return (
        <img
            width={width}
            height={height}
            src={imageSrc}
            alt={altText}
            style={{
                borderRadius: 'var(--lumo-base-border-radius)',
                objectFit: 'cover',
                border: '1px solid var(--lumo-contrast-10pct)',
                ...style
            }}
        />
    );
}
