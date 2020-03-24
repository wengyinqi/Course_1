package lib.servlet;
/**
 * Created by wyq on 19-12-08.
 */
import lib.Dao.CourseDao;
import lib.Dao.DbUtil;
import lib.Dao.UserDao;
import lib.Model.Course;
import lib.Model.Student;
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
@WebServlet(urlPatterns = "/course_Detail", name = "course_Detail")
public class CourseDetail extends HttpServlet {
    DbUtil dbutil = new DbUtil();
    UserDao userDao = new UserDao();

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
        Course course=new Course();
        CourseDao courseDao=new CourseDao();
        course.setCourse_id(map.get("id"));
        course.setCourse_seq(map.get("seq"));




        Connection con = null;
        try {
            ArrayList<Student> allStudent= new ArrayList<Student>();

            System.out.println("开始连接数据库");
            con = dbutil.getCon();
            System.out.println("数据库连接成功");

            allStudent=courseDao.t_Course_Detail(con,course);
            JSONArray allJsonObject=new JSONArray();

            for (Student student:allStudent) {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("id",student.getId());
                jsonObject.put("name",student.getName());
                jsonObject.put("college",student.getDepartment());
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
