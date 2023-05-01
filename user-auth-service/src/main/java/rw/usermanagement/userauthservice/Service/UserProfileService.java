package rw.usermanagement.userauthservice.Service;

import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import rw.usermanagement.userauthservice.Dto.request.CreateProfileDTO;
import rw.usermanagement.userauthservice.Dto.request.RequestProfileVerificationDTO;
import rw.usermanagement.userauthservice.Dto.request.RequestVerificationOutcome;
import rw.usermanagement.userauthservice.Exception.ResourceNotFoundException;
import rw.usermanagement.userauthservice.Model.User;
import rw.usermanagement.userauthservice.Model.UserProfile;
import rw.usermanagement.userauthservice.Repository.UserProfileRepository;
import rw.usermanagement.userauthservice.Repository.UserRepository;
import rw.usermanagement.userauthservice.enums.AccountStatus;
import rw.usermanagement.userauthservice.enums.Gender;
import rw.usermanagement.userauthservice.enums.MaritalStatus;

@Service
public class UserProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    public UserProfile getUserProfileByUserId(int userId) {
        return userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("UserProfile for the user"+userId,HttpStatus.NOT_FOUND));
    }

    public UserProfile createUserProfile(Integer userId, CreateProfileDTO createProfileDTO) {

        UserProfile existingUserProfile = getUserProfileByUserId(userId);
        existingUserProfile.setProfilePhoto(createProfileDTO.getProfilePhoto());
        existingUserProfile.setGender(Gender.valueOf(createProfileDTO.getGender()));
        existingUserProfile.setDateOfBirth(createProfileDTO.getDateOfBirth());
        existingUserProfile.setMaritalStatus(MaritalStatus.valueOf(createProfileDTO.getMaritalStatus()));
        existingUserProfile.setNationality(createProfileDTO.getNationality());
        return userProfileRepository.save(existingUserProfile);
    }

    public UserProfile requestProfileVerification(Integer userId, RequestProfileVerificationDTO requestProfileVerificationDTO) {
        UserProfile existingUserProfile = getUserProfileByUserId(userId);
        existingUserProfile.setIdentityType(requestProfileVerificationDTO.getIdentityType());
        existingUserProfile.setIdentityNumber(requestProfileVerificationDTO.getIdentityNumber());
        existingUserProfile.setOfficialDocument(requestProfileVerificationDTO.getOfficialDocument());
        existingUserProfile.setCompleted(true);
        UserProfile updatedUserProfile = userProfileRepository.save(existingUserProfile);
        User user = userService.findById(updatedUserProfile.getUser().getId());
        user.setStatus(AccountStatus.PENDING_VERIFICATION);
        userRepository.save(user);
        return updatedUserProfile;
    }

    public User AccountVerification(RequestVerificationOutcome requestVerificationOutcome) {
        User user =  userService.findByEmail(requestVerificationOutcome.getEmail());
        user.setStatus(requestVerificationOutcome.getVerificationDecision());
        return userRepository.save(user);
    }
}
