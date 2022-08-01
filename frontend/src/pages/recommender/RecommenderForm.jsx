import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import styled from 'styled-components';
import API from '../../api/axios';
import {
    Button,
    ButtonsKind,
    ButtonsSize
} from '../../components/common/Button';
import {
    Error,
    ErrorWrapper
} from '../../components/application/ErrorElements';
import {
    Container,
    Description,
    FormRec,
    LeftSide,
    LogoText,
    RightSide,
    SuccessContainer,
    Title
} from '../../components/application/FormPageElements';
import RecommenderAnswerInput, {
    INVALID_RECOMMENDATION_STATUS,
    INVALID_SURVEY_STATUS
} from './RecommenderAnswerInput';
import RecommenderInfoSection from './RecommenderInfoSection';

function RecommenderForm() {
    const recommendationId = useLocation().pathname.split('recommendation/')[1];
    const [recommendationData, setRecommendationData] = useState([]);

    const [apiError, setApiError] = useState(null);
    const [apiSuccess, setApiSuccess] = useState(null);

    const [isLoading, setIsLoading] = useState(false);
    const [isSurveyClosed, setIsSurveyClosed] = useState(false);
    const [isRecommendationClosed, setIsRecommendationClosed] = useState(false);
    const [isDraftClosed, setIsDraftClosed] = useState(false);

    const { register, handleSubmit, reset, formState, clearErrors } = useForm({
        shouldUnregister: true,
        defaultValues: { label: '' }
    });

    const processErrorCode = (reason, errorMessage) => {
        switch (reason) {
        case INVALID_SURVEY_STATUS:
            setIsSurveyClosed(true);
            break;
        case INVALID_RECOMMENDATION_STATUS:
            setIsRecommendationClosed(true);
            break;
        default:
            setApiError({ message: errorMessage });
            break;
        }
    };

    const loadData = () => {
        API.get(`recommendations/${recommendationId}/answers`)
            .then(({ data }) => {
                setRecommendationData(data);
            })
            .catch((err) => {
                processErrorCode(
                    err?.response?.data?.reason,
                    'Ошибка при получении рекоммендации'
                );
                console.error(err.message);
            });
    };

    useEffect(() => loadData(), [recommendationId]);

    const handleAnswerInputChange = (idx, e) => {
        const data = {...recommendationData}
        data.answers = data.answers?.map((value) => {
            if (value.question_id !== idx) return value
            const changedValue = {...value}
            changedValue.answer = e.target.value
            return changedValue
        })
        setRecommendationData(data)
    }

    const handleSubmitApi = () => {
        setIsLoading(true);
        API.post(`recommendations/${recommendationId}/answers/close`)
            .then(() => {
                setIsRecommendationClosed(true);
                setApiError(null);
                setApiSuccess(true);
                reset();
                clearErrors();
            })
            .catch((error) => {
                processErrorCode(
                    error?.response?.data?.reason,
                    'Ошибка закрытия рекомендации'
                );
                console.log(error.message);
            })
            .finally(() => {
                setIsLoading(false);
            });
    };

    const errors = [...Object.values(formState.errors), apiError];

    return (
        <Container>
            <LeftSide>
                <LogoText>CHECKLY</LogoText>
                {isSurveyClosed || isRecommendationClosed || (
                    <Description>
                        Ответьте на вопросы о кандидате. Можно отвечать не на
                        все вопросы. Вы можете вернуться к заполнению в удобное время, если
                        сохраните свои ответы в черновике
                    </Description>
                )}
            </LeftSide>
            <RightSide>
                {apiSuccess || isDraftClosed ? (
                    <SuccessContainer>
                        <Title>
                            {isDraftClosed ? (
                                <DraftWrapper>
                                    Сохранено в черновике, Вы можете продолжить
                                    заполнение в любое время
                                    <Button
                                        onClick={() => setIsDraftClosed(false)}
                                        kind={ButtonsKind.tertiary}
                                        size={ButtonsSize.m}
                                        type="button"
                                    >
                                        Продолжить заполнение
                                    </Button>
                                </DraftWrapper>
                            ) : (
                                'Спасибо! Ваш ответ принят!'
                            )}
                        </Title>
                    </SuccessContainer>
                ) : (
                    <>
                        <Title>
                            {isSurveyClosed || isRecommendationClosed || (
                                <RightRecommenderInfoSection
                                    candidateLastName={
                                        recommendationData.candidate_last_name
                                    }
                                    candidateFirstName={
                                        recommendationData.candidate_first_name
                                    }
                                    recommenderLastName={
                                        recommendationData.recommender_last_name
                                    }
                                    recommenderFirstName={
                                        recommendationData.recommender_first_name
                                    }
                                />
                            )}
                            {isSurveyClosed && 'Опрос закрыт'}

                            {!isSurveyClosed &&
                                (isRecommendationClosed
                                    ? 'Ваш ответ уже получен'
                                    : 'Ответьте на вопросы')}
                        </Title>

                        {isSurveyClosed || isRecommendationClosed || (
                            <div>
                                <FormRec
                                    onSubmit={handleSubmit(handleSubmitApi)}
                                >
                                    {recommendationData?.answers?.sort((a, b) => a.question_id - b.question_id).map(
                                        (data) => (
                                            <RecommenderAnswerInput
                                                key={data.question_id}
                                                handleChange={handleAnswerInputChange}
                                                question={data?.question}
                                                setApiError={setApiError}
                                                setIsSurveyClosed={
                                                    setIsSurveyClosed
                                                }
                                                setIsRecommendationClosed={
                                                    setIsRecommendationClosed
                                                }
                                                answer={data?.answer}
                                                answerId={data.question_id}
                                                error={
                                                    errors[data.question_id]
                                                        ?.message
                                                }
                                                recommendationId={
                                                    recommendationId
                                                }
                                                loadData={loadData}
                                                register={register}
                                                processErrorCode={
                                                    processErrorCode
                                                }
                                            />
                                        )
                                    )}
                                    {apiError && (
                                        <ErrorWrapper>
                                            <Error>{apiError.message}</Error>
                                        </ErrorWrapper>
                                    )}
                                    {!isSurveyClosed && (
                                        <ButtonWrapper>
                                            <SubmitButton
                                                onClick={() =>
                                                    setIsDraftClosed(true)
                                                }
                                                kind={ButtonsKind.quaternary}
                                                size={ButtonsSize.m}
                                                type="button"
                                            >
                                                {isDraftClosed
                                                    ? 'Сохранено в черновике'
                                                    : 'Сохранить в черновик'}
                                            </SubmitButton>
                                            <SubmitButton
                                                kind={ButtonsKind.tertiary}
                                                size={ButtonsSize.m}
                                                type="submit"
                                            >
                                                {isLoading
                                                    ? 'Отправка рекоммендации...'
                                                    : 'Отправить'}
                                            </SubmitButton>
                                        </ButtonWrapper>
                                    )}
                                </FormRec>
                            </div>
                        )}
                    </>
                )}
            </RightSide>
        </Container>
    );
}
export default RecommenderForm;

export const RightRecommenderInfoSection = styled(RecommenderInfoSection)`
    @media ${({ theme }) => theme.media.large} {
        display: none !important;
    }
`;

const ButtonWrapper = styled.div`
    display: flex;
    gap: 20px;
    justify-content: center;
    width: 100%;

    @media ${({ theme }) => theme.media.small} {
        flex-direction: column;
        gap: 10px;
        align-items: center;
    }
`;

const SubmitButton = styled(Button)`
    width: 100%;
`;

const DraftWrapper = styled.div`
    display: flex;
    flex-direction: column;
    gap: 20px;
`;
