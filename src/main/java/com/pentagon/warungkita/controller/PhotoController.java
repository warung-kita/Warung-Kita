package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.dto.*;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Photo;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.service.PhotoService;
import com.pentagon.warungkita.service.implement.FileDownloadUtil;
import com.pentagon.warungkita.service.implement.FileUploadUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pentagon/warung-kita")
@AllArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "4.Photo")
public class PhotoController {

    private final PhotoService photoService;
    private static final Logger logger = LogManager.getLogger(PhotoController.class);



    @GetMapping("/photos/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_SELLER')")
    public ResponseEntity<Object> findAll() {
        try {
            List<Photo> photo = photoService.getAll();
            List<PhotoResponseDTO> photoMaps = new ArrayList<>();
            logger.info("==================== Logger Start Get All Photos     ====================");
            for (Photo dataResult : photo) {
                PhotoResponseDTO photoResponseDTO = dataResult.convertToResponse();
                photoMaps.add(photoResponseDTO);
                logger.info("Foto ID           : " + dataResult.getPhotoId());
                logger.info("Nama Foto         : " + dataResult.getPhotoName());
                logger.info("-----------------------------------------------");
            }
            logger.info("==================== Logger Start Get All Categories     ====================");
            return ResponseHandler.generateResponse("Successfully Get All Photos", HttpStatus.OK, photoMaps);
        }catch(ResourceNotFoundException e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Table has no value");
        }
    }


    @GetMapping("/photo/{photoId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_SELLER')")
    public ResponseEntity<Object> getPhotoById(@PathVariable Long photoId){
        try {
            Optional<Photo> photo = photoService.getPhotoById(photoId);
            Photo photoGet = photo.get();
            PhotoResponseDTO result = photoGet.convertToResponse();
            logger.info("==================== Logger Start Get By ID Photo     ====================");
            logger.info("Foto ID           : " + result.getKodeFoto());
            logger.info("Nama Foto         : " + result.getNamaFoto());
            logger.info("==================== Logger Start Get By ID Photo     ====================");
            return ResponseHandler.generateResponse("Successfully Get Photo Id",HttpStatus.OK,result);
        }catch(ResourceNotFoundException e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }

    @GetMapping("/downloadFile/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) {
        FileDownloadUtil downloadUtil = new FileDownloadUtil();

        Resource resource = null;
        try {
            resource = downloadUtil.getFileAsResource(fileCode);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    @PostMapping(value = "/photo/add",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<Object> createPhoto(@RequestPart PhotoRequestDTO photoRequestDTO, @RequestParam("file") MultipartFile multipartFile){
        try{
            if(photoRequestDTO.getPhotoName() == null) {
                throw new ResourceNotFoundException("Please Add Photo Name");
            }
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            long size = multipartFile.getSize();

            String filecode = FileUploadUtil.saveFile(fileName, multipartFile);

            FileUploadResponse response = new FileUploadResponse();
            response.setFileName(fileName);
            response.setSize(size);
            response.setDownloadUri("/downloadFile/" + filecode);

            Photo photo = Photo.builder().photoName(filecode).build();
            photoService.createPhoto(photo);
            PhotoResponseDTO result = photo.convertToResponse();
            logger.info("==================== Logger Start Add New Photo     ====================");
            logger.info("Foto ID           : " + result.getKodeFoto());
            logger.info("Nama Foto         : " + result.getNamaFoto());
            logger.info("==================== Logger Start Add New Photo     ====================");
            return ResponseHandler.generateResponse("Successfully Add Photo", HttpStatus.CREATED,result);
        }catch (Exception e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Failed Create Photo");
        }
    }


    @PutMapping("/photo/update/{photoId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_SELLER')")
    public ResponseEntity<Object> updatePhoto(@PathVariable Long photoId, @RequestBody PhotoRequestDTO photoRequestDTO){
        try {
            Photo photo = photoRequestDTO.convertToEntity();
            photo.setPhotoId(photoId);
            Photo responseUpdate = photoService.updatePhoto(photo);
            PhotoResponseDTO responseDTO = responseUpdate.convertToResponse();
            logger.info("==================== Logger Start Get Updated Photo     ====================");
            logger.info("Foto ID           : " + responseDTO.getKodeFoto());
            logger.info("Nama Foto         : " + responseDTO.getNamaFoto());
            logger.info("==================== Logger Start Get Updated Photo     ====================");
            return ResponseHandler.generateResponse("Successfully Update Photo",HttpStatus.CREATED,responseDTO);
        }catch (Exception e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Bad Request");
        }
    }


    @DeleteMapping("photo/delete/{photoId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_SELLER')")
    public ResponseEntity<Object> deletePhoto(@PathVariable Long photoId){
        try {
            photoService.deletePhoto(photoId);
            Boolean result = Boolean.TRUE;
            logger.info("==================== Logger Start Delete Photo     ====================");
            logger.info("Deleted : " + result);
            logger.info("==================== Logger Start Delete Photo     ====================");
            return ResponseHandler.generateResponse("Successfully Delete Photo",HttpStatus.OK,result);
        }catch(ResourceNotFoundException e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }
}