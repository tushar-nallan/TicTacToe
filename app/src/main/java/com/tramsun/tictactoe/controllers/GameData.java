package com.tramsun.tictactoe.controllers;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Tushar on 19-03-2015.
 */
public class GameData {
    ArrayList<Integer> grid;
    int gridLength;
    int activePlayer;
    int playCount = 0;
    final int maxPlayers;
    public GameData(int gridLength, int maxPlayers) {
        grid = new ArrayList<>(gridLength * gridLength);
        for(int i=0; i<gridLength*gridLength; i++) {
            grid.add(-1);
        }
        this.gridLength = gridLength;
        this.maxPlayers = maxPlayers;
        activePlayer=0;
    }
    public boolean isGridFull(){
        return playCount == gridLength*gridLength;
    }

    public boolean play(int x) {
        if(isGridFull() || grid.get(x) != -1) return false;
        playCount++;
        grid.set(x, activePlayer);
        if(isLineComplete(x)) {
            return true;
        }
        activePlayer = (activePlayer+1)%maxPlayers;
        return false;
    }

    private boolean isLineComplete(int x) {
        return isHorizontalComplete(x) || isVerticalComplete(x) || isDiagonalComplete(x);
    }

    private boolean isDiagonalComplete(int x) {
        return isLeftToRightDiagonalComplete(x) || isRightToLeftDiagonalComplete(x);
    }

    private boolean isRightToLeftDiagonalComplete(int pos) {
        int x = pos/gridLength;
        int y = pos%gridLength;
        if( x + y != gridLength-1 )
            return false;
        boolean isComplete = true;
        x=0; y= gridLength-1;
        for(int i=0;i<gridLength; i++) {
            int iterPos = gridLength*x + y;
            Log.e("GameData", "x="+x+", y="+y+", grid[x][y]="+grid.get(iterPos));
            if(grid.get(iterPos) != activePlayer) {
                isComplete = false;
                break;
            }
            x++;
            y--;
        }
        Log.e("GameData", "isRightToLeftDiagonalComplete="+isComplete);
        return isComplete;
    }

    private boolean isLeftToRightDiagonalComplete(int pos) {
        int x = pos/gridLength;
        int y = pos%gridLength;
        if( x != y )
            return false;
        boolean isComplete = true;
        for(int i=0;i<gridLength; i++) {
            int iterPos = i + gridLength*i;
            if(grid.get(iterPos) != activePlayer) {
                isComplete = false;
                break;
            }
        }
        Log.e("GameData", "isLeftToRightDiagonalComplete="+isComplete);
        return isComplete;
    }

    private boolean isVerticalComplete(int x) {
        int start = x% gridLength;
        boolean colComplete = true;
        while(start < gridLength*gridLength) {
            if(grid.get(start) != activePlayer){
                colComplete = false;
                break;
            }
            start+=gridLength;
        }
        Log.e("GameData", "isVerticalComplete="+colComplete);
        return colComplete;
    }

    private boolean isHorizontalComplete(int x) {
        int start = (x/ gridLength)* gridLength;
        int end = start + gridLength;
        boolean rowComplete = true;
        for(int i=start; i<end; i++) {
            if(grid.get(i) != activePlayer) {
                rowComplete = false;
                break;
            }
        }
        Log.e("GameData", "isHorizontalComplete="+rowComplete);
        return rowComplete;
    }

    public ArrayList<Integer> getGrid() {
        return grid;
    }

    public void setGrid(ArrayList<Integer> grid) {
        this.grid = grid;
    }

    public int getGridLength() {
        return gridLength;
    }

    public void setGridLength(int gridLength) {
        this.gridLength = gridLength;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(int activePlayer) {
        this.activePlayer = activePlayer;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }
}
