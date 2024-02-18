import React, { PropsWithChildren } from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import { BrowserRouter } from 'react-router-dom'
import { UserProvier } from './api/services/userService'
import { ArticleProvider } from './api/services/articleService'
import { Provider } from 'react-redux'
import store from './store/RootReducer'
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
import { ArticleDetailProvider } from './api/services/articleDetialService'
import AlertProvider from './components/organisms/modal/AlertModalContext'

const MultiServiceProvider = ({ children }: PropsWithChildren) => {
  return (
    <>
      <AlertProvider>
        <ArticleDetailProvider>
          <ArticleProvider>
            <UserProvier>
              {children}
              <ToastContainer
                autoClose={1000}
                pauseOnFocusLoss={false}
                pauseOnHover={false}
              />
            </UserProvier>
          </ArticleProvider>
        </ArticleDetailProvider>
      </AlertProvider>
    </>
  )
}

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement)
root.render(
  <React.StrictMode>
    <MultiServiceProvider>
      <Provider store={store}>
        <BrowserRouter>
          <App />
        </BrowserRouter>
      </Provider>
    </MultiServiceProvider>
  </React.StrictMode>,
)
