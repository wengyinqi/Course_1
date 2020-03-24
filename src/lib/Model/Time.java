package lib.Model;
/**
 * Created by wyq on 19-12-08.
 */
import java.util.ArrayList;

public class Time {
    ArrayList<String> startTime=new ArrayList<String>();
    ArrayList<String> endTime=new ArrayList<String>();
    public Time(){
        startTime.add("8:15:00");
        startTime.add("9:10:00");
        startTime.add("10:15:00");
        startTime.add("11:10:00");
        startTime.add("13:50:00");
        startTime.add("14:45:00");
        startTime.add("15:40:00");
        startTime.add("16:45:00");
        startTime.add("17:40:00");
        startTime.add("19:20:00");
        startTime.add("20:15:00");
        startTime.add("21:10:00");
        endTime.add("9:00:00");
        endTime.add("9:55:00");
        endTime.add("11:00:00");
        endTime.add("11:55:00");
        endTime.add("14:35:00");
        endTime.add("15:30:00");
        endTime.add("16:25:00");
        endTime.add("17:30:00");
        endTime.add("18:25:00");
        endTime.add("20:05:00");
        endTime.add("21:00:00");
        endTime.add("21:55:00");
    }
    public ArrayList<String> getStartTime(){
        return startTime;
    }
    public ArrayList<String> getEndTime(){
        return endTime;
    }
}
