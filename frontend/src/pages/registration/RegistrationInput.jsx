/* eslint-disable indent */
import PropTypes from 'prop-types';
import { Input, InputWrapper } from '../../components/application/FormElements';
import RegistrationFormLabel from './RegistrationFormLabel';
import { Error } from '../../components/application/ErrorElements';

export const EMAIL_RULE_PATTERN =
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
export const FIO_RULE_PATTERN = /^[a-zA-Z]+|[а-яёА-ЯЁ]+$/;
export const PASSWORD_RULE_PATTERN = /^[a-zA-Z0-9]+$/;

export const NAME_INPUT_ITEM_ID = 'first_name';
export const LAST_NAME_INPUT_ITEM_ID = 'last_name';
export const COMPANY_INPUT_ITEM_ID = 'company_name';
export const EMAIL_INPUT_ITEM_ID = 'email';
export const PASSWORD_INPUT_ITEM_ID = 'password';
export const PASSWORD_REPEAT_INPUT_ITEM_ID = 'passwordRepeat';

export const PASSWORD_OLD_INPUT_ITEM_ID = 'old_password';
export const PASSWORD_NEW_INPUT_ITEM_ID = 'new_password';
export const PASSWORD_NEWREPEAT_INPUT_ITEM_ID = 'repeat_password';

const PASSWORD_MISMATCH_ERROR_MESSAGE = 'Пароли не совпадают';
const MIN_PASSWORD_LENGTH = 8;

export const getValidationFunc = (inputItemId, getValues) => {
    const result = {};
    switch (inputItemId) {
    case NAME_INPUT_ITEM_ID:
    case LAST_NAME_INPUT_ITEM_ID:
        result.isFIO = (v) =>
            !v ||
                FIO_RULE_PATTERN.test(v) ||
                `Указаны некорректные символы`;
        break;
    case EMAIL_INPUT_ITEM_ID:
        result.isEmail = (v) =>
            !v ||
                EMAIL_RULE_PATTERN.test(v) ||
                'Некорректный формат электронной почты';
        break;
    case PASSWORD_INPUT_ITEM_ID:
        result.passwordSyntaxValidation = (v) =>
            (v.length >= MIN_PASSWORD_LENGTH &&
                    PASSWORD_RULE_PATTERN.test(v)) ||
                'Некорректный пароль. Необходимо указать 8 латинских символов';
        break;
    case PASSWORD_REPEAT_INPUT_ITEM_ID:
        result.equalsToOldPassword = (v) =>
            getValues(PASSWORD_INPUT_ITEM_ID) === v ||
                PASSWORD_MISMATCH_ERROR_MESSAGE;
        break;

    case PASSWORD_OLD_INPUT_ITEM_ID:
        result.passwordSyntaxValidation = (v) =>
            (v.length >= MIN_PASSWORD_LENGTH &&
                    PASSWORD_RULE_PATTERN.test(v)) ||
                'Некорректный пароль. Необходимо указать 8 латинских символов';
        break;
    case PASSWORD_NEW_INPUT_ITEM_ID:
        result.passwordSyntaxValidation = (v) =>
            (v.length >= MIN_PASSWORD_LENGTH &&
                    PASSWORD_RULE_PATTERN.test(v)) ||
                'Некорректный пароль. Необходимо указать 8 латинских символов';
        break;
    case PASSWORD_NEWREPEAT_INPUT_ITEM_ID:
        result.equalsToOldPassword = (v) =>
            getValues(PASSWORD_NEW_INPUT_ITEM_ID) === v ||
            PASSWORD_MISMATCH_ERROR_MESSAGE;
        break;

    default:
        break;
    }
    return result;
};

function RegistrationInput({
    register,
    getValues,
    itemId,
    itemLabel,
    itemType,
    itemPlaceholder,
    error,
    className
}) {
    return (
        <InputWrapper className={className}>
            <RegistrationFormLabel htmlItemFor={itemId} label={itemLabel} />
            <Input
                id={itemId}
                name={itemId}
                type={itemType}
                placeholder={itemPlaceholder}
                {...register(itemId, {
                    required: `Поле не может быть пустым`,
                    validate: getValidationFunc(itemId, getValues)
                })}
            />
            {error && <Error>{error}</Error>}
        </InputWrapper>
    );
}

RegistrationInput.propTypes = {
    itemId: PropTypes.string,
    itemLabel: PropTypes.string,
    itemType: PropTypes.string,
    itemPlaceholder: PropTypes.string,
    error: PropTypes.string,
    getValues: PropTypes.func,
    register: PropTypes.func,
    className: PropTypes.string
};

export default RegistrationInput;
