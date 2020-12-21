package EditWork;

import MysqlTools.MysqlConnect;
import MysqlTools.Work_info;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deletePlans")
public class deletePlans extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Work_info plan=new Work_info();
        int work_id=Integer.parseInt(req.getParameter("work_id"));
        int project_id=Integer.parseInt(req.getParameter("project_id"));
        int user_id=Integer.parseInt(req.getParameter("user_id"));
        Work_info.populate(plan,work_id,user_id,null,null,null,project_id);
        try {
            MysqlConnect connect=new MysqlConnect();
            if(connect.DeletePlan(plan)) {
                resp.getWriter().print("success");
            }else{
                resp.getWriter().print("fail");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
