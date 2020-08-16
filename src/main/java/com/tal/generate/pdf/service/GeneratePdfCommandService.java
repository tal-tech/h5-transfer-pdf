package com.tal.generate.pdf.service;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.tal.generate.pdf.config.PdfConfig;
import com.tal.generate.pdf.exception.PdfGenerateException;
import com.tal.generate.pdf.model.GenerateDto;
import com.tal.generate.pdf.model.Result;
import com.tal.generate.pdf.pdf.PdfGenerate;
import com.tal.generate.pdf.pdf.RunProcessFunction;
import com.tal.generate.pdf.util.FileUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.tal.generate.pdf.constant.Constants.PDF_SUFFIX;

/**
 * @author zhaiyarong
 */
@Service
public class GeneratePdfCommandService {

    private static final Logger LOG = LoggerFactory.getLogger(GeneratePdfCommandService.class);

    @Autowired
    private PdfConfig pdfConfig;

    /**
     * 生成PDF
     *
     * @param pdfName
     * @param generatePdfDto
     * @return
     * @throws PdfGenerateException
     */
    public Result generatePdf(String pdfName, GenerateDto generatePdfDto) throws PdfGenerateException {
        String path = "";
        try {
            generatePdfDto.setOutput(FileUtil.getAbsoluteFilePath(getPdfFileName(generatePdfDto.getOutput(), pdfName)));
            path = renderPdf(generatePdfDto);
        } catch (IOException e) {
            LOG.error("render PDF fail ", e);
            throw new PdfGenerateException("render PDF fail", e);
        }

        return Result.buildSuccessResult(path, "render PDF success");
    }

    public String renderPdf(GenerateDto generateDto) throws IOException {
        RunProcessFunction processFunction = new RunProcessFunction();
        PdfGenerate pdfGenerate = new PdfGenerate(pdfConfig.getNodePath(), processFunction, pdfConfig.getWaitForTimeout(), pdfConfig.getReadStreamTimeout());
        if (!FileUtil.makeDirectory(generateDto.getOutput())) {
            throw new IOException("mkdir the directory error");
        }
        Gson gson = new Gson();
        List<String> params = new ArrayList<>();
        params.add(FileUtil.getAbsoluteFilePath(pdfConfig.getScriptPath()));
        params.add(gson.toJson(generateDto).replace("\"", "\'"));
        List<String> results = pdfGenerate.run(params);
        if (results == null || results.isEmpty() || !FileUtil.isFileExist(results.get(results.size() - 1))) {
            LOG.error("render PDF fail，command: {}  {}", pdfConfig.getScriptPath(), gson.toJson(generateDto));
            throw new PdfGenerateException("render PDF fail.");
        }
        return new File(results.get(results.size() - 1)).getCanonicalPath();
    }

    /**
     * 生成PDF并返回预览PDF
     *
     * @param response
     * @param pdfName
     * @param generateDto
     * @throws PdfGenerateException
     */
    public void showPdf(HttpServletResponse response, String pdfName, GenerateDto generateDto) throws PdfGenerateException {
        String path = "";
        try {
            generateDto.setOutput(FileUtil.getAbsoluteFilePath(getPdfFileName(generateDto.getOutput(), pdfName)));
            path = renderPdf(generateDto);
            readPdf(response, path, pdfName);
        } catch (IOException e) {
            LOG.error("render PDF fail ", e);
            throw new PdfGenerateException("render PDF fail", e);
        }
    }

    private void readPdf(HttpServletResponse response, String filePath, String pdfName) throws PdfGenerateException {
        File file = new File(filePath);
        try (FileInputStream fileInputStream = new FileInputStream(file); OutputStream outputStream = response.getOutputStream();) {
            response.reset();
            response.setContentType("application/pdf;charset=utf-8");
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(pdfName, "UTF-8") + PDF_SUFFIX);
            IOUtils.write(IOUtils.toByteArray(fileInputStream), outputStream);
            outputStream.flush();
        } catch (FileNotFoundException e) {
            LOG.error("render PDF fail", e);
            throw new PdfGenerateException("render PDF fail", e);
        } catch (IOException e) {
            LOG.error("render PDF fail", e);
            throw new PdfGenerateException("render PDF fail", e);
        }
    }

    /**
     * 根据路径和PDF名称获取全路径
     *
     * @param pdfPath
     * @param pdfName
     * @return
     */
    private String getPdfFileName(String pdfPath, String pdfName) {
        Preconditions.checkNotNull(pdfPath, "pdfPath can't be null");
        Preconditions.checkNotNull(pdfName, "pdfName can't be null");
        return pdfPath + pdfName + PDF_SUFFIX;
    }
}
