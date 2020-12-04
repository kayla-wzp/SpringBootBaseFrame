package frame.template.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class ManualInputTempData {

	private static final int FIELD_LENGTH_LIMIT = 3000;
	private String id;

	private String businessId;

	private String templatePrimaryKey;

	private String splitDataOne;

	private String splitDataTwo;

	private String splitDataThere;

	private String splitDataFour;

	private String jsonStr;

	public ManualInputTempData(){}

	public ManualInputTempData(String tempData) {
//		String str = JSON.toJSONString(tempData);
		String str = tempData;
		if (StringUtils.isNotEmpty(str)) {
			if (str.length() > FIELD_LENGTH_LIMIT) {
				this.splitDataOne = str.substring(0, FIELD_LENGTH_LIMIT);
				if (str.length() > FIELD_LENGTH_LIMIT * 2) {
					this.splitDataTwo = str.substring(FIELD_LENGTH_LIMIT, FIELD_LENGTH_LIMIT * 2);
					if (str.length() > FIELD_LENGTH_LIMIT * 3) {
						this.splitDataThere = str.substring(FIELD_LENGTH_LIMIT * 2, FIELD_LENGTH_LIMIT * 3);
						this.splitDataFour = str.substring(FIELD_LENGTH_LIMIT * 3);
					} else {
						this.splitDataThere = str.substring(FIELD_LENGTH_LIMIT * 2);
					}
				} else {
					this.splitDataTwo = str.substring(FIELD_LENGTH_LIMIT);
				}
			} else {
				this.splitDataOne = str;
			}
		}
	}

	public String getJsonStr() {
		if (StringUtils.isEmpty(this.jsonStr)) {
			this.jsonStr = this.splitDataOne;
			if (StringUtils.isNotEmpty(this.splitDataTwo)) {
				this.jsonStr = this.jsonStr + this.splitDataTwo;
				if (StringUtils.isNotEmpty(this.splitDataThere)) {
					this.jsonStr = this.jsonStr + this.splitDataThere;
					if (StringUtils.isNotEmpty(this.splitDataFour)) {
						this.jsonStr = this.jsonStr + this.splitDataFour;
					}
				}
			}
		}
		return this.jsonStr;
	}


}
