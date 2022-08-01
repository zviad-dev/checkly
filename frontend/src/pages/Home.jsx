import { useState, useEffect } from 'react';
import API from '../api/axios';
import PageHeader from '../components/application/PageHeader';
import FullDataTable from '../components/application/FullDataTable';
import { Container, Title } from '../components/application/PageElements';
import InfoSection from '../components/application/InfoSection';
import useAuth from '../userData/useAuth';

function Home() {
    const [tableData, setTableData] = useState([]);
    const userId = useAuth().user.id;

    useEffect(() => {
        API.get(`recruiters/${userId}/surveys`)
            .then(({ data }) => {
                setTableData(data);
            })
            .catch((error) => console.log(error.message));
    }, []);

    return (
        <>
            <PageHeader />
            <Container>
                <InfoSection />
                <Title>Список опросов</Title>
                {tableData.length ? (
                    <FullDataTable data={tableData} />
                ) : (
                    'Список опросов пуст'
                )}
            </Container>
        </>
    );
}

export default Home;
