package lib.servlet;
/**
 * Created by jby on 19-12-08.
 */
import lib.Dao.DbUtil;
import lib.Dao.UserDao;
import lib.Model.User;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;


@WebServlet(urlPatterns = "/signin", name = "signin")
public class Signin extends HttpServlet {
    DbUtil dbutil = new DbUtil();
    UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.setContentType("text/javascript;charset=utf-8");

        String fromdata = req.getParameter("fromdata");
        System.out.println(fromdata);
        JSONObject jo=JSONObject.fromObject(fromdata);
        Map<String,String> map=jo;
        String id=map.get("id");
        int len=id.length();

        String password=map.get("password");

        User user = new User(id, password);
        Connection con = null;
        try {
            User currentUser=null;
            if(len==5||len==13){
            System.out.println("开始连接数据库");
            con = dbutil.getCon();
            System.out.println("数据库连接成功");
            int l=len==13?0:1;
            currentUser= userDao.signin(con, user,l);
            }
            if (currentUser == null){
                System.out.println("登陆出错");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", "");
                jsonObject.put("name", "");
                jsonObject.put("message", "Id or password errors.");
                jsonObject.put("ur","");
                resp.getWriter().write(jsonObject.toString());
                System.out.println(jsonObject.toString());
            }
            else {
                HttpSession session = req.getSession();
                session.setAttribute("currentUser", currentUser);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", currentUser.getId());
                jsonObject.put("name", currentUser.getName());
                jsonObject.put("message", "success!");
                //jsonObject.put("ur", "teacher/index_teacher.html");



                if (currentUser.getRole() == 0) {
                    jsonObject.put("ur", "student/index_student.html");
                } else  if(currentUser.getRole() == 1){
                    jsonObject.put("ur", "teacher/index_teacher.html");
                }
                resp.setContentType("text/javascript;charset=utf-8");
                resp.getWriter().write(jsonObject.toString());
                System.out.println(jsonObject.toString());
            }
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
