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

@WebServlet("/UpdatePlan")
public class UpdatePlan extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Work_info plan=new Work_info();
        int work_id=Integer.parseInt(req.getParameter("work_id"));
        int project_id=Integer.parseInt(req.getParameter("project_id"));
        int user_id=Integer.parseInt(req.getParameter("user"));
        String name=req.getParameter("name");
        String time=req.getParameter("time");
        String preWork=req.getParameter("preWork");
        Work_info.populate(plan,work_id,user_id,name,time,preWork,project_id);
        try {
            MysqlConnect connect=new MysqlConnect();
            if(connect.updatePlan(plan)){
                resp.getWriter().print("success");
            }else{
                resp.getWriter().print("fail");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
