package cn.edu.nuaa.cs.gui.indoor.model;

import javax.vecmath.Point3d;
import java.util.Arrays;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 85492 on 2017/3/3.
 */
public class FloorRoom {
    private int id;
    private String name;
    private String type;

    private String roomCoords;
    private String doorCoords;

    public Vector circleVec = new Vector(5,1);
    public Vector doorVec = new Vector(5,1);

    public FloorRoom(String tuple){
        this.id = getPattern_Integer(tuple);
        this.name = getPattern_String(tuple);
        String temp = tuple.replaceAll("\"s*"+this.name+"\"s*", "");
        this.type = getPattern_String(temp);

        int index = tuple.indexOf('(',2);
        String tmprd = tuple.substring(index, tuple.length()-1);
        Vector vec = getCommpents("("+tmprd+")");
        this.roomCoords = vec.get(0).toString();
        this.doorCoords = vec.get(1).toString();

        createRooms();

        if(!this.type.equals("ST")){
            createDoors();
        }
    }

    public void createRooms(){
        Vector vector = getCommpents(roomCoords);
        for (int i = 0; i < vector.size(); i++) {
            String temp = vector.get(i).toString();

            double height = getPAttern_Double(temp);
            Vector coord2Vec = getPAttern_Coord2(temp);
            Vector pointVec = new Vector();
            for (int j = 0; j < coord2Vec.size(); j++) {
                String str = coord2Vec.get(j).toString();
                String[] ps = str.substring(1,str.length()-1).split(" ");
                Point3d p3d = new Point3d(Double.valueOf(ps[0]), Double.valueOf(ps[1]), height);
                pointVec.add(p3d);
            }
            this.circleVec.add(pointVec);
        }
    }
    public void createDoors(){
        Vector vector = getCommpents(doorCoords);
        for (int i = 0; i < vector.size(); i++) {
            String temp = vector.get(i).toString();
            Vector coord4Vec = getPAttern_Coord4(temp);
            Vector pointVec = new Vector();
            for (int j = 0; j < coord4Vec.size(); j++) {
                String str = coord4Vec.get(j).toString();
                String[] ps = str.substring(1,str.length()-1).split(" ");

                Point3d sp3d = new Point3d(Double.valueOf(ps[0]), Double.valueOf(ps[1]),
                        ((Point3d)((Vector)this.circleVec.get(0)).get(0)).getZ());
                Point3d ep3d = new Point3d(Double.valueOf(ps[2]), Double.valueOf(ps[3]),
                        ((Point3d)((Vector)this.circleVec.get(0)).get(0)).getZ());
                this.doorVec.add(sp3d);
                this.doorVec.add(ep3d);
            }
        }
    }

    public Vector getCommpents(String str){
        String t = "";
        Vector vector = new Vector(5,1);

        char[] tmpChar = str.toCharArray();
        int ind = 1;

        for (int i = 1; i < tmpChar.length-1; i++) {
            if (tmpChar[i] == '(') {
                ind++;
            } else if (tmpChar[i] == ')') {
                ind--;
            }

            t += tmpChar[i];

            if (ind == 1) {
                t = t.trim();
                if(t.length()!=0){
                    vector.add(t);
                }
                t = "";
            }
        }
        return vector;
    }

    public int getPattern_Integer(String str){
        String PATTERN_Number = "\\d+";

        Pattern pattern = Pattern.compile(PATTERN_Number);
        Matcher matcher = pattern.matcher(str);
        Boolean rs = matcher.find();

        int result = Integer.valueOf(matcher.group());

        return result;
    }
    public String getPattern_String(String str){
        String PATTERN_STRING = "\"[^\"]+\"";

        Pattern pattern = Pattern.compile(PATTERN_STRING);
        Matcher matcher = pattern.matcher(str);
        Boolean rs = matcher.find();

        String string = String.valueOf(matcher.group());
        string = string.substring(1, string.length()-1).trim();

        return string;
    }
    public double getPAttern_Double(String str){
        String PATTERN_DOUBLE = "\\d+\\.\\d";

        Pattern pattern = Pattern.compile(PATTERN_DOUBLE);
        Matcher matcher = pattern.matcher(str);
        Boolean rs = matcher.find();

        double result = Double.valueOf(matcher.group());

        return result;
    }
    public Vector getPAttern_Coord2(String str){
        Vector vector = new Vector(4,1);
        String PATTERN_COORD2 = "\\(\\d+\\.\\d+\\s\\d+\\.\\d+\\)";
        Pattern pattern = Pattern.compile(PATTERN_COORD2);
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()){
            vector.add(matcher.group());
        }
        return vector;
    }
    public Vector getPAttern_Coord4(String str){
        Vector vector = new Vector(4,1);
        String PATTERN_COORD4 = "\\((\\d+\\.\\d+\\s){3,}\\d+\\.\\d+\\)";
        Pattern pattern = Pattern.compile(PATTERN_COORD4);
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()){
            vector.add(matcher.group());
        }
        return vector;
    }

    @Override
    public String toString() {
        return "FloorRoom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", roomCoords='" + roomCoords + '\'' +
                ", doorCoords='" + doorCoords + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getRoomCoords() {
        return roomCoords;
    }
    public void setRoomCoords(String roomCoords) {
        this.roomCoords = roomCoords;
    }
    public String getDoorCoords() {
        return doorCoords;
    }
    public void setDoorCoords(String doorCoords) {
        this.doorCoords = doorCoords;
    }
}
