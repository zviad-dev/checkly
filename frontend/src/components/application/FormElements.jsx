import styled from 'styled-components';

export const Input = styled.input`
    padding: 16px 16px;
    border: 1px solid #fff;
    outline: none;
    border-radius: 4px;

    &:hover {
        border: 1px solid #babdbf;
    }
`;

export const InputWrapper = styled.div`
    display: flex;
    flex-direction: column;
    margin-bottom: 20px;
`;

export const FormLabel = styled.label`
    margin-bottom: 8px;
    font-size: 14px;
    color: ${({ theme }) => theme.colors.black};
`;
