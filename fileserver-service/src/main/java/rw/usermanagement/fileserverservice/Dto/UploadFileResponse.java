package rw.usermanagement.fileserverservice.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class UploadFileResponse {
    private String fileName;
    private String url;
}
