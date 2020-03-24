package lib.Dao;
/**
 * Created by jby on 19-12-08.
 */
import lib.Model.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import java.sql.Array;

public class CourseDao {

    public ArrayList<Course> getCourseInfo(Connection con, User user) throws SQLException{
        ArrayList<Course> allCourse=new ArrayList<Course>();
        PreparedStatement pstmt = null;
        try{
            String sql=null;
            sql="select course.Cweek,course.Cschool,course.Cfoor,course.Croom,course.Csection,course.Cname,course.Cnumber,course.Corder,course.Cproperty,course.Ccredit,course.Ctime from course,tc where tc.Tnumber=? and tc.Cnumber=course.Cnumber";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getId());
            ResultSet rs = pstmt.executeQuery();
            //System.out.println(user.getId());






            while(rs.next()){
                Course course=new Course();
                JSONArray session=course.getSession();
                JSONObject temp=new JSONObject();

                String temp2=rs.getString("Ctime");
                System.out.println("temp2:"+temp2);
                String[] split1=temp2.split("-");
                System.out.println("split1[0]"+split1[0]);
                String ws=split1[0].trim();
                String[] split2=split1[1].split("周");
                System.out.println("split2[0]"+split2[0]);
                String we=split2[0].trim();
                System.out.println("here"+ws+"\n"+we+"what");




                temp.put("date",rs.getString("Cweek"));
                temp.put("session_start",rs.getString("Csection").charAt(0));
                temp.put("session_end",rs.getString("Csection").charAt(rs.getString("Csection").length()-1)+"节");
                temp.put("pos",rs.getString("Cschool")+rs.getString("Cfoor")+rs.getString("Croom"));
                session.add(temp);
                course.setCourse_name(rs.getString("Cname"));
                course.setCourse_id(rs.getString("Cnumber"));
                course.setOrder(rs.getInt("Corder"));
                course.setAttribute(rs.getString("Cproperty"));
                course.setCredit(rs.getInt("Ccredit"));
                course.setWeek_start(ws);
                course.setWeek_end(we);
                course.setSession(session);
                allCourse.add(course);
            }
            rs.close();
            pstmt.close();
            return allCourse;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return allCourse;
    }
    public Course course_add(Connection con,Course course) throws SQLException{
        PreparedStatement pstmt=null;
        Course resultCourse=null;
        ClassSet classSet=new ClassSet();
        try{
            String id="";
            String seq="";

            Random random=new Random();
            while(id.length()<9){
                id=id+random.nextInt(10);
            }
            while(seq.length()<2){
                seq=seq+random.nextInt(10);
            }
            int credit=random.nextInt(5);
            int capacity=50+random.nextInt(50);
            //int seq=Integer.parseInt(temp);
            String tempsql="select count(*) from tc";
            pstmt = con.prepareStatement(tempsql);
           // pstmt.setString(1,course.getTeacher_id());
            ResultSet rs = pstmt.executeQuery();
            int count=0;
            while(rs.next()){
                count=rs.getInt(1);
            }
            count++;

            ArrayList<String> cSchool=classSet.getClassSchool();
            ArrayList<String> cFloor=classSet.getClassFloor();
            int cs=random.nextInt(2);
            int cf=random.nextInt(6);

            String tempsql2="select Dnumber from teacher where teacher.Tnumber=?";
            pstmt = con.prepareStatement(tempsql2);
            pstmt.setString(1,course.getTeacher_id());
            ResultSet rs2 = pstmt.executeQuery();
            String Dnumber="";
            if(rs2.next()){
                Dnumber=rs2.getString("Dnumber");
            }



            String sql2course="insert into course(Cnumber,Cname,Corder,Ccredit,Cproperty,Ccapacity," +
                    "Ctime,Cweek,Csection,Cschool,Cfoor,Croom,Dnumber,Mnumber)" +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pstmt = con.prepareStatement(sql2course);
            pstmt.setString(1, id);
            pstmt.setString(2,course.getCourse_name());
            pstmt.setInt(3, Integer.parseInt(seq));
            pstmt.setInt(4,credit);
            pstmt.setString(5,course.getAttribute());
            pstmt.setInt(6,capacity);
            pstmt.setString(7,course.getWeek_start()+"-"+course.getWeek_end()+"周");
            pstmt.setString(8,course.getWeekday());
            pstmt.setInt(9,Integer.parseInt(course.getDuration()));
            pstmt.setString(10,cSchool.get(cs));
            pstmt.setString(11,cFloor.get(cf));
            pstmt.setString(12,classSet.getRandom());
            pstmt.setString(13,Dnumber);
            pstmt.setString(14,"3040000001");
            int rs2course = pstmt.executeUpdate();

            String sql2tc="insert into tc(Tnumber,Cnumber,Corder) values (?,?,?)";
            pstmt = con.prepareStatement(sql2tc);
            //pstmt.setString(1,count+"");
            pstmt.setString(1,course.getTeacher_id());
            pstmt.setString(2,id);
            pstmt.setInt(3,Integer.parseInt(seq));
            int rs2tc=pstmt.executeUpdate();
            if(rs2course>0&&rs2tc>0){
                resultCourse=new Course();
                resultCourse.setCourse_id(id);
                resultCourse.setCourse_seq(seq);
            }


            rs.close();
            pstmt.close();
            return resultCourse;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return resultCourse;
    }
    public boolean t_Course_Delete(Connection con,Course course) throws SQLException{
        boolean judge=false;
        PreparedStatement pstmt=null;
        try{
            System.out.println(course.getCourse_id()+"\n"+course.getCourse_seq());
            String sql2="delete from tc where Cnumber=? and Corder=?";
            pstmt = con.prepareStatement(sql2);
            pstmt.setString(1, course.getCourse_id());
            pstmt.setString(2,course.getCourse_seq());
            int rs2 = pstmt.executeUpdate();
            String sql1="delete from course where Cnumber=? and Corder=?";
            pstmt = con.prepareStatement(sql1);
            pstmt.setString(1, course.getCourse_id());
            pstmt.setString(2,course.getCourse_seq());
            int rs1 = pstmt.executeUpdate();

            if(rs1>0&&rs2>0){
                judge=true;
            }
            pstmt.close();
            return judge;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return judge;

    }
    public boolean t_Course_Edit(Connection con,Course course) throws SQLException{
        boolean judge=false;
        PreparedStatement pstmt=null;
        try {
            String sql2course="update course set Cname=?,Cproperty=?," +
                    "                      Ctime=?,Cweek=?,Csection=?" +
                    "                        where Cnumber=? and Corder=?";
            pstmt = con.prepareStatement(sql2course);
            pstmt.setString(1, course.getCourse_name());
            pstmt.setString(2,course.getAttribute());
            pstmt.setString(3,course.getWeek_start()+"-"+course.getWeek_end()+"周");
            pstmt.setString(4,course.getWeekday());
            pstmt.setString(5,course.getDuration());
            pstmt.setString(6,course.getCourse_id());
            pstmt.setString(7,course.getCourse_seq());
            int rs2course = pstmt.executeUpdate();

            /*String sql2tc="update tc set ,Tnumber,Cnumber,Corder) values (?,?,?,?)";
            pstmt = con.prepareStatement(sql2tc);
            pstmt.setString(1,count+"");
            pstmt.setString(2,course.getTeacher_id());
            pstmt.setString(3,id);
            pstmt.setString(4,seq);
            int rs2tc=pstmt.executeUpdate();*/
            if(rs2course>0){
                judge=true;
            }
            pstmt.close();

            return judge;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return judge;
    }
    public ArrayList<Student> t_Course_Detail(Connection con, Course course) throws SQLException{
        PreparedStatement pstmt=null;
        ArrayList<Student> allStudent=new ArrayList<Student>();
        try{
            String sql="select student.Snumber,student.Sname,department.Dname from student,sc,department where sc.Snumber=student.Snumber and sc.Cnumber=? and student.Dnumber=department.Dnumber";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,course.getCourse_id());
            //pstmt.setString(2,course.getCourse_seq());
            ResultSet rs=pstmt.executeQuery();
            while(rs.next()){
                Student student=new Student();
                student.setId(rs.getString("Snumber"));
                student.setName(rs.getString("Sname"));
                student.setDepartment(rs.getString("Dname"));
                allStudent.add(student);
            }


            rs.close();
            pstmt.close();
            return allStudent;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return allStudent;

    }
    public ArrayList<Course> getCourseTimetable(Connection con,User user){
        ArrayList<Course> allCourse=new ArrayList<Course>();
        PreparedStatement pstmt = null;
        try{
            String sql=null;
            //sql="select course.Cname,course.Cweek,course.Cschool,course.Cfoor,course.Croom,course.Csection,course.Ctime from course,sc where sc.Snumber="+user.getId()+" and sc.Cnumber=course.Cnumber";
            sql="select course.Cname,course.Cweek,course.Cschool,course.Cfoor,course.Croom,course.Csection,course.Ctime,teacher.Tname from course,tc,teacher where teacher.Tnumber=? and tc.Tnumber=? and tc.Cnumber=course.Cnumber";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getId());
            pstmt.setString(2,user.getId());

            ResultSet rs = pstmt.executeQuery();
            Time time=new Time();
            WeekDate weekDate=new WeekDate();
            // ClassSet classroom=new ClassSet();
            //Color color=new Color();
            ArrayList<String> starttime=time.getStartTime();
            ArrayList<String> endtime=time.getEndTime();
            ArrayList<String> startWeekTime=weekDate.getWeekStartDate();
            ArrayList<String> endWeekTime=weekDate.getWeekEndDate();
            // ArrayList<String> school=classroom.getClassSchool();
            // ArrayList<String> floor=classroom.getClassFloor();
            //ArrayList<String> textcolor=color.getTextcolor();
            //ArrayList<String> bcolor=color.getBcolor();







            while(rs.next()){

                String temp=String.valueOf(rs.getInt("Csection"));
                char start=temp.charAt(0);
                char end=temp.charAt(temp.length()-1);
                String section=start+"-"+end;
                int s=start-'0';
                int e=end-'0';
                String temp2=String.valueOf(rs.getString("Ctime"));
                String[] split=temp2.split("-");
                System.out.println(split[0]+","+split[1]);

                String regEx="[^0-9]";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(split[0]);
                int ws=Integer.parseInt(m.replaceAll(""));
                Pattern p2 = Pattern.compile(regEx);
                Matcher m2 = p2.matcher(split[1]);
                int we=Integer.parseInt(m2.replaceAll(""));
                System.out.println("ws: "+ws);
                System.out.println("we: "+we);





                Course course=new Course();
                course.setCourse_name(rs.getString("Cname"));
                course.setWeekday(rs.getString("Cweek"));
                course.setDeacription(rs.getString("Tname")+" "+section+"节\n "+rs.getString("Cschool")+rs.getString("Cfoor")+rs.getString("Croom"));
                course.setStart_time(starttime.get(s-1));
                course.setEnd_time(endtime.get(e-1));
                course.setStart_recur(startWeekTime.get(ws-1));
                course.setEnd_recur(endWeekTime.get(we-1));
                course.setTextcolor("white");
                course.setBackgroundcolor("#3c8dbc");
                course.setBordercolor("3c8dbc");
                allCourse.add(course);
            }
            rs.close();
            pstmt.close();
            return allCourse;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return allCourse;
    }


}
    
