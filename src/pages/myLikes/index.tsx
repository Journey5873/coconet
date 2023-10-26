import { useState } from "react";
import { BiSolidBookBookmark, BiSolidHeart } from "react-icons/bi";
import styled from "styled-components";

import Tabs from "../../components/organisms/customTabs/Tabs";

import Tab from "../../components/organisms/customTabs/Tab";

const MyLikes = () => {
    const [isActive, setIsActive] = useState<boolean>(false);

    const [value, setValue] = useState("one");

    const handleChange = (event: any, newValue: string) => {
        setValue(newValue);
    };

    return (
        <StyledMyPostsPageWrapper>
            <StyledMypostsMyLikes>
                <StyledMypostsMain>
                    <Tabs
                        options={[
                            {
                                name: "읽은 목록",
                                icon: <BiSolidBookBookmark color="#8CAF8E" style={{ width: 35, height: 35 }} />,
                            },
                            {
                                name: "관심 목록",
                                icon: <BiSolidHeart color="#8CAF8E" style={{ width: 35, height: 35 }} />,
                            },
                        ]}
                    >
                        <Tab value="읽은 목록">읽은 목록</Tab>
                        <Tab value="관심 목록">관심 목록</Tab>
                    </Tabs>
                </StyledMypostsMain>
            </StyledMypostsMyLikes>
        </StyledMyPostsPageWrapper>
    );
};
export default MyLikes;

const StyledMyPostsPageWrapper = styled.section`
    padding-top: 5rem;
`;

const StyledMypostsMyLikes = styled.div`
    width: 1300px;
    margin: 0 auto;
    min-height: 100vh;

    @media screen and (max-width: 1919px) {
        width: 1200px;
    }
    @media screen and (max-width: 1024px) {
        width: calc(100% - 2rem);
    }
`;

const StyledMypostsMain = styled.main`
    display: flex;
    flex-direction: column;
`;
