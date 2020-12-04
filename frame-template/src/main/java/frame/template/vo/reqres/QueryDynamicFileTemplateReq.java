package frame.template.vo.reqres;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class QueryDynamicFileTemplateReq{

	private String fileTemplateName;

	private String fileType;


}
