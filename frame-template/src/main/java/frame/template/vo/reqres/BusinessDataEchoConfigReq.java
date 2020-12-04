package frame.template.vo.reqres;


import frame.template.common.FormatNotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class BusinessDataEchoConfigReq {

	private String businessDataEchoConfigId;
	@FormatNotNull("模板ID")
	private String fileTemplateId;
	@FormatNotNull("对象属性名")
	private String objectAttributeName;
	@FormatNotNull("对象类型")
	private String objectType;
	@FormatNotNull("查询SQL")
	private String querySql;


}

