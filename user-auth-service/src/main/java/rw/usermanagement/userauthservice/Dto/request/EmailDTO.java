package rw.usermanagement.userauthservice.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {

    @NotBlank
    private String subject;

    @NotBlank
    @Email
    private String recipient;

    @NotBlank
    private String body;

    @NotBlank
    private String sender;

    private String attachment;
    private String cc;
    private String bcc;


}