package com.pentagon.warungkita.service.implement;

import com.pentagon.warungkita.controller.PhotoController;
import com.pentagon.warungkita.dto.FileUploadResponse;
import com.pentagon.warungkita.dto.PhotoRequestDTO;
import com.pentagon.warungkita.dto.PhotoResponseDTO;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Photo;
import com.pentagon.warungkita.repository.PhotoRepo;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.service.PhotoService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private static final Logger logger = LogManager.getLogger(PhotoController.class);
    private final PhotoRepo photoRepo;

    @Override
    public List<Photo> getAll() {
        List<Photo> photo = photoRepo.findAll();
        if(photo.isEmpty()){
            throw new ResourceNotFoundException("Photo Not Exist");
        }
        return this.photoRepo.findAll();
    }

    @Override
    public Optional<Photo> getPhotoById(Long photoId) {
        Optional<Photo> photo = photoRepo.findById(photoId);
        if(photo.isEmpty()){
            throw new ResourceNotFoundException("Photo not exist with id " + photoId);
        }
        return this.photoRepo.findById(photoId);
    }

    @Override
    public Photo createPhoto(Photo photo) {
        return photoRepo.save(photo);
    }

    @Override
    public Photo updatePhoto(Photo photo) {
        Optional<Photo> optionalPhoto = photoRepo.findById(photo.getPhotoId());
        if(optionalPhoto.isEmpty()){
            throw new ResourceNotFoundException("Photo not exist with id " + photo.getPhotoId());
        }
        return this.photoRepo.save(photo);
    }

    @Override
    public void deletePhoto(Long photoId) {
        Optional<Photo> optionalPhoto = photoRepo.findById(photoId);
        if(optionalPhoto.isEmpty()){
            throw new ResourceNotFoundException("Photo not exist with id " + photoId);
        }
        Photo photo = photoRepo.getReferenceById(photoId);
        this.photoRepo.delete(photo);
    }
}
