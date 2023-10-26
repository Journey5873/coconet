import React, { PropsWithChildren } from "react";

export interface TabsContextProviderProps {
    activeValue: string;
}

const TabContext = React.createContext<any>(null);

function TabProvider({ children, value }: any) {
    return <TabContext.Provider value={value}>{children}</TabContext.Provider>;
}

function useTabContext() {
    const context = React.useContext(TabContext);
    if (context === undefined) {
        throw new Error("useCounterContext must be used within a CounterProvider");
    }
    return context;
}

export { TabProvider, useTabContext };
