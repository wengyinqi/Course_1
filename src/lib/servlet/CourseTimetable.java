package lib.servlet;
/**
 * Created by wyq on 19-12-08.
 */
import lib.Dao.CourseDao;
import lib.Dao.DbUtil;
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
import java.util.Map;

@WebServlet(urlPatterns = "/course_timetable", name = "course_timetable")
public class CourseTimetable extends HttpServlet {
    DbUtil dbutil = new DbUtil();
    CourseDao courseDao = new CourseDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fromdata = req.getParameter("fromdata");
        System.out.println(fromdata);
        JSONObject jo=JSONObject.fromObject(fromdata);
        Map<String,String> map= (Map<String, String>) jo;
        String id=map.get("id");
        System.out.println("id="+id);
        // int len=id.length();

        //String password=map.get("password");

        User user = new User();
        user.setId(id);
        Connection con = null;
        try {
            ArrayList<Course> allCourse= new ArrayList<Course>();

            System.out.println("开始连接数据库");
            con = dbutil.getCon();
            System.out.println("数据库连接成功");


            allCourse=courseDao.getCourseTimetable(con,user);

            //HttpSession session = req.getSession();
            //session.setAttribute("currentUser", currentUser);
            //JSONObject []jsonObject = new JSONObject[100];
            JSONArray allJsonObject=new JSONArray();




            for (Course course:allCourse) {
                JSONObject jsonObject=new JSONObject();
                System.out.println(course.getWeekday());
                switch (course.getWeekday()){
                    case "1":jsonObject.put("daysOfWeek","['1']");
                        break;
                    case "2":jsonObject.put("daysOfWeek","['2']");
                        break;
                    case "3":jsonObject.put("daysOfWeek","['3']");
                        break;
                    case "4":jsonObject.put("daysOfWeek","['4']");
                        break;
                    case "5":jsonObject.put("daysOfWeek","['5']");
                        break;
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
                    default:
                        break;

                }



                jsonObject.put("title",course.getCourse_name());
                jsonObject.put("description",course.getDeacription());
                //jsonObject.put("daysOfWeek",course.getWeekday());
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
