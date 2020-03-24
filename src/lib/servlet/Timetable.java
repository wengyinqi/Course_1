package lib.servlet;
/**
 * Created by jby on 19-12-08.
 */
import lib.Dao.DbUtil;
import lib.Dao.TimetableDao;
import lib.Model.Course;
import lib.Model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/timetable", name = "timetable")
public class Timetable extends HttpServlet {
    DbUtil dbutil = new DbUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fromdata = req.getParameter("fromdata");
        System.out.println("fromdata: "+fromdata);
        User user = new User();
        user.setId(fromdata);
        Connection con = null;
        try {
            ArrayList<Course> allCourse= new ArrayList<Course>();
            con = dbutil.getCon();
            TimetableDao ttb=new TimetableDao();
            allCourse=ttb.getCourseTimetable(con,user);
            JSONArray allJsonObject=new JSONArray();

            for (Course course:allCourse) {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("title",course.getCourse_name());
                jsonObject.put("description",course.getDeacription());
                switch (course.getWeekday()){
                    case "星期一":jsonObject.put("daysOfWeek","['1']");
                    break;
                    case "星期二":jsonObject.put("daysOfWeek","['2']");
                        break;
                    case "星期三":jsonObject.put("daysOfWeek","['3']");
                        break;
                    case "星期四":jsonObject.put("daysOfWeek","['4']");
                        break;
                    case "星期五":jsonObject.put("daysOfWeek","['5']");
                        break;
                }
                jsonObject.put("startTime",course.getStart_time());
                jsonObject.put("endTime",course.getEnd_time());
                jsonObject.put("startRecur",course.getStart_recur());
                jsonObject.put("endRecur",course.getEnd_recur());
                jsonObject.put("textColor",course.getTextcolor());
                jsonObject.put("backgroundColor",course.getBackgroundcolor());
                jsonObject.put("borderColor",course.getBordercolor());
                allJsonObject.add(jsonObject);
            }
            resp.setContentType("text/javascript;charset=utf-8");
            resp.getWriter().write(allJsonObject.toString());
            System.out.println(allJsonObject.toString());

        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                dbutil.closeCon(con);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
