import { useRoutes } from 'react-router-dom'
import { routes } from './routes'
import { useEffect } from 'react'
import { useAppDispatch } from './store/RootReducer'
import { setToken } from './store/authSlice'

function App() {
  const elem = useRoutes(routes)
  const dispatch = useAppDispatch()

  useEffect(() => {
    const accessToken = localStorage.getItem('accessToken')
    if (accessToken) {
      dispatch(setToken({ token: accessToken }))
      console.log('성공')
    }
  }, [])

  return elem
}

export default App
