package logIn;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

import MysqlTools.*;
import org.apache.commons.beanutils.BeanUtils;


@WebServlet("/loginCheck")
public class loginCheck extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            userCheck user=new userCheck();
            userCheck.populate(user,req.getParameter("ID"),req.getParameter("PassWord"));
            MysqlConnect connect=new MysqlConnect();
            String id=connect.getUserID(user);
            if(id.equals("")){
               resp.getWriter().print("fail");
            }
            else{
               if(connect.findUser(user)) {
                   resp.getWriter().print(id);
               }else{
                   resp.getWriter().print("fail");
               }

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    }

