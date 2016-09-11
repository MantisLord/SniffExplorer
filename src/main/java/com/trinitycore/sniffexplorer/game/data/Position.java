package com.trinitycore.sniffexplorer.game.data;

import java.util.Locale;

/**
 * Created by chaouki on 15-03-16.
 */
public class Position {

    private Float X;
    private Float Y;
    private Float Z;
    private Float O;

    public Position() {
    }

    public Position(Float x, Float y, Float z) {
        X = x;
        Y = y;
        Z = z;
    }

    public Position(Float x, Float y, Float z, Float o) {
        this.X = x;
        this.Y = y;
        this.Z = z;
        this.O = o;
    }

    public Float getX() {
        return X;
    }

    public void setX(Float x) {
        X = x;
    }

    public Float getY() {
        return Y;
    }

    public void setY(Float y) {
        Y = y;
    }

    public Float getZ() {
        return Z;
    }

    public void setZ(Float z) {
        Z = z;
    }

    public Float getO() {
        return O;
    }

    public void setO(Float O) {
        this.O = O;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("X=").append(X);
        sb.append(", Y=").append(Y);
        sb.append(", Z=").append(Z);
        sb.append(", O=").append(O);
        return sb.toString();
    }

    public String toFormatedString(){
        if(getO() != null)
            return String.format(Locale.ENGLISH, "%4.4f %4.4f %4.5f %4.6f", getX(), getY(), getZ(), getO());
        else
            return String.format(Locale.ENGLISH, "%4.4f %4.4f %4.5f", getX(), getY(), getZ());
    }

    public String toFormatedStringWoOrientation(){
        return String.format(Locale.ENGLISH, "%4.4f %4.4f %4.5f", getX(), getY(), getZ());
    }

    @Override
    public boolean equals(Object o) { // compare without the orientation
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (X != null ? !X.equals(position.X) : position.X != null) return false;
        if (Y != null ? !Y.equals(position.Y) : position.Y != null) return false;
        return !(Z != null ? !Z.equals(position.Z) : position.Z != null);

    }

    @Override
    public int hashCode() {
        int result = X != null ? X.hashCode() : 0;
        result = 31 * result + (Y != null ? Y.hashCode() : 0);
        result = 31 * result + (Z != null ? Z.hashCode() : 0);
        return result;
    }
}
