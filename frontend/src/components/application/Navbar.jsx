import styled from 'styled-components';
import { useCallback, useEffect, useState, useRef } from 'react';
import { BsChevronDown } from 'react-icons/bs';
import Bell from '../../assets/bell.svg';
import Astronaut from '../../assets/astronaut.svg';
import DropDownSettings from './DropDownSettings';
import useAuth from '../../userData/useAuth';
import API, { BASE_WS_URL } from '../../api/axios';
import EventDropDownNotice from './EventDropDownNotice';

const WS_REOPEN_TIMEOUT = 5000;

function Navbar() {
    const [isSettingsVisible, setSettingsVisible] = useState(false);
    const userData = useAuth().user;
    const [events, setEvents] = useState([]);
    const [newEventsCnt, setNewEventsCnt] = useState(0);
    const [isNoticeVisible, setNoticeVisible] = useState(false);
    const [socket, setSocket] = useState(null);

    const refOutSettings = useRef();
    const refOutNotice = useRef();

    const setNewEventCounter = (eventArr) =>
        setNewEventsCnt(eventArr.filter((v) => !v.viewed).length);

    const loadEvents = useCallback(() => {
        API.get(`recruiters/${userData.id}/events`)
            .then(({ data }) => {
                setEvents(data);
                setNewEventCounter(data);
            })
            .catch((err) => {
                console.error(err.message);
            });
    }, [userData.id]);

    useEffect(() => loadEvents(), [userData.id, loadEvents]);

    const startWS = useCallback(() => {
        if (!socket) {
            const ws = new WebSocket(
                `${BASE_WS_URL}/events/${userData.id}/new_event_notification`
            );
            ws.onmessage = () => {
                console.debug('new_event_notification WS event');
                loadEvents();
                setSocket(ws);
            };
            ws.onclose = () => {
                setSocket(null);
                setTimeout(startWS, WS_REOPEN_TIMEOUT);
            };
        }
    }, [loadEvents, userData.id, socket]);

    useEffect(() => startWS(), [startWS]);

    return (
        <NavbarContainer>
            <NavbarWrapper>
                <LogoText>Checkly</LogoText>
                <ProfileWrapper>
                    <Notice
                        ref={refOutNotice}
                        onClick={() => setNoticeVisible(!isNoticeVisible)}
                    >
                        <LogoNotice src={Bell} />
                        {!!newEventsCnt && <Counter>{newEventsCnt}</Counter>}
                        {isNoticeVisible && (
                            <EventDropDownNotice
                                loadEvents={loadEvents}
                                events={events}
                                userId={userData.id}
                                refOutNotice={refOutNotice}
                                isNoticeVisible={isNoticeVisible}
                                setNoticeVisible={setNoticeVisible}
                            />
                        )}
                    </Notice>
                    <Profile
                        ref={refOutSettings}
                        onClick={() => setSettingsVisible(!isSettingsVisible)}
                    >
                        <LogoProfile src={Astronaut} />
                        <ChevronDown />
                        <ProfileText>
                            <Name>
                                {`${userData.last_name} ${userData.first_name}`}
                            </Name>
                            <Work>{userData.company_name}</Work>
                        </ProfileText>
                    </Profile>
                </ProfileWrapper>
                {isSettingsVisible && (
                    <DropDownSettings
                        isSettingsVisible={isSettingsVisible}
                        setSettingsVisible={setSettingsVisible}
                        refOut={refOutSettings}
                    />
                )}
            </NavbarWrapper>
        </NavbarContainer>
    );
}

export default Navbar;

const NavbarContainer = styled.header`
    height: 70px;
    width: calc(100% - 78px);
    z-index: 99;
    left: 78px;
    position: relative;
    background: #f8faff;
    border-bottom: 1px solid ${({ theme }) => theme.colors.lightGrey};
`;

const NavbarWrapper = styled.div`
    padding: 15px 20px;
    display: flex;
    align-items: center;
    justify-content: space-between;
`;

const LogoText = styled.div`
    color: ${({ theme }) => theme.colors.blue};
    font-size: 1.5rem;
    font-weight: 700;
    margin-right: 20px;
`;

const ProfileWrapper = styled.div`
    display: flex;
    align-items: center;
`;

const Profile = styled.div`
    display: flex;
    align-items: center;
`;

const ProfileText = styled.div`
    width: max-content;
    cursor: pointer;

    @media ${({ theme }) => theme.media.small} {
        display: none;
    }
`;

const LogoProfile = styled.img`
    background: ${({ theme }) => theme.colors.darkBlue};
    height: 40px;
    width: 40px;
    left: 0;
    border-radius: 50%;
    margin-right: 0.5rem;
    cursor: pointer;
`;

const Name = styled.div`
    font-size: 1rem;
`;

const ChevronDown = styled(BsChevronDown)`
    display: none;
    cursor: pointer;

    @media ${({ theme }) => theme.media.small} {
        display: block;
    }
`;

const Work = styled.div`
    font-size: 10px;
    color: ${({ theme }) => theme.colors.darkGrey};
`;

const Notice = styled.div`
    display: flex;
`;

const LogoNotice = styled.img`
    height: 40px;
    width: 40px;
    padding: 10px;
    box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.1);
    border-radius: 50%;
    cursor: pointer;
    margin-right: 10px;
`;

const Counter = styled.div`
    width: 15px;
    height: 15px;
    background-color: red;
    border-radius: 50%;
    color: ${({ theme }) => theme.colors.lightWhite};
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 10px;
    font-weight: bold;
    position: relative;
    top: 5px;
    right: 18px;
    cursor: pointer;
`;
