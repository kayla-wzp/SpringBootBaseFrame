package frame.template.dao;

import frame.template.vo.BusinessDataEchoConfig;
import java.util.List;

public interface BusinessDataEchoConfigDao {
    int deleteByPrimaryKey(String id);

    int insert(BusinessDataEchoConfig record);

    int insertSelective(BusinessDataEchoConfig record);

    BusinessDataEchoConfig selectByPrimaryKey(String id);

    List<BusinessDataEchoConfig> selectByTemplateId(String templateId);

    int updateByPrimaryKeySelective(BusinessDataEchoConfig record);

    int updateByPrimaryKey(BusinessDataEchoConfig record);
}
