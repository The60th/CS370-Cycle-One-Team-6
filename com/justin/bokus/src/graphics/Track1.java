package com.justin.bokus.graphics;

import com.justin.bokus.framework.SimulationBody;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import java.awt.*;

public class Track1 {
    public static void buildWorld(World world, SimulationBody car, SimulationBody car1){

        //use rectangles with triangles on the end to mimic a concave polygon
        SimulationBody infield = new SimulationBody(Color.green);
        infield.setMass(MassType.INFINITE);
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
        world.addBody(infield);

        //top left outside corner
        SimulationBody corner1 = new SimulationBody(Color.green);
        corner1.setMass(MassType.INFINITE);
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
        corner2.setMass(MassType.INFINITE);
        corner2.setMass(MassType.INFINITE);
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
        corner3.setMass(MassType.INFINITE);
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
        corner4.setMass(MassType.INFINITE);
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

        SimulationBody edgeL = new SimulationBody(Color.green);
        edgeL.setMass(MassType.INFINITE);
        edgeL.addFixture(Geometry.createRectangle(5, 230));
        edgeL.translate(-599, 0);
        world.addBody(edgeL);

        SimulationBody edgeU = new SimulationBody(Color.green);
        edgeU.setMass(MassType.INFINITE);
        edgeU.addFixture(Geometry.createRectangle(620, 5));
        edgeU.translate(0, 400);
        world.addBody(edgeU);

        SimulationBody edgeD = new SimulationBody(Color.green);
        edgeD.setMass(MassType.INFINITE);
        edgeD.addFixture(Geometry.createRectangle(620, 5));
        edgeD.translate(0, -400);
        world.addBody(edgeD);

        SimulationBody edgeR = new SimulationBody(Color.green);
        edgeR.setMass(MassType.INFINITE);
        edgeR.addFixture(Geometry.createRectangle(5, 230));
        edgeR.translate(599, 0);
        world.addBody(edgeR);

        car.translate(-575, -25);

        car1.translate(-525, -25);

    }

}
