package frame.template.vo.reqres;


import frame.template.common.FormatNotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class DeleteFileTemplateReq {
    @FormatNotNull("�ļ�ģ��ID")
    private String fileTemplateId;


}
