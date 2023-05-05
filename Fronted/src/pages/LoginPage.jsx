import { useState } from "react";
import { Link } from "react-router-dom";

import ImageLight from "../assets/img/login-office.jpeg";
import ImageDark from "../assets/img/login-office-dark.jpeg";
import { Label, Input, Button } from "@windmill/react-ui";
import {
  successNotification,
  notify,
  errorNotification,
} from "../components/Notification";
import { useNavigate } from "react-router-dom";
import {
  otpPending,
  loginFail,
  loginPending,
  loginSuccess,
} from "../data/authSlice";
import {
  userLogin,
  VerifyOTP,
  getusernameFromToken,
  setAuthToken,
  setUserID,
  getUserID
} from "../service/authService";
import { useDispatch, useSelector } from "react-redux";

function LoginPage() {
  const dispatch = useDispatch();
  let navigate = useNavigate();

  const { isOTPRequired, isLoading } = useSelector((state) => state.auth);
  const [Email, setEmail] = useState("email@23.com");
  const [password, setPassword] = useState("email#2!45QA");
  const [otp, setOtp] = useState("");

  const handleOnChange = (e) => {
    const { name, value } = e.target;

    switch (name) {
      case "Email":
        setEmail(value);
        break;
      case "password":
        setPassword(value);
        break;
      case "otp":
        setOtp(value);
        break;
      default:
        break;
    }
  };

  const handleOnSubmit = async (e) => {
    e.preventDefault();

    if (!Email || !password) {
      errorNotification("All fields are mandatory !");
    }else{
    dispatch(loginPending());
    try {
      const response = await userLogin({ email: Email, password });
      console.log(response);
      if (response.statusCode !== 200) {
        dispatch(loginFail(response.message));
      }
      setUserID(response.result.id);
      dispatch(otpPending(response.result));
      successNotification(response.message);
    } catch (error) {
      dispatch(loginFail(error.message));
      errorNotification(error.message);
    }
  }
  };

  const handleVerifyOTP = async e => {
		e.preventDefault();
        console.log(otp);

        try {
            const response = await VerifyOTP({ "email" :Email, otp });
           console.log(response);
            //console.log(isAuth.accessToken);
             
                  if (response.statusCode !== 200) {
                    errorNotification(error.message);
                  }
                  setAuthToken(response.result);
                  //getusernameFromToken(response.result)
                  dispatch(loginSuccess(response.result));
                  successNotification(response.message);
                  //dispatch(getUserProfile());
                  navigate('/');
              } catch (error) {
                  errorNotification(error.message);
              }
    }
  return (
    <div className="flex items-center min-h-screen p-6 bg-gray-50 dark:bg-gray-900">
      <div className="flex-1 h-full max-w-4xl mx-auto overflow-hidden bg-white rounded-lg shadow-xl dark:bg-gray-800">
        <div className="flex flex-col overflow-y-auto md:flex-row">
          <div className="h-32 md:h-auto md:w-1/2">
            <img
              aria-hidden="true"
              className="object-cover w-full h-full dark:hidden"
              src={ImageLight}
              alt="Office"
            />
            <img
              aria-hidden="true"
              className="hidden object-cover w-full h-full dark:block"
              src={ImageDark}
              alt="Office"
            />
          </div>
          <main className="flex items-center justify-center p-6 sm:p-12 md:w-1/2">
            <div className="w-full">
              <h1 className="mb-4 text-xl font-semibold text-gray-700 dark:text-gray-200">
                Login
              </h1>
              <Label className="mt-4">
                <span>Email</span>
                <input
                  type="text"
                  name="Email"
                  value={Email}
                  onChange={handleOnChange}
                  className="w-full pl-10 pr-3 py-2 rounded-lg border-2 border-gray-200 outline-none focus:border-blue-500"
                  placeholder="john@doe.com"
                />
              </Label>
              

              <Label className="mt-4">
                <span>Password</span>
                <input
                  type="password"
                  name="password"
                  onChange={handleOnChange}
                  value={password}
                  className="w-full pl-10 pr-3 py-2 bg-gray-50 rounded-lg border-2 border-gray-200 outline-none focus:border-blue-500"
                  placeholder="***************"
                />
              </Label>
              {isOTPRequired && <div className="spinner-6 -mt-40 m-auto "></div>}

              {isOTPRequired && (
              <Label className="mt-10">
                <span className="text-blue-900">Check your Email then Enter Received OTP</span>
                <input
                      type="text"
                      name="otp"
                      onChange={handleOnChange}
                      value={otp}
                      className="w-full pl-10 pr-3 py-2 rounded-lg border-2 border-green-400 outline-none focus:border-blue-500"
                      placeholder="Confirm With OTP"
                    />
                     <Button onClick={handleVerifyOTP}  className="mt-4"
                  block>Verify OTP</Button>
              </Label>)}

              

              {!isLoading && !isOTPRequired && (
                <Button
                  onClick={handleOnSubmit}
                  className="mt-4"
                  block
                >
                  Log in
                </Button>
              )}

              <hr className="my-8" />
               
              {!isLoading && (<>
              <p className="mt-4">
                <Link
                  className="text-sm font-medium text-purple-600 dark:text-purple-400 hover:underline"
                  to="/forgot-password"
                >
                  Forgot your password?
                </Link>
              </p>
              <p className="mt-1">
                <Link
                  className="text-sm font-medium text-purple-600 dark:text-purple-400 hover:underline"
                  to="/create-account"
                >
                  Create account
                </Link>
              </p> </>)}
            </div>
          </main>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
