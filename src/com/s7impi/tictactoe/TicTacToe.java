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
		drawBoard(board);
		
		while (state == State.GAMEPLAY)
		{
			playerMove(board, currentPlayer);
			drawBoard(board);
			//currentPlayer = switchPlayers();
			switchPlayers();
			state = checkState(board);
		}
	}
	
	public static State checkState(Board board) {
		Value winner = Value.EMPTY;
		
		for (int i = 0; i < 3; ++i)
		{
			// horizontal
			if (board.getField(i, 0) == board.getField(i, 1) && board.getField(i, 1) == board.getField(i, 2))
				winner = board.getField(i, 0).getValue();
			// vertical
			if (board.getField(0, i) == board.getField(1, i) && board.getField(1, i) == board.getField(2, i))
				winner = board.getField(0, i).getValue();
		}
		// diagonal
		if (board.getField(0, 0) == board.getField(1, 1) && board.getField(1, 1) == board.getField(2, 2))
			winner = board.getField(1, 1).getValue();
		if (board.getField(0, 2) == board.getField(1, 1) && board.getField(1, 1) == board.getField(2, 0))
			winner = board.getField(1, 1).getValue();
		
		if (winner == Value.X) 
			return State.WIN_X;
		else if (winner == Value.O)
			return State.WIN_O;
		else
			return State.GAMEPLAY;
	}
	
	public static void playerMove(Board board, Player player) {
		Scanner s = new Scanner(System.in);
		int i, j;
		
		do {
			System.out.print("Wprowadz swoj ruch: ");
			int move = s.nextInt();
			i = (move - 1) / 3;
			j = (move - 1) % 3;
			if (!(board.isEmpty(i, j)))
				System.out.println("Pole zajete!");
		} while (!(board.isEmpty(i, j)));
		
		s.close();
		board.getField(i, j).setValue(player.getValue());
	}
	
	// FIXME: sprawdzac po value i na tej podstawie robic toggle'a, a nie po obiektach ;o
	public static void switchPlayers() {
		if (currentPlayer == player1)
			currentPlayer = player2;
		else
			currentPlayer = player1;
	}
	
	public static void drawBoard(Board board) {
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j) {
				if (board.getField(i, j).getValue() == Value.EMPTY)
					System.out.print(i*3 + j);
				else if (board.getField(i, j).getValue() == Value.X)
					System.out.print("X");
				else
					System.out.print("O");
			System.out.print(" ");
			}
		System.out.println();
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
	private Field[][] fields;
	
	public Board() {
		fields = new Field[3][3];
	}
	
	public void move(int i, int j, Value value) {
		fields[i][j].setValue(value);
	}
	
	public Field getField(int i, int j) {
		return fields[i][j];
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