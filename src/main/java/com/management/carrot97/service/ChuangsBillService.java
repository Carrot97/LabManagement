package com.management.carrot97.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.carrot97.bean.ChuangsBill;
import com.management.carrot97.constant.NumberConstants;
import com.management.carrot97.mapper.ChuangsBillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChuangsBillService {

    @Autowired
    ChuangsBillMapper chuangsBillMapper;

    public PageInfo<ChuangsBill> getPage(Integer pageNumber) {
        PageHelper.startPage(pageNumber, NumberConstants.BILLPAGESIZE);
        List<ChuangsBill> bills = chuangsBillMapper.selectByPage();
        PageInfo<ChuangsBill> pageInfo = new PageInfo<>(bills);
        return pageInfo;
    }
}
