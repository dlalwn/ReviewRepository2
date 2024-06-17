package com.sparta.reviewspotproject.service;

import com.sparta.reviewspotproject.dto.PostRequestDto;
import com.sparta.reviewspotproject.dto.PostResponseDto;
import com.sparta.reviewspotproject.entity.Post;
import com.sparta.reviewspotproject.entity.User;
import com.sparta.reviewspotproject.repository.PostRepository;
import com.sparta.reviewspotproject.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostResponseDto createPost(PostRequestDto postRequestDto,
                                      User user) {

        Post post = new Post(postRequestDto, user);
        Post savePost = postRepository.save(post);
        return new PostResponseDto(savePost);
    }

    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        return new PostResponseDto(post);
    }

    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    @Transactional
    public PostResponseDto update(Long postId, PostResponseDto postRequestDto, User user) {
        findPost(postId);
        Post post = postRepository.findById(postId).get();

        if (post.getUser().getId() != user.getId()) {
            throw new IllegalArgumentException("게시물 작성자가 아니므로 수정할 수 없습니다.");
        }

        post.update(postRequestDto, user);
        return new PostResponseDto(post);
    }

    public void delete(Long postId, User user) {

        findPost(postId);
        Post post = postRepository.findById(postId).get();
//        Post post = postRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        if (post.getUser().getId() != user.getId()) {
            throw new IllegalArgumentException("게시물 작성자가 아니므로 삭제할 수 없습니다.");
        }
        postRepository.delete(post);
    }

    public Post findPost(Long id){
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
    }


}