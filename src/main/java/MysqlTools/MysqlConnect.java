package MysqlTools;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class MysqlConnect {
    private static String Driver;
    private static String URL;
    private static String User;
    private static String PassWord;
    private static Connection connect;
    static{
        try {
            Properties properties=new Properties();
            InputStream cin=MysqlConnect.class.getClassLoader().getResourceAsStream("MysqlInfo.properties");
            properties.load(cin);
            Driver= properties.getProperty("Driver");
            URL=properties.getProperty("URL");
            User= properties.getProperty("User");
            PassWord= properties.getProperty("PassWord");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MysqlConnect() throws ClassNotFoundException, SQLException {
        Class.forName(Driver);
        connect= DriverManager.getConnection(URL,User,PassWord);
    }

    public boolean findUser(userCheck user){
        try{
            String sqlID="select id from users where user_id= ? ";
            PreparedStatement search= connect.prepareStatement(sqlID);
            search.setString(1,user.getID());
            ResultSet resId= search.executeQuery();
            String id="";
            boolean hasId=false;
            while(resId.next()){
                hasId=true;
                id= resId.getString(1);
            }
            if(hasId) {
                String sqlPassWord="select password from user_password where password_id= ? ";
                PreparedStatement searchCode= connect.prepareCall(sqlPassWord);
                searchCode.setString(1,id);
                ResultSet resCode= searchCode.executeQuery();
                String passWord="";
                while(resCode.next()){
                    passWord=resCode.getString(1);
                    if(passWord.equals(user.getPassWord()))
                        return true;
                }
            }
            else
                return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public boolean checkID(userCheck user)  {
        boolean hasId=false;
        String sqlID="select id from users where user_id= ? ";
        PreparedStatement search= null;
        try {
            search = connect.prepareStatement(sqlID);
            search.setString(1,user.getID());
            ResultSet resId= search.executeQuery();
            String id="";

            while(resId.next()) {
                hasId = true;
                id = resId.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return hasId;
    }

    public String getUserID(userCheck user)  {
        String sqlID="select id from users where user_id= ? ";
        PreparedStatement search= null;
        String id="";
        try {
            search = connect.prepareStatement(sqlID);
            search.setString(1,user.getID());
            ResultSet resId= search.executeQuery();
            while(resId.next()) {
                id = resId.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    public boolean registerID(userCheck user){
        try{
            String insertID="insert into users values(0,?)";
            String insertCode="insert into user_password values(0,?)";
            PreparedStatement insertId= connect.prepareCall(insertID);
            PreparedStatement insertPassWord=connect.prepareCall(insertCode);
            insertId.setString(1, user.getID());
            insertPassWord.setString(1, user.getPassWord());
            insertId.executeUpdate();
            insertPassWord.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public String storedCount(String user_id,String project_id){
        try{
            String Count="select count(*) from basic_workdata where user=? and project_id=?";
            PreparedStatement Return= connect.prepareCall(Count);
            Return.setInt(1,Integer.parseInt(user_id));
            Return.setInt(2,Integer.parseInt(project_id));
            ResultSet res=Return.executeQuery();
            while(res.next()){
                return res.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "0";
    }

    public String projectStoredCount(String user_id){
        try{
            String Count="select count(*) from project where user_id=?";
            PreparedStatement Return= connect.prepareCall(Count);
            Return.setInt(1,Integer.parseInt(user_id));
            ResultSet res=Return.executeQuery();
            while(res.next()){
                String str=res.getString(1);
                if(Integer.parseInt(str)==0)
                    return str;
            }
            Count="select max(project_id) from project where user_id=?";
            Return= connect.prepareCall(Count);
            Return.setInt(1,Integer.parseInt(user_id));
            res=Return.executeQuery();
            while(res.next()){
                return res.getString(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "0";
    }

    public boolean WorkSave(Work_info data){
        try{
            String insertWork="insert into basic_workdata values(null,?,?,?,?,?,?)";
            PreparedStatement insert_Work= connect.prepareCall(insertWork);
            insert_Work.setInt(1,data.getUser());
            insert_Work.setString(2,data.getName());
            insert_Work.setString(3, data.getTime());
            insert_Work.setString(4, data.getPre_work());
            insert_Work.setInt(5,  data.getProject_id());
            insert_Work.setInt(6, data.getID());
            insert_Work.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean ProjectSave(Project_info data){
        try{
            String insertProject="insert into project values(null,?,?,?,?)";
            PreparedStatement insert_Work= connect.prepareCall(insertProject);
            insert_Work.setInt(1,data.getProject_id());
            insert_Work.setString(2, data.getProjectName());
            insert_Work.setInt(3, data.getUser_id());
            insert_Work.setString(4, data.getTime_type());
            insert_Work.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public String getUserProjects(int user_id){
        Map<Integer,String[]> data=new HashMap<Integer, String[]>();
        try{
            String query="select project_id,project_name,time_type from project where user_id=?";
            PreparedStatement statement= connect.prepareStatement(query);
            statement.setInt(1,user_id);
            ResultSet res=statement.executeQuery();
            while(res.next()){
                  String[] item=new String[4];
                  int id=res.getInt(1);
                  item[0]=Integer.toString(id);
                  item[1]=res.getString(2);
                  item[2]=res.getString(3);
                  data.put(id,item);
            }
            String getCount="select count(*),project_id  from (select * from basic_workdata where user=?)as a group by project_id";
            statement= connect.prepareStatement(getCount);
            statement.setInt(1,user_id);
            res= statement.executeQuery();
            while (res.next()){
                int count=res.getInt(1);
                int id=res.getInt(2);
                data.get(id)[3]=Integer.toString(count);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Set<Integer>keys= data.keySet();
        StringBuilder jsonArray=new StringBuilder("[");
        for(int x:keys){
            String[]item=data.get(x);
            String json="";
            if(item[3]!=null){
               json="{"+"\"id\":\""+item[0]+"\","+"\"name\":\""
                    +item[1]+"\","+"\"type\":\""+item[2]+"\","+
                    "\"itemsCount\":\""+item[3]+"\"}";
            }else{
                json="{"+"\"id\":\""+item[0]+"\","+"\"name\":\""
                        +item[1]+"\","+"\"type\":\""+item[2]+"\","+
                        "\"itemsCount\":\""+"0"+"\"}";
            }
            jsonArray.append(json).append(",");
        }
        jsonArray.delete(jsonArray.length()-1,jsonArray.length());
        jsonArray.append("]");
        return  jsonArray.toString();
    }

    public String getSelectedPlans(int project_id,int user_id){
        ArrayList<String[]>plans=new ArrayList<String[]>();
        try {
            String query="select work_id,name,time,pre_work from basic_workdata where project_id=? and user=?";
            PreparedStatement statement= connect.prepareStatement(query);
            statement.setInt(1,project_id);
            statement.setInt(2,user_id);
            ResultSet res= statement.executeQuery();
            while(res.next()){
                String[] item=new String[4];
                item[0]=Integer.toString(res.getInt(1));
                item[1]=res.getString(2);
                item[2]=res.getString(3);
                item[3]=res.getString(4);
                plans.add(item);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        StringBuilder  jsonArray=new StringBuilder("[");
        for(String[]item:plans){
            String json="{"+"\"id\":\""+item[0]+"\","+"\"name\":\""
                    +item[1]+"\","+"\"timeType\":\""+item[2]+"\","+
                    "\"preWork\":\""+item[3]+"\"}";
            jsonArray.append(json).append(",");
        }
        jsonArray.delete(jsonArray.length()-1,jsonArray.length());
        jsonArray.append("]");
        return jsonArray.toString().equals("]")?"":jsonArray.toString();
    }
    public int[][] getPreWorksArray(int project_id,int user_id){
        ArrayList<int[]>arrays=new ArrayList<>();
        try {
            String query="select work_id,pre_work from basic_workdata where project_id=? and user=?";
            PreparedStatement statement= connect.prepareStatement(query);
            statement.setInt(1,project_id);
            statement.setInt(2,user_id);
            ResultSet res= statement.executeQuery();
            while(res.next()){
               int work_id=Integer.parseInt(res.getString(1));
               String preWorks=res.getString(2);
               preWorks=preWorks.trim();
               preWorks=preWorks.replaceAll("[^0-9]"," ");
               String[]next=preWorks.split("\\s+");
               for(String x:next){
                   if(!x.equals(""))
                       arrays.add(new int[]{Integer.parseInt(x),work_id});
               }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        int [][]res=new int[arrays.size()][2];
        int i=0;
        for(int[]x:arrays){
            res[i++]=x;
        }
        return  res;
    }
      public boolean updateProject(Project_info project){
        try {
              String update="update project set project_name=?, time_type=? where project_id=? and user_id=? ";
              PreparedStatement statement= connect.prepareStatement(update);
              statement.setString(1,project.getProjectName());
              statement.setString(2,project.getTime_type());
              statement.setInt(3,project.getProject_id());
              statement.setInt(4,project.getUser_id());
              statement.executeUpdate();
              return true;
          } catch (SQLException throwables) {
              throwables.printStackTrace();
          }
        return false;
      }
    public boolean updatePlan(Work_info plan){
        try {
            String update="update basic_workdata set name=?, time=?,pre_work=? where user=? and project_id=? and work_id=?";
            PreparedStatement statement= connect.prepareStatement(update);
            statement.setString(1,plan.getName());
            statement.setString(2,plan.getTime());
            statement.setString(3,plan.getPre_work());
            statement.setInt(4,plan.getUser());
            statement.setInt(5,plan.getProject_id());
            statement.setInt(6,plan.getID());
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public String[]getInfo(int user_id,int project_id,int id){
        String[]str=new String[5];
        try {
            String getInfo="select id,name,time,pre_work from basic_workdata where user=? and project_id=? and work_id=?";
            PreparedStatement statement= connect.prepareStatement(getInfo);
            statement.setInt(1,user_id);
            statement.setInt(2,project_id);
            statement.setInt(3,id);
            ResultSet res=statement.executeQuery();
            while (res.next()){
                str[0]=res.getString(1);
                str[1]=Integer.toString(id);
                str[2]=res.getString(2);
                str[3]=res.getString(3);
                str[4]= Integer.toString(res.getInt(4));
                return str;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return str;
    }
    public boolean coverPlan(int user_id,int project_id,int id,String[]str){
        try {
            String update="update basic_workdata set name=?, time=?,pre_work=?,work_id=? where id=?";
            PreparedStatement statement= connect.prepareStatement(update);
            statement.setInt(5,id);
            statement.setString(1,str[2]);
            statement.setString(2,str[3]);
            statement.setString(3,str[4]);
            statement.setInt(4,Integer.parseInt(str[1]));
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public boolean switchPlan(int user_id,int project_id,int id1,int id2){
        String[]str1=getInfo(user_id,project_id,id1);
        String[]str2=getInfo(user_id,project_id,id2);
        return coverPlan(user_id,project_id,Integer.parseInt(str2[0]),str1)&&
                coverPlan(user_id,project_id,Integer.parseInt(str1[0]),str2);
    }

    public boolean DeleteProject(Project_info project){
        try {
            String deleteProject=" delete from project where user_id=? and project_id=? ";
            PreparedStatement statement= connect.prepareStatement(deleteProject);
            statement.setInt(1,project.getUser_id());
            statement.setInt(2,project.getProject_id());
            statement.executeUpdate();
            String deletePlans="delete from basic_workdata where user=? and project_id=?";
            statement= connect.prepareStatement(deletePlans);
            statement.setInt(1,project.getUser_id());
            statement.setInt(2,project.getProject_id());
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean DeletePlan(Work_info plan){
        try {
            String delete="delete from basic_workdata where user=? and project_id=? and work_id=?";
            PreparedStatement statement= connect.prepareStatement(delete);
            statement.setInt(1,plan.getUser());
            statement.setInt(2,plan.getProject_id());
            statement.setInt(3,plan.getID());
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public Map<Integer,Integer> getPlanTime(Project_info project){
        Map<Integer,Integer>map=new HashMap<>();
        try {
            String getTime="select work_id,time from basic_workdata where project_id=? and user=? ";
            PreparedStatement statement= connect.prepareStatement(getTime);
            statement.setInt(1,project.getProject_id());
            statement.setInt(2,project.getUser_id());
            ResultSet res= statement.executeQuery();
            while(res.next()){
                int id=res.getInt(1);
                int time=res.getInt(2);
                map.put(id,time);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return map;
    }
}
