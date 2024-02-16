package com.first.myfirstjob;

import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

public class Game {
    private int cellNum;
    private Snake snake;
    private Food food;
    private int score;
    private boolean isOver;

    public Game(int cellNum) {
        this.cellNum = cellNum;
        snake = new Snake(cellNum);
        food = new Food(cellNum, snake.getFullBody());
        score = 0;
        isOver = false;
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    public int getScore() {
        return score;
    }

    public boolean isOver() {
        return isOver;
    }

    public void update() {

        snake.move();
        if (snake.checkCollision()) {
            isOver = true;
            return;
        }

        if (Tool.isEaten(snake.getHead(), food.getLocation())) {
            score++;
            snake.grow();
            switch (food.getType()) {
                case SPEEDUP:
                    snake.speedUp();
                    break;
                case SLOWDOWN:
                    snake.slowDown();
                    break;
            }
            food = new Food(cellNum, snake.getFullBody());
        }

    }

    public void setDirection(Point direction) {
        snake.setDirection(direction);
    }

    public void restart() {
        snake = new Snake(cellNum);
        food = new Food(cellNum, snake.getFullBody());
        score = 0;
        isOver = false;
    }

    public void draw(Canvas canvas, int cellSize) {
        snake.draw(canvas, cellSize);
        food.draw(canvas, cellSize);
    }

}
