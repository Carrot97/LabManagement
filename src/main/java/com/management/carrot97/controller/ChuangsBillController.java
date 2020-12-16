package com.management.carrot97.controller;

import com.github.pagehelper.PageInfo;
import com.management.carrot97.bean.ChuangsBill;
import com.management.carrot97.constant.BooleanConstants;
import com.management.carrot97.constant.StringConstants;
import com.management.carrot97.service.ChuangsBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@RequestMapping("/chuangsBill")
@Controller
public class ChuangsBillController {

    @Autowired
    ChuangsBillService chuangsBillService;

    /**
     * 账单分页展示
     */
    @GetMapping(value = "/list")
    public String list(@RequestParam("pageNumber") Integer pageNumber,
                       Map<String, Object> map) {
        PageInfo<ChuangsBill> pageInfo = chuangsBillService.getPage(pageNumber);
        map.put("pageInfo", pageInfo);
        return "chuangsBill/list";
    }

    /*跳转至添加账单页面*/
    @GetMapping(value = "/add")
    public String getAddPage() {
        return "chuangsBill/add";
    }

    /**
     * 提交添加的账单
     * 1.验证并添加账单
     * 2.若成功则重定向至分页展示页面
     * 3.若失败则返回添加账单页面，并回显错误信息
     */
    @PostMapping(value = "/")
    public String addBill(ChuangsBill bill,
                          Map<String, Object> map) {
//        System.out.println("账单添加操作");
        Map<String, Object> msg = chuangsBillService.verifyAndAdd(bill);
        if (BooleanConstants.AVAILABLE.equals(msg.get(StringConstants.VERIFYSTATUS))) {
            // 添加成功返回主页面
            return "redirect:/chuangsBill/list?pageNumber=1";
        } else {
            // 添加失败回显活动信息和错误信息
            map.put(StringConstants.FEEDBACKMSG, msg.get(StringConstants.ERRORMESSAGE));
            map.put("bill", bill);
            return "chuangsBill/add";
        }
    }


}
