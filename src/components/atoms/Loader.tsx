import React, { useState } from 'react'
import styled, { keyframes } from 'styled-components'
import { ReactComponent as CoconutIcon } from '../assets/images/coconutIcon.svg'

export default function Loader() {
  const [isActive, setIsActive] = useState<boolean>(true)
  return (
    <>
      {isActive && (
        <>
          <StyledLoadingBg />
          <StyledLoading>
            <StyledLoadingSpinner>
              <StyledLoadingImage />
            </StyledLoadingSpinner>
          </StyledLoading>
        </>
      )}
    </>
  )
}

const rotateImage = keyframes`
   0% {
        transform: rotate(0deg);
    }
    25% {
        transform: rotate(30deg);
    }
    50% {
        transform: rotate(0deg);
    }
    75% {
        transform: rotate(-30deg);
    }
    100% {
        transform: rotate(0deg);
    } 
`
const StyledLoadingBg = styled.div`
  box-sizing: border-box;
  display: block;
  position: fixed;
  inset: 0px;
  background: white;
  z-index: 999;
`

const StyledLoading = styled.div`
  box-sizing: border-box;
  display: block;
  position: fixed;
  inset: 0px;
  z-index: 1000;
  overflow: auto;
  outline: 0px;
`

const StyledLoadingSpinner = styled.div`
  width: 20rem;
  height: 20rem;
  position: relative;
  display: flex;
  top: 50%;
  margin: 0 auto;
  transform: translateY(-50%);

  @media screen and (max-width: 768px) {
    width: 16rem;
    height: 16rem;
  }

  @media screen and (max-width: 575px) {
    width: 12rem;
    height: 12rem;
  }
`

const StyledLoadingImage = styled(CoconutIcon)`
  width: 100%;
  height: 100%;
  animation: ${rotateImage} 1.2s linear infinite;
  transform-origin: 50% 50%;
`
