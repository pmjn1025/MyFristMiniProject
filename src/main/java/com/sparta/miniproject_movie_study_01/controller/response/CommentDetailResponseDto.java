package com.sparta.miniproject_movie_study_01.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDetailResponseDto {


    List<CommentResponseDto> commentResponseDtoList;




}
