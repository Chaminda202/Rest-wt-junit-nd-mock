package com.example.demo.controller;

import com.example.demo.model.UserDTO;
import com.example.demo.service.UserService;
import com.example.demo.validator.UserIDExisting;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "users")
@AllArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDTO saveUser(@RequestBody @Valid UserDTO userDTO) {
        return this.userService.save(userDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable(value = "id") @NotNull Integer id, @RequestBody  @Valid UserDTO userDTO) {
        userDTO.setId(id);
        UserDTO response = this.userService.update(userDTO);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Void> delete(@RequestParam (value = "id") @UserIDExisting Integer id) {
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/getById/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable(value = "id") @UserIDExisting Integer id) {
        UserDTO response = this.userService.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "{name}")
    public UserDTO getUserByName(@PathVariable String name) {
        return this.userService.getUserByName(name);
    }

    @GetMapping(value = "/age/{age}")
    public List<UserDTO> getUsersByAge(@PathVariable Integer age) {
        return this.userService.getUsersByAge(age);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(){
        List<UserDTO> response = this.userService.findAll();
        return ResponseEntity.ok().body(response);
    }
}