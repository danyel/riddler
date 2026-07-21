/*
    primary             Recommended for the most important action in a view
    tertiary            Recommended for lower-importance actions
    tertiary-inline     Recommended for embedding a Button as part of text content
    icon                Used to reduce the white space on either side of the icon
    small               Reduces the button size
    large               Increases the button size
    contrast            Recommended as an additional color option
    success             Recommended as an additional color option
    error               Recommended for dangerous or irreversible actions
    warning             Recommended for actions related to warnings
 */
export enum ElementStylingTypes {
    PRIMARY = 'primary',
    PRIMARY_ERROR = 'primary error',
    TERTIARY = 'tertiary',
    TERTIARY_ICON = 'tertiary icon',
    TERTIARY_ICON_RED = 'tertiary icon error',
    TERTIARY_ICON_BLUE = 'tertiary icon secondary',
    TERTIARY_INLINE = 'tertiary-inline',
    ICON = 'icon',
    SMALL = 'small',
    LARGE = 'large',
    CONTRAST = 'contrast',
    ERROR = 'primary error',
    SUCCESS = 'primary success',
    WARNING = 'primary warning'
}