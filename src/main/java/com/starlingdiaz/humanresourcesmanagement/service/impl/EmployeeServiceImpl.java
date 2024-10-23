/**
 * @author Starling Diaz on 10/22/2024.
 * @Github https://github.com/NSTLRD
 * @Website https://mentorly.blog/
 * @Academy https://www.mentor-ly.com/
 * @version human-resources-management 1.0
 * @since 10/22/2024.
 */

package com.starlingdiaz.humanresourcesmanagement.service.impl;

import com.starlingdiaz.humanresourcesmanagement.dto.EmployeeDTO;
import com.starlingdiaz.humanresourcesmanagement.mapper.EmployeeMapper;
import com.starlingdiaz.humanresourcesmanagement.mapper.EmployeeMapperImpl;
import com.starlingdiaz.humanresourcesmanagement.repository.EmployeeRepository;
import com.starlingdiaz.humanresourcesmanagement.service.EmployeeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    public static final String REGISTER_EMPLOYEE = "register_employee";
    public static final String UPDATE_EMPLOYEE = "update_employee";
    private final EmployeeMapper employeeMapper;
    @PersistenceContext
    private EntityManager entityManager;

    private final EmployeeRepository employeeRepository;

    /**
     * Register an employee
     * @param employeeDTO
     * name
     * position
     * department
     * salary
     */
    @Override
    public void registerEmployee(EmployeeDTO employeeDTO) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(REGISTER_EMPLOYEE);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, Double.class, ParameterMode.IN);

        query.setParameter(1, employeeDTO.getName());
        query.setParameter(2, employeeDTO.getPosition());
        query.setParameter(3, employeeDTO.getDepartment());
        query.setParameter(4, employeeDTO.getSalary());
        query.execute();
    }

    @Override
    public void updateEmployee(Long id, EmployeeDTO employeeDTO) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(UPDATE_EMPLOYEE);
        query.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, Double.class, ParameterMode.IN);

        query.setParameter(1, id);
        query.setParameter(2, employeeDTO.getName());
        query.setParameter(3, employeeDTO.getPosition());
        query.setParameter(4, employeeDTO.getDepartment());
        query.setParameter(5, employeeDTO.getSalary());
        query.execute();

    }

    @Override
    public void deleteEmployee(Long id) {
        this.entityManager.createQuery("DELETE FROM Employee e WHERE e.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
      return employeeRepository.findAll()
              .stream().map(employeeMapper::toDTO)
              .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        return this.entityManager.find(EmployeeDTO.class, id);
    }
}
