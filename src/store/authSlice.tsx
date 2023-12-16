import { createSlice } from '@reduxjs/toolkit'
import type { PayloadAction } from '@reduxjs/toolkit'

type AuthState = {
  token: string
  name: string
  career: number
  profileImg?: string
  bio?: string
  githubLink?: string
  blogLink?: string
  notionLink?: string
  roles: string[]
  stacks: string[]
}

const initialState: AuthState = {
  token: '',
  name: '',
  career: 0,
  profileImg: '',
  bio: '',
  githubLink: '',
  blogLink: '',
  notionLink: '',
  roles: [],
  stacks: [],
}

export const authSlice = createSlice({
  name: 'authSlice',
  initialState,
  reducers: {
    setToken: (state, action: PayloadAction<AuthState>) => {
      const {
        token,
        name,
        career,
        profileImg,
        bio,
        githubLink,
        blogLink,
        notionLink,
        roles,
        stacks,
      } = action.payload
      state.token = token
      state.name = name
      state.career = career
      state.profileImg = profileImg
      state.bio = bio
      state.githubLink = githubLink
      state.blogLink = blogLink
      state.notionLink = notionLink
      state.roles = roles
      state.stacks = stacks
    },
    removeToken: (state) => {
      state.token = ''
    },
  },
})

export const { setToken, removeToken } = authSlice.actions

export default authSlice.reducer
