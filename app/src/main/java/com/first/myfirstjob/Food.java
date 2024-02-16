package com.first.myfirstjob;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

public class Food {
    private Point location;

    private Type type;
    public Food(int cellNum, ArrayList<Point> unavailable) {
        Random random = new Random();
        location = new Point();
        while (true) {
            location.x = random.nextInt(cellNum);
            location.y = random.nextInt(cellNum);
            if (Tool.findIndex(unavailable, location) == -1) {
                break;
            }
        }
        int myTool = random.nextInt(10);
        switch (myTool) {
            case 0:
                type = Type.SPEEDUP;
                break;
            case 1:
                type = Type.SLOWDOWN;
                break;
            default:
                type = Type.NORMAL;
                break;
        }

    }

    public Point getLocation() {
        return location;
    }

    public Type getType() {
        return type;
    }

    public void draw(Canvas canvas, int cellSize) {
        Paint paint = new Paint();
        switch (type) {
            case NORMAL:
                paint.setARGB(255, 255, 0, 0);
                break;
            case SPEEDUP:
                paint.setARGB(255, 0, 0, 255);
                break;
            case SLOWDOWN:
                paint.setARGB(255, 255, 255, 0);
                break;
        }
        canvas.drawRect(25 + location.x * cellSize, 25 + location.y * cellSize, 25 + (location.x + 1) * cellSize, 25 + (location.y + 1) * cellSize, paint);
    }
}
