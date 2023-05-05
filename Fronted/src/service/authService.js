import jwt_decode from 'jwt-decode';

import axios from "axios";
import { loginPending } from '../data/authSlice';
const baseUrl = "http://localhost:9055/api/auth/";
const loginUrl = baseUrl + "login";
const rgisterUrl = baseUrl + "register";
const OTPUrl = baseUrl + "otp";


  export const userLogin = (frmData) => {
    //console.log(frmData);

    return new Promise(async (resolve, reject) => {
      try {
        const res = await axios.post(loginUrl, frmData);
        // console.log(frmData.userName);
        //console.log(res.data.userName);
        //dispatch(loginPending());
  
        resolve(res.data);
  
        // if (res.data.userName === frmData.userName) {
        //   //sessionStorage.setItem("accessToken", res.data.accessToken);
        //   localStorage.setItem(
        //     "rogeApp",
        //     JSON.stringify({ refreshToken: res.data.refreshToken })
        //   );
        // }
      } catch (error) {
        reject(error);
      }
    });
  };


  export const VerifyOTP = (frmData) => {
    return new Promise(async (resolve, reject) => {
      try {
        const res = await axios.post(OTPUrl, frmData);
        resolve(res.data);
        const decoded = jwt_decode(res.data.result);
        console.log(decoded);

  
        // if (res.data.userName === frmData.userName) {
        //   //sessionStorage.setItem("accessToken", res.data.accessToken);
        //   localStorage.setItem(
        //     "rogeApp",
        //     JSON.stringify({ refreshToken: res.data.refreshToken })
        //   );
        // }
      } catch (error) {
        reject(error);
      }
    });
  };

  export const userLogout = async () => {
    try {
      await axios.delete(rootUrl, {
        headers: {
          Authorization: sessionStorage.getItem("accessJWT"),
        },
      });
    } catch (error) {
      console.log(error);
    }
  };

  export const fetchNewAccessJWT = () => {
    return new Promise(async (resolve, reject) => {
      try {
        const { refreshToken } = JSON.parse(localStorage.getItem("rogeApp"));
  
        if (!refreshToken) {
          reject("Token not found!");
        }
        console.log(refreshToken);
        // const res = await axios.get(refreshTokenuRL, {
        //   headers: {
        //     Authorization: refreshJWT,
        //   },
        // });
  
        // if (res.data.status === "success") {
        //   sessionStorage.setItem("accessJWT", res.data.accessJWT);
        // }
  
        resolve(true);
      } catch (error) {
        if (error.message === "Request failed with status code 403") {
          localStorage.removeItem("rogeApp");
        }
        reject(false);
      }
    });
  };


  


  export const fetchUser = () => {
    return new Promise(async (resolve, reject) => {
      try {
        const accessJWT = sessionStorage.getItem("accessJWT");
  
        if (!accessJWT) {
          reject("Token not found!");
        }
  
        const res = await axios.get(rootUrl, {
          headers: {
            Authorization: accessJWT,
          },
        });
  
        resolve(res.data);
      } catch (error) {
        console.log(error);
        reject(error.message);
      }
    });
  };




  export const setAuthToken = (token) => {
    if (token) {
      localStorage.setItem('irmbojwtToken', token);
    } else {
      localStorage.removeItem('irmbojwtToken');
    }
  };
  
  export const getAuthToken = () => {
    return localStorage.getItem('irmbojwtToken');
  };

  export const setUserID = (userId) => {
    if (userId) {
      localStorage.setItem('myUserID', userId);
    } else {
      localStorage.removeItem('myUserID');
    }
  };
  
  export const getUserID = () => {
    return localStorage.getItem('myUserID');
  };

  
  export const getusernameFromToken = (token) => {
    try {
      const decoded = jwt_decode(token);
      return decoded.sub;
    } catch (err) {
      return null;
    }
  };

  export function checkTokenValidity(token) {
    const decodedToken = jwt_decode(token);
      if (decodedToken.exp < Date.now() / 1000) { // check if the token is expired
        return false;
      } else {
        return true;
      }

    
  }