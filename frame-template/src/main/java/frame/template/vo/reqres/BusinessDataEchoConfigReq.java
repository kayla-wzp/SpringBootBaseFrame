package frame.template.vo.reqres;


import frame.template.common.FormatNotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class BusinessDataEchoConfigReq {

	private String businessDataEchoConfigId;
	@FormatNotNull("ģ��ID")
	private String fileTemplateId;
	@FormatNotNull("����������")
	private String objectAttributeName;
	@FormatNotNull("��������")
	private String objectType;
	@FormatNotNull("��ѯSQL")
	private String querySql;


}

