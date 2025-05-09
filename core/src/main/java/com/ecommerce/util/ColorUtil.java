package com.ecommerce.util;

import com.ecommerce.enums.Color;
import com.ecommerce.enums.Style;

public class ColorUtil {

    /**
     * Style.
     *
     * @param value the value
     * @param colorType the color type
     * @param styleType the style type
     * @return the string
     */
    public static String style(String value, Color colorType, Style styleType) {

        if (!colorType.getValue().equals("NO_COLOR"))
            value = colorType.getValue() + value + "\033[0m";

        if (!styleType.getValue().equals("NO_STYLE")) {
            String styleValues[] = styleType.getValue().split("#");
            value = styleValues[0] + value + styleValues[1];
        }

        return value;
    }
}
