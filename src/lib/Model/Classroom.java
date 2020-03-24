package lib.Model;

/**
 * Created by wyq on 19-12-08.
 */
public class Classroom {
    private String location;
    private  String capacity;

    public Classroom(){
        super();
    }
    public Classroom(String location, String capacity){
        this.location = location;
        this.capacity = capacity;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
