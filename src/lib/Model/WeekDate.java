package lib.Model;
/**
 * Created by wyq on 19-12-08.
 */
import java.util.ArrayList;

public class WeekDate {
    ArrayList<String> weekStartDate=new ArrayList<String>();
    ArrayList<String> weekEndDate=new ArrayList<String>();

    public ArrayList<String> getWeekStartDate() {
        return weekStartDate;
    }

    public ArrayList<String> getWeekEndDate() {
        return weekEndDate;
    }

    public WeekDate(){
        weekStartDate.add("2019-09-02");
        weekStartDate.add("2019-09-09");
        weekStartDate.add("2019-09-16");
        weekStartDate.add("2019-09-23");
        weekStartDate.add("2019-09-30");
        weekStartDate.add("2019-10-07");
        weekStartDate.add("2019-10-14");
        weekStartDate.add("2019-10-21");
        weekStartDate.add("2019-10-28");
        weekStartDate.add("2019-11-04");
        weekStartDate.add("2019-11-11");
        weekStartDate.add("2019-11-18");
        weekStartDate.add("2019-11-25");
        weekStartDate.add("2019-12-02");
        weekStartDate.add("2019-12-09");
        weekStartDate.add("2019-12-16");
        weekStartDate.add("2019-12-23");
        weekStartDate.add("2019-12-30");
        weekStartDate.add("2020-01-06");
        weekEndDate.add("2019-09-08");
        weekEndDate.add("2019-09-15");
        weekEndDate.add("2019-09-22");
        weekEndDate.add("2019-09-29");
        weekEndDate.add("2019-10-06");
        weekEndDate.add("2019-10-13");
        weekEndDate.add("2019-10-20");
        weekEndDate.add("2019-10-27");
        weekEndDate.add("2019-11-03");
        weekEndDate.add("2019-11-10");
        weekEndDate.add("2019-11-17");
        weekEndDate.add("2019-11-24");
        weekEndDate.add("2019-12-01");
        weekEndDate.add("2019-12-08");
        weekEndDate.add("2019-12-15");
        weekEndDate.add("2019-12-22");
        weekEndDate.add("2019-12-29");
        weekEndDate.add("2020-01-05");
    }
}
