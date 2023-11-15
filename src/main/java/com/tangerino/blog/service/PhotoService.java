package com.tangerino.blog.service;

import com.tangerino.blog.dto.PhotoDTO;
import com.tangerino.blog.entity.Photo;
import com.tangerino.blog.repository.PhotoRepository;
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
public class PhotoService {
    final private PhotoRepository photoRepository;
    final private UserRepository userRepository;
    public List<PhotoDTO> findAll() {
      return photoRepository.findAll().stream().map(photo ->  convert(photo, PhotoDTO.class)).collect(Collectors.toList());
    }

    public PhotoDTO findById(Long id) {
        Optional<Photo> photo = photoRepository.findById(id);
        PhotoDTO photoDTO = new PhotoDTO();

        if (photo.isPresent()){
            BeanUtils.copyProperties(photo, photoDTO);
            return photoDTO;
        }
        throw new NoSuchElementException("Foto não encontrada");
    }


    public boolean delete(Long id, String login) {
        Optional<Photo> reg = photoRepository.findById(id);
        if (reg.isPresent()){
            if (reg.get().getUser().getUsername().equalsIgnoreCase(login)){
                photoRepository.delete(reg.get());
                return true;
            }
        }
        return false;
    }

    public void savePhoto(PhotoDTO photoDTO, String username) {
        var photo = new Photo();
        BeanUtils.copyProperties(photoDTO, photo);
        photo.setUser(userRepository.findByUsername(username).orElseThrow());
        photoRepository.save(photo);
    }
}
