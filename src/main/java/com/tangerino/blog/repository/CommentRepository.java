package com.tangerino.blog.repository;

import com.tangerino.blog.entity.Book;
import com.tangerino.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findById(Long id);
    List<Comment> findAll();
    void deleteById(Long id);
}
