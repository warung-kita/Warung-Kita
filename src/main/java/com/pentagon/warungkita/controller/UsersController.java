package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.dto.UsersRequestDTO;
import com.pentagon.warungkita.dto.UsersResponseDTO;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Users;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.service.implement.UsersServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/pentagon/warung-kita")
public class UsersController {

    private final UsersServiceImpl usersServiceImpl;

    @GetMapping("/users")
    public ResponseEntity <Object> getAll() {
        try {
            List<Users> result = usersServiceImpl.getAll();
            List<UsersResponseDTO> usersResponseDTOList = new ArrayList<>();
            for (Users dataresult:result){
                UsersResponseDTO usersResponseDTO = dataresult.convertToResponse();
                usersResponseDTOList.add(usersResponseDTO);
            }
            return ResponseHandler.generateResponse("Successfully Get All User!", HttpStatus.OK, usersResponseDTOList);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, "Table Has No Value!");
        }
    }

    @PostMapping("/users")
    public ResponseEntity <Object> createUser(@RequestBody UsersRequestDTO usersRequestDTO) {
        try {
            Users users = usersRequestDTO.convertToEntity();
            usersServiceImpl.createUser(users);
            UsersResponseDTO userResult = users.convertToResponse();
            return ResponseHandler.generateResponse("Successfully Created User!", HttpStatus.CREATED, userResult);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "User Already Exist!");
        }
    }

    @GetMapping("/users/{users_Id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long users_Id) {
        try {
            Optional<Users> users = usersServiceImpl.getUserById(users_Id);
            Users userResult = users.get();
            UsersResponseDTO result = userResult.convertToResponse();
            return ResponseHandler.generateResponse("Successfully Get User By ID!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found!" );
        }
    }

    @PutMapping("/users/{users_Id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long users_Id, @RequestBody UsersRequestDTO usersRequestDTO){
        try {
            if(usersRequestDTO.getUserId() == null){
                throw new ResourceNotFoundException("User not exist");
            }
            Users users = usersRequestDTO.convertToEntity();
            users.setUserId(users_Id);
            Users updateUsers = usersServiceImpl.updateUser(users);
            UsersResponseDTO result = updateUsers.convertToResponse();
//            Users user = usersServiceImpl.getUserById(users_Id)
//                    .orElseThrow(() -> new ResourceNotFoundException("User not exist with user_Id :" + users_Id));
//
//            user.setUsername(userDetails.getUsername());
//            user.setFullName(userDetails.getFullName());
//            user.setEmail(userDetails.getEmail());
//            user.setPassword(userDetails.getPassword());
//            user.setAddress(userDetails.getAddress());
//            user.setPhoneNum(userDetails.getPhoneNum());
//            Users updatedUser = usersServiceImpl.updateUser(user);

            return ResponseHandler.generateResponse("Successfully Updated User!",HttpStatus.OK, result);
        }catch(Exception e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data Not Found!");
        }
    }

    @DeleteMapping("/users/{users_Id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long users_Id){
        try {
            usersServiceImpl.deleteUserById(users_Id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("hard deleted", Boolean.TRUE);
            return ResponseHandler.generateResponse("Successfully Delete User! ", HttpStatus.OK, response);
        } catch (ResourceNotFoundException e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found!" );
        }
    }
}
