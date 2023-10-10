import axios from "axios";
import { useRef, useState } from "react";
import styled from "styled-components";
export default function RegisterImage() {
    const [fileURL, setFileURL] = useState<string>("");
    const [file, setFile] = useState<FileList | null>();
    const imgUploadInput = useRef<HTMLInputElement | null>(null);

  const onImageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files) {
      setFile(event.target.files);

      const newFileURL = URL.createObjectURL(event.target.files[0]);
      setFileURL(newFileURL);
    }
  };

  const submitHandler = async (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();

    /** 서버통신 */
    const formData = new FormData();

    if (file) {
      formData.append("file", file[0]);

      try {
        const response = await axios.post("/api/upload", formData, {
          headers: { "content-type": "multipart/form-data" },
        });
      } catch (error: any) {
        console.log("이미지업로드 에러 발생");
        throw new Error(error);
      }
    } else {
      alert("업로드할 이미지가 없습니다");
    }
  };

    return (
    <>
        <StyledImageWrapper>
            <StyledImage
            src={
            fileURL
                ? fileURL
                : "https://hola-post-image.s3.ap-northeast-2.amazonaws.com/default.PNG"
            }
            alt=""
                ></StyledImage>
                <StyledImageEdit src="https://holaworld.io/images/info/profile_edit.png" alt="profile_image_edit" />
                <StyledImageInput
        type="file"
        id="img"
        accept="image/*"
        required
        ref={imgUploadInput}
        onChange={onImageChange}
      ></StyledImageInput>
        </StyledImageWrapper>
      <label htmlFor="img">이미지 업로드</label>
    </>
    )
}

const StyledImageWrapper = styled.label`
    display: flex;
    position: relative;
    cursor: pointer;
    width : 150px;
    height : 150px;
`

const StyledImage = styled.img`
    display: block;
    height: 150px;
    width: 150px;
    border: 1px solid #e1e1e1;
    border-radius: 50%;
    object-fit: cover;
`;

const StyledImageInput = styled.input`
    opacity: 0;
    z-index: -1;
    position: absolute;
    width: 0;
    height: 0;
`
const StyledImageEdit = styled.img`
    position: absolute;
    bottom: 10px;
    right: 10px;
    width: 32px;
    height: 32px;
`