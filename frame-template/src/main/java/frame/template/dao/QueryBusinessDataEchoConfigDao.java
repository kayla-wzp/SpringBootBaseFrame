package frame.template.dao;

import frame.template.vo.reqres.QueryBusinessDataEchoConfigListReq;
import frame.template.vo.reqres.QueryBusinessDataEchoConfigListRes;

import java.util.List;

public interface QueryBusinessDataEchoConfigDao {
	List<QueryBusinessDataEchoConfigListRes> queryBusinessDataEchoConfigList(QueryBusinessDataEchoConfigListReq req);

}
