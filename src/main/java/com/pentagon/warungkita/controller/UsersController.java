package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.dto.*;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Roles;
import com.pentagon.warungkita.model.Users;
import com.pentagon.warungkita.repository.RolesRepo;
import com.pentagon.warungkita.repository.UsersRepo;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.security.service.UserDetailsImpl;
import com.pentagon.warungkita.service.UsersService;
import com.pentagon.warungkita.service.implement.UsersServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/pentagon/warung-kita")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "02.Users")
public class UsersController {

    private static final Logger logger = LogManager.getLogger(UsersController.class);
    private final UsersServiceImpl usersServiceImpl;
    @Autowired
    private final UsersService usersService;
    private final UsersRepo usersRepo;
    private final RolesRepo rolesRepo;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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


    @GetMapping("/users/user_details")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> getUserById() {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<Users> users = usersServiceImpl.getUserById(userDetails.getUserId());
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_SELLER')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> updateUser(@RequestBody UsersRequestDTO usersRequestDTO){
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<Users> user1 = usersRepo.findById(userDetails.getUserId());
            Users users = usersRequestDTO.convertToEntity();
            users.setUserId(userDetails.getUserId());
            users.setEmail(userDetails.getEmail());
            users.setPassword(userDetails.getPassword());
            users.setUsername(userDetails.getUsername());
            users.setFullName(user1.get().getFullName());
            users.setRoles(user1.get().getRoles());

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

    @DeleteMapping("/users/deactive_user/{users_Id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_SELLER')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> deactiveUser(@PathVariable Long users_Id){
        try {
            usersServiceImpl.deleteUserById(users_Id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Delete Status", Boolean.TRUE);
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


    @PutMapping("/becomeSeller")
    @PreAuthorize("hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> becomeSeller() {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Users users = usersServiceImpl.findById(userDetails.getUserId());
            users.setUserId(userDetails.getUserId());
            Roles role1 = rolesRepo.findByName("ROLE_SELLER");
            Roles role2 = rolesRepo.findByName("ROLE_BUYER");
            List<Roles> roles = new ArrayList<>();
            roles.add(role1);
            roles.add(role2);
            users.setRoles(roles);
            users.setActive(true);
            Users updateUsers = usersServiceImpl.updateUser(users);
            UsersResponseDTO result = updateUsers.convertToResponse();
            logger.info("==================== Logger Start Update User By ID ====================");
            logger.info(result);
            logger.info("==================== Logger End Update User By ID =================");
            return ResponseHandler.generateResponse("Successfully Open Shop", HttpStatus.CREATED, result);
        }catch (Exception e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Bad Request!!");
        }

    }
    @PostMapping("/users")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> createUser(@RequestBody UsersRequestDTO usersRequestDTO) {
        return this.usersService.createUser(usersRequestDTO);
    }
    @PutMapping("/users/change_password")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_SELLER')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> changePassword(@RequestBody PassworRequest request){
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Users user1 = usersServiceImpl.findById(userDetails.getUserId());

//            Users users = new Users();
//            request.setOldPassword((passwordEncoder.encode(request.getOldPassword())));
//            request.setPassword((passwordEncoder.encode(request.getPassword())));
//            request.setConfirmPassword((passwordEncoder.encode(request.getConfirmPassword())));
            if (!Objects.equals(request.getUsername(), userDetails.getUsername())){
                throw new ResourceNotFoundException("Username is wrong");
            }
            if (userDetails.getPassword().equals(request.getPassword())){
                throw new ResourceNotFoundException("Password is same");
            }
            if (!Objects.equals(request.getPassword(), request.getConfirmPassword())){
                throw new ResourceNotFoundException("Password is not match, try again");
            }
            user1.setPassword((passwordEncoder.encode(request.getPassword())));
            Users updateUsers = usersServiceImpl.updateUser(user1);
            UsersResponseDTO result = updateUsers.convertToResponse();
            logger.info("==================== Logger Start Update User By ID ====================");
            logger.info(result);
            logger.info("==================== Logger End Update User By ID =================");
            return ResponseHandler.generateResponse("Successfully Updated Password!",HttpStatus.OK, result);
        }catch(Exception e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Bad Request!!");
        }
    }
}
