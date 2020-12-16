package com.management.carrot97.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.carrot97.bean.ChuangsBill;
import com.management.carrot97.constant.BooleanConstants;
import com.management.carrot97.constant.NumberConstants;
import com.management.carrot97.constant.StringConstants;
import com.management.carrot97.mapper.ChuangsBillMapper;
import com.management.carrot97.utils.NumberVerify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChuangsBillService {

    @Autowired
    ChuangsBillMapper chuangsBillMapper;


    /*********************************基本操作类型*******************************/

    public PageInfo<ChuangsBill> getPage(Integer pageNumber) {
        PageHelper.startPage(pageNumber, NumberConstants.BILLPAGESIZE);
        List<ChuangsBill> bills = chuangsBillMapper.selectByPage();
        PageInfo<ChuangsBill> pageInfo = new PageInfo<>(bills);
        return pageInfo;
    }


    /**
     * 添加账单
     * 需要同步，否则不能保证取出的是正确的结余
     */
    private synchronized int addBill(ChuangsBill bill) {
        // 取出上一条账单中的结余
        Double balance = chuangsBillMapper.selectLatestBalance();
        // 计算新的结余
        bill.setBalance(balance - bill.getType() * bill.getAmount());
        // 存储账单并得到反馈
        int result = chuangsBillMapper.insert(bill);
        return result;
    }


    /**
     * 验证账单信息正确性
     */
    private Map<String, Object> verifyBill(ChuangsBill bill) {
        HashMap<String, Object> msg = new HashMap<>();
        msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.AVAILABLE);
        // 验证日期（不为空）
        if (BooleanConstants.AVAILABLE.equals(msg.get(StringConstants.VERIFYSTATUS))
                && bill.getDate() == null) {
            msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.UNAVAILABLE);
            msg.put(StringConstants.ERRORMESSAGE, StringConstants.NULLDATE);
        }

        // 验证收支类型（为-1或1）
        if (BooleanConstants.AVAILABLE.equals(msg.get(StringConstants.VERIFYSTATUS))
                && !NumberVerify.verifyBillType(bill.getType())) {
            msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.UNAVAILABLE);
            msg.put(StringConstants.ERRORMESSAGE, StringConstants.NULLTYPE);
        }

        // 验证金额（不为空，不为负）
        if (BooleanConstants.AVAILABLE.equals(msg.get(StringConstants.VERIFYSTATUS))
                && !NumberVerify.verifyAmount(bill.getAmount())) {
            msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.UNAVAILABLE);
            msg.put(StringConstants.ERRORMESSAGE, StringConstants.ILLEGALAMOUNT);
        }
        return msg;
    }

    /*********************************联合操作类型*******************************/

    /**
     * 验证并添加账单
     * 1.验证账单信息
     * 2.添加账单
     * Map中返回添加状态（成功或失败）和失败信息
     */
    public Map<String, Object> verifyAndAdd(ChuangsBill bill) {
        Map<String, Object> msg = verifyBill(bill);
        if (msg.get(StringConstants.VERIFYSTATUS).equals(BooleanConstants.AVAILABLE)) {
            // 验证通过添加活动
            if (addBill(bill) != 1) {
                msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.UNAVAILABLE);
                msg.put(StringConstants.ERRORMESSAGE, StringConstants.ADDFAILED);
            }
        }
        return msg;

    }


}
