import { createSlice } from '@reduxjs/toolkit'

const initialState = {
  value: 0,
  isLoading: false,
  isAuth: false,
  username: false,
  userInfo:{},
  error: "",
  isOTPRequired:false,
}

export const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
     loginPending: (state) => {
        state.isLoading = true;
      },
      otpPending: (state,{ payload }) => {
        state.isOTPRequired = true;
        state.userInfo = payload;
      },
      loginSuccess: (state,{ payload }) => {
        state.isLoading = false;
        state.isAuth = true;
        //state.userInfo = payload;
        state.error = "";
      },
      loginFail: (state, { payload }) => {
        state.isLoading = false;
        state.error = payload;
      },
      LogOut: (state) => {
        state.isLoading = false;
        state.isOTPRequired = false;
      },
  },
})

export const {loginPending,loginFail,otpPending,loginSuccess,LogOut } = authSlice.actions

export default authSlice.reducer