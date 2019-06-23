package my.oljek.rc.manager;

import com.oljek.main.data.MysqlData;
import my.oljek.rc.RealcraftPlugin;

import java.io.File;
import java.sql.*;
import java.util.*;

public class SQLiteConnectionManager {

    private String fileName;
    private Connection cn = null;

    public SQLiteConnectionManager(String fileName) {
        this.fileName = fileName;
    }

    public boolean connect(boolean error) {
        try {
            if (this.cn != null && !this.cn.isClosed()) {
                return true;
            }
        } catch (SQLException var4) {
            if (error) {
                var4.printStackTrace();
            }

            return false;
        }

        try {
//            Class.forName("org.sqlite.JDBC");
//            this.cn = DriverManager.getConnection("jdbc:sqlite:" + RealcraftPlugin.getInstance().getDataFolder().getPath() + File.separator + fileName + ".s3db");

            Class.forName("com.mysql.jdbc.Driver");
            this.cn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
            return this.cn != null;
        } catch (Exception var3) {
            if (error) {
                var3.printStackTrace();
            }

            System.out.println("ttq");
            return false;
        }
    }

    public List<Map<String, Object>> select(String table, int limit, Object[]... param) {
        this.connect(true);
        Statement statement = this.getStatement();
        String sql = "SELECT * FROM `" + table + "`";

        for(int e = 0; e < param.length; ++e) {
            Object[] columCount = param[e];
            if (e == 0) {
                sql = sql + " WHERE `" + (String)columCount[0] + "`='" + columCount[1] + "'";
            } else {
                sql = sql + " AND `" + (String)columCount[0] + "`='" + columCount[1] + "'";
                if (e == param.length - 1 && limit != -1) {
                    sql = sql + " LIMIT " + limit;
                }
            }
        }

        try {
            ResultSet var13 = statement.executeQuery(sql);
            if (var13 == null) {
                return new ArrayList();
            } else {
                int var14 = var13.getMetaData().getColumnCount();
                ArrayList listMap = new ArrayList();

                while(var13.next()) {
                    HashMap map = new HashMap();

                    for(int i = 1; i <= var14; ++i) {
                        String name = var13.getMetaData().getColumnName(i);
                        map.put(name, var13.getObject(i));
                    }

                    listMap.add(map);
                }

                return listMap;
            }
        } catch (SQLException var12) {
            var12.printStackTrace();
            return new ArrayList();
        }
    }

    public void insert(String table, boolean largeUpdate, Object... param) {
        this.connect(true);
        Statement statement = this.getStatement();
        String sql = "INSERT INTO `" + table + "` VALUES";

        for(int i = 0; i < param.length; ++i) {
            Object par = param[i];
            if (i == 0) {
                sql = sql + "('" + par + "',";
            } else {
                if (i == param.length - 1) {
                    sql = sql + "'" + par + "')";
                    break;
                }

                sql = sql + "'" + par + "',";
            }
        }

        this.executeUpdate(statement, largeUpdate, sql);
    }

    public void update(String table, boolean largeUpdate, Collection<Object[]> sets, Object[]... where) {
        this.connect(true);
        Statement statement = this.getStatement();
        String sql = "UPDATE `" + table + "` SET";

        int i;
        Object[] obj;
        for(i = 0; i < sets.size(); ++i) {
            obj = (Object[])((Object[])((Object[])sets.toArray()[i]));
            if (i == sets.size() - 1) {
                sql = sql + " `" + obj[0] + "`='" + obj[1] + "' ";
                break;
            }

            sql = sql + " `" + obj[0] + "`='" + obj[1] + "',";
        }

        for(i = 0; i < where.length; ++i) {
            obj = where[i];
            if (i != 0) {
                sql = sql + " AND `" + obj[0] + "`='" + obj[1] + "'";
            } else {
                sql = sql + "WHERE `" + obj[0] + "`='" + obj[1] + "'";
            }
        }

        this.executeUpdate(statement, largeUpdate, sql);
    }

    public void delete(String table, boolean largeUpdate, Object[]... where) {
        this.connect(true);
        Statement statement = this.getStatement();
        String sql = "DELETE FROM `" + table + "` WHERE";

        for(int i = 0; i < where.length; ++i) {
            Object[] obj = where[i];
            if (i == 0) {
                sql = sql + " `" + obj[0] + "`='" + obj[1] + "'";
            } else {
                sql = sql + " AND `" + obj[0] + "`='" + obj[1] + "'";
            }
        }

        this.executeUpdate(statement, largeUpdate, sql);
    }

    public void createTable(String table, boolean largeUpdate, String[]... obj) {
        this.connect(true);
        String sql = "CREATE TABLE IF NOT EXISTS `" + table + "` ";

        for(int statement = 0; statement < obj.length; ++statement) {
            String[] object = obj[statement];

            String name = object[0];
            String type = object[1];
            if (statement == 0) {
                sql = sql + " (`" + name + "` " + type + ",";
            } else if (statement == obj.length - 1) {
                sql = sql + " `" + name + "` " + type + ")";
            } else {
                sql = sql + " `" + name + "` " + type + ",";
            }
        }

        Statement var8 = this.getStatement();
        this.executeUpdate(var8, largeUpdate, sql);
    }

    public ResultSet query(String sql) {
        try {
            return this.getStatement().executeQuery(sql);
        } catch (SQLException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public void executeUpdate(String sql) {
        try {
            this.getStatement().executeUpdate(sql);
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }

    private void executeUpdate(Statement statement, boolean largeUpdate, String sql) {
        try {
            if (largeUpdate) {
                statement.executeLargeUpdate(sql);
                return;
            }

            statement.executeUpdate(sql);
        } catch (SQLException var5) {
            var5.printStackTrace();
        }

    }

    private Statement getStatement() {
        this.connect(true);

        try {
            return this.cn.createStatement();
        } catch (SQLException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public MysqlResult selectResult(String table, int limit, Object[]... param) {
        List<Map<String, Object>> result = select(table, limit, param);

        return new MysqlResult(result);
    }

    public static Object[] o(String s, Object o) {
        return new Object[]{s, o};
    }

    public static String[] s(String s, String s2) {
        return new String[]{s, s2};
    }

}
