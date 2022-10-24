package indi.crisp.mylin.service.impl;

import indi.crisp.mylin.abnormal.AppAbnormal;
import indi.crisp.mylin.config.AppEnum;
import indi.crisp.mylin.dao.LeaveDAO;
import indi.crisp.mylin.pojo.Leave;
import indi.crisp.mylin.service.LeaveService;
import indi.crisp.mylin.util.Feedback;
import indi.crisp.mylin.util.MybatisUtil;
import indi.crisp.mylin.util.Paginate;

public class LeaveServiceImpl implements LeaveService {
    @Override
    public int insertLeave(Leave leave) throws AppAbnormal {
        if ( leave == null ) {
            throw new AppAbnormal(AppEnum.ERROR_LEAVE_NULL);
        }
        var session = MybatisUtil.getSqlSession();
        try {
            var leaveDAO = session.getMapper(LeaveDAO.class);
            if ( leaveDAO.insertLeave(leave) > 0) {
                session.commit();
                return AppEnum.LEAVE_INSERT_YES.getCode();
            }
            return AppEnum.LEAVE_INSERT_NO.getCode();
        } finally {
            session.close();
        }
    }

    @Override
    public int updateLeave(Leave leave) throws AppAbnormal {
        if ( leave == null ) {
            throw new AppAbnormal(AppEnum.ERROR_LEAVE_NULL);
        }
        var session = MybatisUtil.getSqlSession();
        try {
            var leaveDAO = session.getMapper(LeaveDAO.class);
            if ( leaveDAO.updateLeaveAuto(leave) > 0) {
                session.commit();
                return AppEnum.LEAVE_UPDATE_NO.getCode();
            }
            return AppEnum.LEAVE_UPDATE_NO.getCode();
        } finally {
            session.close();
        }
    }

    @Override
    public Feedback<Paginate<Leave>> findList(int start, int limit) throws AppAbnormal {
        if ( start < 0 || limit < 0 ) {
            throw new AppAbnormal(AppEnum.ERROR_LEAVE_FIND_LIST);
        }
        var session = MybatisUtil.getSqlSession();
        try {
            var leaveDAO = session.getMapper(LeaveDAO.class);
            var leaves = leaveDAO.findLeaveList(start, limit);
            var count = leaveDAO.countAll();
            return new Feedback<Paginate>()
                    .setResult(new Paginate<Leave>()
                            .setList(leaves)
                            .setCountAll(count)
                            .setIndex(start)
                            .setStep(leaves.size()));
        } finally {
            session.close();
        }
    }

    @Override
    public Feedback<Paginate<Leave>> findEmpList(int eid, int start, int limit) throws AppAbnormal {
        if ( eid < 0 || start < 0 || limit < 0 ) {
            throw new AppAbnormal(AppEnum.ERROR_LEAVE_FIND_LIST);
        }
        var session = MybatisUtil.getSqlSession();
        try {
            var leaveDAO = session.getMapper(LeaveDAO.class);
            var leaves = leaveDAO.findEmpIdList(eid, start, limit);
            return new Feedback<Paginate>()
                    .setResult(new Paginate<Leave>()
                            .setList(leaves)
                            .setIndex(start)
                            .setStep(leaves.size()));
        } finally {
            session.close();
        }
    }

    @Override
    public Feedback<Paginate<Leave>> findDeptLeave(int did, int start, int limit) throws AppAbnormal {
        if ( did < 0 || start < 0 || limit < 0 ) {
            throw new AppAbnormal(AppEnum.ERROR_LEAVE_FIND_LIST);
        }
        var session = MybatisUtil.getSqlSession();
        try {
            var leaveDAO = session.getMapper(LeaveDAO.class);
            var leaves = leaveDAO.findDeptLeave(did, start, limit);
            return new Feedback<Paginate>()
                    .setResult(new Paginate<Leave>()
                            .setList(leaves)
                            .setIndex(start)
                            .setStep(leaves.size()));
        } finally {
            session.close();
        }
    }

    @Override
    public Feedback<Paginate<Leave>> findToId(int tid, int start, int limit) throws AppAbnormal {
        if ( tid < 0 || start < 0 || limit < 0 ) {
            throw new AppAbnormal(AppEnum.ERROR_LEAVE_FIND_LIST);
        }
        var session = MybatisUtil.getSqlSession();
        try {
            var leaveDAO = session.getMapper(LeaveDAO.class);
            var leaves = leaveDAO.findToId(tid, start, limit);
            return new Feedback<Paginate>()
                    .setResult(new Paginate<Leave>()
                            .setList(leaves)
                            .setIndex(start)
                            .setStep(leaves.size()));
        } finally {
            session.close();
        }
    }

}