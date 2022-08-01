import { useState } from 'react';
import PropTypes from 'prop-types';
import { LinearProgress } from '@mui/material';
import {
    FormLabel,
    Input,
    InputWrapper
} from '../../components/application/FormElements';
import { Error } from '../../components/application/ErrorElements';
import API from '../../api/axios';

export const INVALID_SURVEY_STATUS = 'INVALID_SURVEY_STATUS';
export const INVALID_RECOMMENDATION_STATUS = 'INVALID_RECOMMENDATION_STATUS';

function RecommenderAnswerInput({
    recommendationId,
    answerId,
    answer,
    question,
    handleChange,
    loadData,
    processErrorCode
}) {
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    const updateAnswer = (newAnswer) => {
        const dto = { question_id: answerId, question, answer: newAnswer };
        setIsLoading(true);
        API.put(`recommendations/${recommendationId}/answers/${answerId}`, dto)
            .then(() => setError(null))
            .catch((err) => {
                processErrorCode(
                    err?.response?.data?.reason,
                    'Ошибка при обновлении ответа'
                );
                loadData();
            })
            .finally(() => setIsLoading(false));
    };

    const updateNameInput = (e) => {
        if (e.target.value && e.target.value.length >= 100000) {
            setError('Максимальная длина ответа 100000 символов');
        } else {
            updateAnswer(e.target.value);
        }
    };

    return (
        <InputWrapper>
            <FormLabel htmlFor={answerId}>{question}</FormLabel>
            <Input
                id={answerId}
                value={answer}
                type="text"
                placeholder="Введите ответ на вопрос"
                onChange={(e) => handleChange(answerId, e)}
                onBlur={updateNameInput}
            />
            {isLoading && <LinearProgress color="secondary" />}
            <Error>{error}</Error>
        </InputWrapper>
    );
}

RecommenderAnswerInput.propTypes = {
    recommendationId: PropTypes.string,
    answerId: PropTypes.number,
    loadData: PropTypes.func,
    handleChange: PropTypes.func,
    answer: PropTypes.string,
    question: PropTypes.string,
    processErrorCode: PropTypes.func
};

export default RecommenderAnswerInput;
