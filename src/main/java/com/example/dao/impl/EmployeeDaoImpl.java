package com.example.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.EmployeeDAO;
import com.example.entity.Employee;

@Repository
public class EmployeeDaoImpl implements EmployeeDAO
{
	//Using OpenJPA Implementation
	@PersistenceContext(unitName="Spring4MVCOpenJPAdb")	
	private EntityManager em;	

	@Transactional
	public void addEmployee(Employee employee) {
		em.persist(employee);
	}

	@Transactional
	public List<Employee> getAllEmployees() {
		/*Query q = em.createNamedQuery("findAllEmployees");//Named Query
		return q.getResultList();*/
							//OR
		Query q1 = em.createQuery("select e from Employee e");//Simple Query
		return q1.getResultList();
	}

	@Transactional
	public void deleteEmployee(Integer employeeId) {
		em.remove(em.find(Employee.class,employeeId));
	}
}