import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  user: {},
  isUserLoading: false,
  isUserCreationLoading: false,
  errorUser: "",
};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    loaduser(state, action) {
      state.user = action.payload.profile;
      state.isUserLoading= false;
      state.errorUser= "hhhhh";
  },
  UserLoading(state) {
    state.isUserLoading= true;
  },
  loadProfile(state, action) {
    state.user=  action.payload;
    state.isUserLoading= false;
  },
  loadUserFailed(state, action) {
    state.errorUser=  action.payload;
    state.isUserLoading= false;
  },
  AccountCreationPending(state, action) {
    state.isUserCreationLoading = action.payload;
  },
  UserCreationFailed(state, action) {
    state.errorUser= action.payload;
    state.isUserCreationLoading= false;
  },
  },
});

export const {loaduser,loadUserFailed,loadProfile,UserLoading,UserCreationFailed,AccountCreationPending} = userSlice.actions;
export default userSlice.reducer;