package frame.template.facade;

import cffs.manage.query.frame.facade.QueryFacade;
import cffs.manage.query.frame.vo.QueryRes;
import cffs.manage.query.service.dynamictemplate.QueryDynamicFileTemplateService;
import cffs.manage.query.vo.dynamictemplate.QueryDynamicFileTemplateReq;
import cffs.manage.query.vo.dynamictemplate.QueryDynamicFileTemplateRes;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QueryDynamicFileTemplateFacade {

	@Resource
	private QueryDynamicFileTemplateService queryDynamicFileTemplateService;

	public QueryRes<QueryDynamicFileTemplateRes> queryDynamicFileTemplateList(QueryDynamicFileTemplateReq req) {
		List<QueryDynamicFileTemplateRes> resList = queryDynamicFileTemplateService.QueryDynamicFileTemplateList(req);
		return super.buildRes(req, resList);
	}
}
