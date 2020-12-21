package EditWork;

import MysqlTools.MysqlConnect;
import MysqlTools.Project_info;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deleteProject")
public class deleteProject  extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Project_info project=new Project_info();
        int id=Integer.parseInt(req.getParameter("project_id"));
        int user=Integer.parseInt(req.getParameter("user"));
        Project_info.populate(project,id,null,user,null);
        try {
            MysqlConnect connect=new MysqlConnect();
            if(connect.DeleteProject(project)){
                resp.getWriter().print("success");
            }else{
                resp.getWriter().print("fail");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
