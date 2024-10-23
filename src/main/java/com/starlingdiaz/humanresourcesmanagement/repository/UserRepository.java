/**
 * @author Starling Diaz on 10/20/2024.
 * @Github https://github.com/NSTLRD
 * @Website https://mentorly.blog/
 * @Academy https://www.mentor-ly.com/
 * @version human-resources-management 1.0
 * @since 10/20/2024.
 */

package com.starlingdiaz.humanresourcesmanagement.repository;

import com.starlingdiaz.humanresourcesmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);

    // This method links to the 'register_user' stored procedure
    @Procedure(procedureName = "register_user")
    void registerUser(String p_name, String p_lastname, String p_dni, String p_profession, String p_address, String p_country, String p_email, String p_password);

    // This method links to the 'update_user' stored procedure
    @Procedure(procedureName = "update_user")
    void updateUser(Long p_id, String p_name, String p_lastname , String p_dni, String p_profession, String p_address, String p_country);
}
