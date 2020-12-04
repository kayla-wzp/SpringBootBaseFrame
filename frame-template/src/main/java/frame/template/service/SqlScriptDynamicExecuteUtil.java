package frame.template.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SqlScriptDynamicExecuteUtil {

	/**
	 * @param executeSql Ҫִ�е�sql
	 * @param sqlSession ���ݿ�Ự
	 * @param clazz ������������
	 * @param param sql����
	 * @return
	 */
	public static List<?> selectList(String executeSql, SqlSession sqlSession, Class<?> clazz, Object param) {
		String msId = "cffs.core.service.dynamictemplate.DynamicSqlService";
		initSqlSession(executeSql, sqlSession, clazz, msId);
		List<?> list = sqlSession.selectList(msId, param);
		return list;
	}

	/**
	 * @param executeSql Ҫִ�е�sql
	 * @param sqlSession ���ݿ�Ự
	 * @param clazz ������������
	 * @param param sql����
	 * @return
	 */
	public static Object selectOne(String executeSql, SqlSession sqlSession,Class<?> clazz,  Object param) {
		String msId = "cffs.core.service.dynamictemplate.DynamicSqlService";
		initSqlSession(executeSql, sqlSession, clazz, msId);
		return sqlSession.selectOne(msId, param);
	}

	private static void initSqlSession(String executeSql, SqlSession sqlSession, Class<?> clazz, String msId) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>").append(executeSql).append("</script>");
		String sqlStr = sb.toString();
		log.info(sqlStr);
		Configuration configuration = sqlSession.getConfiguration();
		LanguageDriver languageDriver = configuration.getDefaultScriptingLanuageInstance();
		SqlSource sqlSource = languageDriver.createSqlSource(configuration, sqlStr, clazz);
		initConfiguration(configuration, msId, sqlSource, clazz);
	}



	private static void initConfiguration(Configuration configuration, String msId, SqlSource sqlSource,
			final Class<?> resultType) {
		// ��ǿ�߼�:һ��Ҫ��ֹ MappedStatement �ظ�����
		MappedStatement msTest = null;
		try {
			// ��ֹ����������
			synchronized (configuration) {
				msTest = configuration.getMappedStatement(msId);
				if (msTest != null) {
					configuration.getMappedStatementNames().remove(msTest.getId());
				}
			}
		} catch (IllegalArgumentException e) {
			log.info("û�д�mappedStatement,����ע���mappedStatement��configuration����");
		}
		// ����һ�� select ���͵�ms ��ͨ���ƶ�SqlCommandType.SELECT
		// ע�⣡��-�� �������ָ������Ҫ���κ����ã�����cache,CALLABLE,
		MappedStatement ms = new MappedStatement.Builder(
				// -�� ע�⣬������SELECT,������UPDATE\INSERT ����Ҫָ���ɱ��
				configuration, msId, sqlSource, SqlCommandType.SELECT).resultMaps(new ArrayList<ResultMap>() {
					{
						add(new ResultMap.Builder(configuration, "defaultResultMap", resultType, new ArrayList<>(0))
								.build());
					}
				}).build();
		synchronized (configuration) {
			configuration.addMappedStatement(ms);
		}
	}
}
