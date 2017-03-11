package cn.edu.nuaa.cs.gui.indoor.model;

import cn.edu.nuaa.cs.gui.indoor.TDViewer;

import javax.media.j3d.*;
import javax.vecmath.Point3d;
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
    private double height;
    private Vector roomCoords = new Vector(4,1);
    private Vector doorCoords = new Vector(1,1);

    public static String PATTERN_STRING = "\"[^\"]+\"";
    public static String PATTERN_DOUBLE = "\\d+\\.\\d";
    public static String PATTERN_COORD2 = "\\(\\d+\\.\\d+\\s\\d+\\.\\d+\\)";
    public static String PATTERN_COORD4 = "\\((\\d+\\.\\d+\\s){3,3}\\d+\\.\\d+\\)";

    public FloorRoom(String tuple){
        Pattern pattern;
        Matcher matcher;
        Boolean rs;
        //id
        pattern = Pattern.compile("\\d+");
        matcher = pattern.matcher(tuple);
        rs = matcher.find();
        this.id = Integer.valueOf(matcher.group());

        //name,type
        String[] temps = new String[2];
        int i = 0;
        pattern = Pattern.compile(PATTERN_STRING);
        matcher = pattern.matcher(tuple);
        while(matcher.find()){
            temps[i++] = matcher.group();
        }
        this.name = temps[0].substring(1,temps[0].length()-1).trim();
        this.type = temps[1].substring(1,temps[1].length()-1).trim();

        //height
        pattern = Pattern.compile(PATTERN_DOUBLE);
        matcher = pattern.matcher(tuple);
        rs = matcher.find();
        this.height = Double.valueOf(matcher.group());


        //room
        pattern = Pattern.compile(PATTERN_COORD2);
        matcher = pattern.matcher(tuple);
        while(matcher.find()){
            String temp = matcher.group();
            Pattern p = Pattern.compile(PATTERN_DOUBLE);
            Matcher m = p.matcher(temp);
            double xy[] = new double[2];
            int j = 0;
            while (m.find()){
                xy[j++] = Double.valueOf(m.group());
            }
            Point3d p3d = new Point3d(xy[0],xy[1],this.height);
            this.roomCoords.add(p3d);
        }


        //door
        pattern = Pattern.compile(PATTERN_COORD4);
        matcher = pattern.matcher(tuple);
        while(matcher.find()){
            String temp = matcher.group();
            Pattern p = Pattern.compile(PATTERN_DOUBLE);
            Matcher m = p.matcher(temp);
            double xy[] = new double[4];
            int j = 0;
            while (m.find()){
                xy[j++] = Double.valueOf(m.group());
            }
            Point3d p3d1 = new Point3d(xy[0],xy[1],this.height);
            Point3d p3d2 = new Point3d(xy[2],xy[3],this.height);
            this.doorCoords.add(p3d1);
            this.doorCoords.add(p3d2);
        }
    }

    public Shape3D createRoom(){
        int[] strips = new int[1];
        strips[0] = this.roomCoords.size()+1;
        Point3d[] arr_p3d = new Point3d[this.roomCoords.size()+1];
        Point3d p3d0 = new Point3d((Point3d) this.roomCoords.get(0));
        for(int i=0;i<this.roomCoords.size();i++){
            Point3d p3d = (Point3d) this.roomCoords.get(i);
            MapFunction(p3d, TDViewer.min, TDViewer.max);
            arr_p3d[i] = new Point3d(p3d.x, p3d.y, p3d.z);
        }
        MapFunction(p3d0, TDViewer.min, TDViewer.max);
        arr_p3d[arr_p3d.length-1] = new Point3d(p3d0.x, p3d0.y, p3d0.z);

        ColoringAttributes colorAttr = new ColoringAttributes();
        colorAttr.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
        colorAttr.setColor(1.0f, 1.0f, 0.0f);

        LineAttributes lineAttr = new LineAttributes();
        lineAttr.setCapability(LineAttributes.ALLOW_WIDTH_WRITE);
        lineAttr.setLineWidth(2.0f);
        lineAttr.setLinePattern(0);
        lineAttr.setLineAntialiasingEnable(true);

        Appearance appear = new Appearance();
        appear.setColoringAttributes(colorAttr);
        appear.setLineAttributes(lineAttr);

        LineStripArray line = new LineStripArray(arr_p3d.length,LineArray.COORDINATES,strips);
        line.setCoordinates(0, arr_p3d);
        line.setCapability(Geometry.ALLOW_INTERSECT);

        Shape3D structure = new Shape3D(line,appear);

        return structure;
    }
    public Shape3D createDoor(){
        Point3d[] arr_p3d = new Point3d[this.doorCoords.size()];
        for(int i=0;i<this.doorCoords.size();i++){
            Point3d p3d = (Point3d) this.doorCoords.get(i);
            MapFunction(p3d, TDViewer.min,TDViewer.max);
            arr_p3d[i] = new Point3d(p3d.x,p3d.y,p3d.z);
        }

        //door
        ColoringAttributes colorAttr = new ColoringAttributes();
        colorAttr.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
        colorAttr.setColor(0.0f, 0.0f, 1.0f);

        LineAttributes lineAttr = new LineAttributes();
        lineAttr.setCapability(LineAttributes.ALLOW_WIDTH_WRITE);
        lineAttr.setLineWidth(4.0f);
        lineAttr.setLinePattern(0);
        lineAttr.setLineAntialiasingEnable(true);

        Appearance appear = new Appearance();
        appear.setColoringAttributes(colorAttr);
        appear.setLineAttributes(lineAttr);

        LineArray line = new LineArray(arr_p3d.length,LineArray.COORDINATES);
        line.setCoordinates(0, arr_p3d);
        line.setCapability(Geometry.ALLOW_INTERSECT);

        Shape3D structure = new Shape3D(line,appear);

        return structure;
    }

    public void MapFunction(Point3d p, Point3d min, Point3d max) {
        Double minx = new Double(min.x);
        Double miny = new Double(min.y);
        Double minz = new Double(min.z);
        Double maxx = new Double(max.x);
        Double maxy = new Double(max.y);
        Double maxz = new Double(max.z);
        if (maxx.compareTo(minx) > 0) {
            double range = max.x - min.x;
            p.x = 2 * (p.x - min.x) / range - 1.0;
        } else
            p.x = 0.0f;

        if (maxy.compareTo(miny) > 0) {
            double range = max.y - min.y;
            p.y = 2 * (p.y - min.y) / range - 1.0;
        } else
            p.y = 0.0f;

        if (maxz.compareTo(minz) > 0) {
            double range = max.z - min.z;
            p.z = 2 * (p.z - min.z) / range - 1.0;
        } else
            p.z = 0.0f;
    }

    @Override
    public String toString() {
        return "FloorRoom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", height=" + height +
                ", roomCoords=" + roomCoords +
                ", doorCoords=" + doorCoords +
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
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public Vector getRoomCoords() {
        return roomCoords;
    }
    public void setRoomCoords(Vector roomCoords) {
        this.roomCoords = roomCoords;
    }
    public Vector getDoorCoords() {
        return doorCoords;
    }
    public void setDoorCoords(Vector doorCoords) {
        this.doorCoords = doorCoords;
    }
}
