import { Link as LinkR } from 'react-router-dom';
import styled from 'styled-components';
import useAuth from '../../userData/useAuth';

const MENU_ITEM = [
    { title: 'Пункт 1', path: '/item1' },
    { title: 'Пункт 2', path: '/item2' },
    { title: 'Пункт 3', path: '/item3' },
    { title: 'Пункт 4', path: '/item4' },
    { title: 'Пункт 5', path: '/item5' }
];

function Dropdown() {
    const { logOut } = useAuth();

    return (
        <DropdownMenu>
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

export default Dropdown;

const DropdownMenu = styled.div`
    width: 200px;
    position: absolute;
    top: 50px;
    right: 0px;
    list-style: none;
    background: #f8faff;
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
