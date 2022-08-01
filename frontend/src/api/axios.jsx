import axios from 'axios';
import Cookies from 'js-cookie';

const BASE_URL = `http://checkly.ru/api/`;
export const BASE_WS_URL = `ws://checkly.ru/ws`;

const api = axios.create({
    baseURL: BASE_URL
});

api.interceptors.request.use(
    (config) => {
        const authToken = Cookies.get('auth-token');

        if (authToken) {
            // eslint-disable-next-line no-param-reassign
            config.headers.authorization = authToken;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

export default api;
