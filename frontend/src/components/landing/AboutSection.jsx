import { Link as LinkR } from 'react-router-dom';
import styled from 'styled-components';

import Rec from '../../assets/Rec.svg';
import { Button, ButtonsKind, ButtonsSize } from '../common/Button';
import { Img, ImgWrap } from '../common/Img';
import { Container, Wrapper, Row, Column1, Column2 } from '../common/Column';
import { TextWrapper, TopLine, Heading, Subtitle } from '../common/Text';

function AboutSection() {
    return (
        <Container id="about">
            <Wrapper>
                <Row>
                    <Column1>
                        <TextWrapper>
                            <TopLine>CHECKLY</TopLine>
                            <Heading>
                                Сервис для сбора проверенных рекомендаций
                            </Heading>
                            <Subtitle>
                                <ListElement>
                                    Подтверждение навыков бывших коллег
                                </ListElement>
                                <ListElement>
                                    Возможность отправить коллеге рекомендацию
                                </ListElement>
                                <ListElement>
                                    Прозрачная аналитика о реккомендодателе для
                                    HR
                                </ListElement>
                                Собирайте проверенные отзывы о ваших кандидатах
                                от людей, которые знают их лучше всего.
                            </Subtitle>
                            <LinkR to="/signin">
                                <Button
                                    kind={ButtonsKind.tertiary}
                                    size={ButtonsSize.s}
                                >
                                    Начать
                                </Button>
                            </LinkR>
                        </TextWrapper>
                    </Column1>
                    <Column2>
                        <ImgWrap>
                            <Img src={Rec} alt="Rec" />
                        </ImgWrap>
                    </Column2>
                </Row>
            </Wrapper>
        </Container>
    );
}

export default AboutSection;

const ListElement = styled.li`
    margin-bottom: 0.5rem;
`;
