import styled from 'styled-components'
import SingleSelect from '../../components/atoms/Select/SingleSelect'
import { ChangeEvent, useCallback, useRef, useState } from 'react'
import { SelectValue } from '../setting'
import { DemoContainer } from '@mui/x-date-pickers/internals/demo'
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { DatePicker } from '@mui/x-date-pickers/DatePicker'
import MultipleSelect from '../../components/atoms/Select/MultipleSelect'
import MultipleSelectWithCount from '../../components/atoms/Select/MultipleSelectWithCount'
import { Dayjs } from 'dayjs'
import { useArticleService } from '../../api/services/articleService'
import { dateFormat } from '../../utils/utils'
import { toast } from 'react-toastify'
import { useNavigate } from 'react-router-dom'

export interface StackListProps {
  roleName: string
  participant: number
}

export default function PostNew() {
  const articleService = useArticleService()
  const navigation = useNavigate()
  // 등록 날짜
  const plannedStartAt = dateFormat(`${new Date()}`)

  // 모집 구분
  const [category, setCategory] = useState<SelectValue>({
    label: '',
    value: '',
  })
  const handleCategory = (value: SelectValue) => {
    setCategory(value)
  }

  // 모집 포지션/인원
  const [stackLists, setStackLists] = useState<StackListProps[]>([
    { roleName: 'Backend', participant: 0 },
    { roleName: 'Frontend', participant: 0 },
    { roleName: 'Designer', participant: 0 },
    { roleName: 'IOS', participant: 0 },
    { roleName: 'Android', participant: 0 },
    { roleName: 'PM', participant: 0 },
    { roleName: 'QA', participant: 0 },
    { roleName: 'GameDev', participant: 0 },
    { roleName: 'DevOps', participant: 0 },
  ])
  const [positionLists, setPositionLists] = useState<StackListProps[]>([])

  const increaseCount = (stackLabel: string) => {
    setStackLists(
      stackLists.map((stack) =>
        stack.roleName === stackLabel
          ? { ...stack, participant: stack.participant + 1 }
          : stack,
      ),
    )
    setPositionLists(
      stackLists
        .map((stack) =>
          stack.roleName === stackLabel
            ? { ...stack, participant: stack.participant + 1 }
            : stack,
        )
        .filter((stack) => stack.participant > 0),
    )
  }

  const decreaseCount = (stackLabel: string) => {
    setStackLists(
      stackLists.map((stack) =>
        stack.roleName === stackLabel
          ? stack.participant > 0
            ? { ...stack, participant: stack.participant - 1 }
            : { ...stack, participant: 0 }
          : stack,
      ),
    )
    setPositionLists(
      stackLists
        .map((stack) =>
          stack.roleName === stackLabel
            ? stack.participant > 0
              ? { ...stack, participant: stack.participant - 1 }
              : { ...stack, participant: 0 }
            : stack,
        )
        .filter((stack) => stack.participant > 0),
    )
  }

  // 진행 방식
  const [onOffline, setOnOffline] = useState<SelectValue>({
    label: '',
    value: '',
  })
  const handleOnOffline = (value: SelectValue) => setOnOffline(value)

  // 진행 기간
  const [period, setPeriod] = useState<SelectValue>({
    label: '',
    value: '',
  })
  const handlePeriod = (value: SelectValue) => setPeriod(value)

  // 기술 스택
  const [stacks, setStacks] = useState<any>([])
  const handleStack = (option: { value: string; label: string }[]) => {
    setStacks(option.map((item) => item.value))
  }

  // 날짜 선택
  const [date, setDate] = useState<Dayjs | null>(null)

  // 제목
  const [title, setTitle] = useState('')
  const handleTitle = useCallback((e: ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value)
  }, [])

  // 내용
  const [content, setContent] = useState('')
  const handleContent = useCallback((e: ChangeEvent<HTMLTextAreaElement>) => {
    setContent(e.target.value)
  }, [])

  const onRegister = async () => {
    try {
      const requestDto: any = {
        title: title,
        content: content,
        plannedStartAt: plannedStartAt,
        expiredAt: date,
        estimatedDuration: period.value,
        articleType: category.value,
        meetingType: onOffline.value,
        roles: positionLists,
        stacks: [...stacks.map((stack: string) => stack)],
      }

      const result = await articleService.createNewArticle(
        JSON.stringify(requestDto),
      )
      if (result.succeeded) {
        toast.success('새 글을 등록했습니다!')
        navigation('/')
      }
    } catch (error) {
      console.log(error)
      toast.error('다시 시도해 주세요.')
    }
  }

  return (
    <PostRegisterWrapper>
      <section>
        <StyledSelectSection>
          <StyledSectionNumber>1</StyledSectionNumber>
          <StyledSelectSectionTitle>
            프로젝트 기본 정보를 입력해주세요.
          </StyledSelectSectionTitle>
        </StyledSelectSection>
        <StyledPostSelect>
          <StyledPostSelectList>
            <SingleSelect
              label="모집구분"
              value={category.label}
              onChange={handleCategory}
              placeholder="스터디/프로젝트"
            />
          </StyledPostSelectList>
          <StyledPostSelectList>
            <MultipleSelectWithCount
              stackLists={stackLists}
              increaseCount={increaseCount}
              decreaseCount={decreaseCount}
              label="모집 포지션/인원"
              placeholder="프론트엔드/0명"
            />
          </StyledPostSelectList>
        </StyledPostSelect>
        <StyledPostSelect>
          <StyledPostSelectList>
            <SingleSelect
              label="진행 방식"
              value={onOffline.label}
              onChange={handleOnOffline}
              placeholder={'온라인/오프라인'}
            />
          </StyledPostSelectList>
          <StyledPostSelectList>
            <SingleSelect
              label="진행 기간"
              value={period.label}
              onChange={handlePeriod}
              placeholder={'2개월 이하~1년 이상'}
            />
          </StyledPostSelectList>
        </StyledPostSelect>
        <StyledPostSelect>
          <StyledPostSelectList>
            <MultipleSelect
              label="기술 스택"
              value={stacks}
              onChange={handleStack}
              placeholder={'프로젝트 사용 스택'}
            />
          </StyledPostSelectList>
          <StyledPostSelectList>
            <StyledInputLabel>모집 마감일</StyledInputLabel>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DemoContainer
                components={['DatePicker']}
                sx={{ '.MuiTextField-root': { width: '100%' } }}
              >
                <DatePicker
                  format="YYYY-MM-DD"
                  value={date}
                  disablePast
                  onChange={(newDate) => {
                    setDate(newDate)
                  }}
                />
              </DemoContainer>
            </LocalizationProvider>
          </StyledPostSelectList>
        </StyledPostSelect>
      </section>
      <section>
        <StyledSelectSection>
          <StyledSectionNumber>2</StyledSectionNumber>
          <StyledSelectSectionTitle>
            프로젝트에 대해 소개해주세요.
          </StyledSelectSectionTitle>
        </StyledSelectSection>
        <StyledInputLabel style={{ fontSize: 20 }}>제목</StyledInputLabel>
        <StyledTitleInput
          type="text"
          value={title}
          onChange={handleTitle}
          placeholder="글 제목을 입력해주세요!"
        />
        <StyledContentInput
          value={content}
          onChange={handleContent}
          placeholder="프로젝트에 대해 소개해주세요!"
        ></StyledContentInput>
        <section style={{ display: 'flex', flexDirection: 'row-reverse' }}>
          <StyledButton onClick={onRegister}>등록</StyledButton>
          <StyledButton>취소</StyledButton>
        </section>
      </section>
    </PostRegisterWrapper>
  )
}

const PostRegisterWrapper = styled.div`
  max-width: 1040px;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  padding: 125px 16px;
  width: 100%;
  margin: 0 auto;
  color: #333;
  grid-gap: 50px;
  gap: 50px;
  position: relative;
`

const StyledSelectSection = styled.div`
  display: flex;
  align-items: center;
  padding: 16px 0;
  margin-bottom: 36px;
  border-bottom: 3px solid #f2f2f2;

  @media screen and (max-width: 575px) {
    padding: 8px;
  }
`

const StyledSectionNumber = styled.span`
  margin-right: 8px;
  width: 28px;
  height: 28px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  background: #b0dedb;
  font-size: 16px;
  font-weight: 700;
  line-height: 19px;
  color: #fff;

  @media screen and (max-width: 575px) {
    margin-right: 6px;
    width: 20px;
    height: 20px;
    font-size: 12px;
  }
`

const StyledSelectSectionTitle = styled.h2`
  font-weight: 700;
  font-size: 24px;
  line-height: 40px;
  letter-spacing: -0.05em;
  margin: 0;

  @media screen and (max-width: 575px) {
    font-size: 16px;
  }
`
const StyledPostSelect = styled.ul`
  margin-top: 40px;
  display: flex;
  grid-gap: 15px;
  gap: 15px;
  list-style: none;

  @media screen and (max-width: 768px) {
    flex-direction: column;
    grid-gap: 20px;
    gap: 20px;
    margin-top: 20px;
  }
`

const StyledPostSelectList = styled.li`
  flex: 1;
`

const StyledInputLabel = styled.label`
  color: rgb(51, 51, 51);
  font-size: 14px;
  font-weight: 700;
  line-height: 20px;
  letter-spacing: -0.28px;
`
const StyledTitleInput = styled.input`
  margin-top: 20px;
  margin-bottom: 20px;
  width: 100%;
  height: 56px;
  min-height: 56px;
  line-height: 44px;
  box-shadow: none;
  padding-left: 16px;
  padding-right: 52px;
  border: 1px solid #e1e3e8;
  border-radius: 5px;
  font-size: 16px;
  font-weight: 500;
  color: #333;
  outline: none;
  box-sizing: border-box;
`

const StyledContentInput = styled.textarea`
  margin-bottom: 20px;
  width: 100%;
  min-height: 300px;
  line-height: 44px;
  box-shadow: none;
  padding-left: 16px;
  padding-right: 52px;
  border: 1px solid #e1e3e8;
  border-radius: 5px;
  font-size: 16px;
  font-weight: 500;
  color: #333;
  outline: none;
  box-sizing: border-box;
`
const StyledButton = styled.button`
  cursor: pointer;
  outline: none;
  border: none;
  border-radius: 8px;
  padding: 0 1.25rem;
  height: 2rem;
  font-size: 1rem;
  background: rgb(110, 209, 192);
  color: #fff;
  margin-left: 10px;
`
