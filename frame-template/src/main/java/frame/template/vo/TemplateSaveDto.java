package frame.template.vo;

import lombok.Data;

@Data
public class TemplateSaveDto {
    private String templateId;
    private String businessId;
    private String paramStr;
}
