import PropTypes from 'prop-types';
import styled from 'styled-components';
import {
    Button,
    ButtonsKind,
    ButtonsSize
} from '../../components/common/Button';

function SubmitButton({ apiSuccess, isLoading, onCloseClick, index }) {
    return (
        <ButtonWrapper>
            <Button
                kind={ButtonsKind.tertiary}
                size={ButtonsSize.m}
                type="submit"
            >
                {isLoading ? 'Идет отправка...' : 'Сохранить'}
            </Button>
            <Button
                onClick={() => {
                    onCloseClick(index);
                }}
                kind={ButtonsKind.quaternary}
                size={ButtonsSize.m}
                type="button"
            >
                {apiSuccess ? 'Закрыть' : 'Отменить'}
            </Button>
        </ButtonWrapper>
    );
}

SubmitButton.propTypes = {
    onCloseClick: PropTypes.func,
    apiSuccess: PropTypes.objectOf,
    isLoading: PropTypes.bool,
    index: PropTypes.number
};

export default SubmitButton;

const ButtonWrapper = styled.div`
    display: flex;
    gap: 10px;
    justify-content: center;

    @media ${({ theme }) => theme.media.small} {
        flex-direction: column;
    }
`;
