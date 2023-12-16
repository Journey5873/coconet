import { useRoutes } from 'react-router-dom'
import { routes } from './routes'
import { useEffect } from 'react'
import { useAppDispatch } from './store/RootReducer'
import { setToken } from './store/authSlice'

function App() {
  const elem = useRoutes(routes)
  const dispatch = useAppDispatch()

  useEffect(() => {
    const userInfoString = localStorage.getItem('userInfo')
    if (userInfoString) {
      const result = JSON.parse(userInfoString)
      dispatch(setToken(result))
      console.log('성공')
    }
  }, [])

  return elem
}

export default App
