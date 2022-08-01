import { useState } from 'react';
import styled from 'styled-components';
import { Link as LinkR } from 'react-router-dom';
import LogoCheckly from '../../assets/logo.svg';
import Show from '../../assets/menu.svg';
import Grid from '../../assets/grid.svg';
import Questionary from '../../assets/questionary.svg';
import Recommendation from '../../assets/recommendation.svg';
import Support from '../../assets/support.svg';
import Profile from '../../assets/profile.jpg';

const SIDEBAR_LINKS = [
    { name: '/home', trl: 'Главная', icon: Grid },
    { name: '/templates', trl: 'Шаблоны', icon: Questionary },
    { name: '/recommendation', trl: 'Опросы', icon: Recommendation }
];

function Sidebar() {
    const [isSidebarVisible, setSidebarVisible] = useState();

    return (
        <SidebarContainer show={isSidebarVisible}>
            <LogoWrapper>
                <LogoIcon src={LogoCheckly} show={isSidebarVisible} />
                <LogoText show={isSidebarVisible}>Checkly</LogoText>
                <SidebarShowBtn
                    src={Show}
                    onClick={() => setSidebarVisible(!isSidebarVisible)}
                    show={isSidebarVisible}
                />
            </LogoWrapper>
            <SidebarWrapper>
                {SIDEBAR_LINKS.map((link) => (
                    <SidebarItem key={link.name}>
                        <SidebarLink to={link.name}>
                            <SidebarLinkIconWrapper>
                                <SidebarLinkIcon src={link.icon} />
                            </SidebarLinkIconWrapper>
                            <SidebarLinkText show={isSidebarVisible}>
                                {link.trl}
                            </SidebarLinkText>
                        </SidebarLink>
                    </SidebarItem>
                ))}
                <SupportWrapper
                    show={isSidebarVisible}
                    onClick={() => {
                        window.location = 'mailto:smat11@mail.ru';
                    }}
                >
                    <SupportContent show={isSidebarVisible}>
                        <SupportkIconProfile src={Profile} />
                        <SupportInfo show={isSidebarVisible}>
                            <SupportText>Служба поддержки</SupportText>
                            <SupportTextSmall>Всегда на связи</SupportTextSmall>
                        </SupportInfo>
                    </SupportContent>
                    <SupportIcon src={Support} show={isSidebarVisible} />
                </SupportWrapper>
            </SidebarWrapper>
        </SidebarContainer>
    );
}

export default Sidebar;

const SidebarContainer = styled.div`
    position: fixed;
    height: 100%;
    background: ${({ theme }) => theme.colors.darkBlue};
    padding: 6px 14px;
    z-index: 100;
    transition: all 0.5s ease;
    width: ${({ show }) => (show ? '250px' : '78px')};
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.25);
`;

const LogoWrapper = styled.div`
    height: 60px;
    display: flex;
    align-items: center;
    position: relative;
`;

export const LogoIcon = styled.img`
    height: 40px;
    width: 40px;
    transition: all 0.5s ease;
    opacity: ${({ show }) => (show ? '100%' : '0')};
    margin-right: 1rem;
`;

const LogoText = styled.div`
    transition: all 0.5s ease;
    opacity: ${({ show }) => (show ? '100%' : '0')};
    color: ${({ theme }) => theme.colors.lightWhite};
    font-size: 1.5rem;
    font-weight: 600;
    overflow: hidden;
`;

const SidebarShowBtn = styled.img`
    position: absolute;
    height: 24px;
    width: 24px;
    top: 50%;
    transform: translateY(-50%);
    transition: all 0.4s ease;
    cursor: pointer;
    transition: all 0.5s ease;
    right: ${({ show }) => (show ? '0' : '0.8rem')};
`;

const SidebarWrapper = styled.ul`
    margin-top: 20px;
`;

const SidebarItem = styled.li`
    position: relative;
    margin: 8px 0;
    list-style: none;
`;

const SidebarLink = styled(LinkR)`
    display: flex;
    border-radius: 12px;
    align-items: center;
    text-decoration: none;
    transition: all 0.4s ease;

    &:hover {
        background: ${({ theme }) => theme.colors.blue};
    }
`;

const SidebarLinkIconWrapper = styled.div`
    height: 50px;
    min-width: 50px;
    text-align: center;
    line-height: 63px;
    margin-right: 0.5rem;
`;

const SidebarLinkIcon = styled.img``;

const SidebarLinkText = styled.span`
    color: ${({ theme }) => theme.colors.lightWhite};
    font-size: 1rem;
    transition: 0.4s;
    opacity: ${({ show }) => (show ? '100%' : '0')};
    overflow: hidden;
`;

const SupportWrapper = styled.a`
    margin: 8px 0;
    list-style: none;
    position: fixed;
    height: 60px;
    width: 78px;
    left: 0;
    bottom: -8px;
    padding: 7px 14px;
    background: ${({ theme }) => theme.colors.blue};
    transition: all 0.5s ease;
    overflow: hidden;
    text-decoration: none;
    cursor: pointer;
    width: ${({ show }) => (show ? '250px' : '78px')};
`;

const SupportContent = styled.div`
    display: flex;
    align-items: center;
    flex-wrap: nowrap;
    transition: 0.4s;
    opacity: ${({ show }) => (show ? '100%' : '0')};
`;

const SupportkIconProfile = styled.img`
    height: 45px;
    width: 45px;
    object-fit: cover;
    border-radius: 6px;
    margin-right: 10px;
`;

const SupportInfo = styled.div`
    overflow: hidden;
`;

const SupportText = styled.p`
    font-size: 15px;
    color: ${({ theme }) => theme.colors.lightWhite};
    white-space: nowrap;
`;

const SupportTextSmall = styled(SupportText)`
    font-size: 12px;
`;

const SupportIcon = styled.img`
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    background: ${({ theme }) => theme.colors.blue};
    transition: all 0.5s ease;
    right: ${({ show }) => (show ? '0.8rem' : '1.8em')};
`;
