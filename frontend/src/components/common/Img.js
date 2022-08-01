import styled from 'styled-components';

export const ImgWrap = styled.div`
    display: flex;
    justify-content: flex-start;
`;

export const Img = styled.img`
    width: 100%;
    margin: 0 0 1rem 0;
    padding-right: 0;

    @media ${({ theme }) => theme.media.medium} {
        width: 70%;
    }
`;
