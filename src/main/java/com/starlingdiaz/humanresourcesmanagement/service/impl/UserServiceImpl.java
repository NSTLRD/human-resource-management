/**
 * @author Starling Diaz on 10/20/2024.
 * @Github https://github.com/NSTLRD
 * @Website https://mentorly.blog/
 * @Academy https://www.mentor-ly.com/
 * @version human-resources-management 1.0
 * @since 10/20/2024.
 */

package com.starlingdiaz.humanresourcesmanagement.service.impl;

import com.starlingdiaz.humanresourcesmanagement.constants.EmailTemplateName;
import com.starlingdiaz.humanresourcesmanagement.dto.LoginDTO;
import com.starlingdiaz.humanresourcesmanagement.dto.UserDTO;
import com.starlingdiaz.humanresourcesmanagement.dto.response.LoginResponseDTO;
import com.starlingdiaz.humanresourcesmanagement.entity.Token;
import com.starlingdiaz.humanresourcesmanagement.entity.User;
import com.starlingdiaz.humanresourcesmanagement.exception.TokenExpiredException;
import com.starlingdiaz.humanresourcesmanagement.exception.UserNotFoundException;
import com.starlingdiaz.humanresourcesmanagement.repository.TokenRepository;
import com.starlingdiaz.humanresourcesmanagement.repository.UserRepository;
import com.starlingdiaz.humanresourcesmanagement.security.JwTServiceSecurity;
import com.starlingdiaz.humanresourcesmanagement.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String REGISTER_USER = "register_user";
    public static final String UPDATE_USER = "update_user";

    @PersistenceContext
    private EntityManager entityManager;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final EmailServiceImpl emailServiceImpl;
    private final AuthenticationManager authenticationManager;
    private final JwTServiceSecurity jwTServiceSecurity;

    @Value("${mailing.frontend.activation.activationUrl}")
    private String activationUrl;

    @Override
    public UserDTO registerUser(UserDTO userDTO) throws Exception {
        String activationToken = generateActivationCode(6);
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(REGISTER_USER);

        registerUserParameter(query);
        registerUserSetParameter(userDTO, query, activationToken);
        query.execute();

        // Get ID of User
        BigDecimal userIdBigDecimal = (BigDecimal) query.getOutputParameterValue(10);
        Long userId = userIdBigDecimal.longValue();

        // Search the user created
        User newUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found after creation"));

        // save token
        saveTokenForUser(newUser, activationToken);
        sendVerificationEmail(newUser);
        return userDTO;
    }

    private void registerUserSetParameter(UserDTO userDTO, StoredProcedureQuery query, String activationToken) {
        query.setParameter(1, userDTO.getName());
        query.setParameter(2, userDTO.getLastName());
        query.setParameter(3, userDTO.getDni());
        query.setParameter(4, userDTO.getProfession());
        query.setParameter(5, userDTO.getAddress());
        query.setParameter(6, userDTO.getCountry());
        query.setParameter(7, userDTO.getEmail());
        query.setParameter(8, passwordEncoder.encode(userDTO.getPassword()));
        query.setParameter(9, activationToken);
    }

    private static void registerUserParameter(StoredProcedureQuery query) {
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(8, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(9, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(10, BigDecimal.class, ParameterMode.OUT);
    }

    private void saveTokenForUser(User user, String tokenValue) {
        Token token = Token.builder()
                .token(tokenValue)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(45))
                .user(user)
                .build();

        // Guardar el token en la base de datos
        tokenRepository.save(token);
    }

    private void sendVerificationEmail(User user) throws MessagingException {
        emailServiceImpl.sendEmail(
                user.getEmail(),
                user.FullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                user.getToken(),
                "Activate your account"
        );
    }

    @Override
    public void updateUser(Long id, UserDTO userDTO){
        // Call the stored procedure using EntityManager
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(UPDATE_USER);
        query.registerStoredProcedureParameter(1, Long.class,   ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);

        query.setParameter(1, id);
        query.setParameter(2, userDTO.getName());
        query.setParameter(3, userDTO.getDni());
        query.setParameter(4, userDTO.getProfession());
        query.setParameter(5, userDTO.getAddress());
        query.setParameter(6, userDTO.getCountry());
        query.execute();

    }


    @Override
    public String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(45))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    @Override
    public String generateActivationCode(int length) {
        String characters = "123456789";
        StringBuilder result = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            result.append(characters.charAt(randomIndex));
        }
        return result.toString();
    }


    @Override
    public LoginResponseDTO loginAuthenticate(LoginDTO loginDto) {
        if(loginDto.getEmail() == null || loginDto.getEmail().isEmpty()){
            throw new RuntimeException("Email is required");
        }

        if(loginDto.getPassword() == null || loginDto.getPassword().isEmpty()){
            throw new RuntimeException("Password is required");
        }

        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );
            var user = (User) auth.getPrincipal();
            var claims = new HashMap<String, Object>();
            claims.put("name", user.getName());
            claims.put("email", user.getEmail());
            claims.put("id", user.getId());
            var jwtToken = jwTServiceSecurity.generateToken(claims, user);
            return LoginResponseDTO.builder().token(jwtToken).build();
        } catch (AuthenticationException e){
            if(userRepository.findByEmail(loginDto.getEmail()).isPresent()){
                throw new RuntimeException("Password is incorrect");
            } else {
                throw new UserNotFoundException("No user found with the provided email address");
            }
        }
    }

    @Override
    public String activateAccount(String token) throws MessagingException, TokenExpiredException {
        Optional<Token> tokenOptional = Optional.ofNullable(tokenRepository.findByToken(token));
        if(!tokenOptional.isPresent()){
            throw new TokenExpiredException("Invalid Token");
        }

        Token savedToken = tokenOptional.get();
        if(LocalDateTime.now().isAfter(savedToken.getExpiredAt())){
            sendVerificationEmail(savedToken.getUser());
            throw new TokenExpiredException("Activation token has expired. A new token has been sent to the email address.");
        }

        User user = savedToken.getUser();
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        user.setEnabled(true);
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
        return token;
    }
}