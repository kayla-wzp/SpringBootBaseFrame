package frame.template.vo.reqres;

import frame.template.common.FormatNotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class QueryManualInputFieldConfigListReq{
	private String fieldName;

	private String fieldVariableName;

	@FormatNotNull("Ä£°åid")
	private String fileTemplateId;


}
