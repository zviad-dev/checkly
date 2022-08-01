import { Navigate, Outlet } from 'react-router-dom';
import useAuth from '../userData/useAuth';

function PrivateRoute() {
    const auth = useAuth();

    return auth.isLoaded && auth.user ? <Outlet /> : <Navigate to="/signin" />;
}

export default PrivateRoute;
