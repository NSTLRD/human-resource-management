/**
 * @author Starling Diaz on 10/21/2024.
 * @Github https://github.com/NSTLRD
 * @Website https://mentorly.blog/
 * @Academy https://www.mentor-ly.com/
 * @version human-resources-management 1.0
 * @since 10/21/2024.
 */

package com.starlingdiaz.humanresourcesmanagement.service.impl;

import com.starlingdiaz.humanresourcesmanagement.dto.AdminDTO;
import com.starlingdiaz.humanresourcesmanagement.service.AdminService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    public static final String REGISTER_ADMIN = "register_admin";
    public static final String UPDATE_ADMIN = "update_admin";
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void registerAdmin(AdminDTO adminDTO) {
        // Call the stored procedure using EntityManager
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(REGISTER_ADMIN);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);

        query.setParameter(1, adminDTO.getName());
        query.setParameter(2, adminDTO.getRole());
        query.setParameter(3, adminDTO.getEmail());
        query.execute();

    }

    @Override
    public void updateAdmin(Long id, AdminDTO adminDTO) {
        // Call the stored procedure using EntityManager
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(UPDATE_ADMIN);
        query.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);

        query.setParameter(1, id);
        query.setParameter(2, adminDTO.getName());
        query.setParameter(3, adminDTO.getRole());
        query.setParameter(4, adminDTO.getEmail());
        query.execute();
    }
}
