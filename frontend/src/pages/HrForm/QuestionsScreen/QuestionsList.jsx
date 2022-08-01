import styled from 'styled-components';
import PropTypes from 'prop-types';
import { useState } from 'react';
import Remove from '../../../assets/remove.svg';
import API from '../../../api/axios';
import {
    Input,
    InputWrapper
} from '../../../components/application/FormElements';
import useAuth from '../../../userData/useAuth';

function QuestionsList({
    questions,
    deleteQuestionHandler,
    setApiError,
    setIsLoading,
    loadData,
    surveyId
}) {
    const [idToEdit, setEdit] = useState(null);
    const [inputValue, setInputValue] = useState('');
    const recruiterId = useAuth().user.id;

    const editQuestion = (id, question) => {
        setEdit(id);
        setInputValue(question);
    };

    const saveQuestion = (questionId, title) => {
        API.put(
            `recruiters/${recruiterId}/surveys/${surveyId}/questions/${questionId}`,
            { question: title }
        )
            .then(() => {
                loadData();
                setApiError(null);
            })
            .catch((error) => {
                setApiError({
                    message: 'Ошибка отправки изменений вопроса'
                });

                console.log(error.message);
            })
            .finally(() => {
                setIsLoading(false);
            });
    };

    return (
        <Container>
            {questions.map((item) => (
                <Element key={item.id}>
                    {idToEdit === item.id ? (
                        <QuestionInput
                            value={inputValue}
                            onChange={(e) => setInputValue(e.target.value)}
                            onBlur={() => {
                                saveQuestion(item.id, inputValue);
                            }}
                        />
                    ) : (
                        <QuestionInput
                            readOnly
                            id={item.id}
                            value={item.question}
                            onClick={() => {
                                editQuestion(item.id, item.question);
                            }}
                        />
                    )}
                    <RemoveIcon
                        src={Remove}
                        onClick={() => deleteQuestionHandler(item.id)}
                    />
                </Element>
            ))}
        </Container>
    );
}

export default QuestionsList;

QuestionsList.propTypes = {
    deleteQuestionHandler: PropTypes.func,
    questions: PropTypes.arrayOf(PropTypes.shape),
    setApiError: PropTypes.func,
    setIsLoading: PropTypes.func,
    loadData: PropTypes.func,
    surveyId: PropTypes.number
};

const Container = styled.div`
    overflow: hidden;
    margin-bottom: 1rem;
`;

const Element = styled(InputWrapper)`
    flex-direction: row;
    display: flex;
    width: 100%;
`;

const QuestionInput = styled(Input)`
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    width: inherit;
    margin-right: 20px;
`;

const RemoveIcon = styled.img`
    opacity: 0.3;

    &:hover {
        opacity: 0.8;
    }
`;
