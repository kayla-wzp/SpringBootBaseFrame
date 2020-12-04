package frame.template.vo.reqres;

import frame.template.common.FormatNotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DynamicFileTemplateReq {

    @FormatNotNull("ÎÄ¼þID")
    private String fileId;

    private String fileTemplateId;

    private String fileType;

    private String businessType;

    private String fileTemplateName;

}
