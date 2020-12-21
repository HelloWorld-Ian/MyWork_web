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

@WebServlet("/UpdateProject")
public class UpdateProject extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id=Integer.parseInt(req.getParameter("id"));
        String name=req.getParameter("name");
        String time_type=req.getParameter("time_type");
        int user_id=Integer.parseInt(req.getParameter("user"));
        Project_info project=new Project_info();
        Project_info.populate(project,id,name,user_id,time_type);
        try {
            MysqlConnect connect=new MysqlConnect();
            if(connect.updateProject(project)){
                resp.getWriter().print("success");
            }else{
                resp.getWriter().print("fail");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
