package com.expensemanager.expensemanager.controller;

import com.expensemanager.expensemanager.Dto.AuthDto;
import com.expensemanager.expensemanager.Dto.AuthResponseDto;
import com.expensemanager.expensemanager.model.Role;
import com.expensemanager.expensemanager.model.UserEntity;
import com.expensemanager.expensemanager.repository.RoleRepository;
import com.expensemanager.expensemanager.repository.UserRepository;
import com.expensemanager.expensemanager.responseObject.AuthResponseObject;
import com.expensemanager.expensemanager.responseObject.StringResponseObject;
import com.expensemanager.expensemanager.security.JWTGenerator;
import com.expensemanager.expensemanager.utils.RegexConstants;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity<StringResponseObject> register(@RequestBody AuthDto registerDto) {
        String email = registerDto.getEmail();

        Pattern emailFormat = Pattern.compile(RegexConstants.EMAIL);
        String reponseMessage = null;
        if(userRepository.existsByEmail(email)) {
            reponseMessage = "Email is taken.";
        } else if(email.length() < 5) {
            reponseMessage = "Email must be at least 5 characters.";
        } else if(!emailFormat.matcher(email).find()) {
            reponseMessage = "Invalid email format.";
        }

        if(Strings.hasText(reponseMessage)) {
            StringResponseObject responseObject = new StringResponseObject(HttpStatus.BAD_REQUEST.value(), reponseMessage);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }

        String password = registerDto.getPassword();
        String validatePassword = getValidationErrorMessage(password);
        if(Strings.hasText(validatePassword)) {
            StringResponseObject responseObject = new StringResponseObject(HttpStatus.BAD_REQUEST.value(), validatePassword);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity(email, passwordEncoder.encode(registerDto.getPassword()));

        Role roles = roleRepository.findByName("USER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("USER");
                    return roleRepository.save(newRole);
                });;
        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);

        StringResponseObject responseObject = new StringResponseObject(HttpStatus.OK.value(), "User registered successfully");
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseObject> login(@RequestBody AuthDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);

        AuthResponseObject responseObject = new AuthResponseObject(HttpStatus.OK.value(), new AuthResponseDto(token));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    public String getValidationErrorMessage(String password) {
        Pattern passwordDigit = Pattern.compile(RegexConstants.PASSWORD_DIGIT);
        Pattern passwordLowercase = Pattern.compile(RegexConstants.PASSWORD_LOWERCASE);
        Pattern passwordUppercase = Pattern.compile(RegexConstants.PASSWORD_UPPERCASE);
        Pattern passwordSpecial = Pattern.compile(RegexConstants.PASSWORD_SPECIAL);

        if(password.length() < 8) {
            return "Password must be at least 8 characters.";
        }
        if(!passwordDigit.matcher(password).find()) {
            return "Email must contain at least one digit";
        }
        if(!passwordLowercase.matcher(password).find()) {
            return "Email must contain at least one lowercase letter.";
        }
        if(!passwordUppercase.matcher(password).find()) {
            return "Email must contain at least one uppercase letter.";
        }
        if(!passwordSpecial.matcher(password).find()) {
            return "Email must contain at least one special character (!@#$%^&*).";
        }

        return null;
    }
}
