package AddWork;

import MysqlTools.MysqlConnect;
import MysqlTools.Work_info;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/Work_save")
public class Work_save extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Work_info work_info=new Work_info();
        Work_info.populate(work_info,Integer.parseInt(req.getParameter("id"))
        ,Integer.parseInt(req.getParameter("user")),req.getParameter("name"),
                req.getParameter("time"),req.getParameter("pre_work"),
                Integer.parseInt(req.getParameter("project_id")));
        try {
            MysqlConnect connect=new MysqlConnect();
            if(connect.WorkSave(work_info)){
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
