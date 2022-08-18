package com.sparta.miniproject_movie_study_01.service;

import com.sparta.miniproject_movie_study_01.controller.request.PostRequestDto;
import com.sparta.miniproject_movie_study_01.controller.response.*;
import com.sparta.miniproject_movie_study_01.domain.*;
import com.sparta.miniproject_movie_study_01.jwt.TokenProvider;
import com.sparta.miniproject_movie_study_01.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final MovieUpCommingRepository movieUpCommingRepository;

    private final CommentRepository commentRepository;

    private final CommentReplyRepository commentReplyRepository;

    private final LikesRepository likesRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> createPost(PostRequestDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }
        // 엑세스 토큰을 어디서 복호화 하는 걸까???
        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        if (requestDto.getImgUrl().equals("")) {

            Post post = Post.builder()
                    .title(requestDto.getTitle())
                    .content(requestDto.getContent())
                    .imgUrl("0")
                    .likes_count(0)
                    .comment_count(0)
                    .member(member)
                    .build();

            postRepository.save(post);

            return ResponseDto.success(
                    PostResponseDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .imgUrl(post.getImgUrl())
                            .author(post.getMember().getNickname())
                            .likes(post.getLikes_count())
                            .commentCount(post.getComment_count())
                            .createdAt(post.getCreatedAt())
                            .modifiedAt(post.getModifiedAt())
                            .build()
            );

        } else {

            Post post = Post.builder()
                    .title(requestDto.getTitle())
                    .content(requestDto.getContent())
                    .imgUrl(requestDto.getImgUrl())
                    .likes_count(0)
                    .comment_count(0)
                    .member(member)
                    .build();

            postRepository.save(post);

            return ResponseDto.success(
                    PostResponseDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .imgUrl(post.getImgUrl())
                            .author(post.getMember().getNickname())
                            .likes(post.getLikes_count())
                            .commentCount(post.getComment_count())
                            .createdAt(post.getCreatedAt())
                            .modifiedAt(post.getModifiedAt())
                            .build()
            );
        }

    }

//    @Transactional(readOnly = true)
//    public ResponseDto<?> getPost(Long id) {
//        Post post = isPresentPost(id);
//        if (null == post) {
//            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
//        }
//
//        List<Comment> commentList = commentRepository.findAllByPost(post);
//        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
//
//        for (Comment comment : commentList) {
//
//            List<CommentReply> commentReplyList = commentReplyRepository.findAllByComment(comment);
//            List<CommentReplyResponseDto> commentReplyResponseDtoList = new ArrayList<>();
//
//            for (CommentReply commentReply : commentReplyList) {
//
//                commentReplyResponseDtoList.add(
//                        CommentReplyResponseDto.builder()
//                                // 코멘트 index
//                                .commentId(commentReply.getComment().getId())
//                                // 원래 대댓글 index
//                                .id(commentReply.getId())
//                                .author(commentReply.getMember().getNickname())
//                                .content(commentReply.getContent())
//                                .likes(commentReply.getLikes_count())
//                                .createdAt(commentReply.getCreatedAt())
//                                .modifiedAt(commentReply.getModifiedAt())
//                                .build()
//                );
//            }
//
//
//            commentResponseDtoList.add(
//                    CommentResponseDto.builder()
//                            .id(comment.getId())
//                            .author(comment.getMember().getNickname())
//                            .content(comment.getContent())
//                            .likes(comment.getLikes_count())
//                            .commentReplyResponseDtoList(commentReplyResponseDtoList)
//                            .createdAt(comment.getCreatedAt())
//                            .modifiedAt(comment.getModifiedAt())
//                            .build()
//            );
//        }
//
//        return ResponseDto.success(
//                PostResponseDto.builder()
//                        .id(post.getId())
//                        .title(post.getTitle())
//                        .content(post.getContent())
//                        .imgUrl(post.getImgUrl())
//                        .likes(post.getLikes_count())
//                        .commentCount(post.getComment_count())
//                        .commentResponseDtoList(commentResponseDtoList)
//                        .author(post.getMember().getNickname())
//                        .createdAt(post.getCreatedAt())
//                        .modifiedAt(post.getModifiedAt())
//                        .build()
//        );
//    }


    @Transactional(readOnly = true)
    public ResponseDto<?> getmovieUpComming(Long id) {
        MovieUpComming movieUpComming = isPresentMoiveUpcomming(id);
        if (null == movieUpComming) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        List<Comment> commentList = commentRepository.findAllByMovieUpComming(movieUpComming);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : commentList) {

            List<CommentReply> commentReplyList = commentReplyRepository.findAllByComment(comment);
            List<CommentReplyResponseDto> commentReplyResponseDtoList = new ArrayList<>();

            for (CommentReply commentReply : commentReplyList) {

                commentReplyResponseDtoList.add(
                        CommentReplyResponseDto.builder()
                                // 코멘트 index
                                .commentId(commentReply.getComment().getId())
                                // 원래 대댓글 index
                                .id(commentReply.getId())
                                .author(commentReply.getMember().getNickname())
                                .content(commentReply.getContent())
                                .likes(commentReply.getLikes_count())
                                .createdAt(commentReply.getCreatedAt())
                                .modifiedAt(commentReply.getModifiedAt())
                                .build()
                );
            }


            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .author(comment.getMember().getNickname())
                            .content(comment.getContent())
                            .likes(comment.getLikes_count())
                            .commentReplyResponseDtoList(commentReplyResponseDtoList)
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );
        }

//        return ResponseDto.success(
//                PostResponseDto.builder()
//                        .id(post.getId())
//                        .title(post.getTitle())
//                        .content(post.getContent())
//                        .imgUrl(post.getImgUrl())
//                        .likes(post.getLikes_count())
//                        .commentCount(post.getComment_count())
//                        .commentResponseDtoList(commentResponseDtoList)
//                        .author(post.getMember().getNickname())
//                        .createdAt(post.getCreatedAt())
//                        .modifiedAt(post.getModifiedAt())
//                        .build()
//        );

        return ResponseDto.success(
                MovieUpCommingResponseDto.builder()
                        .id(movieUpComming.getId())
                        .img(movieUpComming.getImg())
                        .movieAge(movieUpComming.getMovieAge())
                        .movieTitle(movieUpComming.getMovieTitle())
                        .movieRate(movieUpComming.getMovieRate())
                        .movieOpenDate(movieUpComming.getMovieOpenDate())
                        .likes(movieUpComming.getLikes_count())
                        .commentCount(movieUpComming.getComment_count())
                        .commentResponseDtoList(commentResponseDtoList)
                        .createdAt(movieUpComming.getCreatedAt())
                        .modifiedAt(movieUpComming.getModifiedAt())
                        .build()
        );



    }




//    @Transactional(readOnly = true)
//    public ResponseDto<?> getAllPost() {
//        return ResponseDto.success(postRepository.findAllByOrderByModifiedAtDesc());
//    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllmovieUpComming() {
        return ResponseDto.success(movieUpCommingRepository.findAll());
    }

    @Transactional
    public ResponseDto<Post> updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        if (post.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        post.update(requestDto);
        postRepository.save(post);
        return ResponseDto.success(post);
    }

    @Transactional
    public ResponseDto<?> deletePost(Long id, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        if (post.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
        return ResponseDto.success("delete success");
    }

    @Transactional(readOnly = true)
    public Post isPresentPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

    @Transactional(readOnly = true)
    public MovieUpComming isPresentMoiveUpcomming(Long id) {
        Optional<MovieUpComming> optionalMovieUpComming = movieUpCommingRepository.findById(id);
        return optionalMovieUpComming.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

}
