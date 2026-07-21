import {useEffect, useState} from "react";
import {AnswerEndpoint} from "Frontend/generated/endpoints";
import Answer from "Frontend/generated/be/riddler/v1/answer/client/model/Answer";
import {Notify} from "Frontend/util";

export interface AnswerProperties {
    id: string;
}

export function Answers(answerProperties: AnswerProperties) {
    const [answers, setAnswers] = useState<Answer[]>([]);

    useEffect(() => {
        AnswerEndpoint.findByQuestion(answerProperties.id)
            .then(setAnswers)
            .catch(err => Notify.error('Could not fetch the answers {}', err));
    }, []);

    return (
        <div>
            {answers.map(answer => (<div>{answer.id} {answer.solutions.map((d) => (<span>{d?.value}</span>))}</div>))}
        </div>
    );
}
