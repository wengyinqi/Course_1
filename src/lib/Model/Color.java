package lib.Model;
/**
 * Created by wyq on 19-12-08.
 */
import java.util.ArrayList;

public class Color {


    public ArrayList<String> getTextcolor() {
        return textcolor;
    }

    public ArrayList<String> getBcolor() {
        return bcolor;
    }
    ArrayList<String> textcolor=new ArrayList<String>();
    ArrayList<String> bcolor=new ArrayList<String>();
    public Color(){
        textcolor.add("white");
        bcolor.add("#3c8dbc");
    }

}
