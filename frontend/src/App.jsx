import { BrowserRouter as Router } from 'react-router-dom';
import { createGlobalStyle, ThemeProvider } from 'styled-components';
import { Theme } from './Theme';
import AuthProvider from './userData/AuthProvider';

import AllRoutes from './routes/AllRoutes';

const Global = createGlobalStyle`
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
        font-family: 'Encode Sana Expanded',sans-serif;
        body {
    background: #f8faff;
}
    }
`;

function App() {
    return (
        <AuthProvider>
            <Router>
                <ThemeProvider theme={Theme}>
                    <Global />
                    <AllRoutes />
                </ThemeProvider>
            </Router>
        </AuthProvider>
    );
}

export default App;
