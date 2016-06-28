package com.msh.model.entity.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Tang Yong Di
 * @date 2015/9/29
 */
public class VersionSql implements Comparable<VersionSql> {

    private int version;

    private List<String> sqlList = new ArrayList<>();

    public VersionSql(int version, String... sqls) {
        this.version = version;
        this.sqlList = Arrays.asList(sqls);
    }

    public int getVersion() {
        return version;
    }

    public List<String> getSqlList() {
        return sqlList;
    }

    @Override
    public int compareTo(VersionSql that) {
        return (version < that.version) ? -1 : ((version == that.version) ? 0 : 1);
    }

    @Override
    public String toString() {
        return "{" +
                "version:" + version +
                ", sqlList:" + sqlListString(sqlList) +
                '}';
    }

    private String sqlListString(List<String> sqlList) {
        String str = "[\n";
        int index = 0;
        for (String sql : sqlList) {
            if (index ++ != 0) {
                str += ",\n";
            }
            str += "\"" + sql + "\"";
        }
        return str + "\n]";
    }

}
