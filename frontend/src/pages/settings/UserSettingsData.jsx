import {
    COMPANY_INPUT_ITEM_ID,
    EMAIL_INPUT_ITEM_ID,
    LAST_NAME_INPUT_ITEM_ID,
    NAME_INPUT_ITEM_ID,
    PASSWORD_INPUT_ITEM_ID,
    PASSWORD_REPEAT_INPUT_ITEM_ID,
    PASSWORD_OLD_INPUT_ITEM_ID,
    PASSWORD_NEW_INPUT_ITEM_ID
} from '../registration/RegistrationInput';

export const userSettingsData = [
    {
        title: 'Имя',
        data: 'name',
        control: [
            {
                text: 'Фамилия',
                dto: LAST_NAME_INPUT_ITEM_ID,
                key: LAST_NAME_INPUT_ITEM_ID,
                type: 'text'
            },
            {
                text: 'Имя',
                dto: NAME_INPUT_ITEM_ID,
                key: NAME_INPUT_ITEM_ID,
                type: 'text'
            },
            {
                text: 'Компания',
                dto: COMPANY_INPUT_ITEM_ID,
                key: COMPANY_INPUT_ITEM_ID,
                type: 'text'
            }
        ],
        path: 'info'
    },
    {
        title: 'Пароль',
        info: '********',
        control: [
            {
                text: 'Старый пароль',
                key: PASSWORD_OLD_INPUT_ITEM_ID,
                type: 'password'
            },
            {
                text: 'Новый пароль',
                key: PASSWORD_NEW_INPUT_ITEM_ID,
                type: 'password'
            },
            {
                text: 'Повторите пароль',
                key: PASSWORD_REPEAT_INPUT_ITEM_ID,
                type: 'password'
            }
        ],
        path: 'password'
    },
    {
        title: 'Почта',
        data: EMAIL_INPUT_ITEM_ID,
        control: [
            {
                text: 'Новый email',
                dto: EMAIL_INPUT_ITEM_ID,
                key: 'new_email',
                type: 'email'
            },
            {
                text: 'Ваш текущий пароль',
                key: PASSWORD_INPUT_ITEM_ID,
                type: 'password'
            }
        ],
        path: 'email'
    }
];

export default userSettingsData;
