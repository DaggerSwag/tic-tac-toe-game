package com.game.project;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class TicTacToe {
    static class Move
    {
        int row,col;
    }
    static CustomLabel[][] labels;
    ImageIcon x_tile_colored=new ImageIcon("D:\\Free time projects\\TicTacToe game\\assets\\x-tile-colored.png");
    ImageIcon o_tile_colored=new ImageIcon("D:\\Free time projects\\TicTacToe game\\assets\\o-tile-colored.png");
    ImageIcon blank_tile=new ImageIcon("D:\\Free time projects\\TicTacToe game\\assets\\blank-tile.png");
    static int clickCount=0;
    static JFrame frame=new JFrame("Tic Tac Toe");
    public TicTacToe(){
        frameInitializer();
    }

    //Frame Initialization and CustomLabel object creation and instantiation
    private void frameInitializer()
    {
//        int width = 320;
        int width=o_tile_colored.getIconWidth()*3+15;
        int height=o_tile_colored.getIconHeight()*3+37;

//        int height = 340;
        frame.setSize(width,height);
        clickCount=0;
        labels=new CustomLabel[3][3];
        for(int row=0;row<3;row++) {
            for (int col = 0; col < 3; col++) {
                JLabel myLabel = new JLabel();
                labels[row][col] = new CustomLabel(myLabel, "", row, col);
                frame.add(labels[row][col].getLabel());
            }
        }
        initGame();

        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    private void initGame()
    {
        for(int row=0;row<3;row++)
        {
            for(int col=0;col<3;col++)
            {
                int finalCol = col;
                int finalRow = row;
                //Adding mouse listener to respective tile for player move
                labels[row][col].getLabel().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(Objects.equals(labels[finalRow][finalCol].getSymbol(), "")) {

                            labels[finalRow][finalCol].getLabel().setIcon(x_tile_colored);
                            labels[finalRow][finalCol].setSymbol("X");

                            System.out.println("Coordinates:" + "Row: " + finalRow + " Col: " + finalCol + " Symbol: " + labels[finalRow][finalCol].getSymbol());
                            if(checkWin())
                            {
                                System.out.println(labels[finalRow][finalCol].getSymbol()+" wins");
                                endGame(labels[finalRow][finalCol].getSymbol()+" wins");
                            }
                            else {
                                if (isBoardFull(labels)) {
                                    System.out.println("It's a draw!");
                                    endGame("It's a draw!");
                                }
                                else {
                                    int[] arr = makeCPUMove();
                                    if (Objects.equals(labels[arr[0]][arr[1]].getSymbol(), "")) {
                                        labels[arr[0]][arr[1]].getLabel().setIcon(o_tile_colored);
                                        labels[arr[0]][arr[1]].setSymbol("O");
                                        System.out.println("Coordinates:" + "Row: " + arr[0] + " Col: " + arr[1] + " Symbol: " + labels[arr[0]][arr[1]].getSymbol());
                                        if(checkWin())
                                        {
                                            System.out.println(labels[arr[0]][arr[1]].getSymbol()+" wins");
                                            endGame(labels[arr[0]][arr[1]].getSymbol()+" wins");
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    //TicTacToe Game LOGIC
    private boolean checkWin(){

        //Row-wise check
        for(int i=0;i<3;i++) {
            if ((!Objects.equals(labels[0][i].getSymbol(), "")) &&
                    (Objects.equals(labels[0][i].getSymbol(), labels[1][i].getSymbol())) &&
                    (Objects.equals(labels[1][i].getSymbol(), labels[2][i].getSymbol()))) {
                return true;
            }
        }

        //Column-wise check
            for (int i = 0; i < 3; i++) {
                if ((!Objects.equals(labels[i][0].getSymbol(), "")) &&
                        (Objects.equals(labels[i][0].getSymbol(), labels[i][1].getSymbol())) &&
                        (Objects.equals(labels[i][1].getSymbol(), labels[i][2].getSymbol()))) {
                    return true;
                }
            }

        //Diagonal check from top left to bottom right
            if ((!Objects.equals(labels[0][0].getSymbol(), "")) &&
                    (Objects.equals(labels[0][0].getSymbol(), labels[1][1].getSymbol())) &&
                    (Objects.equals(labels[1][1].getSymbol(), labels[2][2].getSymbol())))
                return true;

        //Diagonal check from top right to bottom left
        return (!Objects.equals(labels[0][2].getSymbol(), "")) &&
                (Objects.equals(labels[0][2].getSymbol(), labels[1][1].getSymbol())) &&
                (Objects.equals(labels[1][1].getSymbol(), labels[2][0].getSymbol()));
    }

    //Random tile coordinates chosen for CPU move
    private int[] makeCPUMove() {

        int best=-1000;
        Move move= new Move();

        move.row=-1;
        move.col=-1;
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(Objects.equals(labels[i][j].getSymbol(),"")) {
                    labels[i][j].setSymbol("O");
                    int val = minimax(labels,0, false);
                    labels[i][j].setSymbol("");

                    if (val > best) {
                        move.row = i;
                        move.col = j;
                        best = val;
                    }
                }
            }
        }
        return new int[]{move.row, move.col};
    }

    private int minimax(CustomLabel[][] labels, int depth, boolean isMax)
    {
        int score = evaluateBoard(labels);
        if(score == 10)
            return score;
        if(score == -10)
            return score;
        if (isBoardFull(labels))
            return 0;

        int best;
        if (isMax)
        {
            best = -1000;
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (Objects.equals(labels[i][j].getSymbol(),""))
                    {
                        labels[i][j].setSymbol("O");
                        best = Math.max(best, minimax(labels, depth + 1, false));

                        labels[i][j].setSymbol("");
                    }
                }
            }
        }
        else {
            best = 1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (Objects.equals(labels[i][j].getSymbol(), "")) {
                        labels[i][j].setSymbol("X");
                        best = Math.min(best, minimax(labels, depth + 1, true));

                        labels[i][j].setSymbol("");
                    }
                }
            }
        }
        return best;
    }

    private boolean isBoardFull(CustomLabel[][] labels){
        boolean isBoardFull = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Objects.equals(labels[i][j].getSymbol(), "")) {
                    isBoardFull = false;
                    break;
                }
            }
        }
        return isBoardFull;
    }

    private int evaluateBoard(CustomLabel[][] labels){
        for(int i=0;i<3;i++) {
            if (
                    (Objects.equals(labels[0][i].getSymbol(), labels[1][i].getSymbol())) &&
                    (Objects.equals(labels[1][i].getSymbol(), labels[2][i].getSymbol()))) {
                if(Objects.equals(labels[0][i].getSymbol(),"X"))
                    return -10;
                else if(Objects.equals(labels[0][i].getSymbol(),"O"))
                    return 10;
            }
        }
        for(int i=0;i<3;i++) {
            if (
                    (Objects.equals(labels[i][0].getSymbol(), labels[i][1].getSymbol())) &&
                    (Objects.equals(labels[i][1].getSymbol(), labels[i][2].getSymbol()))) {
                if(Objects.equals(labels[i][0].getSymbol(),"X"))
                    return -10;
                else if(Objects.equals(labels[i][0].getSymbol(),"O"))
                    return 10;
            }
        }
        if (
                (Objects.equals(labels[0][0].getSymbol(), labels[1][1].getSymbol())) &&
                (Objects.equals(labels[1][1].getSymbol(), labels[2][2].getSymbol()))) {
            if(Objects.equals(labels[0][0].getSymbol(),"X"))
                return -10;
            else if(Objects.equals(labels[0][0].getSymbol(),"O"))
                return 10;
        }
        if (
                (Objects.equals(labels[0][2].getSymbol(), labels[1][1].getSymbol())) &&
                (Objects.equals(labels[1][1].getSymbol(), labels[2][0].getSymbol()))) {
            if(Objects.equals(labels[0][2].getSymbol(),"X"))
                return -10;
            else if(Objects.equals(labels[0][2].getSymbol(),"O"))
                return 10;
        }
        return 0;
    }

    //Resets the game Grid for new Game by removing all existing listeners and sets label icon and symbol to blank_tile and 'O' respectively
    private void resetGameGrid() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                labels[i][j].setSymbol("");
                labels[i][j].getLabel().setIcon(blank_tile);
                for (MouseListener ml : labels[i][j].getLabel().getMouseListeners()) {
                    labels[i][j].getLabel().removeMouseListener(ml);
                }
            }
        }
        clickCount=0;
        initGame();
    }

    //DialogWindow for Winner or Draw declaration
    private void endGame(String result) {
        JOptionPane.showMessageDialog(new JLabel(), result, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        resetGameGrid();
    }
}
