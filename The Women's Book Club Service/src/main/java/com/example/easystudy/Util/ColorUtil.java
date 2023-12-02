package com.example.easystudy.Util;

import java.awt.*;
import java.util.Random;

public class ColorUtil {
    public static final Color black  = new Color(21, 21, 21);
    public static final Color red    = new Color(255, 0, 0);
    public static final Color yellow = new Color(255, 128, 0);
    public static final Color orange = new Color(16, 162, 40);
    public static final Color pink   = new Color(10, 109, 143);
    public static final Color blue   = new Color(100, 8, 145);
    public static final Color green  = new Color(4, 117, 78);
    Color[] colors = {black,red,yellow,orange,blue,green};
    public Color getColor()
    {
        Random random = new Random();
        int number = random.nextInt(colors.length);
        return  colors[number];
    }
}
