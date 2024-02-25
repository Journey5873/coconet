import { createSlice } from '@reduxjs/toolkit'
import type { PayloadAction } from '@reduxjs/toolkit'

type AuthState = {
  token?: string
  name?: string
}

const initialState: AuthState = {
  token: '',
  name: '',
}

export const authSlice = createSlice({
  name: 'authSlice',
  initialState,
  reducers: {
    setToken: (state, action: PayloadAction<AuthState>) => {
      const { token } = action.payload
      state.token = token
    },
    removeToken: (state) => {
      state.token = ''
      state.name = ''
    },
    setUserName: (state, action: PayloadAction<AuthState>) => {
      state.name = action.payload.name
    },
  },
})

export const { setToken, removeToken, setUserName } = authSlice.actions

export default authSlice.reducer
