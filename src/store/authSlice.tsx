import { createSlice } from '@reduxjs/toolkit'
import type { PayloadAction } from '@reduxjs/toolkit'

type AuthState = {
  token: string
}

const initialState: AuthState = {
  token: '',
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
    },
  },
})

export const { setToken, removeToken } = authSlice.actions

export default authSlice.reducer
