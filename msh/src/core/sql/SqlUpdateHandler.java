package core.sql;

import com.msh.model.entity.sql.DataVersion;
import com.msh.model.entity.sql.VersionSql;
import com.msh.service.DataVersionService;
import core.spring.instance.InstanceFactory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 版本控制
 *
 * @author Tang Yong Di
 * @date 2015/9/29
 */
public class SqlUpdateHandler {

	private static final Logger log = Logger.getLogger(SqlUpdateHandler.class);
	private static final String QUERY_HAS_TABLE_SQL = "select count(table_name) from information_schema.tables where table_schema='msh' and table_name='data_version'";
	private static final String CREATE_TABLE_SQL = "create table data_version(id int auto_increment primary key, name varchar(255), version int)";
	private static final String QUERY_COUNT_SQL = "select count(*) from data_version dv where dv.name='" + DataVersion.SERVER_VERSION_KEY + "'";
	private static final String INIT_VERSION_SQL = "insert into data_version (name, version) values ('" + DataVersion.SERVER_VERSION_KEY + "', 0)";
	private static final String QUERY_VERSION_SQL = "select dv.version from data_version dv where dv.name='" + DataVersion.SERVER_VERSION_KEY + "'";
	private static final String UPDATE_VERSION_SQL = "update data_version dv set dv.version=%s where dv.name='" + DataVersion.SERVER_VERSION_KEY + "'";

	private static DataVersionService dataVersionService;

	private static DataVersionService getDataVersionService() {
		if (dataVersionService == null) {
			dataVersionService = InstanceFactory.getInstance(DataVersionService.class);
		}
		return dataVersionService;
	}


	public void handle() {
		initDataVersion();
		int dataVersion = getDataVersion();
		if (Sql.CURRENT_VERSION <= dataVersion) {
			log.info("The sql version is up-to-date");
			System.out.println("The sql version is up-to-date");
			return;
		}
		log.info("Update sql version from " + dataVersion + " to " + Sql.CURRENT_VERSION + ".");
		addSqlVersion(new Sql100(dataVersion));
	}

	private void addSqlVersion(Sql sql) {
		sql.initVersionSql();
		addVersion(sql.getList());
	}

	private void addVersion(List<VersionSql> list) {
		Collections.sort(list);
		for (VersionSql versionSql : list) {
			executeUpdate(versionSql);
		}
	}

	private void executeUpdate(VersionSql versionSql) {
		List<String> sqlList = new ArrayList<>();
		int version = versionSql.getVersion();
		log.info("Current version: " + version + ".");
		sqlList.addAll(versionSql.getSqlList());
		sqlList.add(String.format(UPDATE_VERSION_SQL, version));
		log.info("Update sql added: " + versionSql + ".");
		executeUpdate(sqlList);
	}

	private void executeUpdate(List<String> sqlList) {
		String[] scripts = new String[sqlList.size()];
		scripts = sqlList.toArray(scripts);
		if (scripts.length > 0) {
			log.info("Executing sql...");
			try {
				getDataVersionService().sqlUpdate(scripts);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
	}

	private int getDataVersion() {
		int serverVersion = getDataVersionService().queryForInt(QUERY_COUNT_SQL);
		if (serverVersion == 0) {
			getDataVersionService().sqlUpdate(INIT_VERSION_SQL);
			return 0;
		}
		return getDataVersionService().queryForInt(QUERY_VERSION_SQL);
	}

	private void initDataVersion() {
		int tableCount = getDataVersionService().queryForInt(QUERY_HAS_TABLE_SQL);
		if (tableCount == 0) {
			getDataVersionService().sqlUpdate(CREATE_TABLE_SQL);
		}
	}
}
