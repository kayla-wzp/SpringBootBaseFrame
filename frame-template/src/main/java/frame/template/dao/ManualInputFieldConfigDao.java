package frame.template.dao;


import frame.template.vo.ManualInputFieldConfig;

import java.util.List;

public interface ManualInputFieldConfigDao {
    int deleteByPrimaryKey(String manualInputFieldConfigId);

    int insert(ManualInputFieldConfig record);

    ManualInputFieldConfig selectByPrimaryKey(String manualInputFieldConfigId);

    List<ManualInputFieldConfig> selectByTemplateId(String templateId);

    int updateByPrimaryKey(ManualInputFieldConfig record);
}
