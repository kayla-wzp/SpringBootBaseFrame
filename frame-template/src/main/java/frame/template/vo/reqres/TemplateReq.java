package frame.template.vo.reqres;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import asset.common.util.date.DateUtil;
import asset.common.validate.Validator;
import asset.frame.anno.format.FormatLength;
import asset.frame.anno.format.FormatMatch;
import asset.frame.anno.format.FormatNotNull;
import asset.frame.util.json.DateDeserializer;
import asset.frame.util.json.DateSerializer;
import asset.frame.util.json.DateYearMonthDeserializer;
import asset.frame.util.json.DateYearMonthSerializer;
import asset.frame.util.json.ListDeserializer;
import asset.frame.vo.ValidateFormat;
import cffs.core.constant.errcode.ErrCodeTsign;
import cffs.core.format.MoneyFormat;
import cffs.online.frame.vo.TransReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class TemplateReq {

	@FormatNotNull(value = "Ä£°åid")
	private String templateId;
	@FormatNotNull(value = "ÒµÎñid")
	private String businessId;



}
