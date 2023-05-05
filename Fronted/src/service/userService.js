import axios from "axios";
import { loadProfile } from "../data/userSlice";
import { getAuthToken, getUserID } from "./authService";
import { errorNotification } from "../components/Notification";
const baseUrl = "http://localhost:9055/api/users/";
const profileUrl = baseUrl + "profile";
const RegisterUrl = "http://localhost:9055/api/auth/register";

export const UserCreateAccount = (frmData) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await axios.post(RegisterUrl, frmData);
      resolve(res.data);
    } catch (error) {
      if (error.response && error.response.status === 400) {
        resolve(error.response.data);
      } else {
        reject(error);
      }
    }
  });
};

export const fetchProfile = () => {
  return async (dispatch) => {
    const fetchData = async () => {
      const response = await axios.get(baseUrl + getUserID() + "/profile", {
        headers: {
          Authorization: `Bearer ${getAuthToken()}`,
        },
      });
      if (!response.data) {
        throw new Error("Could not fetch user profile data!");
      }
      return response.data;
    };

    try {
      const ProfileData = await fetchData();
      dispatch(loadProfile(ProfileData));
    } catch (error) {
      errorNotification("Fetching Profile failed!", error.message);
    }
  };
};

export const ProfileUpdate = async (frmData) => {
  const headers = {
    Authorization: `Bearer ${getAuthToken()}`,
  };
  try {
    const url = baseUrl + getUserID() + "/profile";
    return await axios.put(url, frmData, { headers });
  } catch (error) {
    errorNotification(error);
  }
};
