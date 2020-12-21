package drawing;

import Algorithm.planSort;
import MysqlTools.MysqlConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import Algorithm.*;
@WebServlet("/getStructure")
public class getStructure extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int project_id=Integer.parseInt(req.getParameter("project_id"));
            int user=Integer.parseInt(req.getParameter("user"));
            MysqlConnect connect=new MysqlConnect();
            int[][]preWorks=connect.getPreWorksArray(project_id,user);
            String json=new planSort(preWorks).parseJson();
            resp.setCharacterEncoding("utf-8");
            resp.getWriter().print(json);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

