package frame.base.dao;

import frame.base.vo.common.CommonParam;

import java.util.List;

public interface CommonParamDao {
	String selectParamByParamId(String paramId);

	List<CommonParam> selectAllParam();
}
