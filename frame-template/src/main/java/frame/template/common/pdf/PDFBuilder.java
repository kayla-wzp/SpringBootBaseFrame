package frame.template.common.pdf;

import java.io.IOException;
import java.util.List;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class PDFBuilder extends PdfPageEventHelper {

	public List<PdfHeader> pdfHeaderList;

	public String header = "";

	public int presentFontSize = 12;

	public Rectangle pageSize = PageSize.A4;

	public PdfTemplate pdfTemplate;

	public BaseFont baseFont = null;

	public Font fontDetail = null;

	public String waterMarkPath;

	public String fontPath;

	public PDFBuilder() {}

	public PDFBuilder(List<PdfHeader> pdfHeaderList, String fontPath, String waterMarkPath) {
		this.pdfHeaderList = pdfHeaderList;
		this.fontPath = fontPath;
		this.waterMarkPath = waterMarkPath;
	}

	public PDFBuilder(String fontPath, String waterMarkPath) {
		this.fontPath = fontPath;
		this.waterMarkPath = waterMarkPath;
	}

	public PDFBuilder(String yeMei, int presentFontSize, Rectangle pageSize) {
		this.header = yeMei;
		this.presentFontSize = presentFontSize;
		this.pageSize = pageSize;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void setHeader(List<PdfHeader> headerList) {
		this.pdfHeaderList = headerList;
	}

	public void setPresentFontSize(int presentFontSize) {
		this.presentFontSize = presentFontSize;
	}

	@Override
	public void onOpenDocument(PdfWriter writer, Document document) {
		pdfTemplate = writer.getDirectContent().createTemplate(50, 50);
	}

	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		this.addPage(writer, document);
		if (StringUtils.isNotBlank(waterMarkPath)) {
			this.addWatermark(writer);
		}
	}

	public void addPage(PdfWriter writer, Document document) {
		try {
			if (baseFont == null) {
				baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}
			if (fontDetail == null) {
				fontDetail = new Font(baseFont, presentFontSize, Font.NORMAL);
			}
		} catch (Exception e) {
			log.error("×Ö·û¼¯¶ÁÈ¡Ê§°Ü," + fontPath, e);
		}

		setPageHeader(writer, document);

		int pageS = writer.getPageNumber();
		String foot1 = "µÚ " + pageS + " Ò³ /¹²";
		Phrase footer = new Phrase(foot1, fontDetail);

		float len = baseFont.getWidthPoint(foot1, presentFontSize);

		PdfContentByte cb = writer.getDirectContent();

		ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer,
				(document.rightMargin() + document.right() + document.leftMargin() - document.left() - len) / 2.0F
						+ 20F,
				document.bottom() - 20, 0);

		cb.addTemplate(pdfTemplate,
				(document.rightMargin() + document.right() + document.leftMargin() - document.left()) / 2.0F + 20F,
				document.bottom() - 20);

	}

	public void addWatermark(PdfWriter writer) {
		Image image;
		try {
			image = Image.getInstance(waterMarkPath);
			PdfContentByte content = writer.getDirectContentUnder();
			content.beginText();
			image.setAbsolutePosition(125, 300);
			content.addImage(image);
			content.endText();
		} catch (IOException | DocumentException e) {
			log.error("Ë®Ó¡Ìí¼ÓÊ§°Ü," + waterMarkPath, e);
		}
	}

	@Override
	public void onCloseDocument(PdfWriter writer, Document document) {
		pdfTemplate.beginText();
		pdfTemplate.setFontAndSize(baseFont, presentFontSize);
		String foot2 = " " + (writer.getPageNumber()) + " Ò³";
		pdfTemplate.showText(foot2);
		pdfTemplate.endText();
		pdfTemplate.closePath();
	}

	private void setPageHeader(PdfWriter writer, Document document) {
		if (CollectionUtils.isNotEmpty(this.pdfHeaderList)) {
			List<PdfHeader> pdfHeaderList = this.pdfHeaderList;
			pdfHeaderList.stream()
					.forEach(pdfHeader -> ColumnText.showTextAligned(writer.getDirectContent(),
							pdfHeader.getAlignment(), new Phrase(pdfHeader.getContent(), fontDetail), pdfHeader.getX(),
							pdfHeader.getY(), pdfHeader.getRotation()));

		}

		if (StringUtils.isNotBlank(this.header)) {
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(header, fontDetail),
					document.left(), document.top() + 20, 0);
		}
	}
}
