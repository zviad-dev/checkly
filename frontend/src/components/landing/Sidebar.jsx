import styled from 'styled-components';
import { FaTimes } from 'react-icons/fa';
import { Link as LinkS } from 'react-scroll';
import { Link as LinkR } from 'react-router-dom';
import PropTypes from 'prop-types';
import {
    Button,
    ButtonsKind,
    ButtonWrap as BtnWrap,
    ButtonsSize
} from '../common/Button';

const SidebarContainer = styled.aside`
    position: fixed;
    z-index: 999;
    width: 100%;
    height: 100%;
    background: ${({ theme }) => theme.colors.black};
    display: grid;
    align-items: center;
    top: 0;
    left: 0;
    transition: all 0.2s ease-in-out;
    opacity: ${({ isOpen }) => (isOpen ? '100%' : '0')};
    top: ${({ isOpen }) => (isOpen ? '0' : '-100%')};
`;

const Icon = styled.div`
    position: absolute;
    top: 1.2rem;
    right: 1.5rem;
    background: transparent;
    font-size: 2rem;
    cursor: pointer;
    outline: none;
`;

const CloseIcon = styled(FaTimes)`
    color: ${({ theme }) => theme.colors.lightWhite};
`;

const SidebarWrapper = styled.div`
    color: ${({ theme }) => theme.colors.lightWhite};
`;

const SidebarMenu = styled.ul`
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: repeat(6, 80px);
    text-align: center;
    color: ${({ theme }) => theme.colors.blue};
    font-size: 2.5rem;

    @media ${({ theme }) => theme.media.small} {
        grid-template-rows: repeat(6, 60px);
    }
`;

const SidebarLink = styled(LinkS)`
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1.5rem;
    text-decoration: none;
    list-style: none;
    transition: 0.2s ease-in-out;
    color: ${({ theme }) => theme.colors.lightWhite};
    cursor: pointer;

    &:hover {
        color: ${({ theme }) => theme.colors.blue};
        transition: 0.2s ease-in-out;
    }
`;

const SIDEBAR_LINKS = [
    { name: 'about', trl: 'О продукте' },
    { name: 'testimonials', trl: 'Отзывы' },
    { name: 'tariff', trl: 'Тарифы' },
    { name: 'team', trl: 'Команда' }
];

const ButtonWrap = styled(BtnWrap)`
    justify-content: center;
`;

function Sidebar({ isOpen, toggle }) {
    return (
        <SidebarContainer isOpen={isOpen} onClick={toggle}>
            <Icon onClick={toggle}>
                <CloseIcon />
            </Icon>
            <SidebarWrapper>
                <SidebarMenu>
                    CHECKLY
                    {SIDEBAR_LINKS.map((link) => (
                        <SidebarLink
                            to={link.name}
                            onClick={toggle}
                            key={link.name}
                        >
                            {link.trl}
                        </SidebarLink>
                    ))}
                </SidebarMenu>
                <ButtonWrap>
                    <LinkR to="/signin">
                        <Button
                            kind={ButtonsKind.secondary}
                            size={ButtonsSize.s}
                        >
                            Войти
                        </Button>
                    </LinkR>
                </ButtonWrap>
            </SidebarWrapper>
        </SidebarContainer>
    );
}

Sidebar.propTypes = {
    toggle: PropTypes.func,
    isOpen: PropTypes.bool
};

export default Sidebar;
