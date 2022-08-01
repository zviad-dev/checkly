import { Link as LinkR } from 'react-router-dom';
import styled from 'styled-components';
import { useEffect, useRef } from 'react';
import PropTypes from 'prop-types';
import useAuth from '../../userData/useAuth';

const MENU_ITEM = [{ title: 'Настройки', path: '/settings' }];

function DropDownSettings({ isSettingsVisible, setSettingsVisible, refOut }) {
    const { logOut } = useAuth();
    const ref = useRef();

    useEffect(() => {
        const checkIfClickedOutside = (e) => {
            if (
                isSettingsVisible &&
                ref.current &&
                !ref.current.contains(e.target) &&
                !refOut.current.contains(e.target)
            ) {
                setSettingsVisible(false);
            }
        };

        document.addEventListener('mousedown', checkIfClickedOutside);

        return () => {
            document.removeEventListener('mousedown', checkIfClickedOutside);
        };
    }, [isSettingsVisible]);

    return (
        <DropdownMenu ref={ref}>
            {MENU_ITEM.map((item) => (
                <DropdownLink to={item.path} key={item.title}>
                    {item.title}
                </DropdownLink>
            ))}

            <DropdownLink to="/" onClick={logOut}>
                Выход
            </DropdownLink>
        </DropdownMenu>
    );
}

export default DropDownSettings;

DropDownSettings.propTypes = {
    isSettingsVisible: PropTypes.bool,
    setSettingsVisible: PropTypes.func,
    refOut: PropTypes.func
};

const DropdownMenu = styled.div`
    width: 200px;
    position: absolute;
    top: 60px;
    right: 0px;
    list-style: none;
    background: #f8faff;
    border-radius: 6px;
`;

const DropdownLink = styled(LinkR)`
    display: block;
    height: 100%;
    width: 100%;
    text-decoration: none;
    color: ${({ theme }) => theme.colors.black};
    padding: 1rem;
    cursor: pointer;
    transition: all 0.4s ease;

    &:hover {
        background: ${({ theme }) => theme.colors.blue};
        border-radius: 6px;
        color: ${({ theme }) => theme.colors.lightWhite};
    }
`;
