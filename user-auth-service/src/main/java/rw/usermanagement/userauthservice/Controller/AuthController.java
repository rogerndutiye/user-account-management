package rw.usermanagement.userauthservice.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rw.usermanagement.userauthservice.Dto.request.CreateUserDTO;
import rw.usermanagement.userauthservice.Dto.request.RequestLogin;
import rw.usermanagement.userauthservice.Dto.request.RequestOTPVerification;
import rw.usermanagement.userauthservice.Dto.response.ApiResponse;
import rw.usermanagement.userauthservice.Dto.response.UserDTO;
import rw.usermanagement.userauthservice.Exception.CustomException;
import rw.usermanagement.userauthservice.Repository.UserRepository;
import rw.usermanagement.userauthservice.Service.UserService;
import rw.usermanagement.userauthservice.security.AppUserDetailsService;
import rw.usermanagement.userauthservice.security.JwtService;

import javax.validation.Valid;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static rw.usermanagement.userauthservice.Utils.Utils.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {


    private final JwtService jwtTokenUtil;
    private final UserService userService;
    private final AppUserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RedisTemplate<String, String> redisTemplate;

    private static final String OTP_PREFIX = "otp:";
    private static final long OTP_EXPIRATION = 300;

    @PostMapping(path = "/register")
    public ResponseEntity<ApiResponse<?>> registerUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        log.info("controller auth: register user :: [{}] ::", createUserDTO.getEmail());
        validateUserRegister(createUserDTO);

        if (userService.userExists(createUserDTO.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Email address already in use!", null,null));
        }
        UserDTO savedUser = userService.createUser(createUserDTO);
        return ResponseEntity.ok(new ApiResponse<>(201, "User registered successfully, ",savedUser,null));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@Valid @RequestBody RequestLogin request) throws Exception {
        log.info("controller auth: login user :: [{}] ::", request.getEmail());

        authenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        String otp = GenerateOtp();
        redisTemplate.opsForValue().set(OTP_PREFIX + request.getEmail(), otp, OTP_EXPIRATION, TimeUnit.SECONDS);
        //sendOtpByEmail(loginRequest.getEmail(), otp);
        return ResponseEntity.ok(new ApiResponse<>(200, "OTP sent successfully"+otp, userService.getUserInfo(request.getEmail()),null));
    }

    @PostMapping("/otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody RequestOTPVerification request) throws Exception {
        log.info("controller auth: otp validation user :: [{}] ::", request.getEmail());

        //String storedOtp = redisTemplate.opsForValue().get(request.getEmail());
        if (verifyOtp(request.getEmail(), request.getOtp())) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new ApiResponse<>(200, "Authenticated", token,null));
        }
        else{
            throw new CustomException("Invalid or Expired OTP",HttpStatus.UNAUTHORIZED);
        }
    }


    private void validateUserRegister(CreateUserDTO createUserDTO) {
        log.info("user registration VALIDATION request for email :: {}", createUserDTO.getEmail());
        if (!doesBothStringMatch(createUserDTO.getConfirmPassword(), createUserDTO.getPassword())) {
            throw new CustomException(PASSWORD_NOT_MATCH_MSG, HttpStatus.BAD_REQUEST);
        }
        log.info(" validation for user registration is successful, request for email :: {}", createUserDTO.getEmail());
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new CustomException("User is disabled",HttpStatus.LOCKED);
        } catch (BadCredentialsException e) {
            throw new CustomException("Invalid Credentials",HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw new CustomException("Invalid Credentials",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public boolean verifyOtp(String email, String otp) {
        String key = OTP_PREFIX + email;
        String cachedOtp = redisTemplate.opsForValue().get(key);
        if (cachedOtp != null && cachedOtp.equals(otp)) {
            // delete the OTP from Redis cache after successful verification
            redisTemplate.delete(key);
            return true;
        }
        return false;
    }


}
