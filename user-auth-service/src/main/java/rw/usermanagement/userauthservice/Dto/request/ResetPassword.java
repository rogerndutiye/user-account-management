package rw.usermanagement.userauthservice.Dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Getter
@Setter
public class ResetPassword {
    @NotEmpty
    @Email
    private String email;
}
