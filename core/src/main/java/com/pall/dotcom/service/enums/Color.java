package com.pall.dotcom.service.enums;

/**
 * 
 * @author 3|Share - David Truchet
 *
 */
public enum Color {

    AEROSPACE("#33ace0","rgba(51,172,224,1)", "aerospace"), 
    BIOPHARMACEUTICAL("#533061","rgba(83,48,97,1)", "biopharma"), 
    BLUE("#184d9d","rgba(24,77,157,1)", "pallblue"),
    CHARCOAL1("#444444","rgba(68,68,68,1)", "charcoal1"),
    CHARCOAL2("#1b1b1b","rgba(27,27,27,1)", "charcoal2"),
    CHARCOAL3("#2b2a2a","rgba(43,42,42,1)", "charcoal3"),
    CHEMICALS("#599d76","rgba(89,157,118,1)", "chemicals"),
    FOOD("#3a7e07","rgba(58,126,7,1)", "food"),
    HIGHLIGHT("#bdc205","rgba(189,194,5,1)", "highlight"),
    INDUSTRIAL("#222a4a","rgba(34,42,74,1)", "industrial"),
    LABORATORY("#3ba8b8","rgba(65,169,184,1)", "laboratory"),
    MEDICAL("#1b8bea","rgba(27,139,234,1)", "medical"),
    MICROELECTRONICS("#80a546","rgba(128,165,70,1)", "microelec"),
    OIL("#541801","rgba(84,24,1,1)", "oilgas"),
    POWER("#e96f14","rgba(233,111,20,1)", "power"),
    WHITE("#ffffff","rgba(255,255,255,1)", "white");

    private String hexCode;

    private String rgbCode;

    private String cssClass;

    private Color(String hexCode, String rgbCode, String cssClass) {
        this.hexCode = hexCode;
        this.rgbCode = rgbCode;
        this.cssClass = cssClass;
    }

    public String getHexCode() {
        return hexCode;
    }

    public String getRgbCode() {
        return rgbCode;
    }

    public String getCssClass() {
        return cssClass;
    }

    public static Color valueFromRGB(String rgb) {
        if (rgb != null) {
            for (Color color : Color.values()) {
                if (rgb.equals(color.getRgbCode())) {
                    return color;
                }
            }
        }
        return null;
    }
}
