import { useRoutes } from 'react-router-dom'
import { routes } from './routes'
import { useEffect } from 'react'
import { useAppDispatch } from './store/RootReducer'
import { setToken } from './store/authSlice'
import { useArticleDetailService } from './api/services/articleDetialService'

function App() {
  const elem = useRoutes(routes)
  const articleDetailService = useArticleDetailService()
  const dispatch = useAppDispatch()

  const getMyMemeberUUId = async () => {
    try {
      const result = await articleDetailService.getMyMemberId()
      if (result.data) {
        const accessToken = String(result.data)
        if (accessToken) {
          dispatch(setToken({ token: accessToken }))
        }
      }
    } catch (error) {
      console.log(error)
    }
  }

  useEffect(() => {
    getMyMemeberUUId()
  }, [])

  return elem
}

export default App
