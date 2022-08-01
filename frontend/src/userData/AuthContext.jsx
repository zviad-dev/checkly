import { createContext } from 'react';

const AuthContext = createContext({
    isLoaded: false,
    user: null,
    token: null,
    setUser: () => {},
    setToken: () => {},
    logOut: () => {},
    loadData: () => {},
    surveyId: null,
    setSurveyId: () => {}
});

export default AuthContext;
