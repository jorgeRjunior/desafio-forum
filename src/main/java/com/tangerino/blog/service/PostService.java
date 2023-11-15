package com.tangerino.blog.service;

import com.tangerino.blog.dto.PostDTO;
import com.tangerino.blog.entity.Post;
import com.tangerino.blog.repository.PostRepository;
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
public class PostService {
    final private PostRepository postRepository;
    final private UserRepository userRepository;
    public List<PostDTO> findAll() {
        return postRepository.findAll().stream().map(post ->  convert(post, PostDTO.class)).collect(Collectors.toList());
    }

    public PostDTO findById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        PostDTO postDTO = new PostDTO();

        if (post.isPresent()){
            BeanUtils.copyProperties(post, postDTO);
            return postDTO;
        }
        throw new NoSuchElementException("Post não encontrado");
    }


    public boolean delete(Long id, String login) {
        Optional<Post> reg = postRepository.findById(id);
        if (reg.isPresent()){
            if (reg.get().getUser().getUsername().equalsIgnoreCase(login)){
                postRepository.delete(reg.get());
                return true;
            }
        }
        return false;
    }

    public void savePost(PostDTO postDTO, String username) {
        var post = new Post();
        BeanUtils.copyProperties(postDTO, post);
        post.setUser(userRepository.findByUsername(username).orElseThrow());
        postRepository.save(post);
    }
}
