package rw.usermanagement.userauthservice.Exception;



import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
public class BadRequestException extends RuntimeException{

    protected String message;
    protected HttpStatus status;

    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }

    public BadRequestException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}

