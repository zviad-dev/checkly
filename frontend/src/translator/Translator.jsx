function Translator(status) {
    if (status === 'PENDING') {
        return 'В РАБОТЕ';
    }
    if (status === 'OPEN') {
        return 'ОТКРЫТО';
    }
    if (status === 'CLOSED') {
        return 'ЗАВЕРШЕНА';
    }
    if (status === 'FINISHED') {
        return 'ЗАВЕРШЕНА';
    }
}

export default Translator;
