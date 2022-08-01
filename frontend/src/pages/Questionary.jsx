import styled from 'styled-components';
import PageHeader from '../components/application/PageHeader';

function Questionary() {
    return (
        <>
            <PageHeader />
            <Container>Тут будет анкета</Container>
        </>
    );
}

export default Questionary;

const Container = styled.div`
    left: 78px;
    width: calc(100% - 78px);
    position: relative;
`;
