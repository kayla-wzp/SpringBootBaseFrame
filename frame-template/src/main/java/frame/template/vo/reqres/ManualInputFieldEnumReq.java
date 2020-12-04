package frame.template.vo.reqres;


import frame.template.common.FormatNotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ManualInputFieldEnumReq {

	@FormatNotNull("�ֶ���������id")
	private String manualInputFieldConfigId;

	@FormatNotNull("ö��Json��")
	private String enumJsonStr;


}
