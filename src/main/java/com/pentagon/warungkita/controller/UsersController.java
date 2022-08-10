package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.dto.UsersRequestDTO;
import com.pentagon.warungkita.dto.UsersResponseDTO;
import com.pentagon.warungkita.dto.UsersResponsePOST;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Users;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.service.implement.UsersServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/pentagon/warung-kita")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "2.Users")
public class UsersController {

    private static final Logger logger = LogManager.getLogger(UsersController.class);
    private final UsersServiceImpl usersServiceImpl;

    @GetMapping("/users")
    public ResponseEntity <Object> getAll() {
        try {
            List<Users> result = usersServiceImpl.getAll();
            List<UsersResponseDTO> usersResponseDTOList = new ArrayList<>();
            logger.info("==================== Logger Start Get All User ====================");
            for (Users dataresult:result){
                Map<String, Object> order = new HashMap<>();
                order.put("role            : ", dataresult.getRoles());
                order.put("id_akun         : ", dataresult.getUserId());
                order.put("nama_lengkap    : ", dataresult.getFullName());
                order.put("nama            : ", dataresult.getUsername());
                order.put("email           : ", dataresult.getEmail());
                order.put("alamat          : ", dataresult.getAddress());
                order.put("sandi           : ", dataresult.getPassword());
                order.put("nomor_tlp       : ", dataresult.getPhoneNum());
                order.put("foto            : ", dataresult.getProfilPicture());

                logger.info("role          : " + dataresult.getRoles());
                logger.info("id_akun       : " + dataresult.getUserId());
                logger.info("nama_lengkap  : " + dataresult.getFullName());
                logger.info("nama          : " + dataresult.getUsername());
                logger.info("email         : " + dataresult.getEmail());
                logger.info("alamat        : " + dataresult.getAddress());
                logger.info("sandi         : " + dataresult.getPassword());
                logger.info("nomor_tlp     : " + dataresult.getPhoneNum());
                logger.info("foto          : " + dataresult.getProfilPicture());
                UsersResponseDTO usersResponseDTO = dataresult.convertToResponse();
                usersResponseDTOList.add(usersResponseDTO);

                logger.info("==================== Logger End Get All User ====================");
            }
            return ResponseHandler.generateResponse("Successfully Get All User!", HttpStatus.OK, usersResponseDTOList);
        } catch (Exception e) {
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, "Bad Request!!");
        }
    }

    @PostMapping("/users")
    public ResponseEntity <Object> createUser(@RequestBody UsersRequestDTO usersRequestDTO) {
        try {
            Users users = usersRequestDTO.convertToEntity();
            usersServiceImpl.createUser(users);
            UsersResponsePOST userResult = users.convertToResponsePOST();
            logger.info("==================== Logger Start Create New User ====================");
            logger.info(userResult);
            logger.info("==================== Logger End Create New User =================");
            return ResponseHandler.generateResponse("Successfully Created User!", HttpStatus.CREATED, userResult);
        } catch (Exception e) {
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Bad Request!!");
        }
    }

    @GetMapping("/users/{users_Id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long users_Id) {
        try {
            Optional<Users> users = usersServiceImpl.getUserById(users_Id);
            Users userResult = users.get();
            UsersResponseDTO result = userResult.convertToResponse();
            logger.info("==================== Logger Start Get User By ID ====================");
            logger.info(result);
            logger.info("==================== Logger End Get User By ID =================");
            return ResponseHandler.generateResponse("Successfully Get User By ID!", HttpStatus.OK, result);
        } catch (Exception e) {
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found!" );
        }
    }

    @PutMapping("/users/{users_Id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long users_Id, @RequestBody UsersRequestDTO usersRequestDTO){
        try {
//            if(usersRequestDTO.getUserId() == null){
//                throw new ResourceNotFoundException("User not exist");
//            }
            Users users = usersRequestDTO.convertToEntity();
            users.setUserId(users_Id);
            Users updateUsers = usersServiceImpl.updateUser(users);
            UsersResponseDTO result = updateUsers.convertToResponse();
            logger.info("==================== Logger Start Update User By ID ====================");
            logger.info(result);
            logger.info("==================== Logger End Update User By ID =================");
            return ResponseHandler.generateResponse("Successfully Updated User!",HttpStatus.OK, result);
        }catch(Exception e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Bad Request!!");
        }
    }

    @DeleteMapping("/users/{users_Id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long users_Id){
        try {
            usersServiceImpl.deleteUserById(users_Id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("hard deleted", Boolean.TRUE);
            logger.info("==================== Logger Start Hard Delete User By ID ====================");
            logger.info(response);
            logger.info("==================== Logger End Hard Delete User By ID =================");
            return ResponseHandler.generateResponse("Successfully Delete User! ", HttpStatus.OK, response);
        } catch (ResourceNotFoundException e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found!" );
        }
    }
}
