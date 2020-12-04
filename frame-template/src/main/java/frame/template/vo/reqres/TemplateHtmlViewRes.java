package frame.template.vo.reqres;

import java.util.List;

import asset.frame.vo.TransResBody;
import cffs.po.factoring.FactoringPlan;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class TemplateHtmlViewRes implements TransResBody {

	private String htmlViewStr;
}
