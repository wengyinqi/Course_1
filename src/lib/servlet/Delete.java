package lib.servlet;
/**
 * Created by jby on 19-12-08.
 */
import lib.Dao.DbUtil;
import net.sf.json.JSONObject;
import lib.Dao.DeleteDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

@WebServlet(urlPatterns = "/delete", name = "delete")
public class Delete extends HttpServlet {
    DbUtil dbutil = new DbUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fromdata = req.getParameter("fromdata");
        System.out.println(fromdata);
        JSONObject jo = JSONObject.fromObject(fromdata);
        Map<String, String> map = jo;

        Connection con=null;
        try{
            con=dbutil.getCon();
            DeleteDao dl=new DeleteDao();
            JSONObject res=dl.delete(map,con);
            System.out.println(res.toString());
            resp.setContentType("text/javascript;charset=utf-8");
            resp.getWriter().write(res.toString());
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
