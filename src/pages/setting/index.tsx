import React, { useEffect, useReducer, useState } from 'react'
import { User } from '../../models/user'
import { useUserService } from '../../api/services/userService'
import { RegisterDto } from '../../components/organisms/modal/additionalModal'
import RegisterImage from '../../components/atoms/RegisterImage'
import styled from 'styled-components'
import Labelnput from '../../components/molecules/Labelnput'
import LinkList from '../../components/organisms/LinkList'
import GreenButton from '../../components/atoms/Button/GreenButton'
import MultipleSelect from '../../components/atoms/Select/MultipleSelect'

import SingleSelectString from '../../components/atoms/Select/SingleSelectString'
import MultipleSelectString from '../../components/atoms/Select/MultipleSelectString'
import { removeToken, setUserName } from '../../store/authSlice'
import { useNavigate } from 'react-router-dom'
import { useAppDispatch } from '../../store/RootReducer'

export interface SettingFormType {
  name: string
  career: number
  roles: string[]
  stacks: string[]
  githubLink?: string
  blogLink?: string
  notionLink?: string
}

export interface SelectValue {
  value: string
  label: string
}

type UserAction =
  | { type: 'UPDATE_NAME'; name: string }
  | { type: 'UPDATE_CAREER'; career: number }
  | { type: 'UPDATE_PROFILE_IMG'; profileImg: string }
  | { type: 'UPDATE_BIO'; bio: string }
  | { type: 'UPDATE_GITHUB_LINK'; githubLink: string }
  | { type: 'UPDATE_BLOG_LINK'; blogLink: string }
  | { type: 'UPDATE_NOTION_LINK'; notionLink: string }
  | { type: 'UPDATE_ROLES'; roles: string[] }
  | { type: 'UPDATE_STACKS'; stacks: string[] }
  | { type: 'INIT'; payload: User }

function userReducer(state: User, action: UserAction): User {
  switch (action.type) {
    case 'INIT':
      return action.payload
    case 'UPDATE_NAME':
      return { ...state, name: action.name }
    case 'UPDATE_CAREER':
      return { ...state, career: action.career }
    case 'UPDATE_PROFILE_IMG':
      return { ...state, profileImg: action.profileImg }
    case 'UPDATE_BIO':
      return { ...state, bio: action.bio }
    case 'UPDATE_GITHUB_LINK':
      return { ...state, githubLink: action.githubLink }
    case 'UPDATE_BLOG_LINK':
      return { ...state, blogLink: action.blogLink }
    case 'UPDATE_NOTION_LINK':
      return { ...state, notionLink: action.notionLink }
    case 'UPDATE_ROLES':
      return { ...state, roles: action.roles }
    case 'UPDATE_STACKS':
      return { ...state, stacks: action.stacks }
    default:
      return state
  }
}

const initialUserState: User = {
  name: '',
  career: 0,
  roles: [],
  stacks: [],
  bio: '',
  blogLink: '',
  githubLink: '',
  notionLink: '',
  profileImg: '',
}

const SettingPage = () => {
  const userService = useUserService()
  const [userState, dispatch] = useReducer(userReducer, initialUserState)
  const navigate = useNavigate()

  const updateName = (name: string) => dispatch({ type: 'UPDATE_NAME', name })
  const updateCareer = (career: number) =>
    dispatch({ type: 'UPDATE_CAREER', career })
  const updateProfileImg = (profileImg: string) =>
    dispatch({
      type: 'UPDATE_PROFILE_IMG',
      profileImg,
    })
  const updateBio = (bio: string) => dispatch({ type: 'UPDATE_BIO', bio })
  const updateGithubLink = (githubLink: string) =>
    dispatch({
      type: 'UPDATE_GITHUB_LINK',
      githubLink,
    })
  const updateBlogLink = (blogLink: string) =>
    dispatch({
      type: 'UPDATE_BLOG_LINK',
      blogLink,
    })
  const updateNotionLink = (notionLink: string) =>
    dispatch({
      type: 'UPDATE_NOTION_LINK',
      notionLink,
    })
  const updateRoles = (roles: string[]) =>
    dispatch({ type: 'UPDATE_ROLES', roles })
  const updateStacks = (stacks: string[]) =>
    dispatch({ type: 'UPDATE_STACKS', stacks })

  const fetchMyProfile = async () => {
    try {
      const result = await userService.getMyProfile()

      if (result.data) {
        dispatch({ type: 'INIT', payload: result.data })
      } else {
        console.log(result.errors)
      }
    } catch (error) {
      console.log(error)
    }
  }

  const updateProfile = async () => {
    const formData = new FormData()

    try {
      const requestDto: any = {
        career: userState.career,
        name: userState.name,
        roles: userState.roles,
        stacks: userState.stacks,
        bio: userState.bio,
        githubLink: '',
        blogLink: '',
        notionLink: '',
      }
      formData.append(
        'requestDto',
        new Blob([JSON.stringify(requestDto)], { type: 'application/json' }),
      )

      const result = await userService.updateMyProfile(formData)
      console.log(result)
    } catch (error) {
      console.log(error)
    }
  }

  const deleteMyAccount = async () => {
    try {
      const result = await userService.deleteUser()
      localStorage.removeItem('accessToken')
      navigate('/')
    } catch (error) {
      console.log(error)
    }
  }

  useEffect(() => {
    fetchMyProfile()
  }, [])

  return (
    <Container>
      <div style={{ margin: '0 auto' }}>
        <RegisterImage />
      </div>
      <Labelnput
        text="닉네임"
        isRequired
        value={userState.name}
        onChange={(e: any) => updateName(e.target.value)}
      />
      <SingleSelectString
        label="직무"
        onChange={(option: { value: string; label: string }) => {
          updateRoles([option.value])
        }}
        value={userState.roles[0]}
        placeholder="프론트엔드"
      />
      <SingleSelectString
        label="경력"
        onChange={(option: { value: string; label: string }) => {
          updateCareer(+option.value)
        }}
        value={userState.career.toString()}
        placeholder="1년"
      />
      <Labelnput
        text="자기소개"
        value={userState.bio}
        placeholder="자신을 소개해보세요!"
        onChange={(e: any) => updateBio(e.target.value)}
      />
      <MultipleSelectString
        label={'관심스택'}
        onChange={(option: { value: string; label: string }[]) => {
          updateStacks(option.map((item) => item.value))
        }}
        value={userState.stacks}
        placeholder="Select.dd.."
      />
      <LinkList onSubmit={() => console.log('submit')} />
      <StyledButtonWrapper>
        <GreenButton buttonName="저장" onClick={() => updateProfile()} />
        <DeleteUserButton onClick={() => deleteMyAccount()}>
          회원탈퇴
        </DeleteUserButton>
      </StyledButtonWrapper>
    </Container>
  )
}

export default SettingPage

const Container = styled.div`
  width: 100%;
  max-width: 500px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 125px 16px;
  gap: 16px;
  margin: 0px auto;
`

const StyledButtonWrapper = styled.div`
  margin: 0 auto;
  width: 100%;
  display: flex;
  flex-direction: column;
  row-gap: 16px;
  align-items: center;
  justify-items: center;
  text-align: center;
`

const DeleteUserButton = styled.div`
  font-size: 16px;
  font-weight: 700;
  line-height: 126.5%;
  letter-spacing: -0.51px;
  text-align: center;
  color: rgb(194, 198, 207);
  cursor: pointer;
`
