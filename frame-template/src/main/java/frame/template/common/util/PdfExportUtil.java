package frame.template.common.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import frame.template.common.pdf.PDFBuilder;
import frame.template.common.pdf.PdfHeader;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@Slf4j
public class PdfExportUtil {

	private static final String FILE_PACT_WINDOWS = "file:/";
	private static final String FILE_PACT_LINUX = "file:///";

	public static OutputStream exportPdfByHtml(String templateFileName, InputStream templateInputStream, String charSet,
			Map<String, Object> dataMap, String fontPath, String waterMarkPath, List<PdfHeader> pdfHeaderList) {
		log.debug(templateFileName, "CO_TEMPLATE_ROOT_IS_NULL");
		log.debug("" + templateInputStream, "模板文件输入流不能为空");
		log.debug(fontPath, "CO_FONT_FILE_PATH_IS_NULL");
		File file = new File(fontPath);
		log.debug("" + file.exists(), "CO_PDF_TEMPLATE_NOT_EXISTS");
		return createPdfByFreemarkerTemplate(templateFileName, templateInputStream, charSet, dataMap, fontPath,
				waterMarkPath, pdfHeaderList);
	}

	public static OutputStream exportPdfByHtmlWithImage(String templateFileName, InputStream templateInputStream,
			String charSet, Map<String, Object> dataMap, String fontPath, String imagePath) {
		log.debug(templateFileName, "CO_TEMPLATE_ROOT_IS_NULL");
		log.debug("" + templateInputStream, "模板文件输入流不能为空");
		log.debug(fontPath, "CO_FONT_FILE_PATH_IS_NULL");
		File file = new File(fontPath);
		log.debug("" + file.exists(), "CO_PDF_TEMPLATE_NOT_EXISTS");
		return createPdfByFreemarkerTemplateWithImage(templateFileName, templateInputStream, charSet, dataMap, fontPath,
				imagePath);
	}

	public static OutputStream createPdfByFreemarkerTemplateWithImage(String templateFileName,
			InputStream templateInputStream, String charSet, Map<String, Object> dataMap, String fontPath,
			String imagePath) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			Template template = PdfExportUtil.initFreemarkerTemplate(templateFileName, charSet, templateInputStream);
			String htmlContent = PdfExportUtil.freeMarkerRender(template, charSet, dataMap);
			createPdf(htmlContent, fontPath, imagePath, output);
			return output;
		} catch (Exception e) {
			log.error("html转换pdf失败!==》{}", e);
			return null;
		}
	}

	public static OutputStream createPdfByFreemarkerTemplate(String templateFileName, InputStream templateInputStream,
			String charSet, Map<String, Object> dataMap, String fontPath, String waterMarkPath,
			List<PdfHeader> pdfHeaderList) {
		Template template = PdfExportUtil.initFreemarkerTemplate(templateFileName, charSet, templateInputStream);
		String htmlContent = PdfExportUtil.freeMarkerRender(template, charSet, dataMap);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, output);

			PDFBuilder builder = new PDFBuilder(pdfHeaderList, fontPath, waterMarkPath);

			writer.setPageEvent(builder);

			document.open();

			XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
			fontImp.register(fontPath);
			XMLWorkerHelper.getInstance().parseXHtml(writer, document,
					new ByteArrayInputStream(htmlContent.getBytes(charSet)), Charset.forName(charSet), fontImp);
			document.close();
			return output;
		} catch (Exception e) {
			log.error("html转换pdf失败!==》{}", e);
			return null;
		}
	}

	private static void createPdf(String content, String fontPath, String imagePath, OutputStream outputStream)
			throws IOException, DocumentException {
		ITextRenderer render = new ITextRenderer();
		ITextFontResolver fontResolver = render.getFontResolver();
		fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		render.setDocumentFromString(content);
		render.getSharedContext()
				.setBaseURL((SystemUtils.IS_OS_WINDOWS ? FILE_PACT_WINDOWS : FILE_PACT_LINUX) + imagePath);
		render.layout();
		render.createPDF(outputStream);
	}

	@SuppressWarnings("deprecation")
	public static Template initFreemarkerTemplate(String fileName, String charSet, InputStream inputStream) {
		try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charSet)) {
			return new Template(fileName, inputStreamReader, null);
		} catch (IOException e) {
			log.error("Html模板获取失败==》{}", e);
			return null;
		}
	}

	public static String freeMarkerRender(Template freemarkerTemplate, String charSet, Map<String, Object> dataMap) {

		try (StringWriter outWriter = new StringWriter()) {
			freemarkerTemplate.setEncoding(charSet);
			freemarkerTemplate.process(dataMap, outWriter);
			outWriter.flush();
			return outWriter.toString();
		} catch (Exception e) {
			log.error("Html模板添加数据失败==》{}", e);
			return null;
		}
	}

}
