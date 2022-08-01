import { useState } from 'react';
import PropTypes from 'prop-types';
import {
    Box,
    Collapse,
    IconButton,
    List,
    ListItem,
    ListItemText,
    TableCell,
    TableRow,
    Typography
} from '@mui/material';
import { KeyboardArrowDown, KeyboardArrowUp } from '@mui/icons-material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import TemplateUpdateDialog from './TemplateUpdateDialog';
import TemplateDeleteDialog from './TemplateDeleteDialog';

function TemplateDataRow({
    dataId,
    dataName,
    dataQuestions,
    loadData,
    userId
}) {
    const [open, setOpen] = useState(false);
    const [updateDialogOpen, setUpdateDialogOpenOpen] = useState(false);
    const [isOpenDeleteDialog, setIsOpenDeleteDialog] = useState(false);
    const [isEditedTemplateId, setIsEditedTemplateId] = useState(null);

    const updateTemplate = () => {
        setIsEditedTemplateId(dataId);
        setUpdateDialogOpenOpen(true);
    };

    const deleteTemplate = () => {
        setIsEditedTemplateId(dataId);
        setIsOpenDeleteDialog(true);
    };

    return (
        <>
            <TableRow sx={{ '& > *': { borderBottom: 'unset' } }}>
                <TableCell>
                    {!!dataQuestions?.length && (
                        <IconButton
                            aria-label="expand row"
                            size="small"
                            onClick={() => setOpen(!open)}
                        >
                            {open ? <KeyboardArrowUp /> : <KeyboardArrowDown />}
                        </IconButton>
                    )}
                </TableCell>
                <TableCell align="left">{dataName}</TableCell>
                <TableCell align="center">
                    <IconButton aria-label="edit" onClick={updateTemplate}>
                        <EditIcon />
                    </IconButton>
                </TableCell>
                <TableCell align="center">
                    <IconButton aria-label="delete" onClick={deleteTemplate}>
                        <DeleteIcon />
                    </IconButton>
                </TableCell>
            </TableRow>
            {!!dataQuestions?.length && (
                <TableRow>
                    <TableCell
                        style={{ paddingBottom: 0, paddingTop: 0 }}
                        colSpan={6}
                    >
                        <Collapse in={open} timeout="auto" unmountOnExit>
                            <Box sx={{ margin: 1 }}>
                                <Typography gutterBottom component="div">
                                    Список вопросов
                                </Typography>
                                <List
                                    sx={{
                                        width: '100%',
                                        bgcolor: 'background.paper',
                                        padding: 0
                                    }}
                                >
                                    {dataQuestions.map((question) => (
                                        <ListItem
                                            key={question.id}
                                            sx={{
                                                padding: 0,
                                                paddingLeft: '20px'
                                            }}
                                        >
                                            <ListItemText
                                                disableTypography
                                                primary={question.question}
                                            />
                                        </ListItem>
                                    ))}
                                </List>
                            </Box>
                        </Collapse>
                    </TableCell>
                </TableRow>
            )}
            <TemplateDeleteDialog
                userId={userId}
                loadData={loadData}
                dataQuestions={dataQuestions}
                isOpen={isOpenDeleteDialog}
                handleClose={() => setIsOpenDeleteDialog(false)}
                dataId={dataId}
                dataName={dataName}
                editTemplateId={isEditedTemplateId}
            />
            <TemplateUpdateDialog
                open={updateDialogOpen}
                handleClose={() => setUpdateDialogOpenOpen(false)}
                name={dataName}
                questions={dataQuestions}
                userId={userId}
                templateId={isEditedTemplateId}
                loadData={loadData}
            />
        </>
    );
}

export default TemplateDataRow;

TemplateDataRow.propTypes = {
    dataId: PropTypes.number,
    dataName: PropTypes.string,
    dataQuestions: PropTypes.arrayOf(PropTypes.shape),
    loadData: PropTypes.func,
    userId: PropTypes.number
};
