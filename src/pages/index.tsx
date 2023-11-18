import React, { useState } from "react";
import useFetch from "../hooks/useFetch";
import MultiStackSelector from "../components/organisms/multiStackSelector/multiStackSelector";
import CustomCarousel from "../components/organisms/Carousel";
import styled from "styled-components";
import Tab from "../components/organisms/customTabs/Tab";
import Tabs from "../components/organisms/customTabs/Tabs";
import Card from "../components/molecules/card/card";
import FilterSelect from "../components/atoms/Select/filterSelect";

import { DummyData, dummyData } from "../data/data";

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

    return (
        <>
            <div>
                <StyledContents>
                    <CustomCarousel />
                    <Tabs options={[{ name: "전체" }, { name: "프로젝트" }, { name: "스터디" }]}>
                        <Tab value="전체">
                            <div style={{ display: "flex", columnGap: "1rem" }}>
                                <MultiStackSelector handleSelected={() => {}} selected={[]} />
                                <FilterSelect
                                    title="포지션"
                                    options={["전체", "프론트엔드", "백엔드", "데브옵스", "디자이너"]}
                                />
                                <FilterSelect
                                    title="진행방식"
                                    options={["전체", "온라인", "오프라인", "온/오프라인"]}
                                />
                            </div>
                            <StyledItemWrpper>
                                {[dummyData].map((item) => (
                                    <Card item={item} />
                                ))}
                            </StyledItemWrpper>
                        </Tab>
                        <Tab value="프로젝트">dd</Tab>
                        <Tab value="스터디">dd</Tab>
                    </Tabs>
                </StyledContents>
            </div>
        </>
    );
};

export default Index;

const StyledContents = styled.div`
    width: 1220px;
    margin: 0 auto;
`;

const StyledItemWrpper = styled.div`
    margin-top: 1rem;
    display: grid;
    grid-template-columns: repeat(4, 1fr);
`;
