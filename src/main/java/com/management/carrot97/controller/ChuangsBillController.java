package com.management.carrot97.controller;

import com.github.pagehelper.PageInfo;
import com.management.carrot97.bean.ChuangsBill;
import com.management.carrot97.service.ChuangsBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@RequestMapping("/chuangsBill")
@Controller
public class ChuangsBillController {

    @Autowired
    ChuangsBillService chuangsBillService;

    @GetMapping(value = "/list")
    public String list(@RequestParam("pageNumber") Integer pageNumber,
                       Map<String, Object> map) {
        PageInfo<ChuangsBill> pageInfo = chuangsBillService.getPage(pageNumber);
        map.put("pageInfo", pageInfo);
        return "chuangsBill/list";
    }


}
