package rw.usermanagement.userauthservice.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rw.usermanagement.userauthservice.Config.ZMapper;
import rw.usermanagement.userauthservice.Dto.request.CreateUserDTO;
import rw.usermanagement.userauthservice.Dto.request.ResetPasswordConfirmation;
import rw.usermanagement.userauthservice.Dto.response.UserDTO;
import rw.usermanagement.userauthservice.Exception.CustomException;
import rw.usermanagement.userauthservice.Exception.ResourceNotFoundException;
import rw.usermanagement.userauthservice.Model.User;
import rw.usermanagement.userauthservice.Model.UserProfile;
import rw.usermanagement.userauthservice.Repository.UserProfileRepository;
import rw.usermanagement.userauthservice.Repository.UserRepository;
import rw.usermanagement.userauthservice.enums.AccountStatus;

import java.util.Random;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;
    @Autowired private UserProfileRepository userProfileRepository;
    //@Autowired private UserProfileService userProfileService;
    //@Autowired private  JavaMailSender javaMailSender;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private ZMapper Mapper;



    public UserDTO createUser(CreateUserDTO dto) {
        User user = Mapper.toEntity(dto);
        user.setStatus(AccountStatus.UNVERIFIED);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user = userRepository.save(user);
        UserProfile userProfile =new UserProfile();
        userProfile.setFirstName(dto.getFirstName());
        userProfile.setLastName(dto.getLastName());
        userProfile.setUser(user);
        userProfileRepository.save(userProfile);
        return Mapper.toDTO(user);
    }

    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id "+id+" NotFound", HttpStatus.NOT_FOUND));
    }

    public User findByEmail(String email) {

       User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new CustomException("email Not found" + email, HttpStatus.NOT_FOUND);
        }else{
            return  user;
        }
    }

    public boolean userExists(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    public void resetPassword(ResetPasswordConfirmation request) {
        User user = userRepository.findByEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
       // Send Email confirming password reset
    }

    public void resetPassword(String email) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
//        String newPassword = generateRandomPassword();
//        user.setPassword(passwordEncoder.encode(newPassword));
//        userRepository.save(user);
//        sendPasswordResetEmail(user.getEmail(), newPassword);
    }

    private String generateRandomPassword() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 8;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }



}
