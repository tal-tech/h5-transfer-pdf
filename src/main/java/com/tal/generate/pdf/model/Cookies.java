package com.tal.generate.pdf.model;

import com.tal.generate.pdf.annotation.ParamValidator;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @author zhaiyarong
 */
@Getter
@Setter
public class Cookies {
    /**
     * Cookie name
     */
    @NotEmpty
    private String name;
    /**
     * Cookie value
     */
    @NotEmpty
    private String value;
    /**
     * Cookie url
     */
    private String url;
    /**
     * Cookie domain
     */
    private String domain;
    /**
     * Cookie expire
     */
    private long expire;
    /**
     * Cookie httpOnly
     */
    private boolean httpOnly;
    /**
     * Cookie secure
     */
    private boolean secure;
    /**
     * Cookie sameSite
     */
    @ParamValidator(values = {"Strict", "Lax"})
    private String sameSite;
}
