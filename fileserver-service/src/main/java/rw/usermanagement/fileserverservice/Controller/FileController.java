package rw.usermanagement.fileserverservice.Controller;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import rw.usermanagement.fileserverservice.Dto.UploadFileResponse;
import rw.usermanagement.fileserverservice.services.ImageService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping(value = "api/files")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private ImageService fileStorageService;
    @GetMapping(
            value = "viewDocument/{imageName:.+}",
            produces = {MediaType.APPLICATION_PDF_VALUE}
    )
    public @ResponseBody byte[] getFileWithMediaType(@PathVariable(name = "imageName") String fileName) throws IOException {
        return this.fileStorageService.getFileWithMediaType(fileName);
    }

    @GetMapping(
            value = "viewImage/{imageName:.+}",
            produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_GIF_VALUE,MediaType.IMAGE_PNG_VALUE}
    )
    public @ResponseBody byte[] getImageWithMediaType(@PathVariable(name = "imageName") String fileName) throws IOException {
        return this.fileStorageService.getFileWithMediaType(fileName);
    }


    //produces = {MediaType.APPLICATION_PDF_VALUE}
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {

        String fileName = fileStorageService.storeFile(file);
        String fileUri=null;
        String extension = FilenameUtils.getExtension(fileName);
        if(extension.equals("pdf")){
            fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("api/files/viewDocument/")
                    .path(fileName)
                    .toUriString();
            return ResponseEntity.ok(new UploadFileResponse(fileName,fileUri));
        }else{
            fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("api/files/viewImage/")
                    .path(fileName)
                    .toUriString();
            return ResponseEntity.ok(new UploadFileResponse(fileName,fileUri));
        }
    }

}
