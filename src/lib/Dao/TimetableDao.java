package lib.Dao;

/**
 * Created by jby on 19-12-08.
 */
import lib.Model.Course;
import lib.Model.Time;
import lib.Model.User;
import lib.Model.WeekDate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import java.sql.Array;

public class TimetableDao {
    public ArrayList<Course> getCourseTimetable(Connection con,User user){
        System.out.println("进入Dao");
        ArrayList<Course> allCourse=new ArrayList<Course>();
        PreparedStatement pstmt = null;
        ResultSet rs;
        try{
            String sql=null;
            sql="select Tname,course.Cnumber,course.Corder from teacher, tc,course where teacher.Tnumber=tc.Tnumber and tc.Cnumber=course.Cnumber and tc.Corder=course.Corder;";
            pstmt=con.prepareStatement(sql);
            rs=pstmt.executeQuery();
            Map<String,String> map=new HashMap<>();
            while(rs.next()){
                map.put(rs.getString("Cnumber")+rs.getString("Corder"),rs.getString("Tname"));
            }
            sql="select course.Cnumber,course.Corder,course.Cname,course.Cweek,course.Cschool,course.Cfoor,course.Croom,course.Csection,course.Ctime from course,sc where sc.Snumber="+user.getId()+" and sc.Cnumber=course.Cnumber and sc.Corder=course.Corder;";
            System.out.println(sql);
            pstmt = con.prepareStatement(sql);
             rs = pstmt.executeQuery();
            Time time=new Time();
            WeekDate weekDate=new WeekDate();
            ArrayList<String> starttime=time.getStartTime();
            ArrayList<String> endtime=time.getEndTime();
            ArrayList<String> startWeekTime=weekDate.getWeekStartDate();
            ArrayList<String> endWeekTime=weekDate.getWeekEndDate();
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
                course.setDeacription(map.get(rs.getString("Cnumber")+rs.getString("Corder"))+" "+section+"节\n "+rs.getString("Cschool")+rs.getString("Cfoor")+rs.getString("Croom"));
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
