package com.first.myfirstjob;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

public class Snake {
    private Point head;
    private ArrayList<Point> body;
    private Point oldBody;
    private int speed;
    private Point direction;
    private int cellNum;

    public Snake(int cellNum) {
        this.cellNum = cellNum;
        head = new Point((int)(cellNum / 2), (int)(cellNum / 2));
        body = new ArrayList<>();
        body.add(new Point(head.x, head.y + 1));
        speed = 5;
        direction = new Point(0, -1);
    }

    public Point getHead() {
        return head;
    }

    public ArrayList<Point> getBody() {
        return body;
    }

    public Point getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setDirection(Point direction) {
        this.direction = direction;
    }

    public void move() {
        oldBody = this.body.get(this.body.size() - 1);
        this.body.remove(this.body.size() - 1);
        this.body.add(0, this.head);
        this.head = new Point(this.head.x + this.direction.x, this.head.y + this.direction.y);

    }

    public void grow() {
        this.body.add(oldBody);
    }

    public ArrayList<Point> getFullBody() {
        ArrayList<Point> fullBody = new ArrayList<>(this.body);
        fullBody.add(this.head);
        return fullBody;
    }

    public boolean checkCollision() {
        return this.head.x < 0 || this.head.x >= cellNum || this.head.y < 0 || this.head.y >= cellNum || Tool.findIndex(this.body, this.head) != -1;
    }

    public void speedUp() {
        if (this.speed < 9) {
            this.speed += 2;
        }
    }

    public void slowDown() {
        if (this.speed > 1) {
            this.speed -= 2;
        }
    }

    public void draw(Canvas canvas, int cellSize) {
        Paint paint = new Paint();
        paint.setARGB(255, 0, 255, 0);
        for (Point point : getFullBody()) {
            canvas.drawRect(25 + point.x * cellSize, 25 + point.y * cellSize, 25 + (point.x + 1) * cellSize, 25 + (point.y + 1) * cellSize, paint);
        }
    }

}
