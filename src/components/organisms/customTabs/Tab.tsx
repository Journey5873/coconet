import React, { ReactNode } from "react";

import { useTabContext } from "./useTabContext";

interface Props {
    children: ReactNode;
    value: string;
}

const Tab = ({ children, value }: Props) => {
    const { activeValue } = useTabContext();

    if (activeValue !== value) {
        return null;
    }
    return <>{children}</>;
};

export default Tab;
