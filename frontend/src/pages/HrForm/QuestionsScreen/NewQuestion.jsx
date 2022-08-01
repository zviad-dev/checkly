import styled from 'styled-components';
import PropTypes from 'prop-types';
import { useState, useEffect } from 'react';
import Select from 'react-select';
import {
    Button,
    ButtonsKind,
    ButtonsSize
} from '../../../components/common/Button';
import {
    Input,
    InputWrapper
} from '../../../components/application/FormElements';
import useAuth from '../../../userData/useAuth';
import API from '../../../api/axios';

function NewQuestion({ surveyId, loadData }) {
    const [inputValue, setInputValue] = useState('');
    const [option, setOption] = useState([]);
    const recruiterId = useAuth().user.id;

    useEffect(() => {
        API.get(`recruiters/${recruiterId}/templates`)
            .then(({ data }) => {
                setOption(data);
            })
            .catch((error) => console.log(error.message));
    }, []);

    const handleQuestionsChange = (event) => {
        setInputValue(event.target.value);
    };

    const onSubmit = () => {
        if (!inputValue) {
            return;
        }
        API.post(`recruiters/${recruiterId}/surveys/${surveyId}/questions`, [
            inputValue
        ])
            .then(() => {
                loadData();
                setInputValue('');
            })
            .catch((error) => {
                console.log(error.message);
            });
    };

    const handleSelect = (templateId) => {
        API.post(
            `recruiters/${recruiterId}/surveys/${surveyId}/questions/from-template/${templateId}`
        )
            .then(() => {
                loadData();
            })
            .catch((error) => {
                console.log(error.message);
            });
    };

    return (
        <Container>
            <QuestionWrapper>
                <InputWrap>
                    <Input
                        id="New"
                        type="text"
                        name="New"
                        placeholder="Введите вопрос"
                        onChange={handleQuestionsChange}
                        value={inputValue}
                    />
                </InputWrap>
                <Button
                    kind={ButtonsKind.tertiary}
                    size={ButtonsSize.m}
                    type="button"
                    onClick={onSubmit}
                >
                    Добавить
                </Button>
            </QuestionWrapper>
            <SelectorWrapper>
                <Selector
                    classNamePrefix="Select"
                    placeholder="Выберите вопросы из шаблона"
                    options={option.map((opt) => ({
                        label: opt.name,
                        value: opt.id
                    }))}
                    onChange={(event) => {
                        handleSelect(event.value);
                    }}
                />
            </SelectorWrapper>
        </Container>
    );
}

export default NewQuestion;

NewQuestion.propTypes = {
    surveyId: PropTypes.number,
    loadData: PropTypes.func
};

const Container = styled.div`
    flex-direction: column;
    display: flex;
    width: 100%;
`;

const QuestionWrapper = styled.div`
    flex-direction: row;
    display: flex;
    width: 100%;
    gap: 20px;
`;

const InputWrap = styled(InputWrapper)`
    width: inherit;
`;

const SelectorWrapper = styled.div`
    margin-bottom: 20px;
`;

const Selector = styled(Select)`
    .Select__control {
        width: 100%;
        border: 1px solid #fff;
        border-radius: 4px;
        cursor: pointer;
        font-size: 13px;
        padding: 5px;
        min-height: 50px;
    }

    .Select__control:hover {
        border: 1px solid #babdbf;
    }

    .Select__control--is-focused {
        box-shadow: 0 0 0 0px #babdbf;
        outline: none;
    }

    .Select__option--is-focused {
        background: #f5f5f5;
        transition: all 0.4s ease;
    }

    .Select__multi-value {
        background: #f5f5f5;
        padding: 4px;
        border-radius: 4px;
        font-size: 16px;
    }

    .Select__menu {
        font-size: 16px;
    }
`;
