package com.example.demo.service;

import com.example.demo.model.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO save(UserDTO userDTO);
    UserDTO update(UserDTO userDTO);
    void delete(Integer userId);
    UserDTO findById(Integer userId);
    UserDTO getUserByName(String name);
    List<UserDTO> getUsersByAge(Integer age);
    List<UserDTO> findAll();
}