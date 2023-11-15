package com.tangerino.blog.controllers;

import com.tangerino.blog.dto.CommentDTO;
import com.tangerino.blog.entity.Role;
import com.tangerino.blog.service.CommentService;
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
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity getAllComments() {
        return ResponseEntity.ok(commentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getComment(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findById(id));
    }

    @RolesAllowed(Role.USER)
    @PostMapping
    public ResponseEntity<Object> saveComment(@RequestBody @Valid CommentDTO commentDTO, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            commentService.saveComment(commentDTO, userDetails.getUsername());
            return ResponseEntity.ok(commentDTO);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RolesAllowed(Role.USER)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        boolean deleted = commentService.delete(id, userDetails.getUsername());
        if (!deleted) {
            return new ResponseEntity<>("Voce não pode deletar este comentário", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}