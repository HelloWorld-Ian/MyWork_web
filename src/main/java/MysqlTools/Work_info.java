package MysqlTools;

public class Work_info {
    private int ID;
    private int user;
    private String name;
    private String time;
    private String pre_work;
    private int project_id;

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPre_work(String pre_work) {
        this.pre_work = pre_work;
    }

    public int getID() {
        return ID;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getPre_work() {
        return pre_work;
    }

    public int getProject_id() {
        return project_id;
    }

    public static void populate(Work_info data, int ID, int user, String name, String time, String pre_work,int project_id){
        data.setID(ID);
        data.setUser(user);
        data.setName(name);
        data.setTime(time);
        data.setPre_work(pre_work);
        data.setProject_id(project_id);
    }
}
