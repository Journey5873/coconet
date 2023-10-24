import { configureStore } from "@reduxjs/toolkit";
import positionList from "./positionSlice";

const store = configureStore({
    reducer: {
        positionList: positionList.reducer,
    },
});

export default store;
export type RootState = ReturnType<typeof store.getState>;