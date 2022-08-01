import { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import styled from 'styled-components';
import API from '../api/axios';
import PageHeader from '../components/application/PageHeader';
import { Container, Title } from '../components/application/PageElements';
import {
    InputWrapper,
    FormLabel
} from '../components/application/FormElements';
import useAuth from '../userData/useAuth';
import { StatusWrapper } from '../components/application/Status';
import { Answer } from '../components/application/FormPageElements';
import translator from '../translator/Translator';

const TRLS = [
    {
        title: 'Фамилия рекомендателя',
        data: 'last_name'
    },
    { title: 'Имя рекомендателя', data: 'first_name' },
    { title: 'email рекомендателя', data: 'email' }
];

function HrResponseAnswer() {
    const surveyId = useLocation()
        .pathname.split('surveys/')[1]
        .split('/recommendations/')[0];
    const recommendationId =
        useLocation().pathname.split('recommendations/')[1];
    const recruiterId = useAuth().user.id;
    const [recommendationData, setRecommendationData] = useState([]);
    const [isStatus, setIsStatus] = useState(false);
    const [apiSuccess, setApiSuccess] = useState(null);

    useEffect(() => {
        API.get(
            `recruiters/${recruiterId}/surveys/${surveyId}/recommendations/${recommendationId}`
        )
            .then(({ data }) => {
                setApiSuccess(true);
                setRecommendationData(data.survey_info.recommendation);
                setIsStatus(data.survey_info.recommendation.status);
            })
            .catch((error) => console.log(error.message));
    }, []);

    const answersData = recommendationData.answers;

    return (
        <>
            <PageHeader />
            <Container>
                <FormWrapper>
                    <CandidateWrapper>
                        <Title>Информация о рекомендателе</Title>
                        {apiSuccess &&
                            TRLS.map((elem) => (
                                <InputWrapper key={elem.title}>
                                    <FormLabel>{elem.title}</FormLabel>
                                    <Answer>
                                        {recommendationData[elem.data]}
                                    </Answer>
                                </InputWrapper>
                            ))}
                    </CandidateWrapper>
                    <StatusWrapper className={`Status ${isStatus}`}>
                        {translator(isStatus)}
                    </StatusWrapper>
                </FormWrapper>
                <Title>Ответы на вопросы</Title>

                {answersData?.map((elem, idx) => (
                    <InputWrapp key={(elem.title, idx)}>
                        <FormLabel>{elem.question}</FormLabel>
                        <Answer>{elem.answer}</Answer>
                    </InputWrapp>
                )) || 'Список ответов пуст'}
            </Container>
        </>
    );
}

export default HrResponseAnswer;

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

const InputWrapp = styled(InputWrapper)`
    max-width: 800px;
    width: 100%;
`;
