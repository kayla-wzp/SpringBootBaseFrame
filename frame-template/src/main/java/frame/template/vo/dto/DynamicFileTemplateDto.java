package frame.template.vo.dto;

import frame.template.common.BaseDto;
import frame.template.common.FormatNotNull;
import lombok.Data;

@Data
public class DynamicFileTemplateDto extends BaseDto {

    @FormatNotNull("ÎÄ¼þid")
    private String fileId;

    private String fileTemplateId;

    private String fileType;

    private String businessType;

    private String fileTemplateName;
}
