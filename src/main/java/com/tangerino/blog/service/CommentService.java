package com.tangerino.blog.service;

import com.tangerino.blog.dto.CommentDTO;
import com.tangerino.blog.entity.Comment;
import com.tangerino.blog.repository.CommentRepository;
import com.tangerino.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tangerino.blog.utils.ConvertUtils.convert;

@Service
@AllArgsConstructor
public class CommentService {
    final private CommentRepository commentRepository;
    final private UserRepository userRepository;
    public List<CommentDTO> findAll() {
      return commentRepository.findAll().stream().map(comment ->  convert(comment, CommentDTO.class)).collect(Collectors.toList());
    }

    public CommentDTO findById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        CommentDTO commentDTO = new CommentDTO();

        if (comment.isPresent()){
            BeanUtils.copyProperties(comment, commentDTO);
            return commentDTO;
        }
        throw new NoSuchElementException("Comentário não encontrado");
    }


    public boolean delete(Long id, String login) {
        Optional<Comment> reg = commentRepository.findById(id);
        if (reg.isPresent()){
            if (reg.get().getUser().getUsername().equalsIgnoreCase(login)){
                commentRepository.delete(reg.get());
                return true;
            }
        }
        return false;
    }

    public void saveComment(CommentDTO commentDTO, String username) {
        var comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        comment.setUser(userRepository.findByUsername(username).orElseThrow());
        commentRepository.save(comment);
    }
}
