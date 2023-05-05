import {React} from 'react';
import { Route, Navigate } from 'react-router-dom';
import { useSelector } from "react-redux";
import { checkTokenValidity, getAuthToken } from '../service/authService';

// const PublicRoute = ({component: Component, restricted, ...rest}) => {
//     const isAuth = useSelector((state) => state.auth.isAuth)
//     return (
//         // restricted = false meaning public route
//         // restricted = true meaning restricted route
//         <Route {...rest} render={props => (
//             isAuth() && restricted ?
//                 <Navigate to="/dashboard" />
//             : <Component {...props} />
//         )} />
//     );
// };




function PublicRoute ({ children }) {
    const isAuth = useSelector((state) => state.auth.isAuth)
    if (getAuthToken() !== null && checkTokenValidity(getAuthToken()) ) {
        // not logged in so redirect to login page with the return url
        return <Navigate to="/" state={{ from: history.location }} />
    }

    // authorized so return child components
    return children;
}

 export default PublicRoute;
