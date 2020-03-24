package lib.servlet;

/**
 * Created by wyq on 19-12-08.
 */
import lib.Dao.DbUtil;
import lib.Dao.InfoDao;
import lib.Model.Student;
import lib.Model.Teacher;
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

@WebServlet(urlPatterns = "/info", name = "info")
public class Info extends HttpServlet {
    DbUtil dbutil = new DbUtil();
    InfoDao infoDao = new InfoDao();

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
        String id=map.get("id");
        int len=id.length();

        //String password=map.get("password");

        User user = new User();
        user.setId(id);
        Connection con = null;
        try {
            Student currentStudent=null;
            Teacher currentTeacher=null;
            User currentUser=null;
            if(len==5||len==13){
                System.out.println("开始连接数据库");
                con = dbutil.getCon();
                System.out.println("数据库连接成功");
                int l=len==13?0:1;
                if(l==0){

                    currentStudent= infoDao.getStudentInfo(con, user);
                    currentUser=(User)currentStudent;

                }

                else{

                    currentTeacher=infoDao.getTeacherInfo(con,user);
                    currentUser=(User)currentTeacher;
                }

            }
            if (currentUser==null){
                System.out.println("出错");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", "");
                jsonObject.put("name", "");
                jsonObject.put("message", "Id errors.");
                jsonObject.put("ur","");
                resp.getWriter().write(jsonObject.toString());
                System.out.println(jsonObject.toString());
            }
            else {
                HttpSession session = req.getSession();
                session.setAttribute("currentUser", currentUser);
                JSONObject jsonObject = new JSONObject();

                //jsonObject.put("ur", "teacher/index_teacher.html");



                if (currentUser.getRole() == 0) {
                    jsonObject.put("id", currentStudent.getId());
                    jsonObject.put("name", currentStudent.getName());
                    jsonObject.put("gender",currentStudent.getSex());
                    jsonObject.put("university","四川大学");
                    jsonObject.put("college",currentStudent.getDepartment());
                    jsonObject.put("major",currentStudent.getMajor());
                    jsonObject.put("highschool",currentStudent.getHighschool());
                    jsonObject.put("birthplace",currentStudent.getOrigo());
                    jsonObject.put("tel",currentStudent.getTellphone());
                    jsonObject.put("email",currentStudent.getEmail());
                    jsonObject.put("hobby",currentStudent.getHobby());
                    jsonObject.put("message", "success!");

                    //jsonObject.put("ur", "student/index_student.html");
                } else if(currentUser.getRole() == 1){
                    jsonObject.put("id", currentTeacher.getId());
                    jsonObject.put("name", currentTeacher.getName());
                    jsonObject.put("gender",currentTeacher.getTsex());
                    jsonObject.put("university","四川大学");
                    jsonObject.put("college",currentTeacher.getCollege());
                    jsonObject.put("department",currentTeacher.getDepartment());
                    jsonObject.put("post",currentTeacher.getTpost());
                    jsonObject.put("office",currentTeacher.getTplace());
                    jsonObject.put("tel",currentTeacher.getTphone());
                    jsonObject.put("email",currentTeacher.getTemail());
                    jsonObject.put("interest",currentTeacher.getTresearch());
                    jsonObject.put("personal_web",currentTeacher.getTwebsite());
                    jsonObject.put("zhiwu",currentTeacher.getTzhiwu());
                    jsonObject.put("intro",currentTeacher.getTselfsummary());
                    jsonObject.put("honor",currentTeacher.getTachievement());
                    jsonObject.put("message", "success!");
                    //jsonObject.put("ur", "teacher/index_teacher.html");
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
