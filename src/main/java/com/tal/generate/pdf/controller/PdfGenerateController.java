package com.tal.generate.pdf.controller;

import com.tal.generate.pdf.exception.PdfGenerateException;
import com.tal.generate.pdf.model.GenerateDto;
import com.tal.generate.pdf.model.Result;
import com.tal.generate.pdf.service.GeneratePdfCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.tal.generate.pdf.constant.Constants.OutputType.PDF;

/**
 * @author zhaiyarong, liujiangtao
 */
@RestController
@Validated
@RequestMapping("/rest/pdf")
public class PdfGenerateController {

    @Autowired
    private GeneratePdfCommandService generatePdfCommandService;

    /**
     * 生成PDF
     *
     * @param generatePdfDto
     * @return
     * @throws PdfGenerateException
     */
    @PostMapping(value = "/render")
    @ResponseStatus(value = HttpStatus.OK)
    public Result generatePdf(@Valid @RequestBody GenerateDto generatePdfDto)
            throws PdfGenerateException {
        switch (generatePdfDto.getOutputType()) {
            case PDF:
                return generatePdfCommandService.generatePdf(generatePdfDto.getFileName(), generatePdfDto);
            default:
                break;
        }

        return Result.buildFailResult("At present, PDF is only supported.");
    }

    /**
     * 生成并预览PDF
     *
     * @param response
     * @param generateDto
     * @throws PdfGenerateException
     */
    @GetMapping(value = "/show")
    @CrossOrigin("*")
    @ResponseStatus(value = HttpStatus.OK)
    public void showPdf(HttpServletResponse response, @Valid GenerateDto generateDto)
            throws PdfGenerateException {
        switch (generateDto.getOutputType()) {
            case PDF:
                generatePdfCommandService.showPdf(response, generateDto.getFileName(), generateDto);
            default:
                break;
        }

        return;
    }
}
