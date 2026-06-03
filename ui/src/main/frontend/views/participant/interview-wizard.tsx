// frontend/views/InterviewWizardView.tsx
import React, {useEffect, useState} from 'react';
import {AnswerEndpoint, ParticipantInvitationEndpoint} from 'Frontend/generated/endpoints';
import {ArrowRight, CheckCircle, HelpCircle} from 'lucide-react';
import Invitation from "Frontend/generated/be/riddler/v1/invitation/client/model/Invitation";
import Question from "Frontend/generated/be/riddler/v1/question/client/model/Question";
import {LOGGER} from "Frontend/util";
import CreateAnswer from "Frontend/generated/be/riddler/v1/answer/client/model/CreateAnswer";
import CreateSolution from "Frontend/generated/be/riddler/v1/answer/client/model/CreateSolution";
import {useParams} from "react-router-dom";
import {useSignal} from "@vaadin/hilla-react-signals";
import ParticipantInvitationDetail
    from "Frontend/generated/be/riddler/v1/invitation/bff/model/ParticipantInvitationDetail";

interface ReviewComment {
    id: string;
    author: string;
    text: string;
    timestamp: string;
}

export default function InterviewWizardView() {
    const [invitation, setInvitation] = useState<Invitation | null>(null);
    const [questions, setQuestions] = useState<Question[]>([]);
    const [currentIndex, setCurrentIndex] = useState<number>(0);
    const [loading, setLoading] = useState<boolean>(true);
    const [submitting, setSubmitting] = useState<boolean>(false);
    const [completed, setCompleted] = useState<boolean>(false);
    const [localSelections, setLocalSelections] = useState<string[]>([]);
    const [allSubmittedAnswers, setAllSubmittedAnswers] = useState<Record<string, string[]>>({});
    const params = useParams();
    const invitationId = params.invitation_id;
    const [reviewComments, setReviewComments] = useState<Record<string, ReviewComment[]>>({});

    const handleAddComment = (questionId: string, commentText: string) => {
        const newComment: ReviewComment = {
            id: crypto.randomUUID(),
            author: "Reviewer", // Swap with actual logged-in user context
            text: commentText,
            timestamp: new Date().toLocaleTimeString([], {hour: '2-digit', minute: '2-digit'})
        };

        // Save locally or dispatch to your API endpoint here
        setReviewComments(prev => ({
            ...prev,
            [questionId]: [...(prev[questionId] || []), newComment]
        }));
    };
    useEffect(() => {
        if (!invitationId) return;
        ParticipantInvitationEndpoint.findById(invitationId)
            .then((participantInvitationDetail: ParticipantInvitationDetail) => {
                setInvitation(participantInvitationDetail.invitation);
                return ParticipantInvitationEndpoint.findByIds(participantInvitationDetail.invitation.questions);
            })
            .then((questionDetails) => {
                if (questionDetails) {
                    setQuestions(questionDetails);
                }
            })
            .catch((error) => {
                LOGGER.error("Error loading interview data: {}", error);
            })
            .finally(() => {
                setLoading(false);
            });
    }, [invitationId]);

    useEffect(() => {
        setLocalSelections([]);
    }, [currentIndex]);

    if (loading) {
        return <div style={styles.center}>Loading invitation payload...</div>;
    }

    if (questions.length === 0 || !invitation) {
        return <div style={styles.center}>No assessment questions found for this invitation.</div>;
    }

    const currentQuestion = questions[currentIndex];
    const isNextDisabled = (): boolean => {
        if (currentQuestion.review) {
            return false;
        }
        if (currentQuestion.open) {
            return localSelections.length === 0 || localSelections[0].trim() === '';
        }
        return localSelections.length === 0;
    };

    const handleNext = async () => {
        // if (currentQuestion.review) {
        //     setCompleted(true);
        //     return;
        // }

        if (!currentQuestion.id) return;

        setSubmitting(true);
        try {
            const solutionRecords: CreateSolution[] = localSelections.map(val => ({
                value: val,
                question_id: currentQuestion.id
            } as CreateSolution));

            const payload: CreateAnswer = {
                solutions: solutionRecords,
                invitation_id: invitation.id,
                question_id: currentQuestion.id
            };

            // Call the Hilla backend endpoint
            await AnswerEndpoint.create(payload);

            // Cache the answer locally so it can be reviewed on the final page
            setAllSubmittedAnswers(prev => ({
                ...prev,
                [currentQuestion.id!]: localSelections
            }));

            // Step forward in pagination
            if (currentIndex < questions.length - 1) {
                setCurrentIndex(prev => prev + 1);
            }
        } catch (err) {
            LOGGER.error("Failed to commit answer payload context: {}", err);
        } finally {
            setSubmitting(false);
        }
    };

    const handleRadioToggle = (value: string) => {
        setLocalSelections([value]);
    };

    const handleCheckboxToggle = (value: string, checked: boolean) => {
        if (checked) {
            setLocalSelections(prev => [...prev, value]);
        } else {
            setLocalSelections(prev => prev.filter(item => item !== value));
        }
    };

    const progressPercent = Math.round((currentIndex / questions.length) * 100);

    if (completed) {
        return (
            <div style={styles.successCard}>
                <CheckCircle size={64} color="#2ecc71"/>
                <h2 style={{color: '#2c3e50', margin: '20px 0 10px 0'}}>Interview Submitted</h2>
                <p style={{color: '#7f8c8d', margin: 0}}>Thank you! Your responses have been securely logged.</p>
            </div>
        );
    }

    return (
        <div style={styles.wizardContainer}>
            {/* Progress Bar View */}
            <div style={styles.progressHeader}>
                <span>Question {currentIndex + 1} of {questions.length}</span>
                <span>{progressPercent}% Complete</span>
            </div>
            <div style={styles.progressBarWrapper}>
                <div style={{...styles.progressBarInner, width: `${progressPercent}%`}}/>
            </div>

            {/* Question Layout Viewport */}
            <div style={styles.card}>
                <h4 style={styles.titleText}>{currentQuestion.title || "Interview Question"}</h4>
                <h3 style={styles.questionText}>
                    <HelpCircle size={20} style={{marginRight: '8px', verticalAlign: 'middle', color: '#3498db'}}/>
                    {currentQuestion.question} {currentQuestion.type}
                </h3>

                <div style={styles.inputArea}>
                    {/* 1. Open Text Field Type */}
                    {currentQuestion.open && (
                        <textarea
                            placeholder="Type your response here..."
                            value={localSelections[0] || ''}
                            onChange={(e) => setLocalSelections([e.target.value])}
                            style={styles.textarea}
                            rows={6}
                            disabled={submitting}
                        />
                    )}

                    {/* 2. Single Choice Selection Type */}
                    {currentQuestion.single_choice && currentQuestion.options?.map(opt => (
                        <label key={opt.value} style={styles.optionLabel}>
                            <input
                                type="radio"
                                name={currentQuestion.id}
                                checked={localSelections.includes(opt.value || '')}
                                onChange={() => handleRadioToggle(opt.value || '')}
                                style={styles.radioInput}
                                disabled={submitting}
                            />
                            {opt.value}
                        </label>
                    ))}

                    {/* 3. Multiple Choice Selection Type */}
                    {currentQuestion.multiple_choice && currentQuestion.options?.map(opt => (
                        <label key={opt.value} style={styles.optionLabel}>
                            <input
                                type="checkbox"
                                checked={localSelections.includes(opt.value || '')}
                                onChange={(e) => handleCheckboxToggle(opt.value || '', e.target.checked)}
                                style={styles.radioInput}
                                disabled={submitting}
                            />
                            {opt.value}
                        </label>
                    ))}

                    {/* 4. Complete Interview Summary Review Layout Node */}
                    {currentQuestion.review && (
                        <div style={styles.reviewWrapper}>
                            <h4 style={{margin: '0 0 15px 0', color: '#2c3e50'}}>Review Your Answers</h4>
                            {questions.filter(q => !q.review).map((q, idx) => {
                                // Track the text input for this specific question's comment field
                                const commentInput = useSignal('');

                                return (
                                    <div key={q.id} style={styles.mrContainer}>
                                        {/* The Question and Submitted Answers Row */}
                                        <div style={styles.reviewRow}>
                                            <strong style={{color: '#2c3e50', display: 'block', marginBottom: '4px'}}>
                                                {idx + 1}. {q.question}
                                            </strong>
                                            <div style={styles.reviewAnswer}>
                                                {allSubmittedAnswers[q.id!] && allSubmittedAnswers[q.id!].length > 0 ? (
                                                    allSubmittedAnswers[q.id!].map((ans, i) => (
                                                        <div key={i} style={{padding: '2px 0'}}>• {ans}</div>
                                                    ))
                                                ) : (
                                                    <em style={{color: '#e74c3c'}}>No answer provided</em>
                                                )}
                                            </div>
                                        </div>

                                        {/* The Merge Request-style Comment Thread */}
                                        <div style={styles.commentThread}>
                                            {/* Render existing comments for this question */}
                                            {reviewComments[q.id!]?.map((comment) => (
                                                <div key={comment.id} style={styles.commentBubble}>
                                                    <div style={styles.commentMeta}>
                                                        <strong>{comment.author}</strong>
                                                        <span style={styles.commentTime}>{comment.timestamp}</span>
                                                    </div>
                                                    <div style={styles.commentText}>{comment.text}</div>
                                                </div>
                                            ))}

                                            {/* Inline Comment Input Box */}
                                            <div style={styles.commentInputBox}>
                            <textarea
                                placeholder="Write a review comment..."
                                value={commentInput.value}
                                onChange={(e) => (commentInput.value = e.target.value)}
                                style={styles.textarea}
                            />
                                                <div style={styles.commentActions}>
                                                    <button
                                                        disabled={!commentInput.value.trim()}
                                                        onClick={() => {
                                                            handleAddComment(q.id!, commentInput.value);
                                                            commentInput.value = ''; // Clear text area on success
                                                        }}
                                                        style={styles.submitCommentBtn}
                                                    >
                                                        Add comment
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                );
                            })}
                        </div>
                    )}

                </div>

                {/* Footer Navigation (Next Only Layout) */}
                <div style={styles.actionFooter}>
                    <button onClick={handleNext} disabled={isNextDisabled() || submitting}
                            style={{
                                ...styles.nextBtn,
                                background: isNextDisabled() || submitting ? '#bdc3c7' : '#2ecc71',
                                cursor: isNextDisabled() || submitting ? 'not-allowed' : 'pointer'
                            }}>
                        {submitting ? "Saving..." : currentIndex === questions.length - 1 ? 'Finish Interview' : 'Next Question'}
                        <ArrowRight size={16} style={{marginLeft: '8px'}}/>
                    </button>
                </div>
            </div>
        </div>
    );
}

const styles: Record<string, React.CSSProperties> = {
    wizardContainer: {
        maxWidth: '700px',
        margin: '40px auto',
        padding: '0 20px',
        fontFamily: 'system-ui, -apple-system, sans-serif'
    },
    center: {
        textAlign: 'center',
        padding: '50px',
        fontSize: '1.2rem',
        color: '#7f8c8d',
        fontFamily: 'system-ui, sans-serif'
    },
    progressHeader: {
        display: 'flex',
        justifyContent: 'space-between',
        marginBottom: '8px',
        fontSize: '0.85rem',
        color: '#7f8c8d',
        fontWeight: 600
    },
    progressBarWrapper: {
        width: '100%',
        height: '8px',
        background: '#ecf0f1',
        borderRadius: '4px',
        overflow: 'hidden',
        marginBottom: '30px'
    },
    progressBarInner: {height: '100%', background: '#3498db', transition: 'width 0.3s ease-in-out'},
    card: {
        background: '#fff',
        borderRadius: '12px',
        border: '1px solid #e2e8f0',
        padding: '30px',
        boxShadow: '0 4px 6px -1px rgba(0, 0, 0, 0.05)'
    },
    titleText: {
        margin: '0 0 6px 0',
        fontSize: '0.85rem',
        color: '#3498db',
        textTransform: 'uppercase',
        letterSpacing: '1px'
    },
    questionText: {margin: '0 0 24px 0', fontSize: '1.3rem', color: '#2c3e50', fontWeight: 600, lineHeight: '1.4'},
    inputArea: {minHeight: '180px', marginBottom: '24px'},
    textarea: {
        width: '100%',
        padding: '12px',
        borderRadius: '6px',
        border: '1px solid #cbd5e1',
        fontSize: '1rem',
        fontFamily: 'inherit',
        resize: 'vertical',
        boxSizing: 'border-box'
    },
    optionLabel: {
        display: 'flex',
        alignItems: 'center',
        padding: '14px',
        borderRadius: '8px',
        border: '1px solid #e2e8f0',
        marginBottom: '12px',
        cursor: 'pointer',
        fontSize: '1rem',
        background: '#f8fafc',
        transition: 'background 0.2s'
    },
    radioInput: {marginRight: '12px', width: '18px', height: '18px', cursor: 'pointer'},
    actionFooter: {display: 'flex', justifyContent: 'flex-end', borderTop: '1px solid #f1f5f9', paddingTop: '20px'},
    nextBtn: {
        display: 'inline-flex',
        alignItems: 'center',
        color: '#fff',
        border: 'none',
        padding: '12px 24px',
        borderRadius: '6px',
        fontSize: '1rem',
        fontWeight: 600,
        transition: 'background 0.2s'
    },
    successCard: {
        textAlign: 'center',
        maxWidth: '500px',
        margin: '80px auto',
        background: '#fff',
        padding: '40px',
        borderRadius: '12px',
        border: '1px solid #e2e8f0',
        boxShadow: '0 4px 10px rgba(0, 0, 0, 0.05)'
    },
    reviewWrapper: {
        background: '#f8fafc',
        borderRadius: '8px',
        padding: '20px',
        border: '1px solid #e2e8f0',
        maxHeight: '400px',
        overflowY: 'auto'
    },
    reviewRow: {borderBottom: '1px solid #e2e8f0', paddingBottom: '12px', marginBottom: '12px'},
    reviewAnswer: {
        margin: '6px 0 0 0',
        color: '#2c3e50',
        background: '#fff',
        padding: '8px',
        borderRadius: '4px',
        border: '1px solid #edf2f7',
        fontSize: '0.9rem'
    }
};
