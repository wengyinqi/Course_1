package lib.Model;
/**
 * Created by jby on 19-12-08.
 */
public class Student extends User {
    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setTellphone(String tellphone) {
        this.tellphone = tellphone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHighschool(String highschool) {
        this.highschool = highschool;
    }

    public void setOrigo(String origo) {
        this.origo = origo;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public String getTellphone() {
        return tellphone;
    }

    public String getEmail() {
        return email;
    }

    public String getHighschool() {
        return highschool;
    }

    public String getOrigo() {
        return origo;
    }

    public String getHobby() {
        return hobby;
    }

    public String getDepartment() {
        return department;
    }

    public String getMajor() {
        return major;
    }

    String sex;
    int age;
    String tellphone;
    String email;
    String highschool;
    String origo;
    String hobby;
    String department;
    String major;

}
