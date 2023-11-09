import React, { useState } from "react";

import useFetch from "../hooks/useFetch";
import MultiStackSelector from "../components/organisms/multiStackSelector/multiStackSelector";

interface Dummy {
    count: number;
    name: string;
    age: number;
}

export type ArticleType = "study_group" | "project_group";
export type MettingType = "Online" | "Offline" | "On/Offline";
export type RoleType = ["Backend" | "Frontend" | "Designer" | "IOS" | "Android" | "PM" | "QA" | "GameDev" | "DevOps"];

export interface PostProps {
    title: string;
    content: string;
    expiredAt: string;
    viewCount: number;
    bookmarkCount: number;
    articleType: ArticleType;
    meetingType: MettingType;
    role: RoleType;
    techStacks: string[];
}

export const PostDummy: PostProps = {
    title: "[Flutter] 플러터 앱 개발자 1분 모집합니다.",
    content: `현재 5명 정도 팀을 이루고 있고 어플을 개발하고 있습니다.
            팀은 기획자, 디자이너, 백엔드, 프론트엔드로 역할이 분담되어있습니다.
            디자인은 어느정도 완료되어 있습니다.

            프론트엔드(플러터) 는 현재 한 분이고 추가로 한 분 더 구하려고 합니다.
            같이 성장하실 분, 메일로 연락부탁드립니다.`,
    expiredAt: "2023-10-31",
    viewCount: 0,
    bookmarkCount: 0,
    articleType: "study_group",
    meetingType: "Online",
    role: ["Backend"],
    techStacks: ["Java", "Spring"],
};

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
                <MultiStackSelector handleSelected={() => {}} selected={[]} />
            </div>
        </>
    );
};

export default Index;
