import styled from 'styled-components';

export const Status = styled.div`
    padding: 5px;
    border-radius: 5px;
    width: fit-content;

    &.OPEN {
        color: green;
        background-color: rgba(0, 128, 0, 0.151);
    }
    &.PENDING {
        color: goldenrod;
        background-color: rgba(189, 189, 3, 0.103);
    }
    &.CLOSED {
        color: red;
        background-color: rgba(199, 81, 22, 0.103);
    }
    &.FINISHED {
        color: red;
        background-color: rgba(199, 81, 22, 0.103);
    }
`;

export const StatusWrapper = styled(Status)`
    padding: 10px 30px;
    font-size: 2.5rem;
    min-height: fit-content;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 20px;
    flex-direction: column;
    min-width: fit-content;

    @media ${({ theme }) => theme.media.medium} {
        width: 100%;
        gap: 10px;
    }

    @media ${({ theme }) => theme.media.small} {
        font-size: 2rem;
    }
`;
