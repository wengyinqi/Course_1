package lib.servlet;
/**
 * Created by jby on 19-12-08.
 */
import lib.Dao.DbUtil;
import net.sf.json.JSONArray;
import lib.Dao.DeleteDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(urlPatterns = "/selected", name = "selected")
public class Selected extends HttpServlet {
    DbUtil dbutil = new DbUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        System.out.println(id);


        Connection con=null;
        try{
            con=dbutil.getCon();
            DeleteDao dl=new DeleteDao();
            JSONArray ja=dl.selected(id,con);
            System.out.println(ja.toString());
            resp.setContentType("text/javascript;charset=utf-8");
            resp.getWriter().write(ja.toString());
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
