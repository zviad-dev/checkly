import styled from 'styled-components';
import PropTypes from 'prop-types';
import { getValidationFunc } from '../registration/RegistrationInput';
import {
    Input,
    InputWrapper,
    FormLabel
} from '../../components/application/FormElements';
import {
    Error,
    ErrorWrapper
} from '../../components/application/ErrorElements';
import SubmitButton from './SubmitButton';

function EditForm({
    item,
    errors,
    index,
    userData,
    register,
    getValues,
    apiErorr,
    isLoading,
    apiSuccess,
    handleSubmit,
    handleSubmitApi,
    onCloseClick
}) {
    return (
        <Form onSubmit={handleSubmit(handleSubmitApi)}>
            {item.control.map((elem, idx) => (
                <InputWrapp key={elem.text}>
                    <FormLbl htmlFor={idx}>{elem.text}</FormLbl>
                    <InputAndError>
                        <InputForm
                            id={idx}
                            defaultValue={elem.dto ? userData[elem.dto] : ''}
                            type={elem.type}
                            placeholder="Введите текст"
                            {...register(elem.key, {
                                required: `${elem.text} не может быть пустым`,
                                validate: getValidationFunc(elem.key, getValues)
                            })}
                        />
                        {errors[elem.key]?.message && (
                            <ErrorWrapper>
                                <Error>{errors[elem.key]?.message}</Error>
                            </ErrorWrapper>
                        )}
                    </InputAndError>
                </InputWrapp>
            ))}

            {(apiSuccess || apiErorr) && (
                <NoticeWrapper>
                    {apiErorr && <Error>{apiErorr.message}</Error>}
                    {apiSuccess && <Success>Изменения сохранены!</Success>}
                </NoticeWrapper>
            )}
            <SubmitButton
                error={apiErorr}
                loading={isLoading}
                onCloseClick={onCloseClick}
                index={index}
                apiSuccess={apiSuccess}
            />
        </Form>
    );
}

export default EditForm;

EditForm.propTypes = {
    errors: PropTypes.objectOf,
    index: PropTypes.number,
    item: PropTypes.objectOf,
    userData: PropTypes.objectOf,
    register: PropTypes.func,
    getValues: PropTypes.func,
    apiErorr: PropTypes.objectOf,
    isLoading: PropTypes.bool,
    apiSuccess: PropTypes.bool,
    handleSubmit: PropTypes.func,
    handleSubmitApi: PropTypes.func,
    onCloseClick: PropTypes.func
};

const InputWrapp = styled(InputWrapper)`
    display: flex;
    flex-direction: row;
    align-items: baseline;

    @media ${({ theme }) => theme.media.small} {
        flex-direction: column;
    }
`;

const InputAndError = styled.div`
    display: flex;
    flex-direction: column;
    width: 300px;

    @media ${({ theme }) => theme.media.medium} {
        width: 100%;
    }
`;

const FormLbl = styled(FormLabel)`
    margin-right: 20px;
    font-size: 1rem;
    width: 200px;
`;

const InputForm = styled(Input)`
    font-size: 1rem;
    border: 1px solid #babdbf;
`;

const NoticeWrapper = styled(ErrorWrapper)`
    text-align: center;
`;

const Form = styled.form`
    display: flex;
    flex-direction: column;
    width: 100%;
    position: relative;
    padding: 1rem;
    border-bottom: 1px solid ${({ theme }) => theme.colors.lightGrey};
`;

const Success = styled.li`
    list-style: none;
    color: ${({ theme }) => theme.colors.green};
    font-size: 14px;
`;
