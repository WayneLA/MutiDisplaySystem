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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by 85492 on 2017/2/28.
 */
public class TDViewer extends JPanel {
    private SimpleUniverse simpleUniverse;
    private Canvas3D canvas3d;
    private BranchGroup branchGroup = new BranchGroup();
    private TransformGroup transGroup = new TransformGroup();
    private BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0 );
    private boolean min_max_init=false;



    public static Point3d min = new Point3d(-10.0,-10.0,10.0);
    public static Point3d max = new Point3d(20.0,20.0,20.0);

    public TDViewer(){
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        canvas3d = new Canvas3D(config);
        simpleUniverse = new SimpleUniverse(canvas3d);
        simpleUniverse.getViewingPlatform().setNominalViewingTransform();
        transGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        transGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transGroup.setCapability(Group.ALLOW_CHILDREN_READ);
        transGroup.setCapability(Group.ALLOW_CHILDREN_WRITE);
        branchGroup.setCapability(BranchGroup.ALLOW_DETACH);
        branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);

        branchGroup.addChild(transGroup);

        //light
        Color3f lightColor = new Color3f(1.0f, 0.0f, 0.0f);
        Vector3f lightDirection = new Vector3f(-1.0f, 0.0f, -1.0f);
        DirectionalLight light = new DirectionalLight(lightColor, lightDirection);
        light.setInfluencingBounds(bounds);
        branchGroup.addChild(light);

        //background
		Color3f bgColor = new Color3f(1.0f, 1.0f, 1.0f);
		Background bg = new Background(bgColor);
		bg.setApplicationBounds(bounds);
		branchGroup.addChild(bg);

        simpleUniverse.addBranchGraph(branchGroup);
        createMouseBehavior(branchGroup, transGroup);

        setLayout(new BorderLayout());
        add(canvas3d,BorderLayout.CENTER);

        initMenu();

        initBuilding();
    }

    public void initMenu(){
        final JPopupMenu jp = new JPopupMenu();

        jp.add("红色");
        jp.add("蓝色");
        jp.add("蓝色");
        jp.add("蓝色");
        jp.add("蓝色");

        canvas3d.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    // 弹出菜单
                    jp.show(TDViewer.this, e.getX(), e.getY());
                }
            }
        });
    }

    public void initBuilding(){
        TransformGroup tg = new TransformGroup();


        ArrayList<String> tuples = FileHelper.readRoomTuple(IndoorWindow.fileName);
        for(int i=0;i<tuples.size();i++){
            String temp = tuples.get(i).trim();
            FloorRoom room = new FloorRoom(temp);
            tg.addChild(room.createRoom());
            tg.addChild(room.createDoor());
        }
        branchGroup.detach();
        TransformGroup tgRoot = (TransformGroup) branchGroup.getChild(0);
        tgRoot.addChild(tg);
        if(branchGroup.getParent()==null){
            simpleUniverse.addBranchGraph(branchGroup);
        }
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

}
