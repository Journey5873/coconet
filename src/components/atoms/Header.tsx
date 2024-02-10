import styled from 'styled-components'
import Logo from '../assets/images/Logo.svg'
import chatIcon from '../assets/images/chatIcon.svg'
import notificationIcon from '../assets/images/notificationIcon.svg'
import coconutIcon from '../assets/images/coconutIcon.svg'
import { ReactComponent as Polygon } from '../assets/images/polygon.svg'
import { Link, useNavigate } from 'react-router-dom'
import { useState, useContext } from 'react'
import { AlertContext } from '../organisms/modal/AlertModalContext'
import { useAppSelector, useAppDispatch } from '../../store/RootReducer'
import LoginModal from './LoginModal'
import { RxHamburgerMenu } from 'react-icons/rx'
import { removeToken } from '../../store/authSlice'

export default function Header() {
  const alertContext = useContext(AlertContext)
  const dispatch = useAppDispatch()
  const token = useAppSelector((state) => state.reducer.auth.token)
  const navigate = useNavigate()
  const [openDropdownbar, setOpenDropdownbar] = useState(false)
  const [openLoginModal, setOpenLoginModal] = useState(false)

  const handleDropdownbar = () => {
    setOpenDropdownbar((openDropdownbar) => !openDropdownbar)
  }

  const handleLogout = () => {
    if (alertContext) {
      alertContext.onOpen({
        title: '로그아웃',
        content: '로그아웃 하시겠습니까?',
        onConfirm: () => {
          localStorage.removeItem('accessToken')
          dispatch(removeToken())
          navigate('/')
          alertContext.onClose()
        },
      })
    }
  }

  const handleClickNewArticle = () => {
    if (!token) {
      setOpenLoginModal((openLoginModal) => !openLoginModal)
      return
    }

    navigate('/post/new')
  }

  const dropdownbarItem = [
    { link: '/myPosts', content: '내 작성글' },
    { link: '/myLikes', content: '내 관심글' },
    { link: '/setting', content: '설정' },
  ]

  const navigation = useNavigate()
  return (
    <StyledHeaderWrapper>
      <StyledHeaderInner>
        <Link to="/">
          <StyledLogoImg src={Logo} />
        </Link>
        <StyledMenuWrapper>
          <StyledPostRegister onClick={() => handleClickNewArticle()}>
            새 글 쓰기
          </StyledPostRegister>
          {openLoginModal && (
            <LoginModal
              handleLoginModalVisible={() =>
                setOpenLoginModal((openLoginModal) => !openLoginModal)
              }
            />
          )}
          {!token && (
            <StyledPostRegister
              onClick={() => () =>
                setOpenLoginModal((openLoginModal) => !openLoginModal)
              }
            >
              로그인
            </StyledPostRegister>
          )}

          {token && (
            <>
              <StyledMenuImage>
                <img
                  src={chatIcon}
                  alt="chatIcon"
                  onClick={() => navigation(`/chat`)}
                />
              </StyledMenuImage>
              <StyledMenuImage>
                <img src={notificationIcon} alt="notificationIcon" />
              </StyledMenuImage>
              <StyledLoginUser onClick={() => handleDropdownbar()}>
                <StyledLoginUserImg src={coconutIcon} alt="coconutIcon" />
                <RxHamburgerMenu className="mobileMenu" size={23} />
                <StyledPolygon />
                <StyledDropdownBar openDropdownbar={openDropdownbar}>
                  <StyledDropDownBarMenu>
                    {dropdownbarItem.map((item, i) => {
                      return (
                        <StyledDropDownBarMenuItem key={i}>
                          <Link
                            to={item.link}
                            onClick={() => console.log('페이지 이동')}
                          >
                            {item.content}
                          </Link>
                        </StyledDropDownBarMenuItem>
                      )
                    })}
                    <StyledDropDownBarMenuItem onClick={handleLogout}>
                      로그아웃
                    </StyledDropDownBarMenuItem>
                  </StyledDropDownBarMenu>
                </StyledDropdownBar>
              </StyledLoginUser>
            </>
          )}
        </StyledMenuWrapper>
      </StyledHeaderInner>
    </StyledHeaderWrapper>
  )
}

const StyledHeaderWrapper = styled.div`
  height: 85px;
  background: rgb(255, 255, 255);
  margin: auto;
  border-bottom: 1px solid rgb(242, 243, 246);
  z-index: 999;
  width: 100%;
  position: fixed;
  box-sizing: border-box;
`

const StyledHeaderInner = styled.nav`
  margin: auto;
  max-width: 1180px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-sizing: border-box;
  padding: 0 10px;
  height: 100%;

  @media screen and (max-width: 575px) {
    position: relative;
    padding: 0 20px;
  }
`

const StyledLogoImg = styled.img`
  width: 230px;
  height: 70px;

  @media screen and (max-width: 770px) {
    width: 150px;
    height: 50px;
  }
`

const StyledMenuWrapper = styled.div`
  display: flex;
  grid-gap: 30px;
  gap: 30px;
  align-items: center;

  @media screen and (max-width: 575px) {
    margin-right: 0;
    grid-gap: 12px;
    gap: 12px;
  }
`

const StyledPostRegister = styled.button`
  font-weight: 600;
  font-size: 1.125rem;
  outline: none;
  border: none;
  background-color: #fff;
  cursor: pointer;

  @media screen and (max-width: 575px) {
    display: none;
  }
`

const StyledMenuImage = styled.div`
  width: 25px;
  height: 25px;
  cursor: pointer;

  @media screen and (max-width: 575px) {
    position: static;
    width: 28px;
    height: auto;
  }
`

const StyledLoginUser = styled.div`
  cursor: pointer;
  display: flex;
  align-items: center;
  position: relative;

  .mobileMenu {
    display: none;
  }

  @media screen and (max-width: 575px) {
    .mobileMenu {
      display: block;
    }
  }
`

const StyledLoginUserImg = styled.img`
  display: block;
  height: 35px;
  width: 35px;
  margin-right: 12px;
  border-radius: 50%;
  object-fit: cover;
  transition: all 0.125s ease-in 0s;

  @media screen and (max-width: 575px) {
    display: none;
  }
`

const StyledPolygon = styled(Polygon)`
  fill: #868e96;
  position: absolute;
  top: 15px;
  right: -4px;
  @media screen and (max-width: 575px) {
    display: none;
  }
`
const StyledDropdownBar = styled.div<{ openDropdownbar: boolean }>`
  position: absolute;
  top: 100%;
  margin-top: 1rem;
  right: 0;
  display: ${({ openDropdownbar }) => (openDropdownbar ? 'block' : 'none')};
`

const StyledDropDownBarMenu = styled.ul`
  position: relative;
  z-index: 5;
  width: 12rem;
  background: #fff;
  border: 0.5px solid rgba(37, 53, 98, 0.1);
  border-radius: 2px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
  list-style: none;
`

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
`
