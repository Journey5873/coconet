import React from 'react'
import GlobalLayout from './pages/layout'
import Index from './pages/index'
import SettingPage from './pages/setting'
import MyPosts from './pages/myPosts'
import MyLikes from './pages/myLikes'
import PostDetail from './pages/postDetail'
import { dummyData } from './data/data'
import PostNew from './pages/postNew'
import ChattingPage from './pages/chat'
import PostForm from './pages/postDetail/postForm'

export const routes = [
  {
    path: '/',
    element: <GlobalLayout />,
    children: [
      { path: '/', element: <Index />, index: true },
      {
        path: '/post/:id',
        element: <PostDetail />,
        index: true,
      },
      {
        path: '/post/new',
        element: <PostNew />,
        index: true,
      },
      {
        path: '/post/edit/:id',
        element: <PostForm />,
        index: true,
      },
      {
        path: '/setting',
        element: <SettingPage />,
        index: true,
      },
      {
        path: '/myPosts',
        element: <MyPosts />,
        index: true,
      },
      {
        path: '/myLikes',
        element: <MyLikes />,
        index: true,
      },
      {
        path: '/myLikes',
        element: <MyLikes />,
        index: true,
      },
      {
        path: '/chat',
        element: <ChattingPage />,
        index: true,
      },
    ],
  },
]
