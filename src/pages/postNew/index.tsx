import styled from 'styled-components'
import SingleSelect from '../../components/atoms/Select/SingleSelect'
import { useState } from 'react'
import { SelectValue } from '../setting'
import { DemoContainer } from '@mui/x-date-pickers/internals/demo'
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { DatePicker } from '@mui/x-date-pickers/DatePicker'
import MultipleSelect from '../../components/atoms/Select/MultipleSelect'
import MultipleSelectWithCount from '../../components/atoms/Select/MultipleSelectWithCount'

export default function PostNew() {
  // 모집 구분
  const [category, setCategory] = useState<SelectValue>({
    label: '',
    value: '',
  })
  const handleCategory = (value: SelectValue) => setCategory(value)

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
  const [stack, setStack] = useState<SelectValue>({
    label: '',
    value: '',
  })
  const handleStack = (value: SelectValue) => setStack(value)

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
              value={category}
              onChange={handleCategory}
              placeholder="스터디/프로젝트"
            />
          </StyledPostSelectList>
          <StyledPostSelectList>
            <MultipleSelectWithCount
              label="모집 포지션/인원"
              placeholder="프론트엔드/0명"
            />
          </StyledPostSelectList>
        </StyledPostSelect>
        <StyledPostSelect>
          <StyledPostSelectList>
            <SingleSelect
              label="진행 방식"
              value={onOffline}
              onChange={handleOnOffline}
              placeholder={'온라인/오프라인'}
            />
          </StyledPostSelectList>
          <StyledPostSelectList>
            <SingleSelect
              label="진행 기간"
              value={period}
              onChange={handlePeriod}
              placeholder={'2개월 이하~1년 이상'}
            />
          </StyledPostSelectList>
        </StyledPostSelect>
        <StyledPostSelect>
          <StyledPostSelectList>
            <MultipleSelect
              label="기술 스택"
              value={stack}
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
                <DatePicker disablePast />
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
      </section>
    </PostRegisterWrapper>
  )
}

const PostRegisterWrapper = styled.div`
  max-width: 1040px;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  padding: 60px 16px;
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
  padding: 16px;
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
