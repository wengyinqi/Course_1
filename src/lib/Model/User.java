package lib.Model;

/**
 * Created by jby on 19-12-08.
 */
public class User {
    private String id;
    private String password;
    private int role;
    private String name;

    public User(){
        super();
    }
    public User (String id, String password){
        this.id = id;
        this.password = password;
    }

    public String getId(){

        return id;
    }

    public void setId(String id){

        this.id = id;
    }

    public String getPassword(){

        return password;
    }

    public void setPassword(String password){

        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int n) {
        role=n;//0表示学生  1表示老师
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

