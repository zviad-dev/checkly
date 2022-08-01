import { Navigate, Route, Routes } from 'react-router-dom';
import Home from '../pages/Home';
import Landing from '../pages/Landing';
import Signin from '../pages/Signin';
import HrForm from '../pages/HrForm/index';
import HrResponse from '../pages/HrResponse';
import RecommenderForm from '../pages/recommender/RecommenderForm';
import Registration from '../pages/registration';
import Templates from '../pages/templates';

import GuestRoute from './GuestRoutes';
import PrivateRoute from './PrivateRoute';
import useAuth from '../userData/useAuth';
import Settings from '../pages/settings/index';
import HrResponseAnswer from '../pages/HrResponseAnswer';

function AllRoutes() {
    const auth = useAuth();

    return (
        auth.isLoaded && (
            <Routes>
                <Route path="/" element={<Landing />} exact />
                <Route path="/registration" element={<Registration />} exact />
                <Route
                    path="/recommendation/:id"
                    element={<RecommenderForm />}
                    exact
                />

                <Route path="*" element={<Navigate to="/" />} exact />
                <Route path="/signin" element={<GuestRoute />}>
                    <Route path="/signin" element={<Signin />} exact />
                </Route>
                <Route path="/" element={<PrivateRoute />}>
                    <Route path="/home" element={<Home />} exact />
                    <Route path="/templates" element={<Templates />} exact />
                    <Route path="/settings" element={<Settings />} exact />
                    <Route path="/recommendation" element={<HrForm />} exact />
                    <Route
                        path="/recruiters/:id/surveys/:id/recommendations/:id"
                        element={<HrResponseAnswer />}
                        exact
                    />
                    <Route
                        path="/recruiters/:id/surveys/:id"
                        element={<HrResponse />}
                        exact
                    />
                    <Route
                        path="/settings"
                        element={<Settings to="/" />}
                        exact
                    />
                </Route>
            </Routes>
        )
    );
}

export default AllRoutes;
