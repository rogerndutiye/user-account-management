package rw.usermanagement.fileserverservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import rw.usermanagement.fileserverservice.property.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class FileserverServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileserverServiceApplication.class, args);
	}

}
