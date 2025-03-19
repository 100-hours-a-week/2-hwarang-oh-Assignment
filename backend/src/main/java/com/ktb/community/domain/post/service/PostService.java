package com.ktb.community.domain.post.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ktb.community.domain.likes.model.LikeStatus;
import com.ktb.community.domain.likes.repository.LikeRepository;

import com.ktb.community.domain.post.model.entity.Post;
import com.ktb.community.domain.post.repository.PostRepository;
import com.ktb.community.domain.post.model.dto.PostCreateRequest;
import com.ktb.community.domain.post.model.dto.PostUpdateRequest;

import com.ktb.community.global.exception.ErrorCode;
import com.ktb.community.global.exception.BaseException;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepostiory;

    public PostService(PostRepository postRepository, LikeRepository likeRepostiory) {
        this.likeRepostiory = likeRepostiory;
        this.postRepository = postRepository;
    }

    // IMP : Create Post -> create
    public void createPost(PostCreateRequest request) {
        postRepository.create(request);
    }

    // IMP : Get Posts -> findAll
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    /**
     * IMP : Get Post By Id -> findById
     * ? Why : 게시글의 정보를 조회하기 위해 사용
     * ! 주의 : 게시글 조회 시 조회수를 증가시킨다.
     * TODO : 뭔가 한 번에 처리하는 방법은 없을까?
     * 
     * @param id
     * @return
     */
    public Post getPostById(Long postId) {
        if (!postRepository.existsById(postId))
            throw new BaseException(ErrorCode.NOT_EXIST_POST);

        postRepository.updateViewCount(postId);

        return postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_POST));
    }

    // IMP : Revise Post -> update
    public void revisePost(Long postId, PostUpdateRequest request) {
        if (!postRepository.existsById(postId))
            throw new BaseException(ErrorCode.NOT_EXIST_POST);

        postRepository.update(postId, request);
    }

    // IMP : Delete Post -> delete
    public void deletePost(Long postId) {
        if (!postRepository.existsById(postId))
            throw new BaseException(ErrorCode.NOT_EXIST_POST);

        postRepository.delete(postId);
    }

    // IMP : toggleLike
    public LikeStatus toggleLike(Long postId, Long userId) {
        boolean exists = likeRepostiory.existsByPostIdandUserId(postId, userId);
        if (exists) {
            likeRepostiory.removeLike(postId, userId);
            postRepository.decreaseLikeCount(postId);
            return LikeStatus.UNLIKED;
        } else {
            likeRepostiory.addLike(postId, userId);
            postRepository.increaseLikeCount(postId);
            return LikeStatus.LIKED;
        }
    }

}
