package frame.template.vo.reqres;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class QueryManualInputFieldEnumListRes{
	private String manualInputFieldConfigId;

	private String enumKey;

	private String enumValue;

	private String displayOrder;
}
