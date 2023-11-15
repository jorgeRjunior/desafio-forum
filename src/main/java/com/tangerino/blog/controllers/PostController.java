package com.tangerino.blog.controllers;

import com.tangerino.blog.dto.PostDTO;
import com.tangerino.blog.entity.Role;
import com.tangerino.blog.service.PostService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity getAllPosts() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    @RolesAllowed(Role.USER)
    @PostMapping
    public ResponseEntity<Object> savePost(@RequestBody @Valid PostDTO postDTO, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            postService.savePost(postDTO, userDetails.getUsername());
            return ResponseEntity.ok(postDTO);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RolesAllowed(Role.USER)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        boolean deleted = postService.delete(id, userDetails.getUsername());
        if (!deleted) {
            return new ResponseEntity<>("Voce não pode deletar este post", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}