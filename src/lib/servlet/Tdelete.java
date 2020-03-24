package lib.servlet;
/**
 * Created by wyq on 19-12-08.
 */
import lib.Dao.CourseDao;
import lib.Dao.DbUtil;
import lib.Dao.UserDao;
import lib.Model.Course;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

@WebServlet(urlPatterns = "/t_delete", name = "t_delete")
public class Tdelete extends HttpServlet {
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
        Map<String,String> map=jo;
        Course course=new Course();
        CourseDao courseDao=new CourseDao();

        course.setCourse_id(map.get("course_id"));
        course.setCourse_seq(map.get("course_seq"));
        Connection con = null;
        try {


                System.out.println("开始连接数据库");
                con = dbutil.getCon();
                System.out.println("数据库连接成功");
                JSONObject jsonObject=new JSONObject();
                if(courseDao.t_Course_Delete(con,course)) {
                    jsonObject.put("info",'1');
                    System.out.println("删除成功");
                }
                else {
                    jsonObject.put("info",'0');
                    System.out.println("删除失败");
                }
            resp.setContentType("text/javascript;charset=utf-8");
            resp.getWriter().write(jsonObject.toString());
            System.out.println(jsonObject.toString());


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

