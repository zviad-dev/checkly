import styled from 'styled-components';
import Icon1 from '../../assets/Icon1.svg';
import Icon2 from '../../assets/Icon2.svg';
import Icon3 from '../../assets/Icon3.svg';
import { Heading } from '../common/Text';

const TariffContainer = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    z-index: 2;
    padding: 4rem 0;
    background: ${({ theme }) => theme.colors.lightWhite};

    @media ${({ theme }) => theme.media.medium} {
        height: 1200px;
    }
`;

const TariffWrapper = styled.div`
    max-width: 1000px;
    margin: 0 auto;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    align-items: center;
    grid-gap: 1rem;
    padding: 0 50px;

    @media ${({ theme }) => theme.media.medium} {
        grid-template-columns: 1fr;
        padding: 0 1.5rem;
    }
`;

const TariffCard = styled.div`
    background: ${({ theme }) => theme.colors.lightWhite};
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    border-radius: 10px;
    max-height: 340px;
    padding: 30px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
    transition: all 0.2s ease-in-out;

    &:hover {
        transform: scale(1.02);
        transition: all 0.2s ease-in-out;
        cursor: pointer;
    }
`;

const TariffIcon = styled.img`
    height: 160px;
    width: 160px;
    margin-bottom: 1rem;
`;

const TariffH2 = styled.h2`
    font-size: 1rem;
    margin-bottom: 1rem;
`;

const TariffP = styled.p`
    font-size: 1rem;
    text-align: center;
`;

const CHECKLY_TARIFF = [
    { name: 'Стартап', price: '₽ 199', icon: Icon2 },
    { name: 'Команда', price: '₽ 5.495', icon: Icon1 },
    { name: 'Энтерпрайз', price: '₽ 9.999', icon: Icon3 }
];

function TariffSection() {
    return (
        <TariffContainer id="tariff">
            <Heading>Тарифы</Heading>
            <TariffWrapper>
                {CHECKLY_TARIFF.map((tariff) => (
                    <TariffCard key={tariff.name}>
                        <TariffIcon src={tariff.icon} />
                        <TariffH2>{tariff.name}</TariffH2>
                        <TariffP>{tariff.price}</TariffP>
                    </TariffCard>
                ))}
            </TariffWrapper>
        </TariffContainer>
    );
}

export default TariffSection;
