/**
 * @author Starling Diaz on 10/21/2024.
 * @Github https://github.com/NSTLRD
 * @Website https://mentorly.blog/
 * @Academy https://www.mentor-ly.com/
 * @version human-resources-management 1.0
 * @since 10/21/2024.
 */

package com.starlingdiaz.humanresourcesmanagement.repository;

import com.starlingdiaz.humanresourcesmanagement.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
