package com.insta.service;

import com.insta.model.Image;
import com.insta.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final UserService userService;

    public void saveImage(MultipartFile file) throws IOException {

        imageRepository.save(Image.builder().appUser(userService.search(getPrincipal().getUsername())).image(file.getBytes()).build());
    }

    public List<Image> getImageList() {
        return imageRepository.findByAppUser(userService.search(getPrincipal().getUsername()));
    }

    public Image getImageList(int imageId) {
        return imageRepository.findByAppUserAndId(userService.search(getPrincipal().getUsername()), imageId);
    }

    private UserDetails getPrincipal() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
    }

}
