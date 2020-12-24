package com.management.carrot97.utils.textTransform.transformerImpl;


import com.management.carrot97.utils.textTransform.Transformer;

public class CommonTransformer implements Transformer {

    @Override
    public String transform(String str) {
        boolean reference = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (reference) {
                if (c == ']' || c == '］' || c == '】') reference = false;
                continue;
            }
            if (c == '[' || c == '［' || c == '【') {
                reference = true;
                continue;
            }
            if (c == ' ') continue;
            else if (c == '\n') continue;
            else if (c == '\uE5E5' || c == '\ue5d2' || c == '\ue5cf') continue;
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }
}
