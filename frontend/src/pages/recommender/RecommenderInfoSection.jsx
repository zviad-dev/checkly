import PropTypes from 'prop-types';
import styled from 'styled-components';
import { TitleWrapper } from '../../components/application/PageElements';

function RecommenderInfoSection({
    candidateFirstName,
    candidateLastName,
    recommenderFirstName,
    recommenderLastName
}) {
    return (
        <Container>
            <Item>
                <Name>
                    {recommenderLastName} {recommenderFirstName}
                </Name>
                <Position>Рекомендатель</Position>
            </Item>
            <Item>
                <Name>
                    {candidateLastName} {candidateFirstName}
                </Name>
                <Position>Кандидат</Position>
            </Item>
        </Container>
    );
}

RecommenderInfoSection.propTypes = {
    candidateFirstName: PropTypes.string,
    candidateLastName: PropTypes.string,
    recommenderFirstName: PropTypes.string,
    recommenderLastName: PropTypes.string
};

export default RecommenderInfoSection;

export const Container = styled(TitleWrapper)`
    margin-bottom: 24px;
    min-height: 60px;
    background: ${({ theme }) => theme.colors.white};
    display: flex;
    align-items: center;
    border-radius: 6px;
    padding: 20px;
    font-size: 20px;

    @media ${({ theme }) => theme.media.small} {
        flex-direction: column;
        gap: 10px;
    }
`;

const Item = styled.div`
    text-align: start;
`;

const Name = styled.div``;

const Position = styled.div`
    font-size: 1rem;
    opacity: 60%;
`;
