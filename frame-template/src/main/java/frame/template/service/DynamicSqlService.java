package frame.template.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DynamicSqlService {
	@Resource
	private SqlSession sqlSession;

	public String getStr(String sql, Map<String, Object> enterParamMap) {
		log.debug("sqlSession=>{}", sqlSession);
		return (String) SqlScriptDynamicExecuteUtil.selectOne(sql, sqlSession, String.class, enterParamMap);
	}

	public Map<String, Object> getMap(String sql, Map<String, Object> enterParamMap) {
		log.debug("sqlSession=>{}", sqlSession);
		return (Map<String, Object>) SqlScriptDynamicExecuteUtil.selectOne(sql, sqlSession, Map.class, enterParamMap);
	}

	public List<Map<String, Object>> getListMap(String sql, Map<String, Object> enterParamMap) {
		log.debug("sqlSession=>{}", sqlSession);
		return (List<Map<String, Object>>) SqlScriptDynamicExecuteUtil.selectList(sql, sqlSession, Map.class, enterParamMap);
	}

}
