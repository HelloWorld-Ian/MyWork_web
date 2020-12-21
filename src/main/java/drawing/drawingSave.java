package drawing;

import MysqlTools.MysqlConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/drawingSave")
public class drawingSave  extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int user_id=Integer.parseInt(req.getParameter("user_id"));
        int project_id=Integer.parseInt(req.getParameter("project_id"));
        int id1=Integer.parseInt(req.getParameter("work_id1"));
        int id2=Integer.parseInt(req.getParameter("work_id2"));
        try {
            MysqlConnect connect=new MysqlConnect();
            if(!connect.switchPlan(user_id,project_id,id1,id2)){
                resp.getWriter().print("fail");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
