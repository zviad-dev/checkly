import PropTypes from 'prop-types';
import { FormLabel } from '../../components/application/FormElements';

const spacer = <span>&nbsp;</span>;

function RegistrationFormLabel({ label, htmlItemFor }) {
    return <FormLabel htmlFor={htmlItemFor}>{label || spacer}</FormLabel>;
}

RegistrationFormLabel.propTypes = {
    label: PropTypes.string,
    htmlItemFor: PropTypes.string
};

export default RegistrationFormLabel;
