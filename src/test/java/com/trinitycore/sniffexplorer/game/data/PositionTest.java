package com.trinitycore.sniffexplorer.game.data;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by chaouki on 26-03-17.
 */
public class PositionTest extends TestCase {

    @Test
    public void testIsBetween2D() throws Exception {
        Position a = new Position(0.0f, 0.0f, 0.0f);
        Position b = new Position(0.0f, 5.0f, 0.0f);

        Position c = new Position(0.0f, 2.0f, 0.0f);
        Position d = new Position(1.0f, 2.0f, 0.0f);
        Position e = new Position(0.0f, 6.0f, 0.0f);

        Assert.assertTrue(c.isBetween2D(a, b));
        Assert.assertTrue(!d.isBetween2D(a, b));
        Assert.assertTrue(!e.isBetween2D(a, b));

    }
}