import React, { useState } from "react";
import styled, { keyframes } from "styled-components";


export default function Loader()  {
    const [isActive, setIsActive] = useState<boolean>(true);
    return (
        <>
            {isActive && (
                <>
                    <StyledLoadingBg />
                    <StyledLoading>
                        <StyledLoadingSpinner>
                            <StyledLoadingImage
                                src="https://main-pdf2-aspose-app.s3.us-west-2.amazonaws.com/f9745d48-bafd-4840-8425-2c69b4495361/free-icon-coconut-4469199.svg?X-Amz-Expires=86400&response-content-disposition=attachment%3B%20filename%3D%22free-icon-coconut-4469199.svg%22&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIA4XIV7DNDPELHCB2Q/20231018/us-west-2/s3/aws4_request&X-Amz-Date=20231018T121012Z&X-Amz-SignedHeaders=host&X-Amz-Signature=5f4e74e1934de364b4c3d068d11a9aa373a8f9a3d8178eb2e29bbaa6143cd89c"
                                alt="loading spinner"
                            />
                        </StyledLoadingSpinner>
                    </StyledLoading>
                </>
            )}
       </> 
    );
};

 
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

const StyledLoadingImage = styled.img`
    width: 100%;
    height: 100%;
    animation: ${rotateImage} 1.2s linear infinite;
    transform-origin: 50% 50%;
`

