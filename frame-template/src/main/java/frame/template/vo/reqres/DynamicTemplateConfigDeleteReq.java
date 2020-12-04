package frame.template.vo.reqres;


import frame.template.common.FormatNotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class DynamicTemplateConfigDeleteReq {

	@FormatNotNull(value = "��������ID")
	private String businessDataEchoConfigId;


}
