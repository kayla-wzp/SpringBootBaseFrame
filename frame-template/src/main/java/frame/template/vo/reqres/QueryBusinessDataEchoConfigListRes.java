package frame.template.vo.reqres;

import asset.frame.vo.QueryResBody;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class QueryBusinessDataEchoConfigListRes{

	private String businessDataEchoConfigId;

	private String fileTemplateId;

	private String objectAttributeName;

	private String objectType;

	private String objectTypeText;

	private String querySql;
}
