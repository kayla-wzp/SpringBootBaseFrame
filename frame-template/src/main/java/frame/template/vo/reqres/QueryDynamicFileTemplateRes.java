package frame.template.vo.reqres;

import lombok.Data;

import java.util.Date;


@Data
public class QueryDynamicFileTemplateRes{
	private String fileTemplateId;

	private String fileTemplateName;

	private String fileType;

	private String fileTypeName;

	private String businessType;

	private String businessTypeName;

	private Date lastUpdateTime;

	private String fileId;

	private String fileOriginalName;

	private String fileExtension;

	private String filePath;
}
