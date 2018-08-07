package com.pall.dotcom.service.enums;

/**
 * 
 * @author 3|Share - David Truchet
 *
 */
public enum Layout {

    TWOCOLOPTION1("50-50", "50", "50", ""),
    TWOCOLOPTION2("75-25", "75", "25", ""),
    TWOCOLOPTION3("25-75", "25", "75", ""),
    THREECOLOPTION1("25-50-25", "25", "50", "25"),
    THREECOLOPTION2("33-33-33", "33", "33", "33");
    
    private String layout;
    private String col1Width; 
    private String col2Width; 
    private String col3Width;
    
    private Layout(String layout, String col1Width, String col2Width, String col3Width) {
        this.layout = layout;
        this.col1Width = col1Width;
        this.col2Width = col2Width;
        this.col3Width = col3Width;
    }

    public String getLayout() {
        return layout;
    }

    public String getCol1Width() {
        return col1Width;
    }

    public String getCol2Width() {
        return col2Width;
    }

    public String getCol3Width() {
        return col3Width;
    }

    public static Layout valueFromLayout(String layout) {
        if (layout != null) {
            for (Layout column : Layout.values()) {
                if (layout.equals(column.getLayout())) {
                    return column;
                }
            }
        }
        return null;
    }
}
