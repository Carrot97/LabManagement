package com.management.carrot97.mapper;

import com.management.carrot97.bean.Activity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(Integer id);

    List<Activity> selectRecent();

    /*分页查询*/
    List<Activity> selectPage(String username);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKey(Activity record);
}