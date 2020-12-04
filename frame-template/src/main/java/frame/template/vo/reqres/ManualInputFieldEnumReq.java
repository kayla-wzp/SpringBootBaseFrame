package frame.template.vo.reqres;


import frame.template.common.FormatNotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ManualInputFieldEnumReq {

	@FormatNotNull("手动输入配置id")
	private String manualInputFieldConfigId;

	@FormatNotNull("枚举Json串")
	private String enumJsonStr;


}
