export const CANDIDATE_FIRST_NAME = 'candidate_first_name';
export const CANDIDATE_LAST_NAME = 'candidate_last_name';
export const CANDIDATE_EMAIL = 'candidate_email';
export const CANDIDATE_VACANCY = 'position_name';

export const REC_FIRST_NAME = 'first_name';
export const REC_LAST_NAME = 'last_name';
export const REC_EMAIL = 'email';

export const CANDIDATE_DATA = [
    {
        label: 'Имя кандидата',
        id: CANDIDATE_FIRST_NAME,
        type: 'text',
        placeholder: 'Введите имя кандидата'
    },
    {
        label: 'Фамилия кандидата',
        id: CANDIDATE_LAST_NAME,
        type: 'text',
        placeholder: 'Введите фамилию кандидата'
    },
    {
        label: 'Email кандидата',
        id: CANDIDATE_EMAIL,
        type: 'email',
        placeholder: 'Введите email кандидата'
    },
    {
        label: 'Вакансия',
        id: CANDIDATE_VACANCY,
        type: 'text',
        placeholder: 'Введите вакансию'
    }
];

export const RECOMENDATION_DATA = [
    {
        label: 'Имя рекомендателя',
        id: REC_FIRST_NAME,
        type: 'text',
        placeholder: 'Введите имя'
    },
    {
        label: 'Фамилия рекомендателя',
        id: REC_LAST_NAME,
        type: 'text',
        placeholder: 'Введите фамилию'
    },
    {
        label: 'Email рекомендателя',
        id: REC_EMAIL,
        type: 'email',
        placeholder: 'Введите email'
    }
];
