package com.game.project;

import javax.swing.*;

public class CustomLabel {
    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    private JLabel label;
    private String symbol;
    private int row,col;
    ImageIcon blank_tile=new ImageIcon("D:\\Free time projects\\TicTacToe game\\assets\\blank-tile.png");
    CustomLabel(JLabel label, String symbol, int row, int col){
        this.label=label;
        this.symbol=symbol;
        this.row=row;
        this.col=col;

        label.setIcon(blank_tile);
        label.setBounds(row*100,col*100,blank_tile.getIconWidth(),blank_tile.getIconHeight());
    }
}
