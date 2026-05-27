package com.mediflow.mediflow.user.service;

import com.mediflow.mediflow.user.entity.User;
import com.mediflow.mediflow.user.repository.UserRepository;
import com.mediflow.mediflow.common.helper.AuthUtil;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }


  public List<UserDTO> getUsers() {
        List<User> users = repo.findAll();
        if(users.isEmpty()) {
            throw new RuntimeException("No users found");
        }
        
        return users.stream()
                .map(User::toDTO)
                .collect(Collectors.toList());
    }


    public UserDTO getUserById(UUID id){
        User user = repo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return user.toDTO();
    }

    public UserDTO getUserByEmail() {
        String email = new AuthUtil().getCurrentUserEmail();
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return user.toDTO();
    }

    public UserDTO updateUser(UserDTO userDTO) {
        String userId = new AuthUtil().getCurrentUserId();
        User user = repo.findById(userId    )
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDTO.getName());
        user.setPhone(userDTO.getPhone());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setGender(userDTO.getGender());
        User updatedUser = repo.save(user);
        return updatedUser.toDTO();
    }

    public String deleteUser() {
        String userId = new AuthUtil().getCurrentUserId();
        User user = repo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        repo.delete(user);
        return "User deleted successfully";
    }


 



}
