  package com.example.minesweeper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Collections;

public class  Game {
    private enum State {
        PLAY,
        WIN,
        LOSE,
    }

    Cell[][] cells;
    int mineCount;
    int rows = 24;
    int cols = 12;
    boolean firstTap = true;
    double cellWidth;
    double cellHeight;
    int screenWidth;
    int screenHeight;
    State state = State.PLAY;

    public Game(String gameMode, int screenWidth, int screenHeight) {
        // Create 2d tile array with size for null values on the edges
        cells = new Cell[rows+2][cols+2];
        if (gameMode.equals("expert")) {
            mineCount = 40;
        } else if (gameMode.equals("intermediate")) {
            mineCount = 20;
        } else {
            mineCount = 10;
        }
        cellHeight = (double) screenHeight / rows;
        cellWidth = (double) screenWidth / cols;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        initCells();
    }

    private void initCells() {
       // DONE: create all cells, randomly assigning cells to be mines depending on difficulty.
        // HINT: 1. Create an ArrayList of Booleans
        ArrayList<Boolean> mineQueue = new ArrayList<>();
        for (int i = 0; i < (rows * cols); i++) {
            if (i < mineCount) {
        //      2. set the first n (where n is number of mines you want) to be true
                mineQueue.add(i, true);
            } else {
        //      3. set the remaining to be false (the total number of items in the list should be rows * cols)
                mineQueue.add(i, false);
            }
        }
        //      4. then shuffle the array list using Collections.shuffle()
        Collections.shuffle(mineQueue);
        //      5. Then you can use this arraylist like a queue when iterating of your grid
        // iterates through everything in tile array,
        // ignoring null edge tiles while assigning types to others
        for (int r = 0; r < rows + 1; r++) {
            for (int c = 0; c < cols + 1; c++){
                if (r == 0 || r == rows + 1 || c == 0 || c == cols + 1) {
                    cells[r][c] = null;
                } else {
                    Cell.Type type;
                    if (mineQueue.get(0)) {
                        type = Cell.Type.MINE;
                    } else {
                        type = Cell.Type.EMPTY;
                    }
                    mineQueue.remove(0);
                    double xPos = (c-1) * cellWidth;
                    double yPos = (r-1) * cellHeight;
                    cells[r][c] = new Cell(xPos, yPos, cellWidth, cellHeight, type);
                    // Find number types and assign respective neighbor count
                    }
                }
        }
        setNumberTypes(cells);
        Log.i("Cells:", "initCells() complete");
    }

    private void setNumberTypes(Cell[][] cells) {
        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {
                if (cells[r][c].getType() == Cell.Type.EMPTY) {
                    cells[r][c].setNumNeighbors(countNeighbors(r, c, cells));
                    Log.i("init()", "countNeighbors for (" + r + ", " + c + "): " +
                            countNeighbors(r, c, cells)
                    );
                    if (cells[r][c].getNumNeighbors() > 0) {
                        cells[r][c].setType(Cell.Type.NUMBER);
                        Log.i("init()", "Set tile type to NUMBER");
                    }
                }
            }
        }
    }

    private void selectMine(int row, int col) {

    }


    private int countNeighbors(int row, int col, Cell[][] cells) {
        // DONE: Count how many mines surround the cell at (row, col);
        int counter = 0;
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c ++) {
                if (cells[r][c] != null) {
                    if (cells[r][c].getType() == Cell.Type.MINE) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    private void revealMines() {
        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {
                if (cells[r][c].getType() == Cell.Type.MINE) {
                    cells[r][c].select();
                }
            }
        }
    }

    public int countClearedTiles(Cell[][] cells) {
        int count = 0;
        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {
                if (cells[r][c].getType() != Cell.Type.MINE) {
                    cells[r][c].select();
                }
            }
        }
        return count;
    }

    private void explodeBlankCells(int row, int col) {
        // DONE: recursively select all surrounding cells, only stopping when
        //      you reach a cell that has already been selected,
        //      or when you select a cell that is not Empty
        if (cells[row][col] == null) return;
        if (cells[row][col].isSelected()) return;
//        Thread.sleep(500);
        cells[row][col].select();
        if (cells[row][col].getType() == Cell.Type.NUMBER) return;
        explodeBlankCells(row, col - 1);
        explodeBlankCells(row, col + 1);
        explodeBlankCells(row - 1, col - 1);
        explodeBlankCells(row - 1, col);
        explodeBlankCells(row - 1, col + 1);
        explodeBlankCells(row + 1, col - 1);
        explodeBlankCells(row + 1, col);
        explodeBlankCells(row + 1, col + 1);
        Log.i("explodeBlankCells()", "Row:" + row + " Col: " + col);
    }


    private boolean winCheck() {
        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {
                Cell currentCell = cells[r][c];
                if (currentCell.getType() == Cell.Type.MINE && !currentCell.isMarked()) {
                    return false;
                }
                if (currentCell.getType() != Cell.Type.MINE && currentCell.isMarked()) {
                    return false;
                }
                if (!currentCell.isSelected() && currentCell.getType() != Cell.Type.MINE) {
                    return false;
                }
            }
        }
        return true;
    }
    public void handleTap(MotionEvent e) {
        // DONE: find the cell the player tapped
        //      Depending on what type of cell they tapped
        //         mine: select the cell, reveal the mines, and set the game to the LOSE state
        //         empty cell: select the cell and explode the surrounding cells
        //         all others: simply select the cell
        float locationX = e.getX();
        float locationY = e.getY();
        //find what row/col was tapped
        int colTapped = (int) Math.floor(locationX / cellWidth) + 1;
        int rowTapped = (int) Math.floor(locationY / cellHeight) + 1;
        selectCell(rowTapped, colTapped);
    }

    public void selectCell(int row, int col) {
        if (!cells[row][col].isSelected()) {
            if (cells[row][col].getType() == Cell.Type.EMPTY ||
                    cells[row][col].getType() == Cell.Type.NUMBER) {
                explodeBlankCells(row, col);
            }
            cells[row][col].select();
            if (cells[row][col].getType() == Cell.Type.MINE) {
                if (firstTap) {
                    cells[row][col].setType(Cell.Type.EMPTY);
                    setNumberTypes(cells);
                } else {
                revealMines();
                state = State.LOSE;
                Log.i("GAME INFO", "YOU LOSE!");
                }
            }
        }
        if (firstTap) {
            firstTap = false;
        }
        if (winCheck()) {
            state = State.WIN;
        }
    }


    public void handleLongPress(MotionEvent e) {
        // DONE: find the cell and toggle its mark
        //       then check to see if the player won the game
        float locationX = e.getX();
        float locationY = e.getY();
        //find what row/col was tapped
        int colTapped = (int) Math.floor(locationX / cellWidth) + 1;
        int rowTapped = (int) Math.floor(locationY / cellHeight) + 1;
        Log.i("Cell Info", "(" + rowTapped + "," + colTapped + "):" + cells[rowTapped][colTapped].toString());
        cells[rowTapped][colTapped].toggleMark();
        if (winCheck()) {
            state = State.WIN;
        }
    }


    public void draw(Canvas canvas, Paint paint) {
        for(int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {
                cells[r][c].draw(canvas, paint);
                paint.reset();
            }
        }
        if (state == State.WIN) {
            Log.i("GAME INFO", "YOU WIN!");
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.YELLOW);
            canvas.drawRect((float) (screenWidth / 2) - 500,
                    (float) ((screenHeight / 2) + 300),
                    (float) ((screenWidth/2) + 500),
                    (float) ((screenHeight / 2) - 300),
                    paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(200);
            canvas.drawText("YOU WIN", (float) screenWidth /2 - 400, (float) screenHeight/2 + 75, paint);
        }
        if (state == State.LOSE) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            canvas.drawRect((float) (screenWidth / 2) - 500,
                    (float) ((screenHeight / 2) + 300),
                    (float) ((screenWidth/2) + 500),
                    (float) ((screenHeight / 2) - 300),
                    paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(200);
            canvas.drawText("TRY AGAIN", (float) screenWidth / 2 - 500, (float) screenHeight/ 2 + 75, paint);
        }
    Log.i("draw()", "completed");
    }
}
