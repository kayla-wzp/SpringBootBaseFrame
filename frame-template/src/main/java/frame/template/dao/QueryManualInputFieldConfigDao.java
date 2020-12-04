package frame.template.dao;


import frame.template.vo.reqres.QueryManualInputFieldConfigListReq;
import frame.template.vo.reqres.QueryManualInputFieldConfigListRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QueryManualInputFieldConfigDao {
	List<QueryManualInputFieldConfigListRes> queryManualInputFieldConfigList(@Param("req") QueryManualInputFieldConfigListReq req);

	QueryManualInputFieldConfigListRes querySingleManualInputFieldConfig(@Param("manualInputFieldConfigId") String manualInputFieldConfigId);
}
