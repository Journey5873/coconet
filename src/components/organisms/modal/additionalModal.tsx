import React, { useState } from 'react'
import styled, { keyframes } from 'styled-components'
import CloseIcon from '@mui/icons-material/Close'
import { ReactComponent as CoconetLogo } from '../../assets/images/Logo.svg'
import { MdArrowBackIos } from 'react-icons/md'
import CustomInput from '../../atoms/Input'
import { StyledFlexRowBox } from '../../common/common'
import SingleSelect from '../../atoms/Select/SingleSelect'
import { SelectValue } from '../../../pages/setting'
import { useUserService } from '../../../api/services/userService'

import { useAppDispatch } from '../../../store/RootReducer'
import { setToken } from '../../../store/authSlice'
import { useDropzone } from 'react-dropzone'
import MultipleSelect from '../../atoms//Select/MultipleSelect'
import { useNavigate } from 'react-router-dom'

export type RegisterDto = {
  memberId: string
  name: string
  career: number
  roles: string[]
  stacks: string[]
  bio: string
  githubLink: string
  blogLink: string
  notionLink: string
}

interface Props {
  open: boolean
  handleClose?: () => void
  memberId: string
  //   nickname: string
  //   handleFormValue: (e: any) => void
}

const AdditionalModal = ({ open, handleClose, memberId }: Props) => {
  const navigate = useNavigate()
  const { open: dropzoneOpen } = useDropzone({
    onDrop: (acceptedFiles, fileRejections, event) => {
      const file = acceptedFiles[0]

      if (file) {
        const reader = new FileReader()
        reader.onloadend = () => {
          setThumnail(reader.result as string)
        }
        reader.readAsDataURL(file)
        setImageFile(file)
      }
    },
  })
  const userService = useUserService()
  const dispatch = useAppDispatch()
  const [tab, setTab] = useState<number>(1)
  const [additinalValue, setAdditionalValue] = useState<Record<string, any>>({
    nickname: '',
    roles: {
      label: '',
      value: '',
    },
    career: {
      label: '',
      value: '',
    },
  })
  const [stacks, setStacks] = useState<SelectValue[]>([])
  const [imageFile, setImageFile] = useState<File | null>(null)
  const [thumnail, setThumnail] = useState<string>('')

  const handelChangeAdditionalValue = (e: any) => {
    const { name, value } = e.target
    setAdditionalValue((prev) => ({ ...prev, [name]: value }))
  }

  const handleCarrer = (value: SelectValue) =>
    setAdditionalValue((prev) => ({ ...prev, career: value }))

  const handleRoles = (value: SelectValue) =>
    setAdditionalValue((prev) => ({ ...prev, roles: value }))

  const handleStack = (value: SelectValue[]) => setStacks(value)

  const onRegister = async () => {
    const formData = new FormData()

    try {
      const requestDto: any = {
        memberUUID: memberId,
        career: additinalValue.career.value,
        name: additinalValue.nickname,
        roles: [additinalValue.roles.value],
        stacks: [...stacks.map((stack) => stack.value)],
        bio: '',
        githubLink: '',
        blogLink: '',
        notionLink: '',
      }

      formData.append(
        'requestDto',
        new Blob([JSON.stringify(requestDto)], { type: 'application/json' }),
      )
      formData.append('imageFile', imageFile!)

      const result = await userService.createUser<FormData>(formData)

      if (result.data) {
        const accessToken = result.data?.accessToken
        localStorage.setItem('accessToken', accessToken)
        dispatch(setToken({ token: accessToken }))
        navigate('/')
      } else {
        console.log(result.errors)
      }
    } catch (error) {
      console.log(error)
    }
  }

  if (!open) {
    return null
  }

  return (
    <React.Fragment>
      {/* <Login onClick={() => handleLoginModalVisible()} /> */}
      <StyledLoginModalBg>
        <StyledLoginModalWrapper>
          <StyledLoginModalHeader>
            <CoconetLogo style={{ width: '10rem' }} />
            <CloseIcon
              style={{ color: '#868e96', cursor: 'pointer' }}
              onClick={handleClose}
            />
          </StyledLoginModalHeader>
          <StyledLoginModalContent>
            {tab === 1 && (
              <>
                <StyledLoginTitle>COCONET에 처음오셨군요.</StyledLoginTitle>
                <StyledLoginTitle>닉네임을 설정해주세요!</StyledLoginTitle>
                <StyledContentsWrapper>
                  <StyledFlexRowBox>
                    <span style={{ minWidth: 'fit-content' }}>닉네임</span>
                    <CustomInput
                      name="nickname"
                      value={additinalValue?.nickname}
                      onChange={handelChangeAdditionalValue}
                    />
                  </StyledFlexRowBox>
                  <div style={{ alignSelf: 'center' }}>
                    <StyledFlexRowBox>
                      <StyledButton
                        onClick={async () => {
                          if (!additinalValue?.nickname) {
                            alert('닉네임을 입력해주세요.')
                            return
                          }

                          const isValid = await userService.checkUsername(
                            additinalValue?.nickname,
                          )

                          if (isValid.data) {
                            alert('중복된 닉네임 입니다.')
                            return
                          }

                          setTab(2)
                        }}
                      >
                        다음
                      </StyledButton>
                    </StyledFlexRowBox>
                  </div>
                </StyledContentsWrapper>
              </>
            )}

            {tab === 2 && (
              <>
                <div style={{ alignSelf: 'start' }}>
                  <StyledBackButton onClick={() => setTab(1)}>
                    <MdArrowBackIos />
                  </StyledBackButton>
                </div>

                <StyledLoginTitle>
                  {additinalValue?.nickname} 님 반가워요.
                </StyledLoginTitle>
                <StyledLoginTitle>직무와 경력을 알려주세요.</StyledLoginTitle>
                <StyledContentsWrapper>
                  <SingleSelect
                    label="직무"
                    onChange={handleRoles}
                    value={additinalValue.roles}
                    placeholder="프론트엔드"
                  />
                  <SingleSelect
                    label="경력"
                    onChange={handleCarrer}
                    value={additinalValue.carrer}
                    placeholder="0년"
                  />
                  <div style={{ alignSelf: 'center' }}>
                    <StyledFlexRowBox>
                      <StyledButton
                        onClick={() => {
                          if (!additinalValue?.career.label) {
                            alert('경력을 입력해주세요.')
                            return
                          }
                          if (!additinalValue?.roles.label) {
                            alert('직무를 입력해주세요.')
                            return
                          }
                          setTab(3)
                        }}
                      >
                        다음
                      </StyledButton>
                    </StyledFlexRowBox>
                  </div>
                </StyledContentsWrapper>
              </>
            )}

            {tab === 3 && (
              <>
                <div style={{ alignSelf: 'start' }}>
                  <StyledBackButton onClick={() => setTab(2)}>
                    <MdArrowBackIos />
                  </StyledBackButton>
                </div>
                <StyledLoginTitle>관심 기술을 알려주세요.</StyledLoginTitle>
                <StyledContentsWrapper>
                  <MultipleSelect
                    label={'관심스택'}
                    onChange={handleStack}
                    value={stacks}
                    placeholder="Java, React"
                  />
                  <div style={{ alignSelf: 'center' }}>
                    <StyledFlexRowBox>
                      <StyledButton
                        onClick={() => {
                          if (!stacks.length) {
                            alert('관심 기술을 입력해주세요.')
                            return
                          }

                          setTab(4)
                        }}
                      >
                        다음
                      </StyledButton>
                    </StyledFlexRowBox>
                  </div>
                </StyledContentsWrapper>
              </>
            )}

            {tab === 4 && (
              <>
                <div style={{ alignSelf: 'start' }}>
                  <StyledBackButton onClick={() => setTab(2)}>
                    <MdArrowBackIos />
                  </StyledBackButton>
                </div>
                <StyledLoginTitle>이미지를 업로드해주세요.</StyledLoginTitle>
                <StyledContentsWrapper>
                  <div
                    style={{
                      display: 'flex',
                      alignItems: 'center',
                      justifyContent: 'center',
                    }}
                  >
                    {thumnail ? (
                      <img
                        src={thumnail}
                        width={200}
                        height={200}
                        onClick={() => dropzoneOpen()}
                      />
                    ) : (
                      <StyleImageAddButton onClick={() => dropzoneOpen()}>
                        +
                      </StyleImageAddButton>
                    )}
                  </div>

                  <div style={{ alignSelf: 'center' }}>
                    <StyledFlexRowBox>
                      <StyledButton
                        onClick={() => {
                          if (!imageFile) {
                            alert('프로필 이미지를 업로드해주세요')
                            return
                          }

                          onRegister()
                        }}
                      >
                        다음
                      </StyledButton>
                    </StyledFlexRowBox>
                  </div>
                </StyledContentsWrapper>
              </>
            )}
          </StyledLoginModalContent>
        </StyledLoginModalWrapper>
      </StyledLoginModalBg>
    </React.Fragment>
  )
}

export default AdditionalModal

const loginModalAnimation = keyframes`
  0% {
      opacity: 0;
      top: -100%;
    }

    100% {
      opacity: 1;
      top: 50%;
  }
`

const StyledLoginModalBg = styled.div`
  box-sizing: border-box;
  background-color: rgba(0, 0, 0, 0.7);
  display: block;
  position: fixed;
  inset: 0px;
  z-index: 1000;
  overflow: auto;
  outline: 0px;

  @media (max-width: 768px) {
    width: 100%;
    min-height: 100%;
    position: relative;
  }
`

const StyledLoginModalWrapper = styled.div`
  width: 800px;
  height: 550px;
  box-shadow: 0 2px 12px 0 rgb(0 0 0/9%);
  position: relative;
  display: flex;
  flex-direction: column;
  top: -200%;
  margin: 0 auto;
  transform: translateY(-50%);
  animation: ${loginModalAnimation} 0.5s ease 0s 1 normal forwards running;
`

const StyledLoginModalHeader = styled.div`
  background: #f8f9fa;
  padding: 0 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-radius: 8px 8px 0 0;
  height: 3rem;

  @media (max-width: 768px) {
    background: #f8f9fa;
    padding: 0 1rem;
    border-radius: 0;
  }
`
const StyledLoginModalContent = styled.div`
  background: #fff;
  height: 100%;
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 0 0 8px 8px;
`

const StyledLoginTitle = styled.h1`
  margin: 20px 0;

  @media (max-width: 768px) {
    font-size: 1.2rem;
  }
`

const StyledContentsWrapper = styled.section`
  margin-top: 4rem;
  width: 60%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  row-gap: 1rem;

  @media (max-width: 768px) {
    flex-direction: column;
    grid-gap: 30px;
    gap: 30px;
    margin-top: 1.5rem;
  }
`

const StyledButton = styled.button`
  width: 5rem;
  height: 3rem;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  outline: none;
  transition: all 0.125s ease-in 0s;
  color: #fff;
  background-color: black;
  cursor: pointer;
  border: none;

  @media screen and (max-width: 768px) {
    width: 20rem;
    height: 4.5rem;
  }
`
const StyledBackButton = styled.div`
  cursor: pointer;
  font-size: 20px;
`

const StyleImageAddButton = styled.div`
  width: 100px;
  height: 100px;
  border: 1px dotted black;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
`
