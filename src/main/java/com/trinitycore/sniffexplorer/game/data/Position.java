package com.trinitycore.sniffexplorer.game.data;

import java.util.Locale;

/**
 * Created by chaouki on 15-03-16.
 */
public class Position {

    private float X;
    private float Y;
    private float Z;
    private float orientation;

    public Position() {
    }

    public Position(float x, float y, float z) {
        X = x;
        Y = y;
        Z = z;
    }

    public float getX() {
        return X;
    }

    public void setX(float x) {
        X = x;
    }

    public float getY() {
        return Y;
    }

    public void setY(float y) {
        Y = y;
    }

    public float getZ() {
        return Z;
    }

    public void setZ(float z) {
        Z = z;
    }

    public float getOrientation() {
        return orientation;
    }

    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("X=").append(X);
        sb.append(", Y=").append(Y);
        sb.append(", Z=").append(Z);
        sb.append(", O=").append(orientation);
        return sb.toString();
    }

    public String toFormatedString(){
        return String.format(Locale.ENGLISH, "%4.3f %4.3f %4.4f", getX(), getY(), getZ());
    }
}
