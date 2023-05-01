package rw.usermanagement.userauthservice.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.usermanagement.userauthservice.Model.User;
import rw.usermanagement.userauthservice.enums.Gender;
import rw.usermanagement.userauthservice.enums.IdentityType;
import rw.usermanagement.userauthservice.enums.MaritalStatus;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    private Long id;
    private User user;
    private String profilePhoto;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private MaritalStatus maritalStatus;
    private String nationality;
    private IdentityType identityType;
    private String identityNumber;
    private String officialDocument;
    private boolean isCompleted;
}

