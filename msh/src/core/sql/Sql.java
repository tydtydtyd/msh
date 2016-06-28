package core.sql;

import com.msh.model.entity.sql.VersionSql;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tang Yong Di
 * @date 2015/9/29
 */
public abstract class Sql {

    //当前sql版本
    public static final int CURRENT_VERSION = 5;

    protected int dataVersion;
    private List<VersionSql> list = new ArrayList<>();

    public Sql(int dataVersion) {
        this.dataVersion = dataVersion;
    }

    public abstract void initVersionSql();

    public void add(int version, String... sqlList) {
        if(!valiable(version)) return;
        if(sqlList == null || sqlList.length == 0) return;
        VersionSql sql = new VersionSql(version, sqlList);
        this.list.add(sql);
    }

    private boolean valiable(int version) {
        return (this.dataVersion < CURRENT_VERSION) && (this.dataVersion < version) && (CURRENT_VERSION >= version);
    }

    public List<VersionSql> getList() {
        return list;
    }
}
