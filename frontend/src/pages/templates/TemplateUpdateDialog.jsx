import { useEffect, useState } from 'react';
import {
    Dialog,
    DialogContent,
    DialogTitle,
    IconButton,
    LinearProgress
} from '@mui/material';
import PropTypes from 'prop-types';
import { DataGrid } from '@mui/x-data-grid';
import DeleteIcon from '@mui/icons-material/Delete';
import styled from 'styled-components';
import {
    Button,
    ButtonsKind,
    ButtonsSize
} from '../../components/common/Button';
import { FormLabel, Input } from '../../components/application/FormElements';
import API from '../../api/axios';
import { Error } from '../../components/application/ErrorElements';

const EMPTY_TEMPLATE_NAME = 'Имя шаблона пустое';
const EMPTY_QUESTION_NAME = 'Имя вопроса пустое';

function TemplateUpdateDialog({
    name,
    questions,
    open,
    handleClose,
    loadData,
    userId,
    templateId
}) {
    const [apiError, setApiError] = useState({});
    const [inputValue, setInputValue] = useState({});
    const [newQuestionValue, setNewQuestionValue] = useState({});
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        setInputValue(name);
    }, [name]);

    const updateQuestion = (id, newVal) => {
        setIsLoading(true);
        API.put(
            `recruiters/${userId}/templates/${templateId}/questions/${id}`,
            { question: newVal }
        )
            .then(() => {
                loadData();
            })
            .catch((error) => {
                setApiError({
                    message:
                        error?.response?.data?.reason === 'EMPTY_QUESTION_NAME'
                            ? EMPTY_QUESTION_NAME
                            : 'Ошибка обновления вопроса'
                });
                // eslint-disable-next-line no-console
                console.error(error.message);
            })
            .finally(() => setIsLoading(false));
    };

    const onQuestionRowEditStopEvent = (params) => {
        updateQuestion(params.row.id, params.row.question);
    };

    const updateName = (id) => {
        setIsLoading(true);
        API.put(`recruiters/${userId}/templates/${id}/name`, {
            name: inputValue
        })
            .then(() => {
                loadData();
            })
            .catch((error) => {
                setApiError({
                    message:
                        error?.response?.data?.reason === 'EMPTY_TEMPLATE_NAME'
                            ? EMPTY_TEMPLATE_NAME
                            : 'Ошибка обновления шаблона'
                });
                // eslint-disable-next-line no-console
                console.error(error.message);
            })
            .finally(() => setIsLoading(false));
    };

    const addQuestion = () => {
        setIsLoading(true);
        API.post(`recruiters/${userId}/templates/${templateId}/questions`, [
            newQuestionValue
        ])
            .then(() => {
                loadData();
            })
            .catch((error) => {
                setApiError({
                    message:
                        error?.response?.data?.reason === 'EMPTY_QUESTION_NAME'
                            ? EMPTY_QUESTION_NAME
                            : 'Ошибка добавления вопроса'
                });
                // eslint-disable-next-line no-console
                console.error(error.message);
            })
            .finally(() => setIsLoading(false));
    };

    const deleteQuestion = (questionId) => {
        setIsLoading(true);
        API.delete(
            `recruiters/${userId}/templates/${templateId}/questions/${questionId}`,
            { name: inputValue }
        )
            .then(() => {
                loadData();
            })
            .catch((error) => {
                setApiError({
                    message: 'Ошибка удаления шаблона'
                });
                // eslint-disable-next-line no-console
                console.error(error.message);
            })
            .finally(() => setIsLoading(false));
    };

    const handleNameInputChange = (e) => {
        setInputValue(e.target.value);
    };

    const updateNameInput = () => {
        updateName(templateId);
    };

    const handleNewQuestionInputChange = (e) => {
        setNewQuestionValue(e.target.value);
    };

    const questionColumns = [
        {
            field: 'question',
            headerName: 'Вопрос',
            flex: 20,
            editable: true,
            renderCell: (params) => <div>{params.row.question}</div>
        },
        {
            field: ' ',
            headerName: ' ',
            flex: 1,
            renderCell: (params) => (
                <IconButton
                    aria-label="delete"
                    onClick={() => {
                        deleteQuestion(params.row.id);
                    }}
                >
                    <DeleteIcon />
                </IconButton>
            )
        }
    ];

    return (
        <Dialog open={open} onClose={handleClose} maxWidth="lg">
            <DialogTitleWrapper>Обновление шаблона</DialogTitleWrapper>
            <DialogContent>
                <FormLabel htmlFor="name">Имя</FormLabel>
                <Input
                    id="name"
                    name="Имя"
                    type="text"
                    defaultValue={inputValue}
                    placeholder="Введите имя шаблона"
                    onChange={handleNameInputChange}
                    onBlur={updateNameInput}
                    style={{ width: '100%', border: '1px solid #babdbf' }}
                />
                <DataGridWrapper>
                    <DataGrid
                        sx={{
                            '& .MuiDataGrid-cell:hover': {
                                cursor: 'pointer',
                                outline: 0
                            },
                            '.MuiDataGrid-cell:focus': {
                                outline: 0
                            },
                            '& .MuiDataGrid-columnHeader,  .MuiDataGrid-cell,  .MuiDataGrid-cellCheckbox':
                                {
                                    ':focus': {
                                        outline: 'none'
                                    }
                                },
                            background: '#fff'
                        }}
                        className="datagrid"
                        rows={questions}
                        columns={questionColumns}
                        pageSize={4}
                        rowsPerPageOptions={[4]}
                        disableColumnMenu
                        disableSelectionOnClick
                        getRowId={(row) => row.id}
                        editMode="row"
                        onRowEditStop={onQuestionRowEditStopEvent}
                    />
                </DataGridWrapper>
                <NewQuestionWrapper>
                    <Input
                        id="newQuestionName"
                        name="Имя"
                        type="text"
                        placeholder="Введите новый вопрос"
                        onChange={handleNewQuestionInputChange}
                        style={{
                            width: '100%',
                            border: '1px solid #babdbf'
                        }}
                    />
                    <Button
                        kind={ButtonsKind.quaternary}
                        size={ButtonsSize.m}
                        type="button"
                        style={{
                            width: '40px',
                            padding: '10px',
                            marginLeft: '10px'
                        }}
                        onClick={addQuestion}
                    >
                        +
                    </Button>
                </NewQuestionWrapper>
                <Error>{apiError.message}</Error>

                <OkButtonSection>
                    {isLoading && (
                        <LinearProgress
                            style={{ width: '100%', marginRight: '10px' }}
                            color="secondary"
                        />
                    )}
                    <Button
                        kind={ButtonsKind.tertiary}
                        size={ButtonsSize.m}
                        type="button"
                        onClick={handleClose}
                    >
                        OK
                    </Button>
                </OkButtonSection>
            </DialogContent>
        </Dialog>
    );
}

TemplateUpdateDialog.propTypes = {
    userId: PropTypes.number,
    templateId: PropTypes.number,
    open: PropTypes.bool,
    loadData: PropTypes.func,
    handleClose: PropTypes.func,
    name: PropTypes.string,
    questions: PropTypes.arrayOf(PropTypes.shape)
};

export default TemplateUpdateDialog;

const DataGridWrapper = styled.div`
    width: 700px;
    height: 320px;
    margin-top: 15px;
`;

const NewQuestionWrapper = styled.div`
    display: flex;
    flex-direction: row;
    margin-top: 10px;
`;

const OkButtonSection = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
    align-content: center;
    align-items: center;
`;

const DialogTitleWrapper = styled(DialogTitle)`
    align-self: center;
`;
