package com.game.project;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;
import java.util.Random;

public class TicTacToe {
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
        int width = 320;
        int height = 340;
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
                                boolean isBoardFull = true;
                                for (int i = 0; i < 3; i++) {
                                    for (int j = 0; j < 3; j++) {
                                        if (Objects.equals(labels[i][j].getSymbol(), "")) {
                                            isBoardFull = false;
                                            break;
                                        }
                                    }
                                }
                                if (isBoardFull) {
                                    System.out.println("It's a draw!");
                                    endGame("It's a draw!");
                                }
                                else {
                                    int[] arr = makeCPUMove();
                                    //This while loop checks for empty tiles to play until the CPU makes the right move/tiles that are not played yet.
                                    while(!Objects.equals(labels[arr[0]][arr[1]].getSymbol(), "")) {
                                        arr = makeCPUMove();
                                    }
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
        boolean isComplete=false;

        //Row-wise check
        for(int i=0;i<3;i++) {
            if ((!Objects.equals(labels[0][i].getSymbol(), "")) &&
                    (Objects.equals(labels[0][i].getSymbol(), labels[1][i].getSymbol())) &&
                    (Objects.equals(labels[1][i].getSymbol(), labels[2][i].getSymbol()))) {
                isComplete = true;
                break;
            }
        }

        //Column-wise check
        if(!isComplete) {
            for (int i = 0; i < 3; i++) {
                if ((!Objects.equals(labels[i][0].getSymbol(), "")) &&
                        (Objects.equals(labels[i][0].getSymbol(), labels[i][1].getSymbol())) &&
                        (Objects.equals(labels[i][1].getSymbol(), labels[i][2].getSymbol()))) {
                    isComplete = true;
                    break;
                }
            }
        }

        //Diagonal check from top left to bottom right
        if(!isComplete) {
            if ((!Objects.equals(labels[0][0].getSymbol(), "")) &&
                    (Objects.equals(labels[0][0].getSymbol(), labels[1][1].getSymbol())) &&
                    (Objects.equals(labels[1][1].getSymbol(), labels[2][2].getSymbol())))
                isComplete = true;
        }

        //Diagonal check from top right to bottom left
        if(!isComplete) {
            if ((!Objects.equals(labels[0][2].getSymbol(), "")) &&
                    (Objects.equals(labels[0][2].getSymbol(), labels[1][1].getSymbol())) &&
                    (Objects.equals(labels[1][1].getSymbol(), labels[2][0].getSymbol())))
                isComplete = true;
        }
        return isComplete;
    }

    //Random tile coordinates chosen for CPU move
    private int[] makeCPUMove() {
        Random random=new Random();
        int[] randomVal =new int[2];
        randomVal[0]=random.nextInt(3);
        randomVal[1]=random.nextInt(3);
        return randomVal;
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
