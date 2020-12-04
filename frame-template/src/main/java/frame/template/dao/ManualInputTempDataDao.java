package frame.template.dao;


import frame.template.vo.ManualInputTempData;
import org.apache.ibatis.annotations.Param;

public interface ManualInputTempDataDao {
    int deleteByPrimaryKey(String id);

    int insert(ManualInputTempData record);

    int insertSelective(ManualInputTempData record);

    ManualInputTempData selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ManualInputTempData record);

    int updateByPrimaryKey(ManualInputTempData record);

    ManualInputTempData selectByBusinessIdAndTemplateId(@Param("businessId") String businessId, @Param("templateId") String templateId);

	void deleteBusinessIdAndTemplateId(@Param("businessId") String businessId, @Param("templateId") String templateId);
}
