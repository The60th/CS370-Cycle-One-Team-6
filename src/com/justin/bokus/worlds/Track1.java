package com.justin.bokus.worlds;


import com.justin.bokus.framework.SimulationBody;
import com.justin.bokus.framework.SimulationFrame;
import org.dyn4j.collision.narrowphase.Sat;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.Vector2;

import java.awt.*;

public class Track1 {

    public static void addBodies(World world){
       // world.setNarrowphaseDetector(new Sat());
        //use rectangles with triangles on the end to mimic a concave polygon

        //track infield
        SimulationBody infield = new SimulationBody(Color.green);
        infield.addFixture(Geometry.createPolygon(
                new Vector2(-200, 300), new Vector2(-300, 275), new Vector2(-375, 250),
                new Vector2(-425, 225), new Vector2(-450, 175), new Vector2(-475, 100),
                new Vector2(-500, 0), new Vector2(-475, -100), new Vector2(-450, -175),
                new Vector2(-425, -225), new Vector2(-375, -250), new Vector2(-300, -275),
                new Vector2(-200, -300), new Vector2(200, -300), new Vector2(300, -275),
                new Vector2(375, -250), new Vector2(425, -225), new Vector2(450, -175),
                new Vector2(475, -100), new Vector2(500, 0), new Vector2(475, 100),
                new Vector2(450, 175), new Vector2(425, 225), new Vector2(375, 250),
                new Vector2(300, 275), new Vector2(200, 300)));
        infield.setColor(Color.RED);
        world.addBody(infield);

        //top left outside corner
        SimulationBody corner1 = new SimulationBody(Color.green);
        corner1.addFixture(Geometry.createPolygon(
                new Vector2(-300, 400), new Vector2(-400, 400), new Vector2(-400, 375)));
        corner1.addFixture(Geometry.createPolygon(
                new Vector2(-400, 400), new Vector2(-475, 400), new Vector2(-475, 350), new Vector2(-400, 375)));
        corner1.addFixture(Geometry.createPolygon(
                new Vector2(-475, 400), new Vector2(-525, 400), new Vector2(-525, 325), new Vector2(-475, 350)));
        corner1.addFixture(Geometry.createPolygon(
                new Vector2(-525, 400), new Vector2(-550, 400), new Vector2(-550, 275), new Vector2(-525, 325)));
        corner1.addFixture(Geometry.createPolygon(
                new Vector2(-550, 400), new Vector2(-575, 400), new Vector2(-575, 200), new Vector2(-550, 275)));
        corner1.addFixture(Geometry.createPolygon(
                new Vector2(-575, 400), new Vector2(-600, 400), new Vector2(-600, 100), new Vector2(-575, 200)));
        world.addBody(corner1);

        //bottom left corner
        SimulationBody corner2 = new SimulationBody(Color.green);
        corner2.addFixture(Geometry.createPolygon(
                new Vector2(-600, -100), new Vector2(-600, -400), new Vector2(-575, -400), new Vector2(-575, -200)));
        corner2.addFixture(Geometry.createPolygon(
                new Vector2(-575, -200), new Vector2(-575, -400), new Vector2(-550, -400), new Vector2(-550, -275)));
        corner2.addFixture(Geometry.createPolygon(
                new Vector2(-550, -275), new Vector2(-550, -400), new Vector2(-525, -400), new Vector2(-525, -325)));
        corner2.addFixture(Geometry.createPolygon(
                new Vector2(-525, -325), new Vector2(-525, -400), new Vector2(-475, -400), new Vector2(-475, -350)));
        corner2.addFixture(Geometry.createPolygon(
                new Vector2(-475, -350), new Vector2(-475, -400), new Vector2(-400, -400), new Vector2(-400, -375)));
        corner2.addFixture(Geometry.createPolygon(
                new Vector2(-400, -375), new Vector2(-400, -400), new Vector2(-300, -400)));
        world.addBody(corner2);

        //bottom right corner
        SimulationBody corner3 = new SimulationBody(Color.green);
        corner3.addFixture(Geometry.createPolygon(
                new Vector2(300, -400), new Vector2(400, -400), new Vector2(400, -375)));
        corner3.addFixture(Geometry.createPolygon(
                new Vector2(400, -400), new Vector2(475, -400), new Vector2(475, -350), new Vector2(400, -375)));
        corner3.addFixture(Geometry.createPolygon(
                new Vector2(475, -400), new Vector2(525, -400), new Vector2(525, -325), new Vector2(475, -350)));
        corner3.addFixture(Geometry.createPolygon(
                new Vector2(525, -400), new Vector2(550, -400), new Vector2(550, -275), new Vector2(525, -325)));
        corner3.addFixture(Geometry.createPolygon(
                new Vector2(550, -400), new Vector2(575, -400), new Vector2(575, -200), new Vector2(550, -275)));
        corner3.addFixture(Geometry.createPolygon(
                new Vector2(575, -400), new Vector2(600, -400), new Vector2(600, -100), new Vector2(575, -200)));
        world.addBody(corner3);

        //top right corner
        SimulationBody corner4 = new SimulationBody(Color.green);
        corner4.addFixture(Geometry.createPolygon(
                new Vector2(300, 400), new Vector2(400, 375), new Vector2(400, 400)));
        corner4.addFixture(Geometry.createPolygon(
                new Vector2(475, 400), new Vector2(400, 400), new Vector2(400, 375), new Vector2(475, 350)));
        corner4.addFixture(Geometry.createPolygon(
                new Vector2(525, 400), new Vector2(475, 400), new Vector2(475, 350), new Vector2(525, 325)));
        corner4.addFixture(Geometry.createPolygon(
                new Vector2(550, 400), new Vector2(525, 400), new Vector2(525, 325), new Vector2(550, 275)));
        corner4.addFixture(Geometry.createPolygon(
                new Vector2(575, 400), new Vector2(550, 400), new Vector2(550, 275), new Vector2(575, 200)));
        corner4.addFixture(Geometry.createPolygon(
                new Vector2(600, 400), new Vector2(575, 400), new Vector2(575, 200), new Vector2(600, 100)));
        world.addBody(corner4);


    }

}
