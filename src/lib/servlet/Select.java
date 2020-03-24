package lib.servlet;
/**
 * Created by jby on 19-12-08.
 */
import lib.Dao.DbUtil;
import lib.Dao.SelectDao;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(urlPatterns = "/select", name = "select")
public class Select extends HttpServlet {
    DbUtil dbutil = new DbUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fromdata = req.getParameter("fromdata");
        String id=req.getParameter("id");
        System.out.println("fromdata: "+fromdata);
        System.out.println("id: "+id);
        JSONArray ja = JSONArray.fromObject(fromdata);

        Connection con=null;
        try{
            con = dbutil.getCon();
            SelectDao sl=new SelectDao();
            JSONArray result=sl.select(ja,con,id);
            System.out.println(result.toString());
            resp.setContentType("text/javascript;charset=utf-8");
            resp.getWriter().write(result.toString());
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                dbutil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
//1.为什么选课这里信息都没有传给我 2.建议加课code1 减课code2