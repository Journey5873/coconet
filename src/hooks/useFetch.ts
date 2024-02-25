import { useState, useEffect, useMemo } from "react";

const setItemWithExpireTime = <T extends any[]>(keyName: string, keyValue: T, tts: number) => {
    const now = new Date();
    const obj = {
        value: keyValue,
        expire: now.getTime() + tts,
    };

    const objString = JSON.stringify(obj);

    localStorage.setItem(keyName, objString);
};

export default function useFetch<T extends any[]>(fetchFn: () => Promise<T>, name: string, initialState: T) {
    const [data, setData] = useState<T>(initialState);
    const [isError, setIsError] = useState<boolean>(false);
    const [page, setPage] = useState<number>(1);
    const [lastPage, setLastPage] = useState<number>(0);

    const handlePage = (value: number) => {
        if (value === 1) getState(0);

        if (value !== 1) getState(value * 10);

        setPage(value);
    };

    const getState = async (sliceStartPage: number) => {
        try {
            const now = new Date();
            const cacheData = localStorage.getItem(name);

            if (cacheData) {
                const parseData = JSON.parse(cacheData);

                if (now.getTime() < parseData.expire) {
                    setData(parseData.value.slice(sliceStartPage, sliceStartPage + 10) as T);
                    setIsError(false);
                    return;
                }

                localStorage.removeItem(name);
            }
            const result = await fetchFn();

            setLastPage(Math.ceil([result].length / 10));
            setItemWithExpireTime(name, [result], 1000 * 60 * 10);
            setData([result].slice(sliceStartPage, sliceStartPage + 10) as T);
            setIsError(false);
        } catch (error) {
            setIsError(true);
        }
    };

    useEffect(() => {
        getState(0);
    }, []);

    return {
        data,
        isError,
        page,
        lastPage,
        handlePage,
    };
}
