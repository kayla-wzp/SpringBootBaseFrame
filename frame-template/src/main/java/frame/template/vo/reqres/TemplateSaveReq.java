package frame.template.vo.reqres;


import frame.template.common.FormatNotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class TemplateSaveReq {

	@FormatNotNull(value = "ģ��id")
	private String templateId;
	@FormatNotNull(value = "ҵ��id")
	private String businessId;
	private String paramStr;



}
