import React from "react";

import useFetch from "../hooks/useFetch";

interface Dummy {
    count: number;
    name: string;
    age: number;
}

const Index = () => {
    const getData = async () => {
        const response = await fetch("https://api.agify.io?name=michael");
        const result = await response.json();

        return result;
    };
    const { data } = useFetch<Dummy[]>(getData, "dummy", []);

    console.log(data);

    return <div>hello world</div>;
};

export default Index;
