import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { Link as LinkR } from 'react-router-dom';
import { Link as LinkS, animateScroll as Scroll } from 'react-scroll';
import { FaBars } from 'react-icons/fa';
import PropTypes from 'prop-types';
import { Button, ButtonsKind, ButtonsSize } from '../common/Button';

const Nav = styled.nav`
    background: ${({ scrrolNav }) => (scrrolNav ? '#010606' : 'transparent')};
    height: 80px;
    margin-top: -80px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 1rem;
    position: sticky;
    top: 0;
    z-index: 10;

    @media ${({ theme }) => theme.media.medium} {
        transition: 0.8s all ease;
    }
`;

const NavbarContainer = styled.div`
    display: flex;
    justify-content: space-between;
    height: 80px;
    z-index: 1;
    width: 100%;
    padding: 0 1.5rem;
    max-width: 1200px;
`;

const NavLogo = styled(LinkR)`
    color: ${({ theme }) => theme.colors.lightWhite};
    justify-content: flex-start;
    cursor: pointer;
    font-size: 1.5rem;
    display: flex;
    align-items: center;
    margin-left: 1.5rem;
    font-weight: bold;
    text-decoration: none;
`;

const MobileIcon = styled.div`
    display: none;

    @media ${({ theme }) => theme.media.medium} {
        display: block;
        position: absolute;
        top: 0;
        right: 0;
        transform: translate(-100%, 60%);
        font-size: 2rem;
        cursor: pointer;
        color: ${({ theme }) => theme.colors.lightWhite};
    }
`;

const NavMenu = styled.ul`
    display: flex;
    align-items: center;
    list-style: none;
    text-align: center;
    margin-right: -1.5rem;

    @media ${({ theme }) => theme.media.medium} {
        display: none;
    }
`;

const NavLinks = styled(LinkS)`
    color: ${({ theme }) => theme.colors.lightWhite};
    display: flex;
    align-items: center;
    text-decoration: none;
    padding: 0 1rem;
    height: 100%;
    cursor: pointer;

    &.active {
        border-bottom: 0.2rem solid ${({ theme }) => theme.colors.blue};
    }
`;

const ButtonWrap = styled.nav`
    display: flex;
    align-items: center;

    @media ${({ theme }) => theme.media.medium} {
        display: none;
    }
`;

const NAVBAR_LINKS = [
    { name: 'about', trl: 'О продукте' },
    { name: 'testimonials', trl: 'Отзывы' },
    { name: 'tariff', trl: 'Тарифы' },
    { name: 'team', trl: 'Команда' }
];

function Navbar({ toggle }) {
    const [scrrolNav, setScrrolNav] = useState(false);

    const changeNav = () => {
        if (window.scrollY >= 80) {
            setScrrolNav(true);
        } else {
            setScrrolNav(false);
        }
    };

    useEffect(() => {
        window.addEventListener('scroll', changeNav);
    }, []);

    const toggleHome = () => {
        Scroll.scrollToTop();
    };

    return (
        <Nav scrrolNav={scrrolNav}>
            <NavbarContainer>
                <NavLogo to="/" onClick={toggleHome}>
                    CHECKLY
                </NavLogo>
                <MobileIcon onClick={toggle}>
                    <FaBars />
                </MobileIcon>
                <NavMenu>
                    {NAVBAR_LINKS.map((link) => (
                        <NavLinks
                            to={link.name}
                            smooth
                            duration={500}
                            spy
                            exact="true"
                            offset={-80}
                            key={link.name}
                        >
                            {link.trl}
                        </NavLinks>
                    ))}
                </NavMenu>
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
            </NavbarContainer>
        </Nav>
    );
}

Navbar.propTypes = {
    toggle: PropTypes.func
};

export default Navbar;
