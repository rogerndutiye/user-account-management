import { configureStore } from "@reduxjs/toolkit";
import userReducer from './userSlice';
import authSlice from "./authSlice";

const store = configureStore({
	reducer: {
		user: userReducer,
		auth:authSlice,
	},
});

export default store;