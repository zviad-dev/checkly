import styled from 'styled-components';
import { Link as LinkR } from 'react-router-dom';
import {
    Button,
    ButtonsKind,
    ButtonsSize
} from '../../components/common/Button';
import { FormLabel } from '../../components/application/FormElements';
import { TextWrapper } from '../../components/application/FormPageElements';

function NavigateButtons() {
    const handleHHButtonClick = (event) => {
        // TODO {strelchm} integration and HH icon!
        event.preventDefault();
        // eslint-disable-next-line no-alert
        alert(`Интеграция с HH в разработке`);
    };

    const handleVKButtonClick = (event) => {
        // TODO {strelchm} integration and VK icon!
        event.preventDefault();
        // eslint-disable-next-line no-alert
        alert(`Интеграция с VK в разработке`);
    };

    return (
        <div>
            <RegistrationTextWrapper>
                <FormLabel>или</FormLabel>
            </RegistrationTextWrapper>
            <LinkR to="/registration">
                <FullWidthButton
                    kind={ButtonsKind.quaternary}
                    size={ButtonsSize.sm}
                    onClick={handleVKButtonClick}
                >
                    Зарегистрироваться с помощью VK
                </FullWidthButton>
            </LinkR>
            <HHButtonWrapper to="/registration">
                <FullWidthButton
                    kind={ButtonsKind.quaternary}
                    size={ButtonsSize.sm}
                    onClick={handleHHButtonClick}
                >
                    Зарегистрироваться с помощью HH
                </FullWidthButton>
            </HHButtonWrapper>
        </div>
    );
}

export default NavigateButtons;

const RegistrationTextWrapper = styled(TextWrapper)`
    margin-top: 10px;
    margin-bottom: 10px;
`;

const FullWidthButton = styled(Button)`
    width: 100% !important;
`;

const HHButtonWrapper = styled(LinkR)`
    margin-top: 20px;
    display: inline-block;
    width: 100%;
`;
