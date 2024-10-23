/**
 * @author Starling Diaz on 10/21/2024.
 * @Github https://github.com/NSTLRD
 * @Website https://mentorly.blog/
 * @Academy https://www.mentor-ly.com/
 * @version human-resources-management 1.0
 * @since 10/21/2024.
 */

package com.starlingdiaz.humanresourcesmanagement.service;

import com.starlingdiaz.humanresourcesmanagement.dto.AdminDTO;

public interface AdminService {
    void registerAdmin(AdminDTO adminDTO);
    void updateAdmin(Long id, AdminDTO adminDTO);
}
