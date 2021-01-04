package frame.template.common.pdf;

import lombok.Data;

@Data
public class PdfHeader {
	private int alignment;
	private float x;
	private float y;
	private int rotation;
	private String content;
}


