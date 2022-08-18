package com.pentagon.warungkita.service;

import com.pentagon.warungkita.dto.PhotoRequestDTO;
import com.pentagon.warungkita.model.Photo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PhotoService {
    List<Photo> getAll();
    Optional<Photo> getPhotoById(Long photoId);
    Photo createPhoto(Photo photo);
    Photo updatePhoto (Photo photo);
    void deletePhoto(Long photoId);
}
