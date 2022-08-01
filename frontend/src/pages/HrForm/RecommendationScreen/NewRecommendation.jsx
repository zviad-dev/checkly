import styled from 'styled-components';
import PropTypes from 'prop-types';
import { useState } from 'react';
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
import { RECOMENDATION_DATA } from '../hrFormData';

function NewRecommendation({ surveyId, loadData }) {
    const [inputValue, setInputValue] = useState({
        first_name: '',
        last_name: '',
        email: ''
    });
    const recruiterId = useAuth().user.id;

    const handleRecommendationsChange = (event) => {
        setInputValue({
            ...inputValue,
            [event.target.name]: event.target.value
        });
    };

    const onSubmit = () => {
        if (!inputValue) {
            return;
        }
        API.post(
            `recruiters/${recruiterId}/surveys/${surveyId}/recommendations`,
            [inputValue]
        )
            .then(() => {
                loadData();
                setInputValue({
                    first_name: '',
                    last_name: '',
                    email: ''
                });
            })
            .catch((error) => {
                console.log(error.message);
            });
    };

    return (
        <Container>
            <RecommendationWrapper>
                {RECOMENDATION_DATA.map((item) => (
                    <InputWrap>
                        <Input
                            id={item.id}
                            type={item.type}
                            name={item.id}
                            placeholder={item.placeholder}
                            onChange={handleRecommendationsChange}
                            value={inputValue[item.id]}
                        />
                    </InputWrap>
                ))}

                <Button
                    kind={ButtonsKind.tertiary}
                    size={ButtonsSize.m}
                    type="button"
                    onClick={onSubmit}
                >
                    Добавить
                </Button>
            </RecommendationWrapper>
        </Container>
    );
}

export default NewRecommendation;

NewRecommendation.propTypes = {
    surveyId: PropTypes.number,
    loadData: PropTypes.func
};

const Container = styled.div`
    flex-direction: column;
    display: flex;
    width: 100%;
`;

const RecommendationWrapper = styled.div`
    flex-direction: row;
    display: flex;
    width: 100%;
    gap: 20px;
`;

const InputWrap = styled(InputWrapper)`
    width: inherit;
`;
