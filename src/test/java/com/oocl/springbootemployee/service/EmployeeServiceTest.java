package com.oocl.springbootemployee.service;

import com.oocl.springbootemployee.exception.EmployeeAgeNotValidException;
import com.oocl.springbootemployee.exception.EmployeeInactiveException;
import com.oocl.springbootemployee.exception.EmployeeSalaryNotValidException;
import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import com.oocl.springbootemployee.repository.IEmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_the_given_employees_when_getAllEmployees() {
        //given
        IEmployeeRepository mockedEmployeeRepository = mock(IEmployeeRepository.class);
        when(mockedEmployeeRepository.getAll()).thenReturn(List.of(new Employee(1, "Lucy", 18, Gender.FEMALE, 8000.0)));
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);

        //when
        List<Employee> allEmployees = employeeService.getAllEmployees();

        //then
        assertEquals(1, allEmployees.size());
        assertEquals("Lucy", allEmployees.get(0).getName());
    }

    @Test
    void should_return_the_created_employee_when_create_given_a_employee() {
        //given
        IEmployeeRepository mockedEmployeeRepository = mock(IEmployeeRepository.class);
        Employee lucy = new Employee(1, "Lucy", 18, Gender.FEMALE, 8000.0);
        when(mockedEmployeeRepository.addEmployee(any())).thenReturn(lucy);
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);

        //when
        Employee createdEmployee = employeeService.create(lucy);

        //then
        assertEquals("Lucy", createdEmployee.getName());
    }

    @Test
    void should_throw_employee_age_not_valid_exception_when_create_employee_given_with_age_less_than_18() {
        // Given
        Employee employee = new Employee(1, "Tom", 1, Gender.FEMALE, 8000.0);

        // When
        // Then
        assertThrows(EmployeeAgeNotValidException.class, () -> employeeService.create(employee));
        verify(employeeRepository, never()).addEmployee(any());
    }

    @Test
    void should_throw_employee_age_not_valid_exception_when_create_employee_given_with_age_greater_than_65() {
        // Given
        Employee employee = new Employee(1, "Tom", 20000, Gender.FEMALE, 21000.0);

        // When
        // Then
        assertThrows(EmployeeAgeNotValidException.class, () -> employeeService.create(employee));
        verify(employeeRepository, never()).addEmployee(any());
    }

    @Test
    void should_throw_employee_salary_not_valid_exception_when_create_employee_given_with_age_greater_than_30_and_salary_less_than_20000() {
        // Given
        Employee employee = new Employee(1, "Tom", 31, Gender.FEMALE, 1.0);

        // When
        // Then
        assertThrows(EmployeeSalaryNotValidException.class, () -> employeeService.create(employee));
        verify(employeeRepository, never()).addEmployee(any());
    }

    @Test
    void should_created_employee_active_when_create_employee_given_employee() {
        // Given
        Employee employee = new Employee(1, "Joe", 22, Gender.MALE, 10000.0);

        // When
        employeeService.create(employee);

        // Then
        assertEquals(true, employee.getActive());
        verify(employeeRepository, times(1)).addEmployee(argThat(Employee::getActive));
    }

    @Test
    void should_throw_inactive_exception_when_update_employee_given_inactive_employee() {
        // Given
        int id = 1;
        Employee updatedEmployee = new Employee(1, "Tom", 30, Gender.MALE, 30000.0);

        // When
        when(employeeRepository.getEmployeeById(any())).thenReturn(new Employee(1, "Tom", 30, Gender.MALE, 30000.0, false));

        // Then
        assertThrows(EmployeeInactiveException.class, () -> employeeService.update(1, updatedEmployee));
        verify(employeeRepository, never()).updateEmployee(any(), any());
    }

}
