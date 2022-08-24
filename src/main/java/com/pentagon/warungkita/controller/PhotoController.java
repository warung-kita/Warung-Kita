package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.dto.PhotoRequestDTO;
import com.pentagon.warungkita.service.PhotoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/pentagon/warung-kita")
@AllArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "04.Photo")
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping("/photos/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_SELLER')")
    public ResponseEntity<Object> findAll() {
        return this.photoService.getAll();
    }

    @GetMapping("/photo/{photoId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_SELLER')")
    public ResponseEntity<Object> getPhotoById(@PathVariable Long photoId){
        return this.photoService.getPhotoById(photoId);
    }

    @GetMapping("/downloadFile/{fileCode}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_SELLER')")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) {
       return this.photoService.downloadFile(fileCode);
    }

    @PostMapping(value = "/photo/add",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<Object> createPhoto(@RequestPart PhotoRequestDTO photoRequestDTO, @RequestParam("file") MultipartFile multipartFile){

        return this.photoService.createPhoto(photoRequestDTO, multipartFile);

    }

    @PutMapping("/photo/update/{photoId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_SELLER')")
    public ResponseEntity<Object> updatePhoto(@PathVariable Long photoId, @RequestBody PhotoRequestDTO photoRequestDTO){
        return this.photoService.updatePhoto(photoId, photoRequestDTO);
    }

    @DeleteMapping("photo/delete/{photoId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_SELLER')")
    public ResponseEntity<Object> deletePhoto(@PathVariable Long photoId){
        return this.photoService.deletePhoto(photoId);
    }
}