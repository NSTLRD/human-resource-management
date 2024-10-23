/**
 * @author Starling Diaz on 10/20/2024.
 * @Github https://github.com/NSTLRD
 * @Website https://mentorly.blog/
 * @Academy https://www.mentor-ly.com/
 * @version human-resources-management 1.0
 * @since 10/20/2024.
 */

package com.starlingdiaz.humanresourcesmanagement.service;

import com.starlingdiaz.humanresourcesmanagement.dto.LoginDTO;
import com.starlingdiaz.humanresourcesmanagement.dto.UserDTO;
import com.starlingdiaz.humanresourcesmanagement.dto.response.LoginResponseDTO;
import com.starlingdiaz.humanresourcesmanagement.entity.User;
import com.starlingdiaz.humanresourcesmanagement.exception.TokenExpiredException;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDTO registerUser(UserDTO userDTO) throws Exception;
    void updateUser(Long id, UserDTO userDTO);
    String generateAndSaveActivationToken(User user);
    String generateActivationCode(int length);
    LoginResponseDTO loginAuthenticate(LoginDTO loginDto);
    String activateAccount(String token) throws MessagingException, TokenExpiredException;
}
