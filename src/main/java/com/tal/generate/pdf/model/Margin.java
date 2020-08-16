package com.tal.generate.pdf.model;

import com.tal.generate.pdf.annotation.LabelUnitValidator;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhaiyarong
 */
@Getter
@Setter
public class Margin {
    /**
     * Top margin, accepts values labeled with units
     */
    @LabelUnitValidator(message = "The unit must be one of px, in, cm and mm")
    String top;
    /**
     * Right margin, accepts values labeled with units
     */
    @LabelUnitValidator(message = "The unit must be one of px, in, cm and mm")
    String right;
    /**
     * Bottom margin, accepts values labeled with units
     */
    @LabelUnitValidator(message = "The unit must be one of px, in, cm and mm")
    String bottom;
    /**
     * Left margin, accepts values labeled with units
     */
    @LabelUnitValidator(message = "The unit must be one of px, in, cm and mm")
    String left;
}
