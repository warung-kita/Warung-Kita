package com.pentagon.warungkita.service;

import com.pentagon.warungkita.model.Photo;

import java.util.List;
import java.util.Optional;

public interface PhotoService {
    List<Photo> getAll();
    Optional<Photo> getPhotoById(Long photoId);
    Photo createPhoto(Photo photo);
    Photo updatePhoto (Photo photo);
    void deletePhoto(Long photoId);
}
