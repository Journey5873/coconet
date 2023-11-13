import { combineReducers, configureStore } from "@reduxjs/toolkit";
import positionList from "./positionSlice";

const rootReducer = combineReducers({
    position: positionList.reducer,
});

const store = configureStore({
    reducer: {
        reducer: rootReducer,
    },
});

export type RootState = ReturnType<typeof store.getState>;

export default store;