package lib.Dao;
/**
 * Created by jby on 19-12-08.
 */
import lib.Model.Course;
import lib.Model.Teacher;
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

public class SearchDao {
    public JSONArray Search(Map<String,String> map, Connection con) throws SQLException {
        JSONArray jsa = new JSONArray();
        Course cr=new Course();
        Teacher tr=new Teacher();
       // //System.out.println("读取map数据");
        cr.setCourse_id(map.get("course_id"));//
        cr.setCourse_name(map.get("course_name"));//
        tr.setName(map.get("teacher_name"));
        cr.setCollege(map.get("college"));
        cr.setCampus(map.get("campus"));//
        cr.setSection(map.get("section"));//没有给我section
        cr.setAttribute(map.get("attribute"));//
        String week=map.get("weekday");//没有给我weekday
        String id=map.get("id");
        PreparedStatement pstmt=null;
        ////System.out.println(cr);
        ////System.out.println(tr);
        switch(week){
            case "0":cr.setWeekday("星期日");
            break;
            case "1":cr.setWeekday("星期一");
            break;
            case "2":cr.setWeekday("星期二");
            break;
            case "3":cr.setWeekday("星期三");
            break;
            case "4":cr.setWeekday("星期四");
            break;
            case "5":cr.setWeekday("星期五");
            break;
            case "6":cr.setWeekday("星期六");
            break;
        }
        try {
            String sql = null;
            sql="select distinct Cnumber,Corder ,Tname from tc,teacher where tc.Tnumber=teacher.Tnumber and teacher.Tname like ?";
            pstmt=con.prepareStatement(sql);
            //System.out.println("%"+tr.getName()+"%");
            pstmt.setString(1,"%"+tr.getName()+"%");
            ResultSet rs=pstmt.executeQuery();
            Set<Course> set1=new HashSet<Course>();
            Map<String,String> maptc=new HashMap<>();
            Course tmp=null;
            while(rs.next()){
                tmp=new Course();
                //System.out.println("老师数据加一");
                tmp.setCourse_id(rs.getString("Cnumber"));
                tmp.setCourse_seq(Integer.toString(rs.getInt("Corder")));
                set1.add(tmp);
                    //System.out.println("老师名字有变化");
                    tr.setName(rs.getString("Tname"));
                    //System.out.println(tr.getName());
                    maptc.put(rs.getString("Cnumber")+rs.getInt("Corder"),rs.getString("Tname"));
            }
            String selected=null;
            sql="select Cnumber,Corder from sc where Snumber="+id+";";
            pstmt=con.prepareStatement(sql);
            rs=pstmt.executeQuery();
            Set<String>set3=new HashSet<>();
            while(rs.next()){
                tmp=new Course();
                tmp.setCourse_id(rs.getString("Cnumber"));
                tmp.setCourse_seq(Integer.toString(rs.getInt("Corder")));
                set3.add(rs.getString("Cnumber")+rs.getString("Corder"));
                System.out.println("set3加上："+tmp.getCourse_id()+" "+tmp.getCourse_seq());
            }
            sql="select distinct Dname,Cnumber,Corder from course,department where course.Dnumber=department.Dnumber And Dname like ?";
            pstmt=con.prepareStatement(sql);
            //System.out.println("%"+cr.getCollege()+"%");
            pstmt.setString(1,"%"+cr.getCollege()+"%");
            rs=pstmt.executeQuery();
            Set<Course> set2=new HashSet<Course>();
            Map<String,String> mapdc=new HashMap<>();
            while(rs.next()){
                tmp=new Course();
                tmp.setCourse_id(rs.getString("Cnumber"));
                tmp.setCourse_seq(Integer.toString(rs.getInt("Corder")));

                    //System.out.println("学院名字有变化了");
                    cr.setCollege(rs.getString("Dname"));
                    //System.out.println(cr.getCollege());
                    mapdc.put(rs.getString("Cnumber")+rs.getString("Corder"),rs.getString("Dname"));
                set2.add(tmp);
            }
            sql = "select * from course where Cnumber like ? AND Cname like ? AND Cschool like ? AND Cproperty like ? AND Cweek like ?  ";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,"%"+cr.getCourse_id()+"%");
            pstmt.setString(2,"%"+cr.getCourse_name()+"%");
            pstmt.setString(3,"%"+cr.getCampus()+"%");
            pstmt.setString(4,"%"+cr.getAttribute()+"%");
            pstmt.setString(5,"%"+cr.getWeekday()+"%");
            rs = pstmt.executeQuery();
            JSONObject js = null;
            while(rs.next()) {
                //System.out.println("有课啊");
                tmp=new Course();
                tmp.setCourse_id(rs.getString("Cnumber"));
                tmp.setCourse_seq(Integer.toString(rs.getInt("Corder")));
                String sec=null;
                boolean p=false;
                if(!cr.getSection().equals("")) {
                    //System.out.println("是的");
                    int tem = rs.getInt("Csection");
                    int ta = tem % 100;
                    int tb = tem / 100;
                    if (Integer.parseInt(cr.getSection()) >= tb && Integer.parseInt(cr.getSection()) <= ta) {
                        p = true;
                        //System.out.println("yes");
                    }
                }
                else
                    p=true;
                //System.out.println("set1?"+set1.contains(tmp));
                //System.out.println("set2?"+set2.contains(tmp));
                if(p&&set1.contains(tmp)&&set2.contains(tmp)){
                    /*
                    if(tr.getName().equals("")){
                        sql="select distinct Tname from tc,teacher where "+rs.getString("Cnumber")+"=Cnumber and teacher.Tnumber=tc.Tnumber";
                        PreparedStatement ppp=con.prepareStatement(sql);
                        ResultSet rrr=ppp.executeQuery();
                        if(rrr.next())
                            tr.setName(rrr.getString("Tname"));
                    }
                    if(cr.getCollege().equals("")){

                        sql="select distinct Dname from department,course where department.Dnumber=course.Dnumber and Cnumber="+rs.getString("Cnumber");
                        PreparedStatement ppp=con.prepareStatement(sql);
                        ResultSet rrr=ppp.executeQuery();
                        if(rrr.next())
                            cr.setCollege(rrr.getString("Dname"));
                    }
                    */
                    if(set3.contains(tmp.getCourse_id()+tmp.getCourse_seq()))
                        selected="1";
                    else
                        selected="0";
                    js=new JSONObject();
                    Course crp = new Course();
                    int tem = rs.getInt("Csection");
                    int ta = tem % 100;
                    int tb = tem / 100;
                    sec=tb+"-"+ta+"节";
                    js.put("weekday",rs.getString("Cweek"));
                    js.put("attribute",rs.getString("Cproperty"));
                    js.put("campus",rs.getString("Cschool"));
                    js.put("course_name",rs.getString("Cname"));
                    js.put("course_id",rs.getString("Cnumber"));
                    js.put("building",rs.getString("Cfoor"));
                    js.put("capacity",rs.getInt("Ccapacity"));
                    js.put("classroom",rs.getString("Croom"));
                    js.put("course_seq",rs.getInt("Corder"));
                    js.put("credit",rs.getInt("Ccredit"));
                    js.put("section",sec);
                    js.put("duration",rs.getString("Ctime"));
                    js.put("teacher_name",maptc.get(rs.getString("Cnumber")+rs.getInt("Corder")));
                    js.put("college",mapdc.get(rs.getString("Cnumber")));
                    js.put("selected",selected);
                    //添加json项
                    jsa.add(js);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return jsa;
    }
}
