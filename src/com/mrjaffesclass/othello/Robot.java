package com.mrjaffesclass.othello;
import java.util.ArrayList;
import java.util.Scanner;

public class Robot extends Player {
    public Robot(String str, int color) {
        super(color);
    }
@Override
public Position getNextMove(Board board) {
    Scanner sc = new Scanner(System.in);
    for (int row = 0; row < Constants.SIZE; row++) {
        for (int col = 0; col < Constants.SIZE; col++) {
            Position position = new Position(row, col);
            if (board.isLegalMove(this, position)) {
                System.out.println(position.toString());
            }
        }
    }
    System.out.println("X:");
    int y = sc.nextInt();

    System.out.println("Y:");
    int x = sc.nextInt();

    return new Position(x, y);
}
}