package cn.edu.nuaa.cs.gui.indoor;

import cn.edu.nuaa.cs.gui.indoor.model.FloorRoom;
import cn.edu.nuaa.cs.io.FileHelper;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by 85492 on 2017/2/28.
 */
public class TDViewer extends JPanel implements Runnable {
    private static SimpleUniverse simpleUniverse;
    private static Canvas3D canvas3d;
    private static BranchGroup branchGroup = new BranchGroup();
    private static TransformGroup transformGroup = new TransformGroup();
    private static double objScale = 0.5;
    private BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0 );

    private boolean min_max_init = false;
    public static Vector Rooms = new Vector(5,5);

    public static Point3d min = new Point3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
    public static Point3d max = new Point3d(-Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE);

    public static String[] viewStrs = {"正视图","侧视图","俯视图"};

    public static String fileName = null;

    public TDViewer(){
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        canvas3d = new Canvas3D(config);
        simpleUniverse = new SimpleUniverse(canvas3d);
        simpleUniverse.getViewingPlatform().setNominalViewingTransform();
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transformGroup.setCapability(Group.ALLOW_CHILDREN_READ);
        transformGroup.setCapability(Group.ALLOW_CHILDREN_WRITE);
        branchGroup.setCapability(BranchGroup.ALLOW_DETACH);
        branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        branchGroup.addChild(transformGroup);

        //light
        Color3f lightColor = new Color3f(1.0f, 0.0f, 0.0f);
        Vector3f lightDirection = new Vector3f(-1.0f, 0.0f, -1.0f);
        DirectionalLight light = new DirectionalLight(lightColor, lightDirection);
        light.setInfluencingBounds(bounds);
        branchGroup.addChild(light);

        //background
		Color3f bgColor = new Color3f(0.0f, 0.0f, 0.0f);
		Background bg = new Background(bgColor);
		bg.setApplicationBounds(bounds);
		branchGroup.addChild(bg);

        simpleUniverse.addBranchGraph(branchGroup);
        createMouseBehavior(branchGroup, transformGroup);

        setLayout(new BorderLayout());
        add(canvas3d,BorderLayout.CENTER);

        initMenu();

        initBuilding();

        new Thread(this).start();

    }

    public void initMenu(){
        JPopupMenu jpm = new JPopupMenu();

        JRadioButtonMenuItem items[] = new JRadioButtonMenuItem[5];
        ButtonGroup viewGroup = new ButtonGroup();

        for (int i = 0; i < viewStrs.length; i++) {
            items[i] = new JRadioButtonMenuItem(viewStrs[i]);
            jpm.add(items[i]);
            viewGroup.add(items[i]);
            items[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String src = ((JRadioButtonMenuItem)e.getSource()).getText();
                    TDViewer.setViewer(src);
                }
            });
        }

        canvas3d.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    // 弹出菜单
                    jpm.show(TDViewer.this, e.getX(), e.getY());
                }
            }
        });
    }

    public void initBuilding(){
        TransformGroup tg = new TransformGroup();

        ArrayList<String> tuples = FileHelper.readRoomTuple(IndoorWindow.fileName);

        for(int i=0;i<tuples.size();i++){
            String tuple = tuples.get(i).trim();
            FloorRoom room = new FloorRoom(tuple);
            Rooms.add(room);
        }

        if(!min_max_init){
            for (int i = 0; i < Rooms.size(); i++) {
                Vector circleVec = ((FloorRoom) Rooms.get(i)).circleVec;
                for (int j = 0; j < circleVec.size(); j++) {
                    Vector p3dVec = (Vector) circleVec.get(j);
                    for (int k = 0; k < p3dVec.size(); k++) {
                        Point3d p3d = (Point3d) p3dVec.get(k);
                        min.x = p3d.x < min.x ? p3d.x : min.x;
                        min.y = p3d.y < min.y ? p3d.y : min.y;
                        min.z = p3d.z < min.z ? p3d.z : min.z;
                        max.x = p3d.x > max.x ? p3d.x : max.x;
                        max.y = p3d.y > max.y ? p3d.y : max.y;
                        max.z = p3d.z > max.z ? p3d.x : max.z;
                    }
                }
            }
        }

        tg.addChild(createBuilding());

        branchGroup.detach();
        TransformGroup tgRoot = (TransformGroup) branchGroup.getChild(0);
        tgRoot.addChild(tg);

        //set front viewer
        setViewer(viewStrs[0]);

        if(branchGroup.getParent()==null){
            simpleUniverse.addBranchGraph(branchGroup);
        }
    }

    public TransformGroup createBuilding(){
        TransformGroup tg = new TransformGroup();
        for (int i = 0; i < Rooms.size(); i++) {
            FloorRoom room = (FloorRoom) Rooms.get(i);
            tg.addChild(createRoomTG(room.circleVec));
//            if(room.doorVec.size()!=0){
//                tg.addChild(createDoorTG(room.doorVec));
//            }
        }
        return tg;
    }
    public TransformGroup createRoomTG(Vector circleVec){
        TransformGroup tg = new TransformGroup();
        for (int i = 0; i < circleVec.size(); i++) {
            Vector circle = (Vector) circleVec.get(i);

            int[] strips = {circle.size()+1};
            Point3d[] p3dArray = new Point3d[strips[0]];

            for (int j = 0; j < circle.size(); j++) {
                Point3d p3d = new Point3d((Point3d) circle.get(j));
                MapFunction(p3d, min, max);
                p3dArray[j] = new Point3d(p3d.x, p3d.y, p3d.z);
            }
            Point3d p3d0 = new Point3d((Point3d)circle.get(0));
            MapFunction(p3d0, min, max);
            p3dArray[p3dArray.length-1] = new Point3d(p3d0.x, p3d0.y, p3d0.z);

            ColoringAttributes colorAttr = new ColoringAttributes();
            colorAttr.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
            colorAttr.setColor(1.0f, 1.0f, 0.0f);

            LineAttributes lineAttr = new LineAttributes();
            lineAttr.setCapability(LineAttributes.ALLOW_WIDTH_WRITE);
            lineAttr.setLineWidth(1.0f);
            lineAttr.setLinePattern(0);
            lineAttr.setLineAntialiasingEnable(true);

            Appearance appear = new Appearance();
            appear.setColoringAttributes(colorAttr);
            appear.setLineAttributes(lineAttr);

            LineStripArray line = new LineStripArray(p3dArray.length,LineArray.COORDINATES,strips);
            line.setCoordinates(0, p3dArray);
            line.setCapability(Geometry.ALLOW_INTERSECT);

            Shape3D structure = new Shape3D(line,appear);

            tg.addChild(structure);
        }

        return tg;
    }
    public TransformGroup createDoorTG(Vector lineVec){
        TransformGroup tg = new TransformGroup();

        Point3d[] p3dArray = new Point3d[lineVec.size()];
        for (int i = 0; i < lineVec.size(); i++) {
            Point3d p3d = new Point3d((Point3d) lineVec.get(i));
            MapFunction(p3d, min, max);
            p3dArray[i] = new Point3d(p3d.x, p3d.y, p3d.z);
        }

        ColoringAttributes colorAttr = new ColoringAttributes();
        colorAttr.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
        colorAttr.setColor(0.0f, 0.0f, 1.0f);

        LineAttributes lineAttr = new LineAttributes();
        lineAttr.setCapability(LineAttributes.ALLOW_WIDTH_WRITE);
        lineAttr.setLineWidth(2.0f);
        lineAttr.setLinePattern(0);
        lineAttr.setLineAntialiasingEnable(true);

        Appearance appear = new Appearance();
        appear.setColoringAttributes(colorAttr);
        appear.setLineAttributes(lineAttr);

        LineArray line = new LineArray(p3dArray.length,LineArray.COORDINATES);
        line.setCoordinates(0, p3dArray);
        line.setCapability(Geometry.ALLOW_INTERSECT);

        Shape3D structure = new Shape3D(line,appear);

        tg.addChild(structure);

        return tg;
    }

    public static void setViewer(String str){
        //System.out.println(str);
        Transform3D tran = new Transform3D();
        if(str.equals(viewStrs[0])){
            tran.rotX(Math.toRadians(-90));
            Transform3D tranTemp = new Transform3D();
            tranTemp.rotY(Math.toRadians(0));
            tran.mul(tranTemp);
            tranTemp = new Transform3D();
            tranTemp.rotZ(Math.toRadians(180));
            tran.mul(tranTemp);
        }
        else if(str.equals(viewStrs[1])){
            tran.rotX(Math.toRadians(90));
            Transform3D tranTemp = new Transform3D();
            tranTemp.rotY(Math.toRadians(180));
            tran.mul(tranTemp);
            tranTemp = new Transform3D();
            tranTemp.rotZ(Math.toRadians(90));
            tran.mul(tranTemp);
        }
        else if(str.equals(viewStrs[2])){
            tran.rotX(Math.toRadians(0));
            Transform3D tranTemp = new Transform3D();
            tranTemp.rotY(Math.toRadians(0));
            tran.mul(tranTemp);
            tranTemp = new Transform3D();
            tranTemp.rotZ(Math.toRadians(180));
            tran.mul(tranTemp);
        }
        tran.setScale(objScale);
        transformGroup.setTransform(tran);
    }

    public void createMouseBehavior(BranchGroup bg, TransformGroup tg){
        bg.detach();

        MouseRotate rotate = new MouseRotate();
        rotate.setTransformGroup(tg);
        rotate.setSchedulingBounds(bounds);
        bg.addChild(rotate);

        MouseWheelZoom wheelzoom = new MouseWheelZoom();
        wheelzoom.setTransformGroup(tg);
        wheelzoom.setSchedulingBounds(bounds);
        bg.addChild(wheelzoom);

        MouseTranslate translate = new MouseTranslate();
        translate.setTransformGroup(tg);
        translate.setSchedulingBounds(bounds);
        bg.addChild(translate);

        if(bg.getParent() == null){
            simpleUniverse.addBranchGraph(bg);
        }
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

    public void paintRoom(Vector circle){
        TransformGroup tg = new TransformGroup();
        if(circle!=null){
            int[] strips = {circle.size()+1};
            Point3d[] p3dArray = new Point3d[strips[0]];

            for (int j = 0; j < circle.size(); j++) {
                Point3d p3d = new Point3d((Point3d) circle.get(j));
                MapFunction(p3d, min, max);
                p3dArray[j] = new Point3d(p3d.x, p3d.y, p3d.z);
            }
            Point3d p3d0 = new Point3d((Point3d)circle.get(0));
            MapFunction(p3d0, min, max);
            p3dArray[p3dArray.length-1] = new Point3d(p3d0.x, p3d0.y, p3d0.z);

            ColoringAttributes colorAttr = new ColoringAttributes();
            colorAttr.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
            colorAttr.setColor(1.0f, 0.0f, 0.0f);

            LineAttributes lineAttr = new LineAttributes();
            lineAttr.setCapability(LineAttributes.ALLOW_WIDTH_WRITE);
            lineAttr.setLineWidth(3.0f);
            lineAttr.setLinePattern(0);
            lineAttr.setLineAntialiasingEnable(true);

            Appearance appear = new Appearance();
            appear.setColoringAttributes(colorAttr);
            appear.setLineAttributes(lineAttr);

            LineStripArray line = new LineStripArray(p3dArray.length,LineArray.COORDINATES,strips);
            line.setCoordinates(0, p3dArray);
            line.setCapability(Geometry.ALLOW_INTERSECT);

            Shape3D structure = new Shape3D(line,appear);
            tg.addChild(structure);
        }

        branchGroup.detach();
        TransformGroup tgRoot = (TransformGroup) branchGroup.getChild(0);
        if(tgRoot.numChildren()>1){
            tgRoot.removeChild(1);
        }
        tgRoot.addChild(tg);

        if(branchGroup.getParent()==null){
            simpleUniverse.addBranchGraph(branchGroup);
        }
    }


    public Vector getInsideCircle(Point3d p3d){
        Vector circle = new Vector();
        for (int i = 0; i < Rooms.size(); i++) {
            Vector circleVec = ((FloorRoom) Rooms.get(i)).circleVec;
            for (int j = 0; j < circleVec.size(); j++) {
                circle = (Vector) circleVec.get(j);
                if(isInside(p3d,circle)){
                    return circle;
                }
            }
        }
        return null;
    }

    /**
     * 该算法思想是从点出发向右水平做一条射线，计算该射线与多边形的边的相交点个数，
     * 当点不在多边形边上时，如果是奇数，那么点就一定在多边形内部，否则，在外部。
     */
    public boolean isInside(Point3d p3d, Vector circle){
        int count = 0;
        if(p3d.z >= ((Point3d)(circle.get(0))).z &&
                p3d.z <= ((Point3d)(circle.get(0))).z+3){
            for (int i = 0; i < circle.size(); i++) {
                Point3d p3d1 = (Point3d) circle.get(i);
                Point3d p3d2 = i==circle.size()-1 ? (Point3d) circle.get(0):(Point3d) circle.get(i+1);

                if((p3d.y>p3d1.y && p3d.y<=p3d2.y)
                        ||(p3d.y>p3d2.y && p3d.y<=p3d1.y)){
                    double t = (p3d.y-p3d1.y)/(p3d2.y-p3d1.y);
                    double xt = p3d1.x+t*(p3d2.x-p3d1.x);
                    if(p3d.x==xt)
                        return false;
                    if(p3d.x<xt)
                        ++count;
                }
            }
        }
        else{
            return false;
        }
        if(count%2==0){
            return false;
        }
        return true;
    }


    @Override
    public void run(){
        while(true){
            if(fileName!=null){
                System.out.println(">>>    "+fileName);
                Vector<Point3d> p3dVec = getContent();
                for (int i = 0; i < p3dVec.size(); i++) {
                    Vector circle = getInsideCircle(p3dVec.get(i));
                    paintRoom(circle);
                }
                continue;
            }else{
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Vector<Point3d> getContent() {
        Vector<Point3d> vector = new Vector<Point3d>(5,1);
        File file = new File(IndoorWindow.locationPath + fileName);
        ArrayList<String> contents = FileHelper.readFileByLine(file);
        for (int i = 0; i < contents.size(); i++) {
            String[] coords = contents.get(i).split(" ");
            Point3d p3d = new Point3d(Double.valueOf(coords[0]),Double.valueOf(coords[1]),Double.valueOf(coords[2]));
            vector.add(p3d);
        }
        fileName = null;
        return vector;
    }
}
