import styled from "styled-components";
import Logo from "../assets/images/Logo.svg";
import chatIcon from "../assets/images/chatIcon.svg";
import notificationIcon from "../assets/images/notificationIcon.svg";
import coconutIcon from "../assets/images/coconutIcon.svg";
import { ReactComponent as Polygon } from "../assets/images/polygon.svg";
import { Link } from "react-router-dom";
import { useState } from "react";

export default function Header() {
    const [openDropdownbar, setOpenDropdownbar] = useState(false);

    const handleDropdownbar = () => {
        setOpenDropdownbar((openDropdownbar) => !openDropdownbar);
        console.log(openDropdownbar);
    };

    const dropdownbarItem = [
        { link: "/", content: "내 작성글" },
        { link: "/", content: "내 관심글" },
        { link: "/setting", content: "설정" },
        { link: "/", content: "로그아웃" },
    ];
    return (
        <StyledHeaderWrapper>
            <Link to="/">
                <StyledLogoImg src={Logo} />
            </Link>
            <StyledMenuWrapper>
                <StyledPostRegister>새 글 쓰기</StyledPostRegister>
                <StyledMenuImage>
                    <img src={chatIcon} alt="chatIcon" />
                </StyledMenuImage>
                <StyledMenuImage>
                    <img src={notificationIcon} alt="notificationIcon" />
                </StyledMenuImage>
                <StyledLoginUser onClick={() => handleDropdownbar()}>
                    <StyledLoginUserImg src={coconutIcon} alt="coconutIcon" />
                    <StyledPolygon />
                    <StyledDropdownBar openDropdownbar={openDropdownbar}>
                        <StyledDropDownBarMenu>
                            {dropdownbarItem.map((item, i) => {
                                return (
                                    <StyledDropDownBarMenuItem key={i}>
                                        <Link to={item.link} onClick={() => console.log("페이지 이동")}>
                                            {item.content}
                                        </Link>
                                    </StyledDropDownBarMenuItem>
                                );
                            })}
                        </StyledDropDownBarMenu>
                    </StyledDropdownBar>
                </StyledLoginUser>
            </StyledMenuWrapper>
        </StyledHeaderWrapper>
    );
}

const StyledHeaderWrapper = styled.nav`
    margin: auto;
    max-width: 1180px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 85px;
    padding: 0 10px;
`;

const StyledLogoImg = styled.img`
    width: 105px;
    height: 32px;
`;

const StyledMenuWrapper = styled.div`
    display: flex;
    grid-gap: 30px;
    gap: 30px;
    align-items: center;
`;

const StyledPostRegister = styled.button`
    font-weight: 600;
    font-size: 1.125rem;
    outline: none;
    border: none;
    background-color: #fff;
    cursor: pointer;
`;

const StyledMenuImage = styled.div`
    width: 25px;
    height: 25px;
    cursor: pointer;
`;

const StyledLoginUser = styled.div`
    cursor: pointer;
    display: flex;
    align-items: center;
    position: relative;
`;

const StyledLoginUserImg = styled.img`
    display: block;
    height: 35px;
    width: 35px;
    margin-right: 12px;
    border-radius: 50%;
    object-fit: cover;
    transition: all 0.125s ease-in 0s;
`;

const StyledPolygon = styled(Polygon)`
    fill: #868e96;
    position: absolute;
    top: 15px;
    right: -4px;
`;
const StyledDropdownBar = styled.div<{ openDropdownbar: boolean }>`
    position: absolute;
    top: 100%;
    margin-top: 1rem;
    right: 0;
    display: ${({ openDropdownbar }) => (openDropdownbar ? "block" : "none")};
`;

const StyledDropDownBarMenu = styled.ul`
    position: relative;
    z-index: 5;
    width: 12rem;
    background: #fff;
    border: 0.5px solid rgba(37, 53, 98, 0.1);
    border-radius: 2px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
    list-style: none;
`;

const StyledDropDownBarMenuItem = styled.li`
    padding: 0.75rem 1rem;
    line-height: 1.5;
    font-weight: 500;
    cursor: pointer;
    list-style: none;

    a {
        text-decoration: none;
        color: inherit;
        font-weight: 550;
    }
`;
