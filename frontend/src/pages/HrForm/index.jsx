import { useState, useEffect, useCallback } from 'react';
import PageHeader from '../../components/application/PageHeader';
import { Container, Title } from '../../components/application/PageElements';
import CandidateScreen from './CandidateScreen';
import QuestionsScreen from './QuestionsScreen/index';
import RecommendationScreen from './RecommendationScreen';
import useAuth from '../../userData/useAuth';
import API from '../../api/axios';

function HrForm() {
    const [step, setStep] = useState(1);
    const { surveyId } = useAuth();

    const [survey, setSurvey] = useState(null);
    const recruiterId = useAuth().user.id;
    const [questions, setQuestions] = useState([]);
    const [recommenders, setRecommenders] = useState([]);

    const loadData = useCallback(() => {
        if (surveyId) {
            API.get(`recruiters/${recruiterId}/surveys/${surveyId}`)
                .then(({ data }) => {
                    setSurvey(data);
                    setQuestions(data.questions);
                    setRecommenders(data.recommenders);
                })
                .catch((error) => {
                    console.log(error.message);
                });
        }
    }, [surveyId]);

    useEffect(() => {
        loadData();
    }, [loadData]);

    return (
        <>
            <PageHeader />
            <Container>
                <Title>Опросы</Title>
                {step === 1 && (
                    <CandidateScreen
                        setStep={setStep}
                        surveyId={surveyId}
                        survey={
                            survey ? { ...survey.candidate, ...survey } : null
                        }
                        loadData={loadData}
                    />
                )}
                {step === 2 && (
                    <QuestionsScreen
                        questions={questions}
                        surveyId={surveyId}
                        setStep={setStep}
                        loadData={loadData}
                    />
                )}
                {step === 3 && (
                    <RecommendationScreen
                        recommenders={recommenders}
                        surveyId={surveyId}
                        setStep={setStep}
                        loadData={loadData}
                    />
                )}
            </Container>
        </>
    );
}

export default HrForm;
