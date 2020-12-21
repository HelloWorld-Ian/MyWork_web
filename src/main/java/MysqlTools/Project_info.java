package MysqlTools;

public class Project_info {
    private int project_id;
    private String projectName;
    private int user_id;
    private String time_type;

    public String getProjectName() {
        return projectName;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getTime_type() {
        return time_type;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setTime_type(String time_type) {
        this.time_type = time_type;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    static public void populate(Project_info project,int project_id, String projectName, int user_id, String time_type){
        project.setProject_id(project_id);
        project.setProjectName(projectName);
        project.setUser_id(user_id);
        project.setTime_type(time_type);
    }
}
