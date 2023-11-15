package com.tangerino.blog.controllers;

import com.tangerino.blog.dto.PhotoDTO;
import com.tangerino.blog.entity.Role;
import com.tangerino.blog.service.PhotoService;
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
@RequestMapping("/api/photo")
public class PhotoController {
    private final PhotoService photoService;

    @GetMapping
    public ResponseEntity getAllPhotos() {
        return ResponseEntity.ok(photoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getPhoto(@PathVariable Long id) {
        return ResponseEntity.ok(photoService.findById(id));
    }

    @RolesAllowed(Role.USER)
    @PostMapping
    public ResponseEntity<Object> savePhoto(@RequestBody @Valid PhotoDTO photoDTO, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            photoService.savePhoto(photoDTO, userDetails.getUsername());
            return ResponseEntity.ok(photoDTO);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RolesAllowed(Role.USER)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        boolean deleted = photoService.delete(id, userDetails.getUsername());
        if (!deleted) {
            return new ResponseEntity<>("Voce não pode deletar esta foto", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}