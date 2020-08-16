package com.tal.generate.pdf.enums;

/**
 * @author zhaiyarong
 */
public enum LabelUnit {
    PX("px", "pixel"),
    IN("in", "inch"),
    CM("cm", "centimeter"),
    MM("mm", "millimeter");

    private String name;

    private String unit;

    LabelUnit(String unit, String name) {
        this.unit = unit;
        this.name = name;
    }

    public String getUnit() {
        return this.unit;
    }

}
