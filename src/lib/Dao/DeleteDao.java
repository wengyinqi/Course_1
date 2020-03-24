package lib.Dao;

/**
 * Created by jby on 19-12-08.
 */
import lib.Model.Course;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DeleteDao {
    public JSONArray selected(String id, Connection con ) throws SQLException {
        JSONArray ja=new JSONArray();
        JSONObject jo=null;
        String sql;
        PreparedStatement pstmt=null;
        ResultSet rs;
        try {

            sql = "select * from sc  where  sc.Snumber=" + id + ";";
            pstmt = con.prepareStatement(sql);
            rs=pstmt.executeQuery();
            Course cr=null;
            Set<Course> set1=new HashSet<>();
            while(rs.next()){
                cr=new Course();
                cr.setCourse_seq(rs.getString("Corder"));
                cr.setCourse_id(rs.getString("Cnumber"));
                set1.add(cr);
            }
            Map<String,String> map=new HashMap<String, String>();
            for(Course tmp:set1) {
                sql = "select Tname from teacher,tc where tc.Tnumber=teacher.Tnumber and tc.Cnumber=" +tmp.getCourse_id()+" and tc.Corder="+tmp.getCourse_seq()+";";
                System.out.println(sql);
                pstmt=con.prepareStatement(sql);
                rs=pstmt.executeQuery();
                if(rs.next()){
                    map.put("Tname"+tmp.getCourse_id()+tmp.getCourse_seq(),rs.getString("Tname"));
                }
                sql="select Dname from course,department where course.Dnumber=department.Dnumber and Cnumber=  "+tmp.getCourse_id()+" and Corder="+tmp.getCourse_seq()+";";
                pstmt=con.prepareStatement(sql);
                rs=pstmt.executeQuery();
                if(rs.next()){
                    map.put("Dname"+tmp.getCourse_id()+tmp.getCourse_seq(),rs.getString("Dname"));
                }
                sql="select * from course where Cnumber="+tmp.getCourse_id()+" and Corder="+tmp.getCourse_seq()+";";
                System.out.println(sql);
                pstmt=con.prepareStatement(sql);
                rs=pstmt.executeQuery();
                if(rs.next()){
                    jo=new JSONObject();
                    int tem = rs.getInt("Csection");
                    int ta = tem % 100;
                    int tb = tem / 100;
                    jo.put("id",rs.getString("Cnumber"));
                    jo.put("seq",rs.getString("Corder"));
                    jo.put("name",rs.getString("Cname"));
                    jo.put("credit",rs.getString("Ccredit"));
                    jo.put("attribute",rs.getString("Cproperty"));
                    jo.put("college",map.get("Dname"+rs.getString("Cnumber")+rs.getString("Corder")));
                    jo.put("teacher",map.get("Tname"+rs.getString("Cnumber")+rs.getString("Corder")));
                    jo.put("pos",rs.getString("Cschool")+rs.getString("Cfoor")+rs.getString("Croom"));
                    jo.put("duration",rs.getString("Ctime"));
                    jo.put("section",tb+"-"+ta+"节");
                    ja.add(jo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ja;
    }
    public JSONObject delete(Map<String, String>map, Connection con)throws SQLException{
        JSONObject jo=new JSONObject();
        String sql;
        PreparedStatement pstmt=null;
        ResultSet rs;
        try{
            sql="delete from sc where Snumber="+map.get("user_id")+" and Cnumber="+map.get("course_id")+" and Corder="+map.get("course_seq")+";";
            System.out.println(sql);
            pstmt=con.prepareStatement(sql);
            pstmt.execute();
            jo.put("info","1");
        } catch (Exception e) {
            jo.put("info","0");
            e.printStackTrace();
        }
        return jo;
    }
}
