package com.management.carrot97.controller;

import com.management.carrot97.constant.StringConstants;
import com.management.carrot97.utils.textTransform.Transformer;
import com.management.carrot97.utils.textTransform.TransformerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/work")
@Controller
public class TextController {

    @GetMapping(value = "/transform")
    public String getPage() {
        return "workHelper/text_transformation";
    }


    @PostMapping(value = "/transform")
    @ResponseBody
    public String transformString(@RequestParam(value = "type", required = false) String type,
                               @RequestParam(value = "text") String text) {
        Transformer transformer = TransformerFactory.getTransformer(type);
        String result = transformer.transform(text);
        if (result == null) result = StringConstants.TRANSFORMERROR;
        return result;
    }
}
