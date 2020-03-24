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

@WebServlet(urlPatterns = "/course_manage", name = "course_manage")
public class CourseManage extends HttpServlet {
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


            allCourse=courseDao.getCourseInfo(con,user);

                //HttpSession session = req.getSession();
                //session.setAttribute("currentUser", currentUser);
                //JSONObject []jsonObject = new JSONObject[100];
            JSONArray allJsonObject=new JSONArray();

            for (Course course:allCourse) {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("id",course.getCourse_id());
                jsonObject.put("seq",course.getOrder());
                jsonObject.put("name",course.getCourse_name());
                jsonObject.put("attribute",course.getAttribute());
                jsonObject.put("credit",course.getCredit());
                jsonObject.put("description","");
                jsonObject.put("refer","");
                jsonObject.put("week_start",course.getWeek_start());
                jsonObject.put("week_end",course.getWeek_end());
                jsonObject.put("session", course.getSession().toString());
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
