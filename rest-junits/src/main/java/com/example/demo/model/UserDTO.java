package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    @NotNull(message = "{username.not.null}")
    private String username;
    private Integer age;
    @NotNull(message = "{occupation.not.null}")
    private String occupation;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)  // no need to add
    private LocalDate birthday; // default format yyyy-MM-dd
}