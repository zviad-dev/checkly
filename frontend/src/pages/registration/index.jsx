import { Link as LinkR } from 'react-router-dom';
import Rocket from '../../assets/rocket.svg';
import {
    Container,
    LeftSide,
    LeftIcon,
    RightSideForm,
    CloseIcon
} from '../../components/application/FormPageElements';
import Close from '../../assets/remove.svg';
import FormItem from './FormItem';

function RegistrationElement() {
    return (
        <Container>
            <LeftSide style={{ flexDirection: 'raw' }}>
                <LeftIcon src={Rocket} alt="Rocket" />
            </LeftSide>
            <RightSideForm>
                <LinkR to="/">
                    <CloseIcon src={Close} alt="Закрыть" />
                </LinkR>
                <FormItem />
            </RightSideForm>
        </Container>
    );
}

export default RegistrationElement;
