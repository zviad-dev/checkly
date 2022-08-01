import { DataGrid } from '@mui/x-data-grid';
import styled from 'styled-components';
import { Link as LinkR } from 'react-router-dom';
import PropTypes from 'prop-types';
import { Status } from './Status';
import translator from '../../translator/Translator';
import useAuth from '../../userData/useAuth';

const userColumns = [
    {
        field: 'candidate.candidate_last_name',
        headerName: 'Фамилия',
        flex: 10,
        renderCell: (params) => (
            <Routing
                to={`/recruiters/${params.row.recId}/surveys/${params.row.id}`}
            >
                {params.row.candidate.candidate_last_name}
            </Routing>
        )
    },
    {
        field: 'candidate.candidate_first_name',
        headerName: 'Имя',
        flex: 10,
        renderCell: (params) => (
            <Routing
                to={`/recruiters/${params.row.recId}/surveys/${params.row.id}`}
            >
                {params.row.candidate.candidate_first_name}
            </Routing>
        )
    },
    {
        field: 'candidate.candidate_email',
        headerName: 'Почта',
        flex: 10,
        renderCell: (params) => (
            <Routing
                to={`/recruiters/${params.row.recId}/surveys/${params.row.id}`}
            >
                {params.row.candidate.candidate_email}
            </Routing>
        )
    },
    {
        field: 'position_name',
        headerName: 'Вакансия',
        flex: 10,
        renderCell: (params) => (
            <Routing
                to={`/recruiters/${params.row.recId}/surveys/${params.row.id}`}
            >
                {params.row.position_name}
            </Routing>
        )
    },
    {
        field: 'questions',
        headerName: 'Количество вопросов',
        flex: 10,
        renderCell: (params) => (
            <Routing
                to={`/recruiters/${params.row.recId}/surveys/${params.row.id}`}
            >
                {Object.keys(params.row.questions).length}
            </Routing>
        )
    },
    {
        field: 'recommenders',
        headerName: 'Количество рекомендателей',
        flex: 10,
        renderCell: (params) => (
            <Routing
                to={`/recruiters/${params.row.recId}/surveys/${params.row.id}`}
            >
                {Object.keys(params.row.recommenders).length}
            </Routing>
        )
    },
    {
        field: 'updated_at',
        headerName: 'Дата создания',
        flex: 10,
        renderCell: (params) => (
            <Routing
                to={`/recruiters/${params.row.recId}/surveys/${params.row.id}`}
            >
                {params.row.updated_at}
            </Routing>
        )
    },
    {
        field: 'status',
        headerName: 'Статус',
        flex: 10,
        renderCell: (params) => (
            <Routing
                to={`/recruiters/${params.row.recId}/surveys/${params.row.id}`}
            >
                <StatusCell className={`StatusCell ${params.row.status}`}>
                    {translator(params.row.status)}
                </StatusCell>
            </Routing>
        )
    }
];

function FullDataTable({ data }) {
    const recruiterId = useAuth().user.id;

    const modifiedRows = data.map((element) => ({
        ...element,
        recId: recruiterId
    }));

    return (
        <DataGrid
            sx={{
                '& .MuiDataGrid-cell:hover': {
                    cursor: 'pointer',
                    outline: 0
                },
                '.MuiDataGrid-cell:focus': {
                    outline: 0
                },
                '& .MuiDataGrid-columnHeader,  .MuiDataGrid-cell,  .MuiDataGrid-cellCheckbox':
                    {
                        ':focus': {
                            outline: 'none'
                        }
                    },
                background: '#fff'
            }}
            className="datagrid"
            rows={modifiedRows}
            columns={userColumns}
            recruiterId={recruiterId}
            pageSize={6}
            rowsPerPageOptions={[6]}
            disableColumnMenu
            disableSelectionOnClick
            getRowId={(row) => row.id}
            autoHeight
        />
    );
}

export default FullDataTable;

FullDataTable.propTypes = {
    data: PropTypes.arrayOf(PropTypes.shape)
};

const StatusCell = styled(Status)`
    width: fit-content;
    line-height: 25px;
`;

const Routing = styled(LinkR)`
    text-decoration: none;
    color: ${({ theme }) => theme.colors.black};
    width: 100%;
    line-height: 200px;
`;
