package frame.template.service;

import frame.template.dao.QueryBusinessDataEchoConfigDao;
import frame.template.vo.reqres.QueryBusinessDataEchoConfigListReq;
import frame.template.vo.reqres.QueryBusinessDataEchoConfigListRes;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QueryBusinessDataEchoConfigService {
	@Resource
	private QueryBusinessDataEchoConfigDao queryBusinessDataEchoConfigDao;

	public List<QueryBusinessDataEchoConfigListRes> queryBusinessDataEchoConfigList(
			QueryBusinessDataEchoConfigListReq req) {
//		PageUtil.setPaging(req.getCurrentPage(), req.getPageSize());
		return	queryBusinessDataEchoConfigDao.queryBusinessDataEchoConfigList(req);
	}


}

