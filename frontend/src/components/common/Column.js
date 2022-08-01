import styled from 'styled-components';

export const Container = styled.div`
    color: ${({ theme }) => theme.colors.black};
    background: ${({ theme }) => theme.colors.lightWhite};
    padding: 4rem 0;
`;

export const Wrapper = styled.div`
    display: grid;
    z-index: 2;
    width: 100%;
    max-width: 1200px;
    margin-right: auto;
    margin-left: auto;
    padding-top: 0 1.5rem;
    justify-content: center;
`;

export const Row = styled.div`
    display: grid;
    grid-auto-columns: minmax(auto, 1fr);
    align-items: center;
    grid-template-areas: 'col1 col2';

    @media ${({ theme }) => theme.media.medium} {
        grid-template-areas: 'col1' 'col2';
    }
`;

export const Column1 = styled.div`
    margin-bottom: 1rem;
    padding: 0 1rem;
    grid-area: col1;
`;

export const Column2 = styled.div`
    margin-bottom: 1rem;
    padding: 0 1rem;
    grid-area: col2;
`;
