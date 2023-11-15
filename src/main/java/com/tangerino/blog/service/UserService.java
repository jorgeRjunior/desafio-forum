package com.tangerino.blog.service;

import com.tangerino.blog.dto.CreateUserDTO;
import com.tangerino.blog.dto.UserDTO;
import com.tangerino.blog.entity.Role;
import com.tangerino.blog.entity.User;
import com.tangerino.blog.repository.UserRepository;
import com.tangerino.blog.utils.ConvertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    final private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO create(CreateUserDTO request) {
        User user = ConvertUtils.convert(request, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getAuthorities().add(new Role(Role.USER));
        user = userRepository.save(user);
        return ConvertUtils.convert(user, UserDTO.class);

    }
}
