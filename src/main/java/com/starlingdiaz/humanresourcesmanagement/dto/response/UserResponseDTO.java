/**
 * @author Starling Diaz on 10/22/2024.
 * @Github https://github.com/NSTLRD
 * @Website https://mentorly.blog/
 * @Academy https://www.mentor-ly.com/
 * @version human-resources-management 1.0
 * @since 10/22/2024.
 */

package com.starlingdiaz.humanresourcesmanagement.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class UserResponseDTO {

    private String id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private boolean enabled;


    public UserResponseDTO(String id, LocalDateTime created, LocalDateTime modified, LocalDateTime lastLogin, String token, boolean isActive) {
        this.id = id;
        this.created = created;
        this.modified = modified;
        this.lastLogin = lastLogin;
        this.token = token;
        this.enabled = isActive;
    }

}