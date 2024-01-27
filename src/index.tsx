import React, { PropsWithChildren } from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import { BrowserRouter } from 'react-router-dom'
import { UserProvier } from './api/services/userService'
import { ArticleProvider } from './api/services/articleService'
import { Provider } from 'react-redux'
import store from './store/RootReducer'

const MultiServiceProvider = ({ children }: PropsWithChildren) => {
  return (
    <>
      <ArticleProvider>
        <UserProvier>{children}</UserProvier>
      </ArticleProvider>
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
