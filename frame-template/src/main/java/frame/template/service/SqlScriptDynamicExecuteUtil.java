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
	 * @param executeSql 要执行的sql
	 * @param sqlSession 数据库会话
	 * @param clazz 返回数据类型
	 * @param param sql参数
	 * @return
	 */
	public static List<?> selectList(String executeSql, SqlSession sqlSession, Class<?> clazz, Object param) {
		String msId = "cffs.core.service.dynamictemplate.DynamicSqlService";
		initSqlSession(executeSql, sqlSession, clazz, msId);
		List<?> list = sqlSession.selectList(msId, param);
		return list;
	}

	/**
	 * @param executeSql 要执行的sql
	 * @param sqlSession 数据库会话
	 * @param clazz 返回数据类型
	 * @param param sql参数
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
		// 加强逻辑:一定要防止 MappedStatement 重复问题
		MappedStatement msTest = null;
		try {
			// 防止并发插入多次
			synchronized (configuration) {
				msTest = configuration.getMappedStatement(msId);
				if (msTest != null) {
					configuration.getMappedStatementNames().remove(msTest.getId());
				}
			}
		} catch (IllegalArgumentException e) {
			log.info("没有此mappedStatement,可以注入此mappedStatement到configuration当中");
		}
		// 构建一个 select 类型的ms ，通过制定SqlCommandType.SELECT
		// 注意！！-》 这里可以指定你想要的任何配置，比如cache,CALLABLE,
		MappedStatement ms = new MappedStatement.Builder(
				// -》 注意，这里是SELECT,其他的UPDATE\INSERT 还需要指定成别的
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
