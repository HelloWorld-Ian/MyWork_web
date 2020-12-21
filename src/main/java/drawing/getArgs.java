package drawing;

import Algorithm.planSort;
import MysqlTools.MysqlConnect;
import MysqlTools.Project_info;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/getArgs")
public class getArgs extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int project_id=Integer.parseInt(req.getParameter("project_id"));
        int user=Integer.parseInt(req.getParameter("user"));
        MysqlConnect connect= null;
        Project_info project=new Project_info();
        Project_info.populate(project,project_id,null,user,null);
        try {
            connect = new MysqlConnect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        assert connect != null;
        int[][]preWorks=connect.getPreWorksArray(project_id,user);
        planSort planSort=new planSort(preWorks);
        String json=planSort.parseArgs(planSort.getArgs(project,connect));
        resp.getWriter().print(json);
    }
}
