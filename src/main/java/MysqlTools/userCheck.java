package MysqlTools;

public class userCheck {
    private String ID;
    private String PassWord;


    public String getID() {
        return ID;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPassWord(String PassWord) {
        this.PassWord = PassWord;
    }

    public static void populate(userCheck user,String ID,String PassWord){
        user.setID(ID);
        user.setPassWord(PassWord);
    }
}
