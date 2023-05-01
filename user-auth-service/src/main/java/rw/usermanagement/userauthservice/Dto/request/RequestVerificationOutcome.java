package rw.usermanagement.userauthservice.Dto.request;

import lombok.Getter;
import lombok.Setter;
import rw.usermanagement.userauthservice.enums.AccountStatus;
import rw.usermanagement.userauthservice.enums.MaritalStatus;
import rw.usermanagement.userauthservice.enums.ValueOfEnum;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class RequestVerificationOutcome {
    @NotEmpty
    @Email
    private String email;
    @NotBlank()
    @ValueOfEnum(enumClass = MaritalStatus.class, message = "Invalid Account Status")
    private AccountStatus VerificationDecision;
}
