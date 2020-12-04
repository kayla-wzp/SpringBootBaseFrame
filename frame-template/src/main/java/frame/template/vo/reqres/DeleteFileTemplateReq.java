package frame.template.vo.reqres;


import frame.template.common.FormatNotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class DeleteFileTemplateReq {
    @FormatNotNull("ÎÄ¼þÄ£°åID")
    private String fileTemplateId;


}
