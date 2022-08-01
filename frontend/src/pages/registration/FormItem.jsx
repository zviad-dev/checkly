import styled from 'styled-components';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import API from '../../api/axios';
import {
    Button,
    ButtonsKind,
    ButtonsSize
} from '../../components/common/Button';
import RegistrationInput, {
    COMPANY_INPUT_ITEM_ID,
    EMAIL_INPUT_ITEM_ID,
    LAST_NAME_INPUT_ITEM_ID,
    NAME_INPUT_ITEM_ID,
    PASSWORD_INPUT_ITEM_ID,
    PASSWORD_REPEAT_INPUT_ITEM_ID
} from './RegistrationInput';
import {
    Error,
    ErrorWrapper
} from '../../components/application/ErrorElements';
import { FormRec, Title } from '../../components/application/FormPageElements';

const NAME_ITEMS = [
    {
        id: NAME_INPUT_ITEM_ID,
        type: 'text',
        label: 'Имя',
        placeholder: 'Введите Имя'
    },
    {
        id: LAST_NAME_INPUT_ITEM_ID,
        type: 'text',
        label: 'Фамилия',
        placeholder: 'Введите Фамилию'
    }
];

const USER_INFO_ITEMS = [
    {
        id: COMPANY_INPUT_ITEM_ID,
        type: 'text',
        label: 'Компания',
        placeholder: 'Введите название компании'
    },
    {
        id: EMAIL_INPUT_ITEM_ID,
        type: 'email',
        label: 'Email',
        placeholder: 'Введите email'
    }
];

const PASSWORD_ITEMS = [
    {
        id: PASSWORD_INPUT_ITEM_ID,
        type: 'password',
        label: 'Пароль',
        placeholder: 'Введите пароль'
    },
    {
        id: PASSWORD_REPEAT_INPUT_ITEM_ID,
        type: 'password',
        label: 'Повторите пароль',
        placeholder: 'Повторите пароль'
    }
];

function FormItem() {
    const {
        register,
        handleSubmit,
        formState: { errors },
        getValues
    } = useForm({
        shouldUnregister: true,
        defaultValues: { label: '' }
    });

    const history = useNavigate();
    const [apiError, setApiError] = useState({});

    const handleSubmitApi = (data) => {
        API.post(`registration`, {
            [NAME_INPUT_ITEM_ID]: data[NAME_INPUT_ITEM_ID],
            [LAST_NAME_INPUT_ITEM_ID]: data[LAST_NAME_INPUT_ITEM_ID],
            [COMPANY_INPUT_ITEM_ID]: data[COMPANY_INPUT_ITEM_ID],
            [PASSWORD_INPUT_ITEM_ID]: data[PASSWORD_INPUT_ITEM_ID],
            [EMAIL_INPUT_ITEM_ID]: data[EMAIL_INPUT_ITEM_ID]
        })
            .then(() => history('/signin'))
            .catch((error) => {
                setApiError({
                    message:
                        error?.response?.data?.reason ===
                        'EMAIL_ALREADY_REGISTERED'
                            ? 'Данный email уже зарегестрирован'
                            : 'Ошибка отправки регистрации'
                });
                // eslint-disable-next-line no-console
                console.error(error.message);
            });
    };

    return (
        <FormRec onSubmit={handleSubmit(handleSubmitApi)}>
            <Title>Создайте свой кабинет</Title>
            <RawInputContainer>
                {NAME_ITEMS.map((item) => (
                    <RawRegistrationInput
                        key={item.id}
                        getValues={getValues}
                        register={register}
                        itemId={item.id}
                        itemLabel={item.label}
                        itemType={item.type}
                        itemPlaceholder={item.placeholder}
                        error={errors[item.id]?.message}
                    />
                ))}
            </RawInputContainer>
            {USER_INFO_ITEMS.map((item) => (
                <RegistrationInput
                    key={item.id}
                    getValues={getValues}
                    register={register}
                    itemId={item.id}
                    itemLabel={item.label}
                    itemType={item.type}
                    itemPlaceholder={item.placeholder}
                    error={errors[item.id]?.message}
                />
            ))}

            <RawInputContainer>
                {PASSWORD_ITEMS.map((item) => (
                    <RawRegistrationInput
                        key={item.id}
                        getValues={getValues}
                        register={register}
                        itemId={item.id}
                        itemLabel={item.label}
                        itemType={item.type}
                        itemPlaceholder={item.placeholder}
                        error={errors[item.id]?.message}
                    />
                ))}
            </RawInputContainer>

            {apiError && (
                <ErrorWrapper>
                    <Error>{apiError.message}</Error>
                </ErrorWrapper>
            )}
            <Button
                kind={ButtonsKind.tertiary}
                size={ButtonsSize.l}
                type="submit"
            >
                Зарегистрироваться
            </Button>
        </FormRec>
    );
}

export default FormItem;

const RawInputContainer = styled.div`
    width: 100%;
    display: flex;
    gap: 10px;

    @media ${({ theme }) => theme.media.small} {
        flex-direction: column;
        gap: 10px;
    }
`;

const RawRegistrationInput = styled(RegistrationInput)`
    width: 100%;
`;
