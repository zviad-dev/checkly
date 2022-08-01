import PropTypes from 'prop-types';
import {Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle} from "@mui/material";
import {Button, ButtonsSize} from "../../components/common/Button";
import API from "../../api/axios";

function TemplateDeleteDialog({ editTemplateId, isOpen, handleClose, loadData, userId }) {

    const deleteTemplateById = (id) => API.delete(`recruiters/${userId}/templates/${id}`)
        .then(() => {
            handleClose();
            loadData();
        })
        .catch((error) => {
            // eslint-disable-next-line no-console
            console.error(error.message);
        });

    return (
        <Dialog
            open={isOpen}
            onClose={handleClose}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
            <DialogTitle>Удаление шаблона</DialogTitle>
            <DialogContent>
                <DialogContentText id="alert-dialog-description">
                    Действительно хотите удалить шаблон?
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button size={ButtonsSize.sm} onClick={handleClose}>Отменить</Button>
                <Button size={ButtonsSize.sm} onClick={() => deleteTemplateById(editTemplateId)} autoFocus>
                    Подтвердить
                </Button>
            </DialogActions>
        </Dialog>
    );
}

export default TemplateDeleteDialog;

TemplateDeleteDialog.propTypes = {
    editTemplateId: PropTypes.number,
    isOpen: PropTypes.bool,
    handleClose: PropTypes.func,
    loadData: PropTypes.func,
    userId: PropTypes.number
};
