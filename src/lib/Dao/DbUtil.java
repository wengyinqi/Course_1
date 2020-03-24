package lib.Dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by *** on 19-12-08.
 */
public class DbUtil {

    String url = "jdbc:mysql://localhost:3306/test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private String jdbcName="com.mysql.jdbc.Driver";

    public Connection getCon() throws Exception{
        Class.forName(jdbcName);
        Connection con = DriverManager.getConnection(url,"root","19990809");
        return con;
    }

    public void closeCon(Connection con) throws Exception{
        if(con!=null){
            con.close();
        }
    }

    public static void main(String[] args){
        DbUtil dbutil = new DbUtil();

        try {
            dbutil.getCon();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}