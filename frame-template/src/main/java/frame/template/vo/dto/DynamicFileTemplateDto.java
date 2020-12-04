package frame.template.vo.dto;

import asset.frame.anno.format.FormatNotNull;
import cffs.core.dto.BaseDto;
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
