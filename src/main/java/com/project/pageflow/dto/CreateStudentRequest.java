package com.project.pageflow.dto;


import com.project.pageflow.models.Student;
import com.project.pageflow.models.SecuredUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateStudentRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String rollNumber;

    @NotNull
    private Integer age;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
    
    public Student toStudent() {
      SecuredUser securedUser = SecuredUser.builder()
              .username(this.username)
              .password(this.password)
              .build();
      return Student.builder()
              .name(this.name)
              .age(this.age)
              .securedUser(securedUser)
              .rollNumber(this.rollNumber)
              .email(this.email)
              .build();
    }


}
