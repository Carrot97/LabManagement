package com.management.carrot97.utils.textTransform;


import com.management.carrot97.utils.textTransform.transformerImpl.CommonTransformer;

public class TransformerFactory {

    public static Transformer getTransformer(String type) {
        Transformer transformer = null;
        if (type == null || "".equals(type) || "common".equals(type)) {
            transformer = new CommonTransformer();
        }
        return transformer;
    }
}
