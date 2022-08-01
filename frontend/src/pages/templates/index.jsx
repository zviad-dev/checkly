// eslint-disable-next-line no-unused-vars
import { useCallback, useEffect, useState } from 'react';
import styled from 'styled-components';
import TemplateDataTable from './TemplateDataTable';
import API from '../../api/axios';
import PageHeader from '../../components/application/PageHeader';
import { Container, Title } from '../../components/application/PageElements';
import {
    Button,
    ButtonsKind,
    ButtonsSize
} from '../../components/common/Button';
import useAuth from '../../userData/useAuth';
import TemplateCreateDialog from './TemplateCreateDialog';

function Templates() {
    const userId = useAuth().user.id;
    const [tableData, setTableData] = useState([]);
    const [open, setOpen] = useState(false);

    const loadData = useCallback(() => {
        API.get(`recruiters/${userId}/templates`)
            .then(({ data }) => {
                setTableData(data.sort((a, b) => a.id - b.id));
            })
            .catch((error) => {
                // eslint-disable-next-line no-console
                console.error(error.message);
            });
    }, [userId]);

    useEffect(() => {
        loadData();
    }, [userId, loadData]);

    const handleClickOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    return (
        <>
            <PageHeader />
            <Container>
                <Header>
                    <Title style={{ marginTop: '10px' }}>Шаблоны</Title>
                    <Button
                        style={{ marginLeft: 'auto' }}
                        kind={ButtonsKind.quaternary}
                        size={ButtonsSize.l}
                        onClick={handleClickOpen}
                    >
                        Новый шаблон
                    </Button>
                </Header>
                <TemplateDataTable
                    loadData={loadData}
                    data={tableData}
                    userId={userId}
                />
                <TemplateCreateDialog
                    userId={userId}
                    open={open}
                    handleClose={handleClose}
                    loadData={loadData}
                />
            </Container>
        </>
    );
}

const Header = styled.div`
    display: flex;
    flex-direction: row;
    align-content: center;
    margin-bottom: 20px;
`;

export default Templates;
