package lib.Dao;

/**
 * Created by jby on 19-12-08.
 */
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

public class StuSelectDao {
    public JSONArray select(JSONArray ja, Connection con,String id){
        JSONObject jo=null;
        Map<String,String> map=null;
        PreparedStatement pstmt=null;
        String sql=null;
        ResultSet rs=null;
        JSONArray result=new JSONArray();
        try{
            for(int i=0;i<ja.size();i++) {
                map = ja.getJSONObject(i);
                //计算sc里已经有了多少记录
                sql="select count(scnumber) from sc ;";
                pstmt=con.prepareStatement(sql);
                rs=pstmt.executeQuery();
                int all=0;
                while (rs.next()){
                    all=rs.getInt("scnumber");
                }
                //计算该课程有多少学生选了
                sql="select count(Snumber) as co form sc where Cnumber="+ map.get("course_id")+" and Corder="+map.get("course_seq")+";";
                pstmt=con.prepareStatement(sql);
                rs=pstmt.executeQuery();
                int co=0;
                while(rs.next()){
                    co=rs.getInt("co");
                }
                //查询课容量
                sql = "select * from course where Cnumber=" + map.get("course_id") + " and Corder=" + map.get("course_seq") + ";";
                pstmt = con.prepareStatement(sql);
                rs=pstmt.executeQuery();
                int capacity=0;
                while (rs.next()){
                    capacity=rs.getInt("Ccapacity");
                }
                //可能插入新纪录
                if(capacity-co!=0){
                    sql="insert into sc values("+(all-1)+","+id+","+map.get("Cnumber")+","+map.get("Corder")+";";
                    pstmt=con.prepareStatement(sql);
                    pstmt.executeQuery();
                    jo=new JSONObject();
                    jo.put("course_id",map.get("course_id"));
                    jo.put("course_seq",map.get("course_seq"));
                    jo.put("info","1");
                    result.add(jo);
                }
                else{
                    jo=new JSONObject();
                    jo.put("course_id",map.get("course_id"));
                    jo.put("course_seq",map.get("course_seq"));
                    jo.put("info","0");
                    result.add(jo);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result ;
    }
}
