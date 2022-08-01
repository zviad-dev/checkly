import styled from 'styled-components';

export const TextWrapper = styled.div`
    max-width: 540px;
    padding-top: 0;
    padding-bottom: 4rem;
`;
export const TopLine = styled.p`
    color: ${({ theme }) => theme.colors.blue};
    font-size: 1rem;
    line-height: 1rem;
    font-weight: 700;
    letter-spacing: 0.2rem;
    text-transform: uppercase;
    margin-bottom: 1rem;
`;

export const Heading = styled.h1`
    margin-bottom: 1.5rem;
    font-size: 3rem;
    line-height: 1.1;
    font-weight: 600;

    @media ${({ theme }) => theme.media.small} {
        font-size: 2rem;
    }
`;
export const Subtitle = styled.p`
    margin-bottom: 2rem;
    font-size: 1rem;
    line-height: 1.5rem;
`;
