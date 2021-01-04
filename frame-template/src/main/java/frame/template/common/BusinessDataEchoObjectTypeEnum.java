package frame.template.common;

import lombok.Getter;

@Getter
public enum BusinessDataEchoObjectTypeEnum {

	OBJECT_LIST_MAP("List<Map<String,String>>", "0"),
	OBJECT_MAP("Map<String, String>", "1"),
	OBJECT_String("String", "2");

	private String objectTypeCode;
	private String objectType;

	BusinessDataEchoObjectTypeEnum(String objectTypeCode,String objectType) {
		this.objectTypeCode=objectTypeCode;
		this.objectType=objectType;
	}
}
