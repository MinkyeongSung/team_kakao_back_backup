package com.example.team3_kakaotalk._core.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import com.example.team3_kakaotalk._core.handler.exception.MyBadRequestException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com.example.team3_kakaotalk._core.vo.MyPath;


public class PhotoToStringUtil {
    public static String picToString(String pic, String startFileName){
        try {
            byte[] image = Base64.getDecoder().decode(pic);
            UUID uuid = UUID.randomUUID();
            String fileName = startFileName + "_" + uuid + ".jpg";
            Path filePath = Paths.get(MyPath.IMG_PATH, fileName);
            Files.write(filePath, image);
            return fileName;
        }catch (Exception e){
            throw new MyBadRequestException("디코딩에 실패했습니다");
        }
    }

}
