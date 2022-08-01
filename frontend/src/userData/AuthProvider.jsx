import { useCallback, useEffect, useMemo, useState } from 'react';
import Cookies from 'js-cookie';
import PropTypes from 'prop-types';
import AuthContext from './AuthContext';
import API from '../api/axios';

function AuthProvider({ children }) {
    const [user, setUser] = useState(null);
    const [token, setTokenData] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [surveyId, setSurveyId] = useState(null);

    const setToken = useCallback((tokenData) => {
        setTokenData(tokenData);

        if (tokenData) {
            Cookies.set('auth-token', tokenData);
        } else {
            Cookies.remove('auth-token');
        }
    }, []);

    const logOut = useCallback(() => {
        setUser(null);
        setToken(null);
    }, [setToken]);

    const loadData = useCallback(async () => {
        const tokenData = Cookies.get('auth-token');
        setTokenData(tokenData);

        try {
            if (tokenData) {
                const { data } = await API.get(`login/self`);
                setUser(data);
            }
        } catch {
            setToken(null);
        } finally {
            setIsLoaded(true);
        }
    }, [setToken, token]);

    useEffect(() => {
        loadData();
    }, [loadData]);

    const contextValue = useMemo(
        () => ({
            isLoaded,
            user,
            token,
            setUser,
            setToken,
            logOut,
            loadData,
            setSurveyId,
            surveyId
        }),
        [
            isLoaded,
            user,
            token,
            setUser,
            setToken,
            logOut,
            loadData,
            setSurveyId,
            surveyId
        ]
    );

    return (
        <AuthContext.Provider value={contextValue}>
            {children}
        </AuthContext.Provider>
    );
}

export default AuthProvider;

AuthProvider.propTypes = {
    children: PropTypes.node
};
