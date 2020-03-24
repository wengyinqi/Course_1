package lib.Dao;

import lib.Model.Student;
import lib.Model.Teacher;
import lib.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jby on 19-12-08.
 */
public class UserDao {
    public User signin (Connection con, User user,int n) throws SQLException{
        User resultUser = null;
        PreparedStatement pstmt = null;
        try {
            String sql=null;
            if(n==0) {
                 sql = "select * from student where Snumber=? and Spassword=?";
            }else {
                 sql = "select * from teacher where Tnumber=? and Tpassword=?";
            }

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getPassword());
            ResultSet rs = pstmt.executeQuery();
            //System.out.println(rs);
            if (rs.next()){
                if(n==0) {
                    resultUser = new Student();
                    resultUser.setId(rs.getString("Snumber"));
                    resultUser.setPassword(rs.getString("Spassword"));
                    resultUser.setRole(n);
                    resultUser.setName(rs.getString("Sname"));
                    //System.out.println("从数据库里取出的汉字"+rs.getString("Sname"));
                   // restltUser.setEmail(rs.getString());
                }
                else{
                    resultUser = new Teacher();
                    resultUser.setId(rs.getString("Tnumber"));
                    resultUser.setPassword(rs.getString("Tpassword"));
                    resultUser.setRole(n);
                    resultUser.setName(rs.getString("Tname"));
                }
            }

            return resultUser;
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultUser;
    }
}
