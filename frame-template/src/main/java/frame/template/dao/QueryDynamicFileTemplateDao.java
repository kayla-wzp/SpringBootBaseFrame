package frame.template.dao;


import frame.template.vo.reqres.QueryDynamicFileTemplateReq;
import frame.template.vo.reqres.QueryDynamicFileTemplateRes;

import java.util.List;

public interface QueryDynamicFileTemplateDao {
    List<QueryDynamicFileTemplateRes> QueryDynamicFileTemplateList(QueryDynamicFileTemplateReq req);
}
