import React from 'react'

import styled from 'styled-components'

import Carousel from 'react-material-ui-carousel'

const CustomCarousel = () => {
  const imageList = [
    {
      title: 'main1',
      url: 'https://hola-post-image.s3.ap-northeast-2.amazonaws.com/ad/hola-event_2024-01-27_02-28-34.png',
    },
  ]
  return (
    <StyledCarouselContainer>
      <Carousel cycleNavigation={true} navButtonsAlwaysVisible={true}>
        {imageList.map((content, index) => (
          <StyledCarouselItem key={index}>
            {/* <Typography variant="h3" color="#112b23">
              {content.title}
            </Typography> */}
            <StyledImage src={content.url} />
          </StyledCarouselItem>
        ))}
      </Carousel>
    </StyledCarouselContainer>
  )
}

export default CustomCarousel

const StyledCarouselContainer = styled.div`
  width: 100%;
`

const StyledCarouselItem = styled.div`
  width: 100%;
  aspect-ratio: 3 / 1;
`

const StyledImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: contain;
`
