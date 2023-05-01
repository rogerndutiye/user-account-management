package rw.usermanagement.userauthservice.Dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class ResetPasswordConfirmation {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String otp;

    @NotEmpty
    @Size(min = 8, max = 30)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must contain at least 8 characters, including one uppercase letter, one lowercase letter, one digit, and one special character.")
    private String password;

    @NotEmpty
    @Size(min = 8, max = 30)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must contain at least 8 characters, including one uppercase letter, one lowercase letter, one digit, and one special character.")
    private String ConfirmPassword;
}