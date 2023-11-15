package com.tangerino.blog.repository;

import com.tangerino.blog.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Optional<Photo> findById(Long id);
    List<Photo> findAll();
    void deleteById(Long id);
}
