import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

/**
 * Created by chaouki on 08-03-16.
 */
public class TCTest {

    @Test
    public void testDistanceCalculationBasic(){
        Position A=new Position(5.0, 0, 0);
        Position B=new Position(2.0, 0, 0);

        Assert.assertThat(A.getDistance3D(B), is(3.0));
    }

    @Test
    public void testDistanceCalculationComplex(){
        Position A=new Position(-7, -4, 3);
        Position B=new Position(17, 6, 2.5);

        Assert.assertEquals(A.getDistance3D(B), 26.004807, 0.000001);
    }

    @Test
    public void testDestinationCalculation2D(){
        Position gluthInitial=new Position(3274.252, -3166.707, 297.4225); // initial position of gluth
        Position zombie=new Position(3266.77, -3174.818, 297.4225); // position of the zombie

        Position destination = getZombieHugDestination(gluthInitial, zombie, 2.0);
        Assert.assertEquals(destination.getDistance3D(zombie), 2.0, 0.001);
        Assert.assertThat(gluthInitial.getDistance3D(destination), is(Matchers.lessThanOrEqualTo(gluthInitial.getDistance3D(zombie))));
        Assert.assertThat(gluthInitial.getDistance3D(destination)+destination.getDistance3D(zombie), is(Matchers.closeTo(gluthInitial.getDistance3D(zombie), 0.001)));

//        Position gluthFinal=new Position(3268.126, -3173.348, 297.4225); // position of gluth after charge
//        Assert.assertEquals(destination.GetPositionX(), gluthFinal.GetPositionX(), 0.001);
//        Assert.assertEquals(destination.GetPositionY(), gluthFinal.GetPositionY(), 0.001);
//        Assert.assertEquals(destination.GetPositionZ(), gluthFinal.GetPositionZ(), 0.001);
    }

    Position getZombieHugDestination(Position moving, Position fixed, double dist)
    {
        if (moving.getDistance3D(fixed) <= dist)
            return moving;
        else
        {
            double a = fixed.GetPositionX(), b = fixed.GetPositionY(); // fixed
            double c = moving.GetPositionX(), d = moving.GetPositionY(); // moving
            double sqrt = Math.sqrt(a * a - 2 * a * c + b * b - 2 * b * d + c * c + d * d);
            double x1 = (dist*(a - c)*getSign(b - d) + a*sqrt) / sqrt;
            double y1 = (dist*Math.abs(b - d) + b*sqrt) / sqrt;
            double x2 = (-1*dist*(a - c)*getSign(b - d) + a*sqrt) / sqrt;
            double y2 = (-1*dist*Math.abs(b - d) + b*sqrt) / sqrt;
            Position pos1=new Position(x1, y1, fixed.GetPositionZ());
            Position pos2=new Position(x2, y2, fixed.GetPositionZ());
            if (moving.getDistance3D(pos1) < moving.getDistance3D(pos2))
            return pos1;
            else
            return pos2;
        }
    }

    double getSign(double value) { return  (value > 0) ? 1.0 : ((value < 0) ? -1.0 : 0.0); }

    static class Position{
        private double x, y, z;

        public double getDistance3D(Position pos){
            double square = Math.pow(pos.GetPositionX() - this.GetPositionX(), 2) + Math.pow(pos.GetPositionY() - this.GetPositionY(), 2) +Math.pow(pos.GetPositionZ() - this.GetPositionZ(), 2);
            return Math.sqrt(square);
        }

        public Position(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public double GetPositionX() {
            return x;
        }

        public double GetPositionY() {
            return y;
        }

        public double GetPositionZ() {
            return z;
        }
    }

    
}
