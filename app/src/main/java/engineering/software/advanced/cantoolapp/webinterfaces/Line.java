package engineering.software.advanced.cantoolapp.webinterfaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import engineering.software.advanced.cantoolapp.converter.entity.Signal;

/**
 * Created by ningge on 2017/10/25.
 */

public class Line {
    private String message_name;
    private ArrayList<MessagesWrapper> messages;
    private final static Map<String, String> colors = new HashMap<>();
    static {
        colors.put("blue", "#36a2eb");
        colors.put("green","#4bc0c0");
        colors.put("grey", "#c9cbcf");
        colors.put("orange", "#ff9f40");
        colors.put("purple", "#9966ff");
        colors.put("red",   "#ff6384");
        colors.put("yellow",    "#ffcd56");
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

    public  List<String> getLables() {
        List<String> labels = new ArrayList<>();

        for(MessagesWrapper m : messages) {
            labels.add(m.getTime());
        }

        return labels;
    }

    public List<LineData> egetDatas() {
        Map<String,  LineData> result = new HashMap<>();
        int i = 0;
        for(MessagesWrapper m : messages) {
            Set<Signal> signals = m.getSignals();
            for(Signal signal: signals) {
                if(result.get(signal.getName()) == null) {
                     result.put(signal.getName(), new LineData(signal.getName(), new ArrayList<String>(), false, this.getColor(i)));
                }

                result.get(signal.getName()).getData().add(signal.getValue() + "");
                i++;
            }
        }

        return new ArrayList<>(result.values());
    }
}
class LineData {
    private String label = "";
    private List<String> data = new ArrayList<>();
    private boolean fill = false;
    private String borderColor = "";

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    private String backgroundColor = "";

    public LineData(String label, List<String> data, boolean fill, String borderColor) {
        this.label = label;
        this.data = data;
        this.fill = fill;
        this.borderColor = borderColor;
        this.backgroundColor = borderColor;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }


    public String getLabel() {

        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
