import React from "react";
import GlobalLayout from "./pages/layout";
import Index from "./pages/index";
import SettingPage from "./pages/setting";
import MyPosts from "./pages/myPosts";
import MyLikes from "./pages/myLikes";
import PostDetail from "./pages/postDetail";

export const routes = [
    {
        path: "/",
        element: <GlobalLayout />,
        children: [
            { path: "/", element: <Index />, index: true },
            {
                path: "/post/:id",
                element: <PostDetail />,
                index: true,
            },
            {
                path: "/setting",
                element: <SettingPage />,
                index: true,
            },
            {
                path: "/myPosts",
                element: <MyPosts />,
                index: true,
            },
            {
                path: "/myLikes",
                element: <MyLikes />,
                index: true,
            },
        ],
    },
];
