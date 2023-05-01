package rw.usermanagement.userauthservice.Dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestOTPVerification {
    @NotEmpty
    @Email
    private String email;
    @Size(min = 4, max = 6)
    private String otp;
}
