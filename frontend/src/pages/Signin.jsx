import { useForm } from 'react-hook-form';
import { useState } from 'react';
import { Link as LinkR } from 'react-router-dom';
import API from '../api/axios';
import Close from '../assets/remove.svg';
import Astronaut from '../assets/astronaut.svg';
import { Button, ButtonsKind, ButtonsSize } from '../components/common/Button';
import {
    Title,
    Container,
    LeftSide,
    LeftIcon,
    RightSideForm,
    CloseIcon,
    Form,
    TextWrapper,
    Link
} from '../components/application/FormPageElements';
import useAuth from '../userData/useAuth';
import {
    Input,
    InputWrapper,
    FormLabel
} from '../components/application/FormElements';
import { Error, ErrorWrapper } from '../components/application/ErrorElements';

const INPUT_ITEM = [
    {
        label: 'Почта',
        id: 'email',
        placeholder: 'Введите почту'
    },
    {
        label: 'Пароль',
        id: 'password',
        placeholder: 'Введите пароль'
    }
];

function Signin() {
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

    const [apiErorr, setApiError] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const auth = useAuth();

    const handleSubmitApi = (data) => {
        setIsLoading(true);

        API.post(`login`, data)
            .then((response) => {
                auth.setToken(response.data.token);
                auth.setUser(response.data.recruiter_info);
                setApiError({});
                clearErrors();
                reset();
            })
            .catch((error) => {
                if (error.response.status === 403) {
                    setApiError({
                        message:
                            'Неправильные данные для входа. Пожалуйста, попробуйте снова'
                    });
                } else {
                    setApiError({
                        message: 'Что-то пошло не так, попробуйте снова'
                    });
                }
                console.log(error.message);
            })
            .finally(() => {
                setIsLoading(false);
            });
    };

    return (
        <Container>
            <LeftSide>
                <LeftIcon src={Astronaut} alt="Космонавт" />
            </LeftSide>
            <RightSideForm>
                <LinkR to="/">
                    <CloseIcon src={Close} alt="Закрыть" />
                </LinkR>
                <Form onSubmit={handleSubmit(handleSubmitApi)}>
                    <Title>Войти в кабинет</Title>
                    {INPUT_ITEM.map((item) => (
                        <InputWrapper key={item.id}>
                            <FormLabel htmlFor={item.id}>
                                {item.label}
                            </FormLabel>
                            <Input
                                id={item.id}
                                type={item.id}
                                placeholder={item.placeholder}
                                {...register(item.id, {
                                    required: `${item.label} не может быть пустым`,
                                    validate: {
                                        MoreThanFour: (v) =>
                                            v.length >= 8 ||
                                            `Минимальная длина ${item.label} 8 символа`,
                                        lessThanTwenty: (v) =>
                                            v.length <= 100000 ||
                                            `Максимальная длина ${item.label} 100000 символов`
                                    }
                                })}
                            />
                            {errors[item.id]?.message && (
                                <Error>{errors[item.id]?.message}</Error>
                            )}
                        </InputWrapper>
                    ))}
                    {apiErorr && (
                        <ErrorWrapper>
                            <Error>{apiErorr.message}</Error>
                        </ErrorWrapper>
                    )}
                    <Button
                        kind={ButtonsKind.tertiary}
                        size={ButtonsSize.l}
                        type="submit"
                    >
                        {isLoading ? 'Идет отправка...' : 'Войти'}
                    </Button>
                    <TextWrapper>
                        <FormLabel>А вас ещё нет аккаунта?</FormLabel>
                        <Link to="/registration">Зарегистрироваться</Link>
                    </TextWrapper>
                </Form>
            </RightSideForm>
        </Container>
    );
}

export default Signin;
