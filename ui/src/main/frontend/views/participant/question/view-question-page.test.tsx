import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {beforeEach, describe, expect, it, vi} from 'vitest';
import ViewQuestionPage from 'Frontend/views/participant/question/view-question-page';

const mockTrack = vi.fn();
vi.mock('./useActivityTracker', () => ({
    useActivityTracker: () => ({track: mockTrack}),
}));

vi.mock('react-router-dom', () => ({
    useParams: () => ({id: 'question-123'}),
}));

vi.mock('@vaadin/hilla-react-signals', () => ({
    useSignal: (initialValue: string) => {
        return {value: initialValue};
    },
}));

const mockGetEndpoint = vi.fn();
vi.mock('Frontend/generated/endpoints', () => ({
    ParticipantEndpoint: {
        get: (...args: any[]) => mockGetEndpoint(...args),
    },
}));

vi.mock('Frontend/generated/Notify', () => ({
    Notify: {
        error: vi.fn(),
    },
}));

describe('ViewQuestionPage Component', () => {
    beforeEach(() => {
        vi.clearAllMocks();
    });

    it('should fetch and render an open-ended question and track typing activity', async () => {
        const user = userEvent.setup();

        mockGetEndpoint.mockResolvedValue({
            id: 'question-123',
            question: 'What is your favorite programming language?',
            open: true,
            single_choice: false,
            multiple_choice: false,
        });

        render(<ViewQuestionPage/>);

        expect(mockGetEndpoint).toHaveBeenCalledWith('question-123');

        const questionText = await screen.findByText('What is your favorite programming language?');
        expect(questionText).toBeDefined();

        const textArea = screen.getByLabelText('Your Answer');
        expect(textArea).toBeDefined();

        await user.type(textArea, 'Java');

        expect(mockTrack).toHaveBeenCalledWith('INPUT', 'textarea-answer', 'Java');
    });

    it('should render correct badges for choice-based questions', async () => {
        mockGetEndpoint.mockResolvedValue({
            id: 'question-123',
            question: 'Select your preferred frameworks:',
            open: false,
            single_choice: true,
            multiple_choice: true,
        });

        render(<ViewQuestionPage/>);

        expect(await screen.findByText('single')).toBeDefined();
        expect(screen.getByText('multiple')).toBeDefined();
        expect(screen.queryByLabelText('Your Answer')).not.toBeDefined();
    });
});
