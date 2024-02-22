import { useEffect, useState } from 'react'
import { SelectValue } from '../../pages/setting'
import SingleSelect from '../atoms/Select/SingleSelect'
import LinkList from '../organisms/LinkList'
import CloseIcon from '@mui/icons-material/Close'
import { ReactComponent as CoconetLogo } from '../assets/images/Logo.svg'
import { IoIosArrowBack } from 'react-icons/io'
import styled from 'styled-components'

interface SupportButtonModalProps {
  handleSupportButton: () => void
  isVisible: boolean
}

export default function SupportButtonModal({
  handleSupportButton,
  isVisible,
}: SupportButtonModalProps) {
  const [nextModalVisible, setNextModalVisible] = useState(false)
  const handleNextModal = () => {
    setNextModalVisible(!nextModalVisible)
  }

  const [carrer, setCarrer] = useState<SelectValue>({
    label: '',
    value: '',
  })

  const handleCarrer = (value: SelectValue) => setCarrer(value)

  useEffect(() => {
    if (isVisible) {
      setNextModalVisible(true)
    }
  }, [isVisible])

  console.log(isVisible)
  return (
    <>
      {isVisible && (
        <>
          <StyledGrayBackground />
          <StyledModalWrapper>
            <StyledLoginModalHeader>
              <CoconetLogo style={{ width: '10rem' }} />
              <CloseIcon
                style={{ color: '#868e96', cursor: 'pointer' }}
                onClick={() => handleSupportButton()}
              />
            </StyledLoginModalHeader>
            <StyledContentWrapper>
              {nextModalVisible ? (
                <div style={{ textAlign: 'center' }}>
                  <StyledSetSelfIntroduce>
                    자신을 소개할 수 있는 짧은 글을 작성해 주세요!
                  </StyledSetSelfIntroduce>
                  <StyledTextArea name="info" id="info"></StyledTextArea>
                  <StyledSupportButton onClick={handleNextModal}>
                    다음
                  </StyledSupportButton>
                </div>
              ) : (
                <div>
                  <IoIosArrowBack
                    size={30}
                    style={{ cursor: 'pointer' }}
                    onClick={handleNextModal}
                  />
                  <div style={{ textAlign: 'center', marginTop: 20 }}>
                    <StyledSelectPositionTitle>
                      지원하실 포지션을 선택해주세요!
                    </StyledSelectPositionTitle>
                    <StyledSelectPositionWrapper>
                      <SingleSelect
                        label="직무"
                        onChange={handleCarrer}
                        value={carrer}
                        placeholder={'프론트엔드, 백엔드...'}
                      />
                      {/* <LinkList onSubmit={() => console.log('submit')} /> */}
                    </StyledSelectPositionWrapper>
                  </div>
                </div>
              )}
            </StyledContentWrapper>
          </StyledModalWrapper>
        </>
      )}
    </>
  )
}

const StyledGrayBackground = styled.div`
  box-sizing: border-box;
  display: block;
  position: fixed;
  inset: 0px;
  background: rgba(77, 77, 77, 0.5);
  z-index: 999;
`

const StyledLoginModalHeader = styled.div`
  background: #f8f9fa;
  padding: 0 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-radius: 8px 8px 0 0;
  width: 100%;
  height: 3rem;
  box-sizing: border-box;

  @media (max-width: 768px) {
    background: #f8f9fa;
    padding: 0 1rem;
    border-radius: 0;
  }
`

const StyledModalWrapper = styled.div`
  width: 700px;
  height: 450px;
  position: fixed;
  z-index: 9999;
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 8px;
  /* background: #B0DEDB; */
  background: #fff;
`

const StyledSetSelfIntroduce = styled.h2`
  margin-top: 4rem;
  text-align: center;
  margin-bottom: 32px;
`

const StyledTextArea = styled.textarea`
  height: 134px;
  width: 87%;
  padding: 15px 13px;
  border-radius: 5px;
  border: 1px solid rgb(225, 226, 227);
  resize: none;
  font-size: 16px;
  font-style: normal;
  font-weight: 400;
  outline: none;
  line-height: 150%;
  box-sizing: border-box;
`

const StyledContentWrapper = styled.div`
  padding: 1.5rem;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
`

const StyledSupportButton = styled.button`
  margin-top: 2.5rem;
  font-weight: 700;
  color: #fff;
  border-radius: 4px;
  width: 6rem;
  height: 2.5rem;
  font-size: 1.25rem;
  background-color: #262626;
  cursor: pointer;
`

const StyledSelectPositionTitle = styled.h2`
  margin-bottom: 32px;
`

const StyledSelectPositionWrapper = styled.div`
  padding: 0 60px;
  text-align: left;
`
