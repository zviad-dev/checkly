import styled from 'styled-components';
import Band from '../../assets/Team.svg';
import { Img, ImgWrap } from '../common/Img';
import { Container, Wrapper, Row, Column1, Column2 } from '../common/Column';
import { TextWrapper, TopLine, Heading, Subtitle } from '../common/Text';

const SubtitleTestimonials = styled(Subtitle)`
    background: ${({ theme }) => theme.colors.lightWhite};
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
    border-radius: 10px;
    padding: 1rem;

    &:hover {
        transform: scale(1.02);
        transition: all 0.2s ease-in-out;
        cursor: pointer;
    }
`;

const TEAM_LIST = [
    { name: 'Ментор - Мясников Макс' },
    { name: 'PM - Сатюков Станислав, Смаль Екатерина' },
    { name: 'Технические менторы - Сулим Алексей, Колесникова Ксения' },
    {
        name: 'Команда разработки - Кольчурин Александр, Кузнецов Всеволод, Носков Дмитрий, Ремизова Марина, Скворцов Александр, Стрельченко Михаил, Толорая Звиад'
    }
];

function TeamSection() {
    return (
        <Container id="team">
            <Wrapper>
                <Row>
                    <Column1>
                        <TextWrapper>
                            <TopLine>CHECKLY</TopLine>
                            <Heading>Автоматизация поиска сотрудников</Heading>
                            {TEAM_LIST.map((team) => (
                                <SubtitleTestimonials key={team.name}>
                                    {team.name}
                                </SubtitleTestimonials>
                            ))}
                        </TextWrapper>
                    </Column1>
                    <Column2>
                        <ImgWrap>
                            <Img src={Band} alt="Rec" />
                        </ImgWrap>
                    </Column2>
                </Row>
            </Wrapper>
        </Container>
    );
}

export default TeamSection;
