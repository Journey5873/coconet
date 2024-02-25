import { combineReducers, configureStore } from '@reduxjs/toolkit'
import positionList from './positionSlice'
import { authSlice } from './authSlice'
import { TypedUseSelectorHook, useDispatch } from 'react-redux'
import { useSelector } from 'react-redux'

const rootReducer = combineReducers({
  position: positionList.reducer,
  auth: authSlice.reducer,
})

const store = configureStore({
  reducer: {
    reducer: rootReducer,
  },
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch
export const useAppDispatch = () => useDispatch<AppDispatch>()
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector

export default store
