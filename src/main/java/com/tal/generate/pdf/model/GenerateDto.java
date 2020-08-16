package com.tal.generate.pdf.model;

import com.tal.generate.pdf.annotation.ParamValidator;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

import static com.tal.generate.pdf.constant.Constants.OutputType.PDF;

/**
 * @author zhaiyarong
 */
@Setter
@Getter
public class GenerateDto {

    /**
     * URL to render as PDF or HTML or IMAGE;
     */
    @URL(message = "The URL is not valid")
    @NotEmpty
    private String url;
    /**
     * 输出格式。主要包含：PDF
     */
    @ParamValidator(values = {PDF}, message = "the output type must be one of PDF")
    private String outputType;
    /**
     * 输出文件
     */
    private String output;
    /**
     * pdf 名称
     */
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Pdf name can only be composed of English and numbers ")
    @NotEmpty
    private String fileName;
    /**
     * 输出PDF时多媒体处理
     */
    private boolean emulateMediaType;
    /**
     * 懒加载element
     */
    private boolean scrollPage;
    /**
     * 元素渲染等待时间（ms）
     */
    private long waitFor;
    /**
     * PDF
     */
    @Valid
    private Pdf pdf;
    /**
     * IMAGE
     */
    @Valid
    private Screenshot screenshot;

    @Valid
    private List<Cookies> cookies;
    /**
     * Launch
     */
    private Launch launch;
    /**
     * default emulateMediaType
     */
    private static final boolean DEFAULT_EMULATE_MEDIA_TYPE = false;
    /**
     * default wait for time
     */
    private static final long DEFAULT_WAIT_FOR = 1000 * 6;
    /**
     * default output directory
     */
    private static final String DEFAULT_OUTPUT = "./";

    {
        output = DEFAULT_OUTPUT;
        emulateMediaType = DEFAULT_EMULATE_MEDIA_TYPE;
        waitFor = DEFAULT_WAIT_FOR;
    }
}
