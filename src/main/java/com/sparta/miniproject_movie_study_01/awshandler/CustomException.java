package com.sparta.miniproject_movie_study_01.awshandler;




import com.sparta.miniproject_movie_study_01.controller.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Getter
@AllArgsConstructor
@RestControllerAdvice
@RestController
public class CustomException {

    @ExceptionHandler(FileTypeErrorException.class)
    public ResponseDto<?> fileTypeErrorException(){
        return ResponseDto.fail("file type error","이미지가 아닙니다.");
    }

    @ExceptionHandler(FileSizeErrorException.class)
    public ResponseDto<?> fileSizeErrorException(){
        return ResponseDto.fail("file size error","이미지업로드 한도를 넘으셨습니다.");
    }

}
