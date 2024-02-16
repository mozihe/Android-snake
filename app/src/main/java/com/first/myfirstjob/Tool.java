package com.first.myfirstjob;

import android.graphics.Point;

import java.util.ArrayList;

public class Tool {
    public static int findIndex(ArrayList<Point> list, Point point) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).x == point.x && list.get(i).y == point.y) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isEaten(Point head, Point food) {
        return head.x == food.x && head.y == food.y;
    }
}
