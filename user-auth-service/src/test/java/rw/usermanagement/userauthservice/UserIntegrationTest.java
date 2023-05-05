package rw.usermanagement.userauthservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import rw.usermanagement.userauthservice.Dto.request.CreateUserDTO;
import rw.usermanagement.userauthservice.Dto.request.EmailDto;
import rw.usermanagement.userauthservice.Model.User;
import rw.usermanagement.userauthservice.Repository.UserRepository;
import rw.usermanagement.userauthservice.Service.UserService;
import rw.usermanagement.userauthservice.security.AppUserDetailsService;
import rw.usermanagement.userauthservice.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;


@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired private MockMvc mockMvc;

    @InjectMocks
    private UserService userService;

    @Autowired
    private  JwtService jwtTokenUtil;

    @Autowired
    private  AppUserDetailsService userDetailsService;


    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private KafkaTemplate<String, EmailDto> kafkaTemplate;

    private static final String OTP_PREFIX = "otp:";
    private static final long OTP_EXPIRATION = 300;

    @BeforeEach
    public void setUp() {
        // Clear Redis cache before each test
        // This is necessary to ensure that we get a fresh value from the database
        // instead of a cached value from Redis
        //redisTemplate.getConnectionFactory().getConnection().flushDb();
    }



    @Test
    public void testSignup() throws Exception {

        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"email\": \"test@example.com\",\"confirmPassword\": \"email#2!45QA\", \"password\": \"email#2!45QA\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        User user = userRepository.findByEmail("test@example.com");
        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    public void signUp_DuplicateEmail_ReturnsBadRequest() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"email\": \"test1@example.com\",\"confirmPassword\": \"email#2!45QA\", \"password\": \"email#2!45QA\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Sign up the user again with the same email address
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"email\": \"test1@example.com\",\"confirmPassword\": \"email#2!45QA\", \"password\": \"email#2!45QA\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void signUp_InvalidPassword_ReturnsBadRequest() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"email\": \"test@example.com\",\"confirmPassword\": \"email\", \"password\": \"email\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void testLogin() throws Exception {

        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"email\": \"test5@example.com\",\"confirmPassword\": \"email#2!45QA\", \"password\": \"email#2!45QA\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"email\": \"test5@example.com\", \"password\": \"email#2!45QA\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }



}
