package rw.usermanagement.userauthservice.Dto.response;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class ApiResponse<T> {
    private Integer statusCode;
    private String Message;
    private T Result;
    private T Errors;
    private String timestamp = LocalDateTime.now().toString();


    public ApiResponse(int statusCode, String message, T result,T Errors) {
        this.statusCode = statusCode;
        this.Message = message;
        this.Result = result;
        this.Errors = Errors;
        this.timestamp = LocalDateTime.now().toString();
    }

}