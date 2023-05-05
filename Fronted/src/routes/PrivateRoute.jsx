import {React} from 'react';
import { Route, Navigate } from 'react-router-dom';
import { useSelector } from "react-redux";
import { checkTokenValidity, getAuthToken } from '../service/authService';


function PrivateRoute ({ children }) {
    const isAuth = useSelector((state) => state.auth.isAuth)
    if (getAuthToken() === null || !checkTokenValidity(getAuthToken()) ) {
        return <Navigate to="/login"/>
    }
    return children;
}

 export default PrivateRoute;