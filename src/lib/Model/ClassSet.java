package lib.Model;
/**
 * Created by wyq on 19-12-08.
 */
import java.util.ArrayList;
import java.util.Random;

public class ClassSet {
    ArrayList<String> classSchool=new ArrayList<String>();
    ArrayList<String> classFloor=new ArrayList<String>();
    //ArrayList<String> classRoom=new ArrayList<String>();
    public ClassSet(){
        classSchool.add("望江");
        classSchool.add("江安");
        classFloor.add("第一教学楼A座");
        classFloor.add("第一教学楼B座");
        classFloor.add("第一教学楼C座");
        classFloor.add("第一教学楼D座");
        classFloor.add("综合楼B");
        classFloor.add("综合楼C");
    }

    public ArrayList<String> getClassSchool() {
        return classSchool;
    }

    public ArrayList<String> getClassFloor() {
        return classFloor;
    }
    public String getRandom(){
        String str="";
        Random random=new Random();
        while(str.length()<3){
            if(str.length()==1){
                str+="0";
            }
            else if(str.length()==0){
                int r=0;
                while (r==0){
                    r=random.nextInt(6);
                }
                str+=""+r;
            }
            else{
                str+=""+random.nextInt(10);
            }
        }
        return str;
    }
}
