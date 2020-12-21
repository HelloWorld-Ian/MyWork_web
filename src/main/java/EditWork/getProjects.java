package EditWork;

import MysqlTools.MysqlConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/getProjects")
public class getProjects extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            MysqlConnect connect=new MysqlConnect();
            int id=Integer.parseInt(req.getParameter("user_id"));
            String json=connect.getUserProjects(id);
            resp.setCharacterEncoding("utf-8");
            resp.getWriter().print(json);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
