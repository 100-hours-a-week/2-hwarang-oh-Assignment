package com.ktb.community.domain.post.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ktb.community.domain.post.model.entity.Post;
import com.ktb.community.domain.post.repository.PostRepository;

@Service
public class PostService {

    private final PostRepository postRepository;

    private PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void createPost(Long userId, Post post) {
        post.setUserId(userId);
        postRepository.createPost(post);
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id);
    }

    public void revisePost(Long id, String title, String content, String postImageUrl) {
        postRepository.updatePost(id, title, content, postImageUrl);
    }

    public void removePost(Long id) {
        postRepository.deletePost(id);
    }

}
