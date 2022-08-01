import styled from 'styled-components';
import { Link as LinkR } from 'react-router-dom';
import Video from '../../assets/video.mp4';
import { Button, ButtonsKind, ButtonsSize } from '../common/Button';

const HeroContainer = styled.div`
    background: ${({ theme }) => theme.colors.black};
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 0 2rem;
    height: 800px;
    position: relative;
    z-index: 2;
`;

const HeroBg = styled.div`
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 100%;
    overflow: hidden;
`;

const Videobg = styled.video`
    width: 100%;
    height: 100%;
    -o-object-fit: cover;
    object-fit: cover;
    background: ${({ theme }) => theme.colors.black};
`;

const HeroContent = styled.div`
    z-index: 3;
    max-width: 1200px;
    position: absolute;
    padding: 8px 1.5rem;
    display: flex;
    flex-direction: column;
    align-items: center;
`;
const HeroH1 = styled.h1`
    color: ${({ theme }) => theme.colors.lightWhite};
    font-size: 3rem;
    text-align: center;

    @media ${({ theme }) => theme.media.medium} {
        font-size: 2.5rem;
    }

    @media ${({ theme }) => theme.media.small} {
        font-size: 2rem;
    }
`;

const HeroP = styled.p`
    margin-top: 1.5rem;
    color: ${({ theme }) => theme.colors.lightWhite};
    font-size: 1.5rem;
    text-align: center;
    max-width: 600px;

    @media ${({ theme }) => theme.media.medium} {
        font-size: 1.5rem;
    }

    @media ${({ theme }) => theme.media.small} {
        font-size: 1rem;
    }
`;
const HeroBtnWrapper = styled.div`
    margin-top: 4rem;
`;

function HeroSection() {
    return (
        <HeroContainer>
            <HeroBg>
                <Videobg autoPlay loop muted src={Video} type="video/mp4" />
            </HeroBg>
            <HeroContent>
                <HeroH1>CHECKLY</HeroH1>
                <HeroP>
                    Сервис проверки рекомендаций от бывших работодателей
                </HeroP>
                <HeroBtnWrapper>
                    <LinkR to="/signin">
                        <Button
                            kind={ButtonsKind.secondary}
                            size={ButtonsSize.s}
                        >
                            Начать
                        </Button>
                    </LinkR>
                </HeroBtnWrapper>
            </HeroContent>
        </HeroContainer>
    );
}

export default HeroSection;
