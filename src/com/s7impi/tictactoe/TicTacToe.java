package com.s7impi.tictactoe;

import java.util.Scanner;

enum State {
	GAMEPLAY,
	WIN_X,
	WIN_O,
	DRAW
}

public class TicTacToe {
	
	public static Player player1 = new Player(Value.X);
	public static Player player2 = new Player(Value.O);
	public static Player currentPlayer;

	public static void main(String[] args) {
		currentPlayer = player1;
		
		Board board = new Board();
		State state = State.GAMEPLAY;
		
		while (state == State.GAMEPLAY)
		{
			drawBoard(board);
			switchPlayers();
			playerMove(board, currentPlayer);
			//currentPlayer = switchPlayers();
			state = checkState(board, currentPlayer);
		}
		
		drawBoard(board);
		
		if (state == State.WIN_X)
			System.out.println("Wygral X!");
		else if (state == State.WIN_O)
			System.out.println("Wygral O!");
		else
			System.out.println("Remis!");
	}

	public static State checkState(Board board, Player player) {
		Value winner = Value.EMPTY;
		
		// draw
		if (board.getOccupiedFields() == 9)
			return State.DRAW;
		
		// win
		for (int i = 0; i < 3; ++i) {			
			// horizontal
			if (board.getFieldValue(i, 0) == player.getValue() && board.getFieldValue(i, 1) == player.getValue() && board.getFieldValue(i, 2) == player.getValue())
				winner = board.getFieldValue(i, 0);
			// vertical
			if (board.getFieldValue(0, i) == player.getValue() && board.getFieldValue(1, i) == player.getValue() && board.getFieldValue(2, i) == player.getValue())
				winner = board.getFieldValue(0, i);
		}
		
		// diagonal
		if (board.getFieldValue(0, 0) == player.getValue() && board.getFieldValue(1, 1) == player.getValue() && board.getFieldValue(2, 2) == player.getValue())
			winner = board.getFieldValue(1, 1);
		if (board.getFieldValue(0, 2) == player.getValue() && board.getFieldValue(1, 1) == player.getValue() && board.getFieldValue(2, 0) == player.getValue())
			winner = board.getFieldValue(1, 1);
		
		if (winner == Value.X) 
			return State.WIN_X;
		else if (winner == Value.O)
			return State.WIN_O;
		else
			return State.GAMEPLAY;
	}
	
	public static void playerMove(Board board, Player player) {
		Scanner s = new Scanner(System.in);
		int move, i, j;
		
		// FIXME: Exception handling: ArrayIndexOutOfBoundsException
		do {
			System.out.print("Wprowadz swoj ruch: ");
			move = s.nextInt();
			i = (move - 1) / 3;
			j = (move - 1) % 3;
			/*
			if (!(board.isEmpty(i, j)))
				System.out.println("Pole zajete!");
			if (move < 1 || move > 9)
				System.out.println("Pole niedozwolone!");
			*/
		} while (move < 1 || move > 9 || !(board.isEmpty(i, j)));
		
		//s.close(); FIXME: (?)
		board.move(i, j, player.getValue());
	}
	
	// FIXME: sprawdzac po value i na tej podstawie robic toggle'a, a nie po obiektach ;o
	public static void switchPlayers() {
		if (currentPlayer == player1)
			currentPlayer = player2;
		else
			currentPlayer = player1;
	}
	
	public static void drawBoard(Board board) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				if (board.getField(i, j).getValue() == Value.EMPTY)
					System.out.print(i*3 + j + 1);
				else if (board.getField(i, j).getValue() == Value.X)
					System.out.print("X");
				else
					System.out.print("O");
				
				System.out.print(" ");
			}
			System.out.println("");
		}
	}

}

enum Value {
	EMPTY,
	X,
	O
}

class Player {
	private Value value;
	
	public Player(Value v) {
		value = v;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}
}

class Board {
	private Field[][] fields = new Field[3][3];
	private int occupiedFields;
	
	public Board() {
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j)
				fields[i][j] = new Field();
		occupiedFields = 0;
	}
	
	public void move(int i, int j, Value value) {
		fields[i][j].setValue(value);
		++occupiedFields;
	}
	
	public Field getField(int i, int j) {
		return fields[i][j];
	}
	
	public int getOccupiedFields() {
		return occupiedFields;
	}
	
	public Value getFieldValue(int i, int j) {
		return fields[i][j].getValue();
	}
	
	public boolean isEmpty(int i, int j) {
		return fields[i][j].isEmpty();
	}
}

class Field {
	private Value value;
	
	public Field() {
		value = Value.EMPTY;
	}
	
	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}
	
	public boolean isEmpty() {
		return value == Value.EMPTY;
	}
}