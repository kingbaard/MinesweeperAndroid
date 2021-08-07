package com.example.minesweeper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Cell {

    // FEEL FREE TO CHANGE THESE!
    private int[] colors = {
            Color.GRAY,
            Color.GREEN,
            Color.RED,
            Color.rgb(0,0,100),
            Color.YELLOW,
            Color.CYAN,
            Color.MAGENTA,
            Color.BLACK
    };

    private final float BORDER_SIZE = 3f;

    public enum Type {
        MINE,
        NUMBER,
        EMPTY
    }

    private boolean isMarked = false;
    private boolean isSelected = false;
    private Type type;
    private double xPos;
    private double yPos;
    private double width;
    private double height;
    private int numNeighbors = 0;

    public Cell(double xPos, double yPos, double width, double height, Type type) {
        this.type = type;
        this.yPos = yPos;
        this.xPos = xPos;
        this.width = width;
        this.height = height;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void toggleMark() {
        isMarked = !isMarked;
    }

    public void select() {
        isSelected = true;
        isMarked = false;
    }

    public void setNumNeighbors(int numNeighbors) {
        this.numNeighbors = numNeighbors;
    }

    public void highlight11(Canvas canvas, Paint paint) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect((float) xPos,
                (float) yPos,
                (float) xPos + (float) width,
                (float) yPos + (float) height,
                paint);
    }

    public void draw(Canvas canvas, Paint paint) {
        // DONE: Draw the cell at its position depending on the state it is in
        paint.setStyle(Paint.Style.FILL);
        //If not selected and not marked paint default color, else marked color
        if (!isSelected) {
            if (isMarked) {
                paint.setColor(colors[5]);
            } else {
                paint.setColor(colors[0]);
            }
        } else if (type == Type.MINE) {
            paint.setColor(Color.RED);
        } else {
            paint.setColor(Color.WHITE);
        }
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect((float) xPos,
                (float) yPos,
                (float) xPos + (float) width,
                (float) yPos + (float) height,
                paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(BORDER_SIZE);
        paint.setColor(colors[7]); //black
        canvas.drawRect((float) xPos,
                (float) yPos,
                (float) xPos+ (float) width,
                (float) yPos + (float) height,
                paint);
        if (type == Type.NUMBER && isSelected) {
            paint.setStyle(Paint.Style.FILL);
            int numberColor = Color.BLACK;
            switch (numNeighbors) {
                case 1:
                    numberColor = Color.GREEN;
                    break;
                case 2:
                    numberColor = Color.BLUE;
                    break;
                case 3:
                    numberColor = Color.MAGENTA;
                    break;
                case 4:
                    numberColor = Color.RED;
            }
            paint.setColor(numberColor);
            paint.setTextSize(100);
            float numLocationX = (float) xPos + (float) (width / 2) - 25;
            float numLocationY = (float) yPos + (float) (height / 2) + 35;
            canvas.drawText(Integer.toString(numNeighbors), numLocationX, numLocationY, paint);
        }
    }

    public Type getType() {
        return type;
    }

    public int getNumNeighbors() {return numNeighbors; }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "isMarked=" + isMarked +
                ", isSelected=" + isSelected +
                ", type=" + type +
                ", xPos=" + xPos +
                ", yPos=" + yPos +
                ", numNeighbors=" + numNeighbors +
                '}';
    }
}
