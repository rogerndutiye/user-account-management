package rw.usermanagement.userauthservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rw.usermanagement.userauthservice.Exception.CustomException;
import rw.usermanagement.userauthservice.Repository.UserRepository;

import java.util.ArrayList;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
   rw.usermanagement.userauthservice.Model.User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new CustomException("email Not found" + email, HttpStatus.NOT_FOUND);
        }
        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}