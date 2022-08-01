import styled from 'styled-components';
import { Link as LinkR } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { TitleWrapper } from './PageElements';
import { Button, ButtonsKind, ButtonsSize } from '../common/Button';
import useAuth from '../../userData/useAuth';
import API from '../../api/axios';

const STAT_DATA = [
    { title: 'Всего', data: 'total_surveys' },
    { title: 'Открыто', data: 'open_surveys' },
    { title: 'В работе', data: 'pending_surveys' },
    { title: 'Завершены ', data: 'sum' }
];

function InfoSection() {
    const [statisticsData, setStatisticsData] = useState(null);
    const userId = useAuth().user.id;

    useEffect(() => {
        API.get(`recruiters/${userId}/surveys/statistics`)
            .then(({ data }) => {
                setStatisticsData(data);
            })
            .catch((error) => console.log(error.message));
    }, []);

    return (
        <Container>
            {statisticsData ? (
                STAT_DATA.map((stat) => (
                    <StatElement key={stat.title}>
                        <StatCount>
                            {stat.data === 'sum'
                                ? (statisticsData.finished_surveys || 0) +
                                  (statisticsData.closed_surveys || 0)
                                : statisticsData[stat.data] || 0}
                        </StatCount>
                        <StatName>{stat.title}</StatName>
                    </StatElement>
                ))
            ) : (
                <StatName>Статистики пока нет</StatName>
            )}

            <ButttonWrapper>
                <LinkR to="/templates">
                    <Button kind={ButtonsKind.quaternary} size={ButtonsSize.l}>
                        Шаблоны
                    </Button>
                </LinkR>
                <LinkR to="/recommendation">
                    <Button kind={ButtonsKind.tertiary} size={ButtonsSize.l}>
                        Новый опрос
                    </Button>
                </LinkR>
            </ButttonWrapper>
        </Container>
    );
}

export default InfoSection;

export const Container = styled(TitleWrapper)`
    margin-bottom: 24px;
    min-height: 120px;
    background: ${({ theme }) => theme.colors.white};
    display: flex;
    align-items: center;
    border-radius: 6px;
    padding: 20px;

    @media ${({ theme }) => theme.media.large} {
        flex-direction: column;
        gap: 10px;
    }
`;

const StatElement = styled.div`
    display: flex;
    flex-direction: column;
`;

const StatCount = styled.div`
    font-size: 2rem;
    text-align: center;
    font-weight: 800;
    color: ${({ theme }) => theme.colors.blue};
`;

const StatName = styled.div`
    text-align: center;
    color: ${({ theme }) => theme.colors.darkGrey};
`;

const ButttonWrapper = styled.div`
    display: flex;
    gap: 20px;

    @media ${({ theme }) => theme.media.large} {
        flex-direction: column;
        gap: 10px;
        align-items: center;
    }
`;
