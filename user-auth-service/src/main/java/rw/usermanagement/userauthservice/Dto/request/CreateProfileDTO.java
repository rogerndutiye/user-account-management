package rw.usermanagement.userauthservice.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.usermanagement.userauthservice.enums.Gender;
import rw.usermanagement.userauthservice.enums.MaritalStatus;
import rw.usermanagement.userauthservice.enums.ValueOfEnum;

import javax.persistence.Temporal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProfileDTO {

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotBlank()
    private String nationality;

    @NotBlank()
    @ValueOfEnum(enumClass = Gender.class, message = "Invalid Gender")
    private String gender;

    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;

    @NotBlank()
    @ValueOfEnum(enumClass = MaritalStatus.class, message = "Invalid marital Status")
    private String maritalStatus;
    @NotBlank()
    private String profilePhoto;
}
