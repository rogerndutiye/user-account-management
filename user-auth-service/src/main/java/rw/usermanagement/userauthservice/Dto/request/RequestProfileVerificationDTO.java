package rw.usermanagement.userauthservice.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.usermanagement.userauthservice.enums.IdentityType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestProfileVerificationDTO {
    @NotNull(message = "Identity type is mandatory")
    private IdentityType identityType;

    @NotBlank(message = "Identity number is mandatory")
    private String identityNumber;
    @NotBlank()
    private String officialDocument;
}
