import styled from 'styled-components';
import { Link as LinkR } from 'react-router-dom';

export const Container = styled.div`
    background: #f8faff;
    display: flex;
    flex-direction: row;
    height: 100vh;
`;

export const CloseIcon = styled.img`
    height: 24px;
    position: absolute;
    top: 20px;
    right: 20px;
    z-index: 1;
    color: #fff;
    cursor: pointer;
    opacity: 0.3;

    &:hover {
        opacity: 0.8;
    }
`;

export const LeftSide = styled.div`
    background: ${({ theme }) => theme.colors.darkBlue};
    flex: 1;
    padding: 2rem;

    @media ${({ theme }) => theme.media.large} {
        display: none;
    }
`;

export const RightSide = styled.div`
    flex: 1;
    padding: 2rem;
    overflow: auto;
`;

export const RightSideForm = styled.div`
    flex: 1;
    overflow: auto;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 2rem;
`;

export const LeftIcon = styled.img`
    width: 80%;
    height: 80%;
    position: relative;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
`;

export const LogoText = styled.div`
    color: ${({ theme }) => theme.colors.blue};
    font-weight: 700;
    font-size: 32px;
`;

export const Description = styled.div`
    margin-top: 1rem;
    color: ${({ theme }) => theme.colors.lightWhite};
`;

export const FormRec = styled.form`
    background: #f8faff;
    max-width: 600px;
    width: 100%;
    display: grid;
    margin: 0 auto;
    border-radius: 4px;
`;

export const Form = styled.form`
    position: relative;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    max-width: 400px;
`;

export const Title = styled.h1`
    margin-bottom: 30px;
    color: ${({ theme }) => theme.colors.black};
    font-size: 24px;
    font-weight: 400;
    text-align: center;
`;

export const TextWrapper = styled.div`
    margin-top: 2rem;
    text-align-last: center;
`;

export const Link = styled(LinkR)`
    margin-left: 10px;
    color: ${({ theme }) => theme.colors.blue};
    text-decoration: none;
`;

export const SuccessContainer = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
`;

export const Answer = styled.div`
    padding: 16px 16px;
    border: 1px solid #fff;
    outline: none;
    border-radius: 4px;
    background: #fff;
    font-size: 13px;

    &:hover {
        border: 1px solid #babdbf;
    }
`;
