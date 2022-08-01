import { useState } from 'react';
import styled from 'styled-components';
import PropTypes from 'prop-types';
import {
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TablePagination,
    TableRow
} from '@mui/material';
import TemplateDataRow from './TemplateDataRow';

function TemplateDataTable({ data, loadData, userId }) {
    const [rowsPerPage, setRowsPerPage] = useState(5);
    const [page, setPage] = useState(0);

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(Number(event.target.value));
        setPage(0);
    };

    return (
        <Container>
            <TableContainer component={Paper}>
                <Table aria-label="collapsible table">
                    <TableHead>
                        <TableRow hover>
                            <TableCell width="15px" />
                            <TableCell align="left">Название</TableCell>
                            <TableCell width="15px" align="center" />
                            <TableCell width="15px" align="center" />
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {data
                            .slice(
                                page * rowsPerPage,
                                page * rowsPerPage + rowsPerPage
                            )
                            .map((row) => (
                                <TemplateDataRow
                                    key={row.id}
                                    dataId={row.id}
                                    dataName={row.name}
                                    dataQuestions={row.questions}
                                    loadData={loadData}
                                    userId={userId}
                                />
                            ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <TablePagination
                rowsPerPageOptions={[5, 10, 25]}
                component="div"
                count={data.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            />
        </Container>
    );
}

export default TemplateDataTable;

TemplateDataTable.propTypes = {
    data: PropTypes.arrayOf(PropTypes.shape),
    loadData: PropTypes.func,
    userId: PropTypes.number
};

const Container = styled.div`
    height: 425px;
    width: 100%;
    position: relative;
`;
