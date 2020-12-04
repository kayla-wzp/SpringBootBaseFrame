package frame.template.vo;

import lombok.Data;

import java.util.Date;

@Data
public class DynamicFileTemplate {
    private String fileTemplateId;

    private String fileId;

    private String fileType;

    private String fileExtension;

    private String businessType;

    private String businessPhase;

    private Date createTime;

    private Date lastUpdateTime;

    private String fileTemplateName;
}