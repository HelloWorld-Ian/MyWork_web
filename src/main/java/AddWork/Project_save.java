package AddWork;

import MysqlTools.MysqlConnect;
import MysqlTools.Project_info;
import MysqlTools.Work_info;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/Project_save")
public class Project_save  extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Project_info pro =new Project_info();
        int project_id=Integer.parseInt(req.getParameter("project_id"));
        String project_name=req.getParameter("project_name");
        int user_id=Integer.parseInt(req.getParameter("user_id"));
        String time_type=req.getParameter("time_type");
        Project_info.populate(pro,project_id,project_name,user_id,time_type);
        try {
            MysqlConnect connect=new MysqlConnect();
            if(connect.ProjectSave(pro)){
                resp.getWriter().print("success");
            }else{
                resp.getWriter().print("fail");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
