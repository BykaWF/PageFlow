package com.project.pageflow.dto;

import com.project.pageflow.models.Student;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateStudentInfoDto {

    private String firstName;

    private String secondName;

    private String rollNumber;

    @Email
    private String email;

    public Student toStudent(){
        return Student.builder()
                .firstName(this.firstName)
                .secondName(this.secondName)
                .rollNumber(this.rollNumber)
                .email(this.email)
                .build();
    }
}
