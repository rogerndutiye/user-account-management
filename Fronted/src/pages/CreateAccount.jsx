
import { useState } from "react";
import { Link } from 'react-router-dom'

import ImageLight from '../assets/img/create-account-office.jpeg'
import ImageDark from '../assets/img/create-account-office-dark.jpeg'
import { Input, Label, Button } from '@windmill/react-ui'
import { AccountCreationPending, UserCreationFailed } from "../data/userSlice";
import { useNavigate } from "react-router-dom";
import { errorNotification, successNotification } from "../components/Notification";
import { useDispatch, useSelector } from "react-redux";
import { UserCreateAccount } from "../service/userService";
import ErrorComponent from "../components/ErrorComponent";

function CreateAccount() {

  let navigate = useNavigate();
  const dispatch = useDispatch();
  const { isUserCreationLoading,errorUser } = useSelector((state) => state.user);

  const [Email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  

  const handleOnChange = (e) => {
    const { name, value } = e.target;

    switch (name) {
      case "Email":
        setEmail(value);
        break;
      case "password":
        setPassword(value);
        break;
      case "confirmPassword":
        setConfirmPassword(value);
        break;
      default:
        break;
    }
  };

  const handleOnSubmit = async (e) => {
    e.preventDefault();

    if (password !== confirmPassword) {
      errorNotification("Passwords do not match!");
    }else if (!Email || !password || !confirmPassword) {
      errorNotification("Please fill out all fields");
    }else{
    dispatch(AccountCreationPending(true));
    try {
      const response = await UserCreateAccount({ email: Email, password,confirmPassword });
      console.log(response);
      if (response.statusCode !== 201) {
        dispatch(UserCreationFailed(response.errors));
        errorNotification(response.message);
      }else{
      dispatch(AccountCreationPending(false));
      successNotification(response.message);
      navigate('/login')
      }
    } catch (error) {
      dispatch(AccountCreationPending(false));
      errorNotification(error.message);
    }
  }
  };


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
                Create account
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

              <Label className="mt-5">
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

              {isUserCreationLoading && <div className="spinner-6 -mt-40 m-auto "></div>}

              <Label className="mt-5">
                <span>Confirm password</span>
                <input
                  type="password"
                  name="confirmPassword"
                  onChange={handleOnChange}
                  value={confirmPassword}
                  className="w-full pl-10 pr-3 py-2 bg-gray-50 rounded-lg border-2 border-gray-200 outline-none focus:border-blue-500"
                  placeholder="***************"
                />
              </Label>



      {errorUser &&(
        <ErrorComponent errors={errorUser} />
      )}

              

              <Button  onClick={handleOnSubmit} block className="mt-4">
                Create account
              </Button>

              <hr className="my-8" />

            

              <p className="mt-4">
                <Link
                  className="text-sm font-medium text-purple-600 dark:text-purple-400 hover:underline"
                  to="/login"
                >
                  Already have an account? Login
                </Link>
              </p>
            </div>
          </main>
        </div>
      </div>
    </div>
  )
}

export default CreateAccount
