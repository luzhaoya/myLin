package indi.crisp.mylin.service.impl;

import indi.crisp.mylin.abnormal.AppAbnormal;
import indi.crisp.mylin.config.AppEnum;
import indi.crisp.mylin.dao.DeptDAO;
import indi.crisp.mylin.dao.EmployeeDAO;
import indi.crisp.mylin.pojo.Dept;
import indi.crisp.mylin.service.DeptService;
import indi.crisp.mylin.util.Feedback;
import indi.crisp.mylin.util.MybatisUtil;
import indi.crisp.mylin.util.Paginate;

public class DeptServiceImpl implements DeptService {
    @Override
    public int insertDept(Dept dept) throws AppAbnormal {
        if ( dept == null ) {
            throw new AppAbnormal(AppEnum.ERROR_DEPT_NULL);
        }
        var session = MybatisUtil.getSqlSession();
        try {
            var deptDAO = session.getMapper(DeptDAO.class);
            dept.setDhost(0);
            if ( deptDAO.insertDept(dept) > 0) {
                session.commit();
                return AppEnum.DEPT_INSERT_YES.getCode();
            }
            return AppEnum.DEPT_INSERT_NO.getCode();
        } finally {
            session.close();
        }
    }

    @Override
    public int deleteDept(int dno) throws AppAbnormal {
        if ( dno < 0 ) {
            throw new AppAbnormal(AppEnum.ERROR_DEPT_ID);
        }
        var dept = new Dept();
        dept.setDno(dno);
        dept.setDstatus(1102); //标记为解散状态
        return updateDept(dept);
    }

    @Override
    public int updateDept(Dept dept) throws AppAbnormal {
        if ( dept == null ) {
            throw new AppAbnormal(AppEnum.ERROR_DEPT_NULL);
        }
        var session = MybatisUtil.getSqlSession();
        try {
            var deptDAO = session.getMapper(DeptDAO.class);
            if ( deptDAO.updateDeptAuto(dept) > 0) {
                session.commit();
                return AppEnum.DEPT_UPDATE_YES.getCode();
            }
            return AppEnum.DEPT_UPDATE_NO.getCode();
        } finally {
            session.close();
        }
    }

    @Override
    public Feedback<Paginate<Dept>> findList(int start, int limit) throws AppAbnormal {
        if ( start < 1 || limit < 1 ) {
            throw new AppAbnormal(AppEnum.ERROR_DEPT_FIND_LIST);
        }
        var session = MybatisUtil.getSqlSession();
        try {
            var deptDAO = session.getMapper(DeptDAO.class);
            var count = deptDAO.countAll();
            var deptList = deptDAO.findDeptList(start,limit);
            return new Feedback<>().setResult(new Paginate<Dept>()
                    .setList(deptList).setCountAll(count).setIndex(start).setStep(deptList.size()))
                    .setStatusCode(AppEnum.DEPT_FIND_YES.getCode());
        } finally {
            session.close();
        }
    }

    @Override
    public Feedback<Dept> findDept(int id) throws AppAbnormal {
        if ( id < 0 ) {
            throw new AppAbnormal(AppEnum.ERROR_DEPT_ID);
        }
        var session = MybatisUtil.getSqlSession();
        try {
            var deptDAO = session.getMapper(DeptDAO.class);
            var dept = deptDAO.findDeptByID(id);
            if ( dept == null ) {
                return new Feedback<>().setStatusCode(AppEnum.DEPT_FIND_NO.getCode()).setExplain(null);
            }
            return new Feedback<>().setStatusCode(AppEnum.DEPT_FIND_YES.getCode()).setResult(dept);
        } finally {
            session.close();
        }
    }

    @Override
    public int updateDeptHost(int did, int eid) throws AppAbnormal {
        //原来有人就调整他的职位
        //原来没人就不管直接把角色一改，部门负责人直接换
        var session = MybatisUtil.getSqlSession();
        try {
            var empDAO = session.getMapper(EmployeeDAO.class);
            var deptDAO = session.getMapper(DeptDAO.class);

            var emp1 = empDAO.findDidEmp(did);
            if ( emp1 != null ) {
                //有原来负责人
                emp1.setErole(1); //改角色
                empDAO.updateEmployeeAuto(emp1);
            }

            var emp2 = empDAO.findEmployeeByID(eid);
            var dept = deptDAO.findDeptByID(did);

            dept.setDhost(emp2.getEmpno());
            emp2.setErole(2);

            deptDAO.updateDeptAuto(dept);
            empDAO.updateEmployee(emp2);

            session.commit();
            return 1;
        } finally {
            session.close();
        }
    }
}
