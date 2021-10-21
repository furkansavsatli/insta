package com.insta.repository;

import com.insta.model.AppUser;
import com.insta.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    List<Image> findByAppUser(AppUser appUser);

    Image findByAppUserAndId(AppUser appUser, int Id);
}
