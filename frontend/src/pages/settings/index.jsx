import styled from 'styled-components';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import PageHeader from '../../components/application/PageHeader';
import { Container, Title } from '../../components/application/PageElements';
import useAuth from '../../userData/useAuth';
import API from '../../api/axios';
import { userSettingsData } from './UserSettingsData';
import EditForm from './EditForm';

function Settings() {
    const auth = useAuth();

    const userData = useAuth().user;
    const userId = useAuth().user.id;
    const [clicked, setClicked] = useState(false);
    const [apiErorr, setApiError] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [path, setPath] = useState();
    const [apiSuccess, setApiSuccess] = useState(null);

    const {
        register,
        handleSubmit,
        formState: { errors },
        clearErrors,
        getValues
    } = useForm({
        shouldUnregister: true,
        defaultValues: { label: '' }
    });

    const handleSubmitApi = (data) => {
        setIsLoading(true);

        if (path === 'password') {
            // eslint-disable-next-line no-param-reassign
            delete data.repeat_password;
        }

        API.put(`recruiter/${userId}/settings/${path}`, data)
            .then(() => {
                setApiError(null);
                clearErrors();
                setApiSuccess(true);
                auth.loadData();
            })
            .catch((error) => {
                setApiError({ message: 'Ошибка отправки ответа' });
                setApiSuccess(false);

                console.log(error.message);
            })
            .finally(() => {
                setIsLoading(false);
            });
    };

    const toggle = (index, way) => {
        setPath(way);
        if (clicked === index) {
            return setClicked(null);
        }

        return setClicked(index);
    };

    const onCloseClick = (index) => {
        toggle(index);
        setApiSuccess(false);
    };

    return (
        <>
            <PageHeader />
            <Container>
                <Title>Настройки</Title>
                <AccordionWrapper>
                    {userSettingsData.map((item, index) => (
                        <AccordionItem>
                            <Accordion key={item.title}>
                                <AccordionText>{item.title}</AccordionText>
                                <AccordionText>
                                    {item.info && item.info}
                                    {item.data === 'name'
                                        ? `${userData.last_name} ${userData.first_name} ${userData.company_name}`
                                        : userData[item.data]}
                                </AccordionText>
                                <AccordionText>
                                    {clicked !== index && (
                                        <EditButton
                                            onClick={() =>
                                                toggle(index, item.path)
                                            }
                                        >
                                            Изменить
                                        </EditButton>
                                    )}
                                </AccordionText>
                            </Accordion>
                            {clicked === index && (
                                <Dropdown>
                                    <EditForm
                                        item={item}
                                        errors={errors}
                                        index={index}
                                        userData={userData}
                                        register={register}
                                        getValues={getValues}
                                        apiErorr={apiErorr}
                                        isLoading={isLoading}
                                        apiSuccess={apiSuccess}
                                        handleSubmit={handleSubmit}
                                        handleSubmitApi={handleSubmitApi}
                                        onCloseClick={onCloseClick}
                                    />
                                </Dropdown>
                            )}
                        </AccordionItem>
                    ))}
                </AccordionWrapper>
            </Container>
        </>
    );
}

export default Settings;

const AccordionWrapper = styled.div`
    background: ${({ theme }) => theme.colors.white};
    border-radius: 6px;
    max-width: 800px;
`;

const AccordionItem = styled.div``;

const Accordion = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    text-align: center;
    margin-top: 1rem;
    border-bottom: 1px solid ${({ theme }) => theme.colors.lightGrey};
    padding: 1rem;

    @media ${({ theme }) => theme.media.small} {
        flex-direction: column;
        gap: 10px;
    }
`;

const AccordionText = styled.div``;

const EditButton = styled.button`
    color: ${({ theme }) => theme.colors.blue};
    cursor: pointer;
    padding: 0;
    border: none;
    background: none;
    font-size: 1rem;

    &:hover {
        color: ${({ theme }) => theme.colors.lightBlue};
    }
`;

const Dropdown = styled.div``;
