import { Navigate, Outlet } from 'react-router-dom';
import useAuth from '../userData/useAuth';

function GuestRoute() {
    const auth = useAuth();

    return auth.isLoaded && auth.user ? <Navigate to="/home" /> : <Outlet />;
}

export default GuestRoute;
