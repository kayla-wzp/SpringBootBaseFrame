package frame.template.vo.reqres;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class QueryBusinessDataEchoConfigListReq{

	private String fileTemplateId;

	private String objectAttributeName;


}
