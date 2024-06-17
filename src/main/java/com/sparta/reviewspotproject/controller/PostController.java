package com.sparta.reviewspotproject.controller;

import com.sparta.reviewspotproject.dto.PostRequestDto;
import com.sparta.reviewspotproject.dto.PostResponseDto;
import com.sparta.reviewspotproject.security.UserDetailsImpl;
import com.sparta.reviewspotproject.service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public PostResponseDto createPost(@Valid @RequestBody PostRequestDto postRequestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.createPost(postRequestDto, userDetails.getUser());

    }

    @GetMapping("/posts/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId) {

        return postService.getPost(postId);
    }

    @GetMapping("/posts")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }

    @PutMapping("/posts/{postId}")
    public PostResponseDto updatePost(@PathVariable Long postId,
                                      @Valid @RequestBody PostResponseDto postRequestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.update(postId, postRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.delete(postId, userDetails.getUser());
        return new ResponseEntity<>("댓글이 성공적으로 삭제되었습니다.", HttpStatus.OK);
    }
}
