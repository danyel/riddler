import {useEffect, useRef} from 'react';
import {ActivityEndpoint} from 'Frontend/generated/endpoints';
import CreateActivity from "Frontend/generated/be/riddler/v1/activity/client/model/CreateActivity";

export function useActivityTracker(questionId: string) {
    const queue = useRef<CreateActivity[]>([]);

    // 1. Core tracking collector
    const track = (actionType: string, elementId: string, additionalData = '') => {
        const activity: CreateActivity = {
            question_id: questionId,
            action_type: actionType,
            element_id: elementId,
            timestamp: new Date().toISOString(),
            additional_data: additionalData
        };
        queue.current.push(activity);
    };

    // 2. Periodic background sync loop
    useEffect(() => {
        const flushQueue = async () => {
            if (queue.current.length === 0) return;
            const batchToSend = [...queue.current];
            queue.current = []; // Empty the active queue immediately to prevent race conditions

            try {
                await ActivityEndpoint.logActions(batchToSend);
            } catch (error) {
                console.error("Failed to sync user activities", error);
                queue.current = [...batchToSend, ...queue.current]; // Re-queue on network failures
            }
        };

        const interval = setInterval(flushQueue, 5000); // Sync data to server every 5 seconds

        // Flush remaining events when user leaves the page
        return () => {
            clearInterval(interval);
            if (queue.current.length > 0) {
                ActivityEndpoint.logActions(queue.current).catch(console.error);
            }
        };
    }, [questionId]);

    return {track};
}
