package com.coconet.memberservice.converter;

import com.coconet.memberservice.common.errorcode.ErrorCode;
import com.coconet.memberservice.common.exception.ApiException;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;


public class ImageConverter {
    public static byte[] toImage(String photoPath) {
        String absolutePath = new File("").getAbsolutePath() + "/";
        String path = photoPath == null ? "src/main/resources/memberProfilePics/basic_image.png": photoPath;
        File imageFile = new File(absolutePath + path);

        try {
            return FileCopyUtils.copyToByteArray(imageFile);
        } catch(IOException e) {
            imageFile = new File(absolutePath + "src/main/resources/memberProfilePics/basic_image.png");
            try {
                return FileCopyUtils.copyToByteArray(imageFile);
            } catch (IOException ex) {
                throw new ApiException(ErrorCode.SERVER_ERROR, "Error happened when image file had converted");
            }
        }
    }
}
