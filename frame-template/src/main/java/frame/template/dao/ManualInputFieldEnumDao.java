package frame.template.dao;


import frame.template.vo.ManualInputFieldEnum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ManualInputFieldEnumDao {
	int deleteByManualInputFieldConfigId(String manualInputFieldConfigId);

	int batchInsert(@Param("list") List<ManualInputFieldEnum> manualInputFieldEnums);

	List<ManualInputFieldEnum> selectByManualInputFieldConfigId(String manualInputFieldConfigId);

}
