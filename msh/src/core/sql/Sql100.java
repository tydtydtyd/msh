package core.sql;

/**
 * @author Tang Yong Di
 * @date 2015/9/29
 */
public class Sql100 extends Sql {

    public Sql100(int dataVersion) {
        super(dataVersion);
    }

    @Override
    public void initVersionSql() {

        /* e.g:
        add(1, "create table ...", "alter table ....");
        */

        //系统用户表
        add(1, "create table system_user(id int auto_increment primary key, username varchar(255), gender bit, phone varchar(255), account varchar(255), " +
				"password varchar(255), last_login_time datetime, status int, role_id int)");

        //系统角色表
        add(2, "create table system_role(id int auto_increment primary key, name varchar(255), code varchar(255), authority varchar(255), update_time datetime)");

		//入职人员表
		add(3, "create table entry_user(id int auto_increment primary key, name varchar(255), gender bit, telephone varchar(255), creation_time datetime, " +
				"join_date date)");

		//入职人员结账表
		add(4, "create table entry_salary(id int auto_increment primary key, user_id int, creation_time datetime, update_time datetime, salary decimal(12,2), " +
				"salary_date date)");

		//默认管理员
		add(5, "insert into system_user (username, gender, phone, account, password, status, role_id) " +
                "values ('唐永頔', 1, '13540224775', 'tyd', '202cb962ac59075b964b07152d234b70', 1, 1)",

                "insert into system_role (name, code, authority, update_time) " +
                        "values ('管理员', 'ADMIN', 'ALL', now())");
    }
}
