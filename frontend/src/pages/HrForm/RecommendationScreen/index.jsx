import styled from 'styled-components';
import { useState } from 'react';
import PropTypes from 'prop-types';
import API from '../../../api/axios';
import NewRecommendation from './NewRecommendation';
import RecommendationList from './RecommendationList';
import {
    Button,
    ButtonsKind,
    ButtonsSize
} from '../../../components/common/Button';
import {
    Error,
    ErrorWrapper
} from '../../../components/application/ErrorElements';
import {
    Title,
    SuccessContainer
} from '../../../components/application/FormPageElements';
import useAuth from '../../../userData/useAuth';

function RecommendationScreen({ surveyId, setStep, loadData, recommenders }) {
    const [apiErorr, setApiError] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const recruiterId = useAuth().user.id;
    const [apiSuccess, setApiSuccess] = useState(null);
    const [isSave, setSetIsSave] = useState(null);

    const auth = useAuth();

    const deleteRecommendationHandler = (recommendationId) => {
        API.delete(
            `recruiters/${recruiterId}/surveys/${surveyId}/recommendations/${recommendationId}`
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

    const handleSubmitApi = () => {
        setIsLoading(true);

        API.put(`recruiters/${recruiterId}/surveys/${surveyId}/send`)
            .then(() => {
                setApiError(null);
                loadData();
                setApiSuccess(true);
                auth.setSurveyId(null);
            })
            .catch((error) => {
                setApiError({
                    message: 'Ошибка сохранения опроса'
                });

                console.log(error.message);
            })
            .finally(() => {
                setIsLoading(false);
            });
    };

    return (
        <>
            {!isSave && !apiSuccess && (
                <>
                    <Title>Информация о рекомендателях</Title>
                    <RecommendationsWrapper>
                        <RecommendationsContainer>
                            <NewRecommendation
                                surveyId={surveyId}
                                loadData={loadData}
                            />
                            <RecommendationList
                                recommenders={recommenders}
                                deleteRecommendationHandler={
                                    deleteRecommendationHandler
                                }
                                surveyId={surveyId}
                                setApiError={setApiError}
                                setIsLoading={setIsLoading}
                                loadData={loadData}
                            />
                        </RecommendationsContainer>
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
                                    setStep(2);
                                }}
                            >
                                Назад
                            </Button>
                            <Button
                                kind={ButtonsKind.tertiary}
                                size={ButtonsSize.m}
                                type="submit"
                                onClick={() => {
                                    setSetIsSave(true);
                                    auth.setSurveyId(null);
                                }}
                            >
                                Сохранить
                            </Button>
                            <Button
                                kind={ButtonsKind.tertiary}
                                size={ButtonsSize.m}
                                type="submit"
                                onClick={handleSubmitApi}
                            >
                                {isLoading ? 'Идет отправка...' : 'Отправить'}
                            </Button>
                        </ButtonWrapper>
                    </RecommendationsWrapper>
                </>
            )}
            {(apiSuccess || isSave) && (
                <SuccessContainer>
                    <Title>
                        {isSave ? 'Опрос сохранен!' : 'Опрос отправлен!'}
                    </Title>
                </SuccessContainer>
            )}
        </>
    );
}

export default RecommendationScreen;

RecommendationScreen.propTypes = {
    surveyId: PropTypes.number,
    setStep: PropTypes.func,
    loadData: PropTypes.func,
    recommenders: PropTypes.arrayOf
};

const RecommendationsContainer = styled.div``;

const RecommendationsWrapper = styled.div`
    background: #f8faff;
    max-width: 600px;
    width: 100%;
    display: grid;
    margin: 0 auto;
    border-radius: 4px;
    justify-content: center;
`;

const ButtonWrapper = styled.div`
    display: flex;
    justify-content: flex-end;
    gap: 20px;
`;
