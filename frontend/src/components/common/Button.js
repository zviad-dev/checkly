/* eslint-disable indent */
import styled from 'styled-components';

export const ButtonWrap = styled.div`
    display: flex;
    justify-content: flex-start;
`;

export const ButtonsKind = {
    primary: 'primary',
    secondary: 'secondary',
    tertiary: 'tertiary',
    quaternary: 'quaternary',
    remove: 'remove',
    edit: 'edit'
};

export const ButtonsSize = {
    s: 's',
    sm: 'sm',
    m: 'm',
    ml: 'ml',
    l: 'l'
};

const ButtonsProps = {
    [ButtonsKind.primary]: {
        color: 'lightWhite',
        background: 'black',
        hoverBackground: 'blue'
    },
    [ButtonsKind.secondary]: {
        color: 'black',
        background: 'lightWhite',
        hoverBackground: 'blue',
        hoverColor: 'white'
    },
    [ButtonsKind.tertiary]: {
        color: 'lightWhite',
        background: 'blue',
        hoverBackground: 'lightBlue'
    },
    [ButtonsKind.remove]: {
        color: 'lightWhite',
        background: 'red',
        hoverBackground: 'darkRed'
    },
    [ButtonsKind.edit]: {
        color: 'lightWhite',
        background: 'green',
        hoverBackground: 'darkGreen'
    },
    [ButtonsKind.quaternary]: {
        color: 'blue',
        background: 'lightWhite',
        hoverBackground: 'white'
    },
    [ButtonsSize.s]: {
        borderRadius: '50px',
        padding: '1rem 4rem',
        border: 'none',
        fontSize: '20px'
    },
    [ButtonsSize.sm]: {
        borderRadius: '4px',
        padding: '0.65rem 2rem',
        height: '40px',
        border: '1px solid'
    },
    [ButtonsSize.m]: {
        borderRadius: '4px',
        padding: '0.5rem 1rem',
        height: '50px',
        border: '1px solid #297FEE',
        fontSize: '20px'
    },
    [ButtonsSize.ml]: {
        borderRadius: '4px',
        padding: '1.3rem 4rem',
        height: '60px'
    },

    [ButtonsSize.l]: {
        borderRadius: '4px',
        padding: '1rem 4rem',
        height: '60px',
        border: '1px solid',
        fontSize: '20px'
    }
};

export const Button = styled.button`
    border-radius: ${({ size = ButtonsSize.s }) =>
        ButtonsProps[size].borderRadius};
    background: ${({ theme, kind = ButtonsKind.primary }) =>
        theme.colors[ButtonsProps[kind].background]};
    white-space: nowrap;
    padding: ${({ size = ButtonsSize.s }) => ButtonsProps[size].padding};
    color: ${({ theme, kind = ButtonsKind.primary }) =>
        theme.colors[ButtonsProps[kind].color]};
    font-size: ${({ size = ButtonsSize.s }) => ButtonsProps[size].fontSize};
    outline: none;
    border: ${({ size = ButtonsSize.s }) => ButtonsProps[size].border};
    cursor: pointer;
    transition: all 0.2s ease-in-out;
    text-decoration: none;
    height: ${({ size = ButtonsSize.s }) => ButtonsProps[size].height};
    &:hover {
        transition: all 0.2s ease-in-out;
        background: ${({ theme, kind = ButtonsKind.primary }) =>
            theme.colors[ButtonsProps[kind].hoverBackground]};
        color: ${({ theme, kind = ButtonsKind.primary }) =>
            theme.colors[ButtonsProps[kind].hoverColor]};
    }
`;
