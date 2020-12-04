package frame.template.dao;

import frame.template.vo.reqres.QueryManualInputFieldEnumListRes;

import java.util.List;

public interface QueryManualInputFieldEnumDao {
	List<QueryManualInputFieldEnumListRes> queryManualInputFieldEnumList(String manualInputFieldConfigId);
}
