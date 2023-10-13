import React from "react";
import GlobalLayout from "./pages/layout";
import Index from "./pages/index";
import SettingPage from "./pages/setting";

export const routes = [
    {
        path: "/",
        element: <GlobalLayout />,
        children: [
            { path: "/", element: <Index />, index: true },
            {
                path: "/setting",
                element: <SettingPage />,
                index: true,
            },
        ],
    },
];
