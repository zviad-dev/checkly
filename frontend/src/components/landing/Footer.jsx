import styled from 'styled-components';
import { Link as LinkR } from 'react-router-dom';
import { animateScroll as Scroll } from 'react-scroll';

const FooterContainer = styled.footer`
    background-color: ${({ theme }) => theme.colors.black};
`;

const FooterWrapp = styled.div`
    padding: 3rem 1.5rem;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    max-width: 1100px;
    margin: 0 auto;
`;

const SocialMedia = styled.section`
    max-width: 1000px;
    width: 100%;
`;

const SocialMediaWrap = styled.div`
    display: flex;
    justify-content: space-evenly;
    align-items: center;
    max-width: 1100px;
    margin: 2rem auto 0 auto;

    @media ${({ theme }) => theme.media.medium} {
        flex-direction: column;
    }
`;

const SocialLogo = styled(LinkR)`
    color: ${({ theme }) => theme.colors.lightWhite};
    justify-self: start;
    cursor: pointer;
    text-decoration: none;
    font-size: 1.5rem;
    display: flex;
    align-items: center;
    margin-bottom: 1rem;
    font-weight: bold;
`;

const WebsiteRights = styled.small`
    color: ${({ theme }) => theme.colors.lightWhite};
    margin-bottom: 1rem;
`;

function Footer() {
    const toggleHome = () => {
        Scroll.scrollToTop();
    };

    return (
        <FooterContainer>
            <FooterWrapp>
                <SocialMedia>
                    <SocialMediaWrap>
                        <SocialLogo to="/" onClick={toggleHome}>
                            checkly
                        </SocialLogo>
                        <WebsiteRights>
                            © 2022 Группа компаний CHECKLY
                        </WebsiteRights>
                    </SocialMediaWrap>
                </SocialMedia>
            </FooterWrapp>
        </FooterContainer>
    );
}

export default Footer;
