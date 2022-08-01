import { Link as LinkR } from 'react-router-dom';
import styled from 'styled-components';
import PropTypes from 'prop-types';
import { useEffect, useRef } from 'react';
import API from '../../api/axios';
import Astronaut from '../../assets/astronaut.svg';
import useAuth from '../../userData/useAuth';

function EventDropDownNotice({
    events,
    loadEvents,
    userId,
    refOutNotice,
    isNoticeVisible,
    setNoticeVisible
}) {
    const recruiterId = useAuth().user.id;
    const ref = useRef();

    useEffect(() => {
        const checkIfClickedOutside = (e) => {
            if (
                isNoticeVisible &&
                ref.current &&
                !ref.current.contains(e.target) &&
                !refOutNotice.current.contains(e.target)
            ) {
                setNoticeVisible(false);
            }
        };

        document.addEventListener('mousedown', checkIfClickedOutside);

        return () => {
            document.removeEventListener('mousedown', checkIfClickedOutside);
        };
    }, [isNoticeVisible]);

    const deleteAllEvents = () => {
        API.delete(`recruiters/${userId}/events`)
            .then(() => loadEvents())
            .catch((err) => {
                console.error(err.message);
            });
    };

    const viewEvents = (idArr) => {
        API.post(`recruiters/${userId}/events/view`, { ids: idArr })
            .then(() => {
                loadEvents();
            })
            .catch((err) => {
                console.error(err.message);
            });
    };

    return (
        <DropdownMenu ref={ref}>
            {events?.length > 0 ? (
                <>
                    <MenuHeader>
                        <ClearButton onClick={deleteAllEvents}>
                            Очистить
                        </ClearButton>
                    </MenuHeader>
                    {events.map((item) => (
                        <ItemWrapper key={item.id}>
                            <NoticeWrapper
                                viewed={item.viewed}
                                onClick={() => viewEvents([item.id])}
                            >
                                <Icon src={Astronaut} />
                                {item.recommendationId ? (
                                    <Info onClick={() => viewEvents([item.id])}>
                                        <Header
                                            to={`/recruiters/${recruiterId}/surveys/${item.surveyId}/recommendations/${item.recommendationId}`}
                                        >
                                            {item.message}
                                        </Header>
                                        <CandidateFio>
                                            Кандидат: {item.candidateFio}
                                        </CandidateFio>
                                        <RecommendationFio>
                                            Рекомендатель: {item.recommenderFio}
                                        </RecommendationFio>
                                        <Date>{item.created}</Date>
                                    </Info>
                                ) : (
                                    <Info>
                                        <Header
                                            to={`/recruiters/${recruiterId}/surveys/${item.surveyId}`}
                                        >
                                            {item.message}
                                        </Header>
                                        <Date>{item.created}</Date>
                                    </Info>
                                )}
                            </NoticeWrapper>
                        </ItemWrapper>
                    ))}
                </>
            ) : (
                <ItemWrapper>Нет новых уведомлений</ItemWrapper>
            )}
        </DropdownMenu>
    );
}

EventDropDownNotice.propTypes = {
    events: PropTypes.arrayOf(PropTypes.shape),
    loadEvents: PropTypes.func,
    userId: PropTypes.number,
    refOutNotice: PropTypes.func,
    isNoticeVisible: PropTypes.bool,
    setNoticeVisible: PropTypes.func
};

const DropdownMenu = styled.div`
    width: 400px;
    max-height: 400px;
    position: absolute;
    top: 60px;
    right: 0px;
    list-style: none;
    background: #f8faff;
    border-radius: 6px;
    font-size: 12px;
    overflow: auto;
`;

const MenuHeader = styled.div`
    padding: 0.5rem;
    color: ${({ theme }) => theme.colors.blue};
    font-size: 14px;
    display: flex;
    flex-direction: row-reverse;
`;

const ItemWrapper = styled.div`
    padding: 0.5rem;
    border-bottom: 1px solid ${({ theme }) => theme.colors.lightGrey};
`;

const NoticeWrapper = styled.div`
    display: flex;
    align-items: center;
    opacity: ${({ viewed }) => (viewed ? '50%' : '100%')};
`;

const ClearButton = styled.div`
    cursor: pointer;

    &:hover {
        color: ${({ theme }) => theme.colors.darkBlue};
    }
`;

const Icon = styled.img`
    background: ${({ theme }) => theme.colors.darkBlue};
    height: 40px;
    width: 40px;
    left: 0;
    border-radius: 50%;
    margin-right: 0.5rem;
`;

const Info = styled.div`
    width: 100%;
`;

const Header = styled(LinkR)`
    text-decoration: none;
    color: ${({ theme }) => theme.colors.blue};

    &:hover {
        color: ${({ theme }) => theme.colors.darkBlue};
    }
`;

const CandidateFio = styled.div`
    margin-top: 2px;
`;

const RecommendationFio = styled.div``;

const Date = styled.div`
    margin-top: 2px;
    color: #999999;
`;

export default EventDropDownNotice;
