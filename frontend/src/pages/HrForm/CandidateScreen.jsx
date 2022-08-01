import { useState, useEffect } from 'react';
import { useForm } from 'react-hook-form';
import PropTypes from 'prop-types';
import styled from 'styled-components';
import API from '../../api/axios';
import {
    Button,
    ButtonsKind,
    ButtonsSize
} from '../../components/common/Button';
import {
    Input,
    FormLabel,
    InputWrapper
} from '../../components/application/FormElements';
import {
    Error,
    ErrorWrapper
} from '../../components/application/ErrorElements';
import { Title, FormRec } from '../../components/application/FormPageElements';
import useAuth from '../../userData/useAuth';
import { CANDIDATE_DATA } from './hrFormData';

function CandidateScreen({ setStep, survey, surveyId, loadData }) {
    const {
        register,
        handleSubmit,
        reset,
        formState: { errors },
        clearErrors
    } = useForm({
        shouldUnregister: true,
        defaultValues: { label: '' }
    });
    const auth = useAuth();

    useEffect(() => {
        reset(survey);
    }, [survey]);

    const [apiErorr, setApiError] = useState(null);
    const [isLoading, setIsLoading] = useState(false);

    const recruiterId = useAuth().user.id;

    const onSubmit = () => {
        setStep(2);
        loadData();
    };

    const handleSubmitApi = (data) => {
        setIsLoading(true);

        if (survey) {
            API.put(`recruiters/${recruiterId}/surveys/${surveyId}`, data)
                .then(() => {
                    setApiError(null);
                    clearErrors();
                    onSubmit();
                })
                .catch((error) => {
                    setApiError({
                        message: 'Ошибка отправки изменений кандидата'
                    });

                    console.log(error.message);
                })
                .finally(() => {
                    setIsLoading(false);
                });
        } else {
            API.post(`recruiters/${recruiterId}/surveys`, data)
                .then((response) => {
                    auth.setSurveyId(response.data.id);
                    setApiError(null);
                    reset();
                    clearErrors();
                    onSubmit();
                })
                .catch((error) => {
                    setApiError({
                        message: 'Ошибка отправки информации о кандидате'
                    });
                    console.log(error.message);
                })
                .finally(() => {
                    setIsLoading(false);
                });
        }
    };

    return (
        <>
            <Title>Информация о кандидате</Title>
            <FormRec onSubmit={handleSubmit(handleSubmitApi)}>
                {CANDIDATE_DATA.map((item) => (
                    <InputWrapper key={item.id}>
                        <FormLabel htmlFor={item.id}>{item.label}</FormLabel>
                        <Input
                            id={item.id}
                            type={item.type}
                            placeholder={item.placeholder}
                            {...register(item.id, {
                                required: `${item.label} не может быть пустым`,
                                validate: {
                                    lessThanTwenty: (v) =>
                                        v.length <= 100000 ||
                                        'Максимальная длина 100000 символов'
                                }
                            })}
                        />
                        <Error>{errors[item.id]?.message}</Error>
                    </InputWrapper>
                ))}
                {apiErorr && (
                    <ErrorWrapper>
                        <Error>{apiErorr.message}</Error>
                    </ErrorWrapper>
                )}
                <ButtonWrapper>
                    <Button
                        kind={ButtonsKind.tertiary}
                        size={ButtonsSize.m}
                        type="submit"
                    >
                        {isLoading ? 'Идет сохранение...' : 'Продолжить'}
                    </Button>
                </ButtonWrapper>
            </FormRec>
        </>
    );
}

export default CandidateScreen;

CandidateScreen.propTypes = {
    setStep: PropTypes.func,
    survey: PropTypes.objectOf,
    surveyId: PropTypes.number,
    loadData: PropTypes.func
};

const ButtonWrapper = styled.div`
    display: flex;
    justify-content: flex-end;
`;
