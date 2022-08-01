import { useState, useEffect, useCallback } from 'react';
import { useLocation, Link as LinkR } from 'react-router-dom';
import styled from 'styled-components';

import API from '../api/axios';
import PageHeader from '../components/application/PageHeader';
import { Container, Title } from '../components/application/PageElements';
import {
    InputWrapper,
    FormLabel
} from '../components/application/FormElements';
import useAuth from '../userData/useAuth';
import DataTable from '../components/application/DataTable';
import { Button, ButtonsKind, ButtonsSize } from '../components/common/Button';
import { Error } from '../components/application/ErrorElements';
import { StatusWrapper } from '../components/application/Status';
import { Answer } from '../components/application/FormPageElements';
import translator from '../translator/Translator';

const TRLS = [
    {
        title: 'Фамилия кандидата',
        data: 'candidate_last_name',
        isCandidate: true
    },
    {
        title: 'Имя кандидата',
        data: 'candidate_first_name',
        isCandidate: true
    },
    {
        title: 'email кандидата',
        data: 'candidate_email',
        isCandidate: true
    },
    { title: 'Вакансия', data: 'position_name', isCandidate: false },
    { title: 'Время создания', data: 'created_at', isCandidate: false }
];

function HrResponse() {
    const surveyId = useLocation().pathname.split('surveys/')[1];
    const recruiterId = useAuth().user.id;
    const [recommendationData, setRecommendationData] = useState([]);
    const [status, setStatus] = useState(false);
    const [apiSuccess, setApiSuccess] = useState(null);
    const [apiErorr, setApiError] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [dataRecommendations, setDataRecommendations] = useState([]);
    const auth = useAuth();

    useEffect(() => {
        API.get(`recruiters/${recruiterId}/surveys/${surveyId}/recommendations`)
            .then(({ data }) => {
                setApiSuccess(true);
                setRecommendationData(data.survey_info);
                setStatus(data.survey_info.status);
                setDataRecommendations(data.survey_info.recommendations);
            })
            .catch((error) => console.log(error.message));
    }, []);
    const dataCanidate = recommendationData.candidate;

    const onSubmit = useCallback(() => {
        setIsLoading(true);
        API.put(`recruiters/${recruiterId}/surveys/${surveyId}/close`)
            .then((response) => {
                setApiError(null);
                setStatus(response.data.status);
            })
            .catch((error) => {
                setApiError({
                    message: 'Ошибка закрытия опроса'
                });
                console.log(error.message);
            })
            .finally(() => {
                setIsLoading(false);
            });
    }, []);

    return (
        <>
            <PageHeader />
            <Container>
                <FormWrapper>
                    <CandidateWrapper>
                        <Title>Информация о кандидате</Title>
                        {apiSuccess
                            ? TRLS.map((elem) => (
                                <InputWrapper key={elem.data}>
                                    <FormLabel>{elem.title}</FormLabel>
                                    {elem.isCandidate ? (
                                        <Answer>
                                            {dataCanidate[elem.data]}
                                        </Answer>
                                    ) : (
                                        <Answer>
                                            {recommendationData[elem.data]}
                                        </Answer>
                                    )}
                                </InputWrapper>
                            ))
                            : 'Информации о кандидате нет'}
                    </CandidateWrapper>
                    <StatusWrapper className={`Status ${status}`}>
                        {translator(status)}
                        {!['FINISHED', 'CLOSED'].includes(status) && (
                            <Buttonwrapper>
                                {apiErorr && <Error>{apiErorr.message}</Error>}
                                {status === 'OPEN' && (
                                    <LinkR to="/recommendation">
                                        <Button
                                            kind={ButtonsKind.quaternary}
                                            size={ButtonsSize.m}
                                            onClick={auth.setSurveyId(surveyId)}
                                        >
                                            Продолжить заполнение
                                        </Button>
                                    </LinkR>
                                )}

                                <Button
                                    kind={ButtonsKind.tertiary}
                                    size={ButtonsSize.m}
                                    onClick={onSubmit}
                                >
                                    {isLoading
                                        ? 'Идет закрытие...'
                                        : 'Закрыть опрос'}
                                </Button>
                            </Buttonwrapper>
                        )}
                    </StatusWrapper>
                </FormWrapper>
                {dataRecommendations?.length ? (
                    <>
                        <Title>Информация о рекомендателях</Title>

                        <DataTable
                            row={dataRecommendations}
                            surveyId={surveyId}
                        />
                    </>
                ) : (
                    'Список рекомендателей пуст'
                )}
            </Container>
        </>
    );
}

export default HrResponse;

const FormWrapper = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    gap: 20px;

    @media ${({ theme }) => theme.media.medium} {
        flex-direction: column-reverse;
    }
`;

const CandidateWrapper = styled.div`
    max-width: 800px;
    width: 100%;
`;

const Buttonwrapper = styled.div`
    display: flex;
    flex-direction: column;
    gap: 20px;
`;
