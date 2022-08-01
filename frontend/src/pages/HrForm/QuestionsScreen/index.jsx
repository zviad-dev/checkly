import styled from 'styled-components';
import { useState } from 'react';
import PropTypes from 'prop-types';
import API from '../../../api/axios';
import NewQuestion from './NewQuestion';
import QuestionsList from './QuestionsList';
import {
    Button,
    ButtonsKind,
    ButtonsSize
} from '../../../components/common/Button';
import {
    Error,
    ErrorWrapper
} from '../../../components/application/ErrorElements';
import { Title } from '../../../components/application/FormPageElements';
import useAuth from '../../../userData/useAuth';

function QuestionsScreen({ surveyId, setStep, loadData, questions }) {
    const [apiErorr, setApiError] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const recruiterId = useAuth().user.id;

    const deleteQuestionHandler = (questionId) => {
        API.delete(
            `recruiters/${recruiterId}/surveys/${surveyId}/questions/${questionId}`
        )
            .then(() => {
                loadData();
            })
            .catch((error) => {
                setApiError({
                    message: 'Ошибка удаления информации о кандидате'
                });

                console.log(error.message);
            });
    };

    return (
        <>
            <Title>Создать опрос</Title>
            <QuestionsWrapper>
                <QuestionsContainer>
                    <NewQuestion surveyId={surveyId} loadData={loadData} />
                    <QuestionsList
                        questions={questions}
                        deleteQuestionHandler={deleteQuestionHandler}
                        surveyId={surveyId}
                        setApiError={setApiError}
                        setIsLoading={setIsLoading}
                        loadData={loadData}
                    />
                </QuestionsContainer>
                {apiErorr && (
                    <ErrorWrapper>
                        <Error>{apiErorr.message}</Error>
                    </ErrorWrapper>
                )}
                <ButtonWrapper>
                    <Button
                        kind={ButtonsKind.quaternary}
                        size={ButtonsSize.m}
                        type="button"
                        onClick={() => {
                            setStep(1);
                        }}
                    >
                        Назад
                    </Button>
                    <Button
                        kind={ButtonsKind.tertiary}
                        size={ButtonsSize.m}
                        type="submit"
                        onClick={() => {
                            setStep(3);
                        }}
                    >
                        {isLoading ? 'Идет отправка...' : 'Продолжить'}
                    </Button>
                </ButtonWrapper>
            </QuestionsWrapper>
        </>
    );
}

export default QuestionsScreen;

QuestionsScreen.propTypes = {
    surveyId: PropTypes.number,
    setStep: PropTypes.func,
    loadData: PropTypes.func,
    questions: PropTypes.arrayOf
};

const QuestionsContainer = styled.div``;

const QuestionsWrapper = styled.div`
    background: #f8faff;
    max-width: 600px;
    width: 100%;
    display: grid;
    margin: 0 auto;
    border-radius: 4px;
`;

const ButtonWrapper = styled.div`
    display: flex;
    justify-content: flex-end;
    gap: 20px;
`;
