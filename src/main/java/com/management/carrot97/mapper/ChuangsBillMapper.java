package com.management.carrot97.mapper;

import com.management.carrot97.bean.ChuangsBill;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChuangsBillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChuangsBill record);

    int insertSelective(ChuangsBill record);

    ChuangsBill selectByPrimaryKey(Integer id);

    /*分页查询*/
    List<ChuangsBill> selectByPage();

    int updateByPrimaryKeySelective(ChuangsBill record);

    int updateByPrimaryKey(ChuangsBill record);
}