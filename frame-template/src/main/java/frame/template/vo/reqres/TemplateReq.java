package frame.template.vo.reqres;

import frame.template.common.FormatNotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class TemplateReq {

	@FormatNotNull(value = "Ä£°åid")
	private String templateId;
	@FormatNotNull(value = "ÒµÎñid")
	private String businessId;
}
