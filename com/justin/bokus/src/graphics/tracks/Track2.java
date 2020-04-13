package graphics.tracks;

import framework.SimulationBody;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import java.awt.*;

public class Track2 {
    public static void buildWorld(World world, SimulationBody car, SimulationBody car1){
        SimulationBody infield = new SimulationBody(Color.green);
        infield.setMass(MassType.INFINITE);
        infield.addFixture(Geometry.createPolygon(
                new Vector2(-400, 250), new Vector2(-400, -250), new Vector2(300, 00)));
        world.addBody(infield);

        SimulationBody left = new SimulationBody(Color.green);
        left.setMass(MassType.INFINITE);
        left.addFixture(Geometry.createPolygon(
                new Vector2(-500, -400), new Vector2(-500, 400), new Vector2(-600, 400), new Vector2(-600, -400)));
        world.addBody(left);

        SimulationBody topRight = new SimulationBody(Color.green);
        topRight.setMass(MassType.INFINITE);
        topRight.addFixture(Geometry.createPolygon(
                new Vector2(600, 400), new Vector2(-500, 400), new Vector2(500, 0), new Vector2(600,  0)));
        world.addBody(topRight);

        SimulationBody bottomRight = new SimulationBody(Color.green);
        bottomRight.setMass(MassType.INFINITE);
        bottomRight.addFixture(Geometry.createPolygon(
                new Vector2(-500, -400), new Vector2(600, -400), new Vector2(600,  0), new Vector2(500, 0)));
        world.addBody(bottomRight);

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

        car.translate(-475, -25);

        car1.translate(-425, -25);
    }
}