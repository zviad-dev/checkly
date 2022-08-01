import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { Dialog, DialogContent, DialogTitle } from '@mui/material';
import PropTypes from 'prop-types';
import styled from 'styled-components';
import API from '../../api/axios';
import {
    Button,
    ButtonsKind,
    ButtonsSize
} from '../../components/common/Button';
import { Input } from '../../components/application/FormElements';
import { Error } from '../../components/application/ErrorElements';
import { FormRec } from '../../components/application/FormPageElements';

function TemplateCreateDialog({ userId, open, handleClose, loadData }) {
    const [apiError, setApiError] = useState({});

    const {
        register,
        handleSubmit,
        formState: { errors }
    } = useForm({
        shouldUnregister: true,
        defaultValues: { label: '' }
    });

    const handleSubmitApi = (data) => {
        API.post(`recruiters/${userId}/templates`, {
            name: data.name
        })
            .then(() => {
                handleClose();
                loadData();
            })
            .catch((error) => {
                setApiError({
                    message:
                        error?.response?.data?.reason === 'EMPTY_TEMPLATE_NAME'
                            ? 'Имя шаблона пустое'
                            : 'Ошибка создания шаблона регистрации'
                });
                // eslint-disable-next-line no-console
                console.error(error.message);
            });
    };

    return (
        <Dialog open={open} onClose={handleClose}>
            <DialogTitleWrapper>Создание шаблона</DialogTitleWrapper>
            <DialogContent>
                <FormRec
                    style={{ padding: '10px' }}
                    onSubmit={handleSubmit(handleSubmitApi)}
                >
                    <Input
                        id="name"
                        name="Имя"
                        type="text"
                        placeholder="Введите имя шаблона"
                        {...register('name', {
                            required: `Поле не может быть пустым`
                        })}
                    />
                    <Error>{errors.name?.message}</Error>
                    <Error>{apiError.message}</Error>
                    <ButtonSection>
                        <Button
                            kind={ButtonsKind.quaternary}
                            size={ButtonsSize.m}
                            type="button"
                            onClick={handleClose}
                        >
                            Отмена
                        </Button>
                        <Button
                            kind={ButtonsKind.tertiary}
                            size={ButtonsSize.m}
                        >
                            Создать
                        </Button>
                    </ButtonSection>
                </FormRec>
            </DialogContent>
        </Dialog>
    );
}

TemplateCreateDialog.propTypes = {
    userId: PropTypes.number,
    open: PropTypes.bool,
    handleClose: PropTypes.func,
    loadData: PropTypes.func
};

export default TemplateCreateDialog;

const ButtonSection = styled.div`
    display: flex;
    flex-direction: row;
    gap: 5px;
`;

const DialogTitleWrapper = styled(DialogTitle)`
    align-self: center;
`;
