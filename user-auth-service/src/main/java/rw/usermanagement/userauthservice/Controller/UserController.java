package rw.usermanagement.userauthservice.Controller;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rw.usermanagement.userauthservice.Config.ZMapper;
import rw.usermanagement.userauthservice.Dto.request.*;
import rw.usermanagement.userauthservice.Dto.response.ApiResponse;
import rw.usermanagement.userauthservice.Dto.response.UserDTO;
import rw.usermanagement.userauthservice.Dto.response.UserProfileDTO;
import rw.usermanagement.userauthservice.Exception.CustomException;
import rw.usermanagement.userauthservice.Model.User;
import rw.usermanagement.userauthservice.Model.UserProfile;
import rw.usermanagement.userauthservice.Repository.UserRepository;
import rw.usermanagement.userauthservice.Service.UserProfileService;
import rw.usermanagement.userauthservice.Service.UserService;
import rw.usermanagement.userauthservice.security.AppUserDetailsService;
import rw.usermanagement.userauthservice.security.JwtService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

import static rw.usermanagement.userauthservice.Utils.Utils.GenerateOtp;

@Log4j2
@RestController
@RequestMapping("api/users")
public class UserController {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserRepository userRepository;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private AppUserDetailsService userDetailsService;
    @Autowired private JwtService jwtTokenUtil;
    @Autowired private UserService userService;
    @Autowired private UserProfileService userProfileService;
    @Autowired private ZMapper Mapper;

    @Autowired
    private  RedisTemplate<String, String> redisTemplate;

    private static final String PASSRESET_PREFIX = "passreset_otp:";
    private static final long PASSRESET_EXPIRATION = 300;



    @PostMapping("/reset-password")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody ResetPassword request) throws Exception {
        log.info("controller auth: Reset Password user :: [{}] ::", request.getEmail());

        if ( !userService.userExists(request.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Email address "+request.getEmail()+ " does not exist", null,null));
        }
        String otp = GenerateOtp();
        redisTemplate.opsForValue().set(PASSRESET_PREFIX + request.getEmail(), otp, PASSRESET_EXPIRATION, TimeUnit.SECONDS);
        //sendOtpByEmail(loginRequest.getEmail(), otp);
        return ResponseEntity.ok(new ApiResponse<>(200, "Reset Password OTP sent successfully", otp,null));
    }

    @PostMapping("/confirm-reset-password")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody ResetPasswordConfirmation request) throws Exception {
        log.info("controller auth: Reset Password Confirmation  user :: [{}] ::", request.getEmail());

        if ( !userService.userExists(request.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Email address "+request.getEmail()+ " does not exist", null,null));
        }


        if (verifyPassResetOtp(request.getEmail(), request.getOtp())) {

            userService.resetPassword(request);
            return ResponseEntity.ok(new ApiResponse<>(200, "your password has been reset successfully", null,null));
        }
        else{
            throw new CustomException("Invalid or Expired Password Reset OTP",HttpStatus.UNAUTHORIZED);
        }
    }

    private boolean verifyPassResetOtp(String email, String otp) {
        String key = PASSRESET_PREFIX + email;
        String cachedOtp = redisTemplate.opsForValue().get(key);
        if (cachedOtp != null && cachedOtp.equals(otp)) {
            // delete the OTP from Redis cache after successful verification
            redisTemplate.delete(key);
            return true;
        }
        return false;
    }


    @GetMapping("/{id}/profile")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<UserProfileDTO> profile (@Valid @PathVariable Integer id) {
        UserProfile createdUserProfile = userProfileService.getUserProfileByUserId(id);
        return new ResponseEntity<>(Mapper.toUserProfileDTO(createdUserProfile), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/profile")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<UserProfileDTO>completeProfile(@Valid @PathVariable Integer id, @Valid @RequestBody CreateProfileDTO createProfileDTO) {
        UserProfile createdUserProfile = userProfileService.createUserProfile(id,createProfileDTO);
        return new ResponseEntity<>(Mapper.toUserProfileDTO(createdUserProfile), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/verify-account")
    public ResponseEntity<UserProfileDTO> verifyAccount (@Valid @PathVariable Integer id, @Valid @RequestBody RequestProfileVerificationDTO requestProfileVerificationDTO) {
        UserProfile userProfile = userProfileService.requestProfileVerification(id, requestProfileVerificationDTO);
        return new ResponseEntity<>(Mapper.toUserProfileDTO(userProfile), HttpStatus.CREATED);
    }

    @PutMapping("/verify-account/callback")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse> verifyAccountCallback (@Valid @RequestBody RequestVerificationOutcome requestVerificationOutcome) {
        User user = userProfileService.AccountVerification(requestVerificationOutcome);
        return ResponseEntity.ok(new ApiResponse<>(200, "Account Status Set Successfully",Mapper.toDTO(user),null));
    }
    

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization")) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok(new ApiResponse(200, "Logout successful",null,null));
    }
}