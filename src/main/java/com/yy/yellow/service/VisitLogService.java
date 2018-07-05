package com.yy.yellow.service;

import java.util.List;
import com.yy.yellow.po.VisitLog;
import com.yy.yellow.util.QueryCondition;

public interface VisitLogService {
    void add(VisitLog obj);
    void delete(int id);
    void update(VisitLog obj);
    VisitLog find(QueryCondition qc);
    VisitLog findById(int id);
    List<VisitLog> query(QueryCondition qc);
    int getCount(QueryCondition qc);
}
