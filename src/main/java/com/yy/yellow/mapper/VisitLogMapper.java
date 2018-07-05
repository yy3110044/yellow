package com.yy.yellow.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.yy.yellow.po.VisitLog;
import com.yy.yellow.util.QueryCondition;

@Mapper
public interface VisitLogMapper {
    void add(VisitLog obj);
    void delete(int id);
    void update(VisitLog obj);
    VisitLog find(QueryCondition qc);
    VisitLog findById(int id);
    List<VisitLog> query(QueryCondition qc);
    int getCount(QueryCondition qc);
}
