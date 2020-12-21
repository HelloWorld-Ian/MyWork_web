package logIn;


import MysqlTools.MysqlConnect;
import MysqlTools.Project_info;
import MysqlTools.Work_info;
import MysqlTools.userCheck;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;

import Algorithm.*;
public class test {
   public void addData(){
       try {
           MysqlConnect mysql=new MysqlConnect();
           userCheck user=new userCheck();
           user.setID("Be");
           user.setPassWord("123456");
           if(mysql.registerID(user))
               System.out.println("success");
           else
               System.out.println("fail");

       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       } catch (SQLException throwables) {
           throwables.printStackTrace();
       }
   }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        new MysqlConnect().switchPlan(1,6,1,2);
    }
}
