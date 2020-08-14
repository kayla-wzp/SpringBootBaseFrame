package frame.base.service.cache;

import frame.base.dao.CommonParamDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class CacheService {
	@Resource
	private CommonParamDao commonParamDao;

	public String getParamValueAvail(String parameterIdStr) {
		return commonParamDao.selectParamByParamId(parameterIdStr);
	}
}
