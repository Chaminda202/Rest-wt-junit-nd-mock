package com.example.demo.service.impl;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.UserDTO;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("userService")
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MessageSource messageSource;


    @Override
    public UserDTO save(UserDTO userDTO) {
        return this.userMapper
                .mapUserToUserDto(this.userRepository.save(this.userMapper.mapUserDtoToUserEntity(userDTO)));
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        Optional<User> optionalUser = this.userRepository
                .findById(userDTO.getId());
        if(optionalUser.isPresent()){
            return this.userMapper.mapUserToUserDto(this.userRepository
                    .save(this.userMapper.mapUserDtoToUserEntity(userDTO)));
        }
        throw new UserNotFoundException(
                this.messageSource.getMessage("user.notfound", new Integer[]{userDTO.getId()}, Locale.ENGLISH));
    }

    @Override
    public void delete(Integer userId) {
        Optional<User> optionalUser = this.userRepository
                .findById(userId);
        if(optionalUser.isPresent()){
            this.userRepository.deleteById(userId);
        }
        throw new UserNotFoundException(
                this.messageSource.getMessage("user.notfound", new Integer[]{userId}, Locale.ENGLISH));
    }

    //Can be refactor above code like below
    @Override
    public UserDTO findById(Integer userId) {
        return this.userRepository
                .findById(userId)
                .map(this.userMapper::mapUserToUserDto)
                .orElseThrow(() ->new UserNotFoundException(
                        this.messageSource.getMessage("user.notfound", new Integer[]{userId}, Locale.ENGLISH)
                ));
    }

    @Override
    public UserDTO getUserByName(String name) {
        return this.userRepository
                .findByUsername(name)
                .map(this.userMapper::mapUserToUserDto)
                .orElseThrow(() -> new UserNotFoundException("User is not exist " + name));
    }

    @Override
    public List<UserDTO> getUsersByAge(Integer age) {
        return this.userRepository
                .findByAge(age)
                .stream()
                .map(this.userMapper::mapUserToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findAll() {
        return this.userRepository
                .findAll()
                .stream()
                .map(this.userMapper::mapUserToUserDto)
                .collect(Collectors.toList());
    }
}