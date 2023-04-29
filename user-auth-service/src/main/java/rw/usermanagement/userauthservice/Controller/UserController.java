package rw.usermanagement.userauthservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rw.usermanagement.emailservice.Dto.EmailDto;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;



    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody EmailDto email) {
        // Save the user to the database
        // ...

        // Send an email message to Kafka
        // Send an email message to Kafka
        kafkaTemplate.send("user-signup-topic", email);
        return ResponseEntity.ok("User signed up successfully.");
    }
}
