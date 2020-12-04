package frame.template.dao;

import frame.template.vo.DynamicFileTemplate;

public interface DynamicFileTemplateDao {
    int deleteByPrimaryKey(String fileTemplateId);

    int insert(DynamicFileTemplate record);

    int insertSelective(DynamicFileTemplate record);

    DynamicFileTemplate selectByPrimaryKey(String fileTemplateId);

    int updateByPrimaryKeySelective(DynamicFileTemplate record);

    int updateByPrimaryKey(DynamicFileTemplate record);
}