import styled from 'styled-components';
import PropTypes from 'prop-types';
import { useState } from 'react';
import Remove from '../../../assets/remove.svg';
import API from '../../../api/axios';
import {
    Input,
    InputWrapper
} from '../../../components/application/FormElements';
import useAuth from '../../../userData/useAuth';
import { RECOMENDATION_DATA } from '../hrFormData';

function RecommendationList({
    recommenders,
    deleteRecommendationHandler,
    setApiError,
    setIsLoading,
    loadData,
    surveyId
}) {
    const recruiterId = useAuth().user.id;
    const [idToEdit, setEdit] = useState(null);
    const [inputValue, setInputValue] = useState({
        first_name: '',
        last_name: '',
        email: ''
    });

    const editRecommendation = (event, id) => {
        setEdit(id);
        setInputValue({
            first_name: recommenders.find((rec) => rec.id === id).first_name,
            last_name: recommenders.find((rec) => rec.id === id).last_name,
            email: recommenders.find((rec) => rec.id === id).email
        });
    };

    const handleRecommendationsChange = (event) => {
        setInputValue({
            ...inputValue,
            [event.target.name]: event.target.value
        });
    };

    const saveRecommendation = (recommendationId) => {
        API.put(
            `recruiters/${recruiterId}/surveys/${surveyId}/recommendations/${recommendationId}`,
            inputValue
        )
            .then(() => {
                loadData();
                setApiError(null);
            })
            .catch((error) => {
                setApiError({
                    message: 'Ошибка отправки изменений рекомендателя'
                });
                console.log(error.message);
            })
            .finally(() => {
                setIsLoading(false);
            });
    };

    return (
        <Container>
            {recommenders.map((item) => (
                <Element key={item.id}>
                    {idToEdit === item.id
                        ? RECOMENDATION_DATA.map((point) => (
                            <RecommendationInput
                                name={point.id}
                                onChange={handleRecommendationsChange}
                                onBlur={() => {
                                    saveRecommendation(item.id);
                                }}
                                value={inputValue[point.id]}
                            />
                        ))
                        : RECOMENDATION_DATA.map((elem) => (
                            <RecommendationInput
                                readOnly
                                id={elem.id}
                                type={elem.type}
                                name={elem.id}
                                placeholder={elem.placeholder}
                                onClick={(event) =>
                                    editRecommendation(event, item.id)
                                }
                                value={item[elem.id]}
                            />
                        ))}
                    <RemoveIcon
                        src={Remove}
                        onClick={() => deleteRecommendationHandler(item.id)}
                    />
                </Element>
            ))}
        </Container>
    );
}

export default RecommendationList;

RecommendationList.propTypes = {
    deleteRecommendationHandler: PropTypes.func,
    recommenders: PropTypes.arrayOf(PropTypes.shape),
    setApiError: PropTypes.func,
    setIsLoading: PropTypes.func,
    loadData: PropTypes.func,
    surveyId: PropTypes.number
};

const Container = styled.div`
    overflow: hidden;
    margin-bottom: 1rem;
`;

const Element = styled(InputWrapper)`
    flex-direction: row;
    display: flex;
    width: 100%;
`;

const RecommendationInput = styled(Input)`
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    width: inherit;
    margin-right: 20px;
`;

const RemoveIcon = styled.img`
    opacity: 0.3;

    &:hover {
        opacity: 0.8;
    }
`;
