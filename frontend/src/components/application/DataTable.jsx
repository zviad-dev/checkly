import styled from 'styled-components';
import T from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import Row from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { Link as LinkR } from 'react-router-dom';
import PropTypes from 'prop-types';
import { Status } from './Status';
import translator from '../../translator/Translator';
import useAuth from '../../userData/useAuth';

const column = ['Имя', 'Фамилия', 'email', 'Статус'];

function DataTable({ row, surveyId }) {
    const recruiterId = useAuth().user.id;

    return (
        <Container>
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            {column.map((item) => (
                                <TableCell key={item}>{item}</TableCell>
                            ))}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {row.map((data, index) => (
                            <TableRow
                                key={(data.candidateFirstName, index)}
                                component={LinkR}
                                to={`/recruiters/${recruiterId}/surveys/${surveyId}/recommendations/${data.id}`}
                            >
                                {Object.keys(data).map((key, idx) =>
                                    key === 'status' ? (
                                        <TableCell key={key}>
                                            <Status
                                                className={`Status ${data.status}`}
                                            >
                                                {translator(data.status)}
                                            </Status>
                                        </TableCell>
                                    ) : (
                                        key !== 'answers' &&
                                        key !== 'id' && (
                                            <TableCell key={(data[key], idx)}>
                                                {data[key]}
                                            </TableCell>
                                        )
                                    )
                                )}
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </Container>
    );
}

export default DataTable;

DataTable.propTypes = {
    row: PropTypes.arrayOf,
    surveyId: PropTypes.string
};

const Container = styled.header``;

const Table = styled(T)`
    background: #fff;
`;

const TableRow = styled(Row)`
    text-decoration: none;
    &:hover {
        background: ${({ theme }) => theme.colors.lightWhite};
        color: #fff;
    }
`;
