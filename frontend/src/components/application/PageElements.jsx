import styled from 'styled-components';

export const Container = styled.div`
    background: #f8faff;
    left: 78px;
    width: calc(100% - 78px);
    position: relative;
    padding: 24px 20px;
`;

export const TitleWrapper = styled.div`
    display: flex;
    justify-content: space-between;
`;

export const Title = styled.h1`
    font-size: 1rem;
    color: ${({ theme }) => theme.colors.blue};
    margin-bottom: 24px;
`;
