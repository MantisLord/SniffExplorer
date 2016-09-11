package com.trinitycore.sniffexplorer.utils;

import com.trinitycore.sniffexplorer.game.data.Position;
import org.apache.commons.math3.ml.distance.EuclideanDistance;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by chaouki on 11-09-16.
 */
public class GeometryUtils {

    public static double getDistance3D(Position positionA, Position positionB) {
        double a[]={positionA.getX(), positionA.getY(), positionA.getZ()};
        double b[]={positionB.getX(), positionB.getY(), positionB.getZ()};
        double value = new EuclideanDistance().compute(a, b);
        final BigDecimal valueBigDecimal = new BigDecimal(value);
        return new BigDecimal(value).setScale(3, RoundingMode.HALF_UP).doubleValue();
    }

    public static double getDistance2D(Position positionA, Position positionB) {
        double a[]={positionA.getX(), positionA.getY()};
        double b[]={positionB.getX(), positionB.getY()};
        return new EuclideanDistance().compute(a, b);
    }
}
