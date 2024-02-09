import React from 'react'

import styled from 'styled-components'

import Carousel from 'react-material-ui-carousel'

const CustomCarousel = () => {
  const imageList = [
    {
      title: 'main1',
      url: '',
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
  width: 1200px;
`

const StyledCarouselItem = styled.div`
  width: 100%;
  aspect-ratio: 3 / 1;
`

const StyledImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`
