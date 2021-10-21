package com.insta.controller;

import com.insta.model.Image;
import com.insta.service.ImageService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/image")
@Api(tags = "image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity processUpload(@RequestParam("file") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (!(Objects.equals(file.getContentType(), "image/jpeg") || Objects.equals(file.getContentType(), "image/png"))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        imageService.saveImage(file);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<List<Image>> getImageList() {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.getImageList());
    }

    @GetMapping("/{imageId}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<Image> getImage(@PathVariable int imageId) {

        if (imageService.getImageList(imageId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(imageService.getImageList(imageId));
    }
}
