import * as React from 'react';
import { Routes, Route, Outlet, NavLink } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import CreateAccount from '../pages/CreateAccount';
import LoginPage from '../pages/LoginPage';
import ProfilePage from '../pages/ProfilePage';
import Page404 from '../pages/Page404';
import PublicRoute from './PublicRoute';
import PrivateRoute from './PrivateRoute';
import ForgotPassword from '../pages/ForgotPassword';
import ChangePassword from '../pages/ChangePassword';

const AppRouter = () => (
    <Routes>
      <Route element={<Layout />}>
      <Route path="/" element={<PrivateRoute> <ProfilePage /></PrivateRoute>} />
      <Route path="login" element={<PublicRoute> <LoginPage /></PublicRoute>} />
      <Route path="create-account" element={<PublicRoute> <CreateAccount /></PublicRoute>} />
      <Route path="forgot-password" element={<PublicRoute> <ForgotPassword /></PublicRoute>} />
      <Route path="change-password" element={<PublicRoute> <ChangePassword /></PublicRoute>} />
      <Route path="*"  element={<Page404 />} />
    </Route>
  </Routes>
  );

  const Layout = () => {
    const style = ({ isActive }) => ({
      fontWeight: isActive ? 'bold' : 'normal',
    });
  
    return (
      <>
        <main>
          <Outlet />
        </main>

        <ToastContainer />
      </>
    )
  };
  
  
  
  export default AppRouter;
  