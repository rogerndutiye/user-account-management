import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { ProfileUpdate, fetchProfile } from "../service/userService";
import "../assets/css/tailwind.min.css";
import { Label, Button } from "@windmill/react-ui";
import DatePicker from "react-datepicker";
import axios from "axios";

import Profileavatar from "../assets/img/profileavatar.jpeg";

import "react-datepicker/dist/react-datepicker.css";
import {
  errorNotification,
  successNotification,
} from "../components/Notification";
import { useNavigate } from "react-router-dom";
import { UserLoading } from "../data/userSlice";
import { setAuthToken, setUserID } from "../service/authService";
import { LogOut } from "../data/authSlice";

function ProfilePage() {
  let navigate = useNavigate();
  const { user, isUserLoading } = useSelector((state) => state.user);

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(UserLoading());
    dispatch(fetchProfile());
  }, []);

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [dateOfBirth, setDateOfBirth] = useState(new Date());
  const [gender, setGender] = useState("");
  const [maritalStatus, setMaritalStatus] = useState("");
  const [nationality, setNationality] = useState("");
  const [file, setFile] = useState("");
  const [profilePhoto, setProfilePhoto] = useState("");

  const handleChangeMaritalStatus = (event) => {
    console.log(event.target.value);
    setMaritalStatus(event.target.value);
  };

  const handleChangeGender = (event) => {
    console.log(event.target.value);
    setGender(event.target.value);
  };


  const handleLogout = (e) => {
    e.preventDefault();
    setUserID();
    setAuthToken();
    dispatch(LogOut());
    successNotification("You have successfully logged out!")
    navigate('/login');


  }

  const handleOnChange = (e) => {
    const { name, value } = e.target;

    switch (name) {
      case "firstName":
        setFirstName(value);
        break;
      case "lastName":
        setLastName(value);
        break;
      case "dateOfBirth":
        setDateOfBirth(value);
        break;
      case "nationality":
        setNationality(value);
        break;
      default:
        break;
    }
  };

  const handleOnSubmit = async (e) => {
    e.preventDefault();

    if (
      !dateOfBirth ||
      !gender ||
      !maritalStatus ||
      !nationality ||
      !profilePhoto ||
      !firstName ||
      !lastName
    ) {
      errorNotification("All fields are mandatory !");
    } else {
      try {
        //dispatch(UserLoading());
        console.log("hahah");
        const response = await ProfileUpdate({
          dateOfBirth,
          gender,
          maritalStatus,
          nationality,
          profilePhoto,
          firstName,
          lastName
        });
        if (response.statusCode !== 201) {
          errorNotification(response.message);
        }
        //dispatch(otpPending(response.result));
        successNotification(response.message);
        navigate("/login");
      } catch (error) {
        errorNotification(error.message);
      }
    }
  };



  const onFileChangeHandler = async (e) => {
    e.preventDefault();
    setFile(e.target.files[0]);
    console.log(file);
    const formData = new FormData();
    formData.append("file", file);
    try {
      const response = await axios({
        method: "post",
        url: "http://localhost:9200/api/files/upload",
        data: formData,
        headers: { "Content-Type": "multipart/form-data" },
      });
      setProfilePhoto(response.data.url);
    } catch (error) {
      errorNotification("File Upload failed, please choose the file again");
    }

    // const response= axios.post("http://localhost:9200/api/files/upload", formData);
    // console.log(response.data);
    // fetch('http://localhost:9200/api/files/upload', {
    //     method: 'post',
    //     body: formData
    // }).then(res => {
    //     if(res.ok) {
    //         console.log(res.data.url);
    //         alert("File uploaded successfully.")
    //     }
    // });
  };


  function getAge(dateString) 
   {
    var today = new Date();
    var birthDate = new Date(dateString);
    var age = today.getFullYear() - birthDate.getFullYear();
    var m = today.getMonth() - birthDate.getMonth();
    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) 
    {
        age--;
    }
    return age;
}

  return (
    <>
      <div className="max-w-3xl flex items-center h-auto lg:h-screen flex-wrap mx-auto my-32 lg:my-0">
      {isUserLoading && (
                  <div className="spinner-6 mt-500 m-auto "></div>
                )}
                {!isUserLoading &&(<>
        <div
          id="profile"
          className="w-full lg:w-4/6 rounded-lg lg:rounded-l-lg lg:rounded-r-none shadow-2xl bg-white opacity-75 mx-6 lg:mx-0"
        >

          <div className="p-4 md:p-12 text-center lg:text-left">
            {/* <div
            className="block lg:hidden rounded-full shadow-xl mx-auto -mt-16 h-48 w-48 bg-cover bg-center"
            style={{
              backgroundImage: `url("https://source.unsplash.com/MP0IUfwrn0A")` 
            }}
          ></div> */}
            {/* {
            user && (
              <h1 className="text-3xl font-bold pt-8 lg:pt-0">{user.email} ggg</h1>
              )
          } */}

            {!user.completed && (
              <>
                <a href="#" className="text-1xl font-semibold text-gray-900  dark:text-white decoration-blue-500 decoration-double"> Please complete your profile</a> 
                <div className="mx-auto lg:mx-0 w-4/5 pt-3 border-b-2 border-green-500 opacity-25"></div>

                

                <Label className="mt-5">
                  <span>First Name</span>
                  <input
                    type="text"
                    name="firstName"
                    onChange={handleOnChange}
                    value={firstName}
                    className="w-full pl-10 pr-3 py-2 rounded-lg border-2 border-gray-200 outline-none focus:border-blue-500"
                    placeholder="Your first Name"
                  />
                </Label>

                <Label className="mt-5">
                  <span>Last Name</span>
                  <input
                    type="text"
                    name="lastName"
                    onChange={handleOnChange}
                    value={lastName}
                    className="w-full pl-10 pr-3 py-2 rounded-lg border-2 border-gray-200 outline-none focus:border-blue-500"
                    placeholder="Your last Name"
                  />
                </Label>

                <Label className="mt-4">
                  <span>Date Of Birth</span>
                  <DatePicker
                    name="dateOfBirth"
                    value={dateOfBirth}
                    selected={dateOfBirth}
                    onChange={(date) => setDateOfBirth(date)}
                    maxDate={new Date()}
                    placeholderText="Select a date before today "
                    className="w-full pl-10 pr-3 py-2 rounded-lg border-2 border-gray-200 outline-none focus:border-blue-500"
                  />
                </Label>

                <Label className="mt-4">
                  <span> Gender</span>
                  <select
                    value={gender}
                    onChange={handleChangeGender}
                    className="w-full pl-0 pr-3 py-2 rounded-lg border-2 border-gray-200 outline-none focus:border-blue-500"
                  >
                    <option value="">--Choose an option--</option>
                    <option value="MALE">MALE 🍏</option>
                    <option value="FEMALE">FEMALE 🥝</option>
                  </select>
                </Label>

                <Label className="mt-4">
                  <span> Marital Status</span>
                  <select
                    value={maritalStatus}
                    onChange={handleChangeMaritalStatus}
                    className="w-full pl-0 pr-3 py-2 rounded-lg border-2 border-gray-200 outline-none focus:border-blue-500"
                  >
                    <option value="">--Choose an option--</option>
                    <option value="SINGLE">SINGLE</option>
                    <option value="MARRIED">MARRIED</option>
                    <option value="DIVORCED">DIVORCED</option>
                    <option value="WIDOWED">WIDOWED</option>
                  </select>
                </Label>

                {isUserLoading && (
                  <div className="spinner-6 -mt-40 m-auto "></div>
                )}

                <Label className="mt-5">
                  <span>Nationality</span>
                  <input
                    type="text"
                    name="nationality"
                    onChange={handleOnChange}
                    value={nationality}
                    className="w-full pl-10 pr-3 py-2 rounded-lg border-2 border-gray-200 outline-none focus:border-blue-500"
                    placeholder="Your Nationality"
                  />
                </Label>

                <Label className="mt-5">
                  <span>Upload Your profile Photo </span>
                  <input
                    type="file"
                    name="file"
                    className="w-full pl-10 pr-3 py-2 rounded-lg border-2 border-gray-200 outline-none focus:border-blue-500"
                    onChange={onFileChangeHandler}
                  />
                </Label>

                {!isUserLoading && (
                  <Button onClick={handleOnSubmit} className="mt-4" block>
                    Update Profile
                  </Button>
                )}
              </>
            )}
            {user.completed && (
              <>
              <div className="flex justify-between">
  <p className="text-left"><span className="text-2xl font-bold pt-8 lg:pt-0">{user.firstName} {user.lastName}</span></p>
  <p className="text-right"> 

  {user.user.status ==="VERIFIED" && (
    <span className="bg-green-100 text-green-800 text-xs font-medium inline-flex items-center px-2.5 py-1 rounded dark:bg-gray-700 dark:text-green-400 border border-green-400">
    <svg aria-hidden="true" className="w-3 h-3 mr-1" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm1-12a1 1 0 10-2 0v4a1 1 0 00.293.707l2.828 2.829a1 1 0 101.415-1.415L11 9.586V6z" clipRule="evenodd"></path></svg>
    VERIFIED
  </span>
  )}

{user.user.status ==="UNVERIFIED" && (
  <span className="bg-blue-100 text-blue-800 text-xs font-medium inline-flex items-center px-2.5 py-1 rounded dark:bg-gray-700 dark:text-blue-400 border border-blue-400">
  <svg aria-hidden="true" className="w-3 h-3 mr-1" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm1-12a1 1 0 10-2 0v4a1 1 0 00.293.707l2.828 2.829a1 1 0 101.415-1.415L11 9.586V6z" clipRule="evenodd"></path></svg>
  UNVERIFIED
</span>
    )}

{user.user.status ==="PENDING_VERIFICATION" && (
  <span className="bg-yellow-100 text-yellow-800 text-xs font-medium inline-flex items-center px-2.5 py-1 rounded dark:bg-gray-700 dark:text-yellow-400 border border-yellow-400">
  <svg aria-hidden="true" className="w-3 h-3 mr-1" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm1-12a1 1 0 10-2 0v4a1 1 0 00.293.707l2.828 2.829a1 1 0 101.415-1.415L11 9.586V6z" clipRule="evenodd"></path></svg>
  PENDING FOR APPROVAL
</span>
    )}
  
 

  </p>
</div>
                
                <div className="mx-auto lg:mx-0 w-4/5 pt-3 border-b-2 border-green-500 opacity-25"></div>
               
                <p className="pt-2 text-gray-600 text-xs lg:text-sm flex items-center justify-center lg:justify-start">
                  <svg
                    className="h-4 fill-current text-green-700 pr-4"
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 20 20"
                  >
                    <path d="M10 20a10 10 0 1 1 0-20 10 10 0 0 1 0 20zm7.75-8a8.01 8.01 0 0 0 0-4h-3.82a28.81 28.81 0 0 1 0 4h3.82zm-.82 2h-3.22a14.44 14.44 0 0 1-.95 3.51A8.03 8.03 0 0 0 16.93 14zm-8.85-2h3.84a24.61 24.61 0 0 0 0-4H8.08a24.61 24.61 0 0 0 0 4zm.25 2c.41 2.4 1.13 4 1.67 4s1.26-1.6 1.67-4H8.33zm-6.08-2h3.82a28.81 28.81 0 0 1 0-4H2.25a8.01 8.01 0 0 0 0 4zm.82 2a8.03 8.03 0 0 0 4.17 3.51c-.42-.96-.74-2.16-.95-3.51H3.07zm13.86-8a8.03 8.03 0 0 0-4.17-3.51c.42.96.74 2.16.95 3.51h3.22zm-8.6 0h3.34c-.41-2.4-1.13-4-1.67-4S8.74 3.6 8.33 6zM3.07 6h3.22c.2-1.35.53-2.55.95-3.51A8.03 8.03 0 0 0 3.07 6z" />
                  </svg>
                  <span className="font-bold mr-2"> Gender :  </span> {user.gender}
                </p>

                <p className="pt-2 text-gray-600 text-xs lg:text-sm flex items-center justify-center lg:justify-start">
                  <svg
                    className="h-4 fill-current text-green-700 pr-4"
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 20 20"
                  >
                    <path d="M10 20a10 10 0 1 1 0-20 10 10 0 0 1 0 20zm7.75-8a8.01 8.01 0 0 0 0-4h-3.82a28.81 28.81 0 0 1 0 4h3.82zm-.82 2h-3.22a14.44 14.44 0 0 1-.95 3.51A8.03 8.03 0 0 0 16.93 14zm-8.85-2h3.84a24.61 24.61 0 0 0 0-4H8.08a24.61 24.61 0 0 0 0 4zm.25 2c.41 2.4 1.13 4 1.67 4s1.26-1.6 1.67-4H8.33zm-6.08-2h3.82a28.81 28.81 0 0 1 0-4H2.25a8.01 8.01 0 0 0 0 4zm.82 2a8.03 8.03 0 0 0 4.17 3.51c-.42-.96-.74-2.16-.95-3.51H3.07zm13.86-8a8.03 8.03 0 0 0-4.17-3.51c.42.96.74 2.16.95 3.51h3.22zm-8.6 0h3.34c-.41-2.4-1.13-4-1.67-4S8.74 3.6 8.33 6zM3.07 6h3.22c.2-1.35.53-2.55.95-3.51A8.03 8.03 0 0 0 3.07 6z" />
                  </svg>
                  <span className="font-bold mr-2"> Age :  </span>  {getAge(user.dateOfBirth)}
                </p>

                <p className="pt-2 text-gray-600 text-xs lg:text-sm flex items-center justify-center lg:justify-start">
                  <svg
                    className="h-4 fill-current text-green-700 pr-4"
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 20 20"
                  >
                    <path d="M10 20a10 10 0 1 1 0-20 10 10 0 0 1 0 20zm7.75-8a8.01 8.01 0 0 0 0-4h-3.82a28.81 28.81 0 0 1 0 4h3.82zm-.82 2h-3.22a14.44 14.44 0 0 1-.95 3.51A8.03 8.03 0 0 0 16.93 14zm-8.85-2h3.84a24.61 24.61 0 0 0 0-4H8.08a24.61 24.61 0 0 0 0 4zm.25 2c.41 2.4 1.13 4 1.67 4s1.26-1.6 1.67-4H8.33zm-6.08-2h3.82a28.81 28.81 0 0 1 0-4H2.25a8.01 8.01 0 0 0 0 4zm.82 2a8.03 8.03 0 0 0 4.17 3.51c-.42-.96-.74-2.16-.95-3.51H3.07zm13.86-8a8.03 8.03 0 0 0-4.17-3.51c.42.96.74 2.16.95 3.51h3.22zm-8.6 0h3.34c-.41-2.4-1.13-4-1.67-4S8.74 3.6 8.33 6zM3.07 6h3.22c.2-1.35.53-2.55.95-3.51A8.03 8.03 0 0 0 3.07 6z" />
                  </svg>
                  <span className="font-bold mr-2">  Date Of Birth :  </span> {user.dateOfBirth}
                </p>

                <p className="pt-2 text-gray-600 text-xs lg:text-sm flex items-center justify-center lg:justify-start">
                  <svg
                    className="h-4 fill-current text-green-700 pr-4"
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 20 20"
                  >
                    <path d="M10 20a10 10 0 1 1 0-20 10 10 0 0 1 0 20zm7.75-8a8.01 8.01 0 0 0 0-4h-3.82a28.81 28.81 0 0 1 0 4h3.82zm-.82 2h-3.22a14.44 14.44 0 0 1-.95 3.51A8.03 8.03 0 0 0 16.93 14zm-8.85-2h3.84a24.61 24.61 0 0 0 0-4H8.08a24.61 24.61 0 0 0 0 4zm.25 2c.41 2.4 1.13 4 1.67 4s1.26-1.6 1.67-4H8.33zm-6.08-2h3.82a28.81 28.81 0 0 1 0-4H2.25a8.01 8.01 0 0 0 0 4zm.82 2a8.03 8.03 0 0 0 4.17 3.51c-.42-.96-.74-2.16-.95-3.51H3.07zm13.86-8a8.03 8.03 0 0 0-4.17-3.51c.42.96.74 2.16.95 3.51h3.22zm-8.6 0h3.34c-.41-2.4-1.13-4-1.67-4S8.74 3.6 8.33 6zM3.07 6h3.22c.2-1.35.53-2.55.95-3.51A8.03 8.03 0 0 0 3.07 6z" />
                  </svg>
                  <span className="font-bold mr-2">  Marital Status :  </span>  {user.maritalStatus}
                </p>

                <p className="pt-2 text-gray-600 text-xs lg:text-sm flex items-center justify-center lg:justify-start">
                  <svg
                    className="h-4 fill-current text-green-700 pr-4"
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 20 20"
                  >
                    <path d="M10 20a10 10 0 1 1 0-20 10 10 0 0 1 0 20zm7.75-8a8.01 8.01 0 0 0 0-4h-3.82a28.81 28.81 0 0 1 0 4h3.82zm-.82 2h-3.22a14.44 14.44 0 0 1-.95 3.51A8.03 8.03 0 0 0 16.93 14zm-8.85-2h3.84a24.61 24.61 0 0 0 0-4H8.08a24.61 24.61 0 0 0 0 4zm.25 2c.41 2.4 1.13 4 1.67 4s1.26-1.6 1.67-4H8.33zm-6.08-2h3.82a28.81 28.81 0 0 1 0-4H2.25a8.01 8.01 0 0 0 0 4zm.82 2a8.03 8.03 0 0 0 4.17 3.51c-.42-.96-.74-2.16-.95-3.51H3.07zm13.86-8a8.03 8.03 0 0 0-4.17-3.51c.42.96.74 2.16.95 3.51h3.22zm-8.6 0h3.34c-.41-2.4-1.13-4-1.67-4S8.74 3.6 8.33 6zM3.07 6h3.22c.2-1.35.53-2.55.95-3.51A8.03 8.03 0 0 0 3.07 6z" />
                  </svg>
                  <span className="font-bold mr-2">  Nationality :  </span>  {user.nationality}
                </p>


               

              
                
              </>
            )}
          </div>
        </div>

        <div className="w-full lg:w-2/6">
          {user.completed && (
            <img
              src={user.profilePhoto}
              className="rounded-none lg:rounded-lg shadow-2xl hidden lg:block"
            />
          )}

          {!user.completed && (
            <img
              src={Profileavatar}
              className="rounded-none lg:rounded-lg shadow-2xl hidden lg:block"
            />
          )}
        </div>
        </>)}

        <div className="absolute top-0 right-0 h-12 w-18 p-4">
          <button  onClick={handleLogout} className="js-change-theme focus:outline-none"> Logout  🌙</button>
        </div>
      </div>
    </>
  );
}

export default ProfilePage;
