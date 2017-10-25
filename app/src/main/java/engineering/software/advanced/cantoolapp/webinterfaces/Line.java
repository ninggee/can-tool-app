package engineering.software.advanced.cantoolapp.webinterfaces;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ningge on 2017/10/25.
 */

public class Line {
    private String message_name;
    private ArrayList<MessagesWrapper> messages;
    private final static Map<String, String> colors = new HashMap<>();
    static {
        colors.put("blue", "rgb(54, 162, 235)");
        colors.put("green","rgb(75, 192, 192)");
        colors.put("grey", "rgb(201, 203, 207)");
        colors.put("orange", "rgb(255, 159, 64)");
        colors.put("purple", "rgb(153, 102, 255)");
        colors.put("red",   "rgb(255, 99, 132)");
        colors.put("yellow",    "rgb(255, 205, 86)");
    }
    private  final static String[] color_names= {"red", "orange", "yellow", "green", "blue", "purple", "grey"};

    public Line(String message_name, ArrayList<MessagesWrapper> messages) {
        this.message_name = message_name;
        this.messages = messages;
        //sort messages by its time
        Collections.sort(this.messages, new Comparator<MessagesWrapper>() {
            @Override
            public int compare(MessagesWrapper o1, MessagesWrapper o2) {
                return Integer.parseInt(o1.getTime()) - Integer.parseInt(o2.getTime());
            }
        });
    }

    private String getColor(int index) {
        String color_name = color_names[index % color_names.length];
        return colors.get(color_name);
    }

    public String getLables() {
        List<String> labels = new ArrayList<>();

        for(MessagesWrapper m : messages) {
            labels.add(m.getTime());
        }

        return new Gson().toJson(labels);
    }

    public String getDatas() {




        return "";
    }
}
class LineData {

}
