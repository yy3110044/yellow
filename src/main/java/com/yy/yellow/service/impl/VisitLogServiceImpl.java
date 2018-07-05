package com.yy.yellow.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.yy.yellow.mapper.VisitLogMapper;
import com.yy.yellow.po.VisitLog;
import com.yy.yellow.service.VisitLogService;
import com.yy.yellow.util.QueryCondition;

@Repository("visitLogService")
@Transactional
public class VisitLogServiceImpl implements VisitLogService {
    @Autowired
    private VisitLogMapper mapper;

    @Override
    public void add(VisitLog obj) {
        mapper.add(obj);
    }

    @Override
    public void delete(int id) {
        mapper.delete(id);
    }

    @Override
    public void update(VisitLog obj) {
        mapper.update(obj);
    }

    @Override
    public VisitLog find(QueryCondition qc) {
        return mapper.find(qc);
    }

    @Override
    public VisitLog findById(int id) {
        return mapper.findById(id);
    }

    @Override
    public List<VisitLog> query(QueryCondition qc) {
        return mapper.query(qc);
    }

    @Override
    public int getCount(QueryCondition qc) {
        return mapper.getCount(qc);
    }
}
