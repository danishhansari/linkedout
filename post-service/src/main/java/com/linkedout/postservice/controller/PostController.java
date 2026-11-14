package com.linkedout.postservice.controller;

import com.linkedout.postservice.entity.Comment;
import com.linkedout.postservice.entity.Post;
import com.linkedout.postservice.service.PostService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    // Create post
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestParam UUID authorId,
                                           @RequestParam String content,
                                           @RequestParam(required = false)
                                               MultipartFile image) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.createPost(authorId, content, image));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getAllPosts(@PathVariable UUID postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getUserPosts(@PathVariable UUID userId) {
        return ResponseEntity.ok(postService.getUserPosts(userId));
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable UUID postId,
                                           @RequestParam UUID userId) {
        return ResponseEntity.ok(postService.likePost(postId, userId));
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable UUID postId,
                                              @RequestParam UUID authorId,
                                              @RequestBody String content) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.addComment(postId, authorId, content));
    }


    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable UUID postId, @RequestParam UUID userId) {
        postService.deletePost(postId, userId);
        return ResponseEntity.ok("Post deleted");
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable UUID postId) {
        return ResponseEntity.ok(postService.getComments(postId));
    }
}
