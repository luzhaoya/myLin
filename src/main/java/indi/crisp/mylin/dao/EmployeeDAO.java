package indi.crisp.mylin.dao;

import indi.crisp.mylin.abnormal.AppAbnormal;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import indi.crisp.mylin.pojo.Employee;

public interface EmployeeDAO {

	Employee findEmployeeByID(@Param("empno") int empno);
	List<Employee> findEmployeeList(@Param("start") int start, @Param("count") int count);
	Integer insertEmployee(@Param("employee") Employee employee);
	Integer updateEmployee(@Param("employee") Employee employee);
	Integer updateEmployeeAuto(@Param("employee") Employee employee);
	Integer deleteEmployee(@Param("empno") int empno);
	Integer countAll();
	// 根据账号查找员工
	Employee findEmployeebyAccount(@Param("account") String account) throws AppAbnormal;
}
