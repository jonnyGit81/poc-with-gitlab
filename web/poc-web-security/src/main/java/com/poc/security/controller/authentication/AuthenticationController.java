package com.poc.security.controller.authentication;

import com.poc.db.model.AdminUser;
import com.poc.db.model.AdminUserBuilder;
import com.poc.db.repository.AdminUserRepository;
import com.poc.security.controller.authentication.form.LoginForm;
import com.poc.security.controller.authentication.form.SignupForm;
import com.poc.security.service.UserDetailsImpl;
import com.poc.support.dto.ApiResponse;
import com.poc.support.dto.AuthenticationInfo;
import com.poc.support.jwt.JwtParser;
import com.poc.support.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

//@CrossOrigin -> are using global cors, @See websecurity config
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AdminUserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtParser jwtUtils;


    @PostMapping(value = "/signin",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest,
                                              HttpServletRequest request) throws MethodArgumentNotValidException {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(((UserDetailsImpl) authentication.getPrincipal()).getUsername());

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        AdminUser adminUser = userDetails.getAdminUser();
        AuthenticationInfo authenticationInfo =
                AuthenticationInfo.of()
                        .token(jwt)
                        .userId(adminUser.id)
                        .userName(adminUser.getName())
                        .email(adminUser.getEmail())
                        .accessLevel(adminUser.getAccessLevel())
                        .makerCheckerRole(adminUser.getMakerCheckerRole())
                        .grantedAuthorities(
                                Optional.ofNullable(userDetails.getAuthorities())
                                        .map(authorities -> authorities.stream()
                                                .filter(Objects::nonNull)
                                                .map(GrantedAuthority::getAuthority)
                                                .distinct()
                                                .collect(Collectors.toList()))
                                        .orElse(new ArrayList<>()))
                        .grantedModules(userDetails.getGrantedModules())
                        .grantedUrls(userDetails.getGrantedUrls())
                        .build();

        LogUtil.INFO.apply(LOG, adminUser.getName() + " has successful login");

        return ResponseEntity.ok(
                ApiResponse.of(request, HttpStatus.OK)
                        .data(authenticationInfo)
                        .message("LOGIN SUCCESS")
                        .build());
    }


    @PostMapping(value = "/signup",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupForm signUpRequest, HttpServletRequest request) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.of(request, HttpStatus.CONFLICT)
                            .message("Email is already in use!")
                            .build());
        }

        // Create new user's account
        AdminUser user = new AdminUserBuilder()
                .withAccessLevel(AdminUser.AccessLevel.USER.name())
                .withEmail(signUpRequest.getEmail())
                .withName(signUpRequest.getName())
                .withPassword(encoder.encode(signUpRequest.getPassword()))
                .withStatus(AdminUser.Status.ACTIVE)
                .withAccessLevel(signUpRequest.getAccessLevel())
                .withMakerCheckerRole(AdminUser.MakerCheckerRole.valueOf(signUpRequest.getMakerCheckerRole()))
                .build();

        userRepository.save(user);

        LogUtil.INFO.apply(LOG, user.getEmail() + " has successful created");

        return ResponseEntity.ok(
                ApiResponse.of(request, HttpStatus.OK)
                        .message("Login success")
        );
    }

}
