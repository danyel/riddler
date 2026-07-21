import {type ComponentType, ReactElement, useEffect, useMemo, useState} from "react";
import {Button, Grid, GridColumn, HorizontalLayout} from "@vaadin/react-components";
// @ts-ignore
import styles from "Frontend/themes/riddler/common.module.css";
import type {GridBodyReactRendererProps} from "@vaadin/react-components/renderers/grid.d.ts";
import Empty from "Frontend/components/ui/empty/empty";

export interface ColumnName {
    path: string;
    renderer?: ComponentType<GridBodyReactRendererProps<any>> | null;
    flexGrow?: number;
    width?: string;
    header?: string;
}

export default function RiddlerTable(
    {
        elements,
        columnNames,
        emptyMessage,
        helperMessage,
        actionButtons,
        pageSize = 10 // Added a default page size config
    }:
    {
        elements: any[],
        columnNames: ColumnName[],
        emptyMessage: string,
        helperMessage: string,
        actionButtons?: ({item}: { item: any }) => ReactElement<any>,
        pageSize?: number
    }
) {
    const [currentPage, setCurrentPage] = useState(0);
    const totalPages = Math.ceil(elements.length / pageSize);
    const paginatedElements = useMemo(() => {
        const start = currentPage * pageSize;
        const end = start + pageSize;
        return elements.slice(start, end);
    }, [elements, currentPage, pageSize]);

    useEffect(() => {
        if (currentPage >= totalPages && totalPages > 0) {
            setCurrentPage(totalPages - 1);
        }
    }, [elements.length, totalPages, currentPage]);

    return (
        <>
            <HorizontalLayout className={styles.full_width_layout}>
                {elements.length > 0 ? (
                    <Grid items={paginatedElements} allRowsVisible={true}>
                        {
                            columnNames.map(columnName => (
                                <GridColumn header={columnName.header} width={columnName.width}
                                            flexGrow={columnName.flexGrow} key={columnName.path} path={columnName.path}
                                            renderer={columnName.renderer}/>
                            ))
                        }
                        {actionButtons && (<GridColumn header={'Actions'} width="200px"
                                                       flexGrow={0} renderer={actionButtons}/>)}
                    </Grid>
                ) : (
                    <Empty emptyMessage={emptyMessage} helperMessage={helperMessage}/>
                )}
            </HorizontalLayout>

            {
                totalPages > 1 && (
                    <HorizontalLayout className={styles.full_width_layout}>
                        <Button
                            disabled={currentPage === 0}
                            onClick={() => setCurrentPage(p => p - 1)}
                        >
                            Previous
                        </Button>
                        <span>
                        Page <strong>{currentPage + 1}</strong> of {totalPages}
                    </span>
                        <Button
                            disabled={currentPage >= totalPages - 1}
                            onClick={() => setCurrentPage(p => p + 1)}
                        >
                            Next
                        </Button>
                    </HorizontalLayout>
                )
            }
        </>
    );
}