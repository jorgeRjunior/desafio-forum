package com.tangerino.blog.controllers;

import com.tangerino.blog.dto.BookDTO;
import com.tangerino.blog.entity.Role;
import com.tangerino.blog.service.BookService;
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
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity getAllBooks(){
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getBook(@PathVariable Long id){
        return ResponseEntity.ok(bookService.findById(id));
    }
    @RolesAllowed(Role.USER)

    @PostMapping
    public ResponseEntity<Object> saveBook(@RequestBody @Valid BookDTO bookDTO, @AuthenticationPrincipal UserDetails userDetails){
        try {
            bookService.saveBook(bookDTO, userDetails.getUsername());
            return ResponseEntity.ok(bookDTO);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @RolesAllowed(Role.USER)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        boolean deleted = bookService.delete(id, userDetails.getUsername());
        if(!deleted) {
            return new ResponseEntity<>("Voce nao pode deletar", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
