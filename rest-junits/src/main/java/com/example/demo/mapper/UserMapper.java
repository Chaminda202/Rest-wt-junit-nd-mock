package com.example.demo.mapper;

import com.example.demo.model.UserDTO;
import com.example.demo.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User mapUserDtoToUserEntity(UserDTO userDTO){
        return userDTO != null ?
                User.builder()
                        .id(userDTO.getId())
                        .username(userDTO.getUsername())
                        .age(userDTO.getAge())
                        .occupation(userDTO.getOccupation())
                        .birthday(userDTO.getBirthday())
                        .build() : null;
    }

    public UserDTO mapUserToUserDto(User user){
        return user != null ?
                UserDTO.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .age(user.getAge())
                        .occupation(user.getOccupation())
                        .birthday(user.getBirthday())
                        .build() : null;
    }
}