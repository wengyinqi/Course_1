package lib.Dao;
/**
 * Created by jby on 19-12-08.
 */
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SelectDao {
    public JSONArray select(JSONArray ja, Connection con,String id){
        JSONObject jo=null;
        Map<String,String> map=null;
        PreparedStatement pstmt=null;
        String sql=null;
        ResultSet rs=null;
        JSONArray result=new JSONArray();
        try{
        for(int i=0;i<ja.size();i++) {
            Set<String> has=new HashSet<>();
            map = ja.getJSONObject(i);
            sql="select * from sc where Cnumber="+map.get("course_id")+" and Corder="+map.get("course_seq")+" and Snumber="+id+";";
            pstmt=con.prepareStatement(sql);
            rs=pstmt.executeQuery();
            if(rs.next()){
                jo=new JSONObject();
                jo.put("course_id",rs.getString("Cnumber"));
                jo.put("course_seq",rs.getString("Corder"));
                jo.put("info","0");
                jo.put("message","该课已在您的课表！");
                result.add(jo);
                continue;
            }
            //计算该课程有多少学生选了
            sql="select count(Snumber) as co   from sc,course where course.Cnumber=sc.Cnumber and course.Corder=sc.Corder and course.Cnumber="+ map.get("course_id")+" and course.Corder="+map.get("course_seq")+";";
            ////System.out.println(sql);
            pstmt=con.prepareStatement(sql);
            rs=pstmt.executeQuery();
            int co=0;

            while(rs.next()){
                co=rs.getInt("co");
            }
            sql="Select Csection,Cweek  from sc,course where course.Cnumber=sc.Cnumber and course.Corder=sc.Corder and  sc.Snumber="+id+";";
            System.out.println(sql);
            pstmt=con.prepareStatement(sql);
            rs=pstmt.executeQuery();

            while(rs.next()){
                int tem = rs.getInt("Csection");
                int ta = tem % 100;
                int tb = tem / 100;
                for(int k=tb;k<=ta;k++)
                has.add(rs.getString("Cweek")+k);
                System.out.println("has 加入："+rs.getString("Cweek")+tb);
            }
            boolean fail=false;
            //查询课容量
            sql = "select * from course where Cnumber=" + map.get("course_id") + " and Corder=" + map.get("course_seq") + ";";
            pstmt = con.prepareStatement(sql);
            rs=pstmt.executeQuery();
            int capacity=0;
            while (rs.next()){
                int tem = rs.getInt("Csection");
                int ta = tem % 100;
                int tb = tem / 100;
                System.out.println("新加入课程："+rs.getString("Cweek")+tb);
                for(int k=tb;k<=ta;k++)
                if(has.contains(rs.getString("Cweek")+k)){
                    ////System.out.println("tb:"+tb);
                    fail=true;
                    break;
                }
                capacity=rs.getInt("Ccapacity");
            }
            if(fail){
                jo=new JSONObject();
                jo.put("course_id",map.get("course_id"));
                jo.put("course_seq",map.get("course_seq"));
                jo.put("info","0");
                jo.put("message","时间冲突，不能选课！");
                result.add(jo);
                continue;
            }
            //可能插入新纪录
            if(capacity-co!=0){
                sql="insert into sc(Snumber,Cnumber,Corder) values('"+id+"'"+","+"'"+map.get("course_id")+"'"+","+"'"+map.get("course_seq")+"'"+")"+";";
                ////System.out.println(sql);
                pstmt=con.prepareStatement(sql);
                pstmt.execute();
                jo=new JSONObject();
                jo.put("course_id",map.get("course_id"));
                jo.put("course_seq",map.get("course_seq"));
                jo.put("info","1");
                jo.put("message","选课成功！");
                result.add(jo);
            }
            else{
                jo=new JSONObject();
                jo.put("course_id",map.get("course_id"));
                jo.put("course_seq",map.get("course_seq"));
                jo.put("info","0");
                jo.put("message","系统出错，请重试！");
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
