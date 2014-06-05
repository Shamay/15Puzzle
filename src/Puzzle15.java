/*
 * Copyright 1998-2014 Konstantin Bulenkov http://bulenkov.com/about
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//package com.bulenkov.game2048;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;



/**
 * @author Konstantin Bulenkov
 */
public class Puzzle15 extends JPanel{
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private static final Color BG_COLOR = new Color(0xbbada0);
    private static final String FONT_NAME = "Arial";
    private static final int TILE_SIZE = 64;
    private static final int TILES_MARGIN = 16;
    private static final int TIME = 300;
    private static final int MOVES = 300;
    private static int gameMode =  1; // (int) (Math.random() * 2) + 1; 
    		//gameMode 1 is timed. gameMode 2 is not
    private static int type = (int) (Math.random() * 2) + 1; 
    		// 1 is solve/un/solve and 2 is un/solve/un
    private static int trial = -2;
    
    private Tile[] myTiles;
    private int nullTileIndex;
    private boolean myWin = false;
    private boolean myLose = false;
    private int myMoves = MOVES;
    private int myTime = TIME;

    public Puzzle15(ArrayList<Integer> par){
	setFocusable(true);
	addKeyListener(new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
		    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
		    resetGame(nextTrial());
		    }
		    
		    if (!myWin && !myLose) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
			    left();
			    break;
			case KeyEvent.VK_RIGHT:
			    right();
			    break;
			case KeyEvent.VK_DOWN:
			    down();
			    break;
			case KeyEvent.VK_UP:
			    up();
			    break;
			}
		    }

		    if (!myWin && (myTime < 1 || myMoves < 1)) {
			myLose = true;
		    }
		    
		    if(checkWin()){
			myWin = true;
		    }
		    
		    repaint();
		}
	    });
	
		// Timer
		if(gameMode == 1){
			ActionListener taskPerformer = new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if((!myWin && !myLose) && myTime > 0)
							myTime--;
					else if(!myWin && myTime < 1)
							myLose=true;
					repaint();
				}
			};
			new Timer(1000, taskPerformer).start();
		}
	resetGame(par);	
    }

    public void resetGame(ArrayList<Integer> par){
	if(gameMode == 1){
	    myTime = TIME;
	}else if(gameMode == 2){
	    myMoves = MOVES;
	}
	myWin = false;
	myLose = false;
	myTiles = new Tile[4 * 4];
	myTiles[0] = new Tile(par.get(0));
	myTiles[1] = new Tile(par.get(1));
	myTiles[2] = new Tile(par.get(2));
	myTiles[3] = new Tile (par.get(3));
	myTiles[4] = new Tile (par.get(4));
	myTiles[5] = new Tile (par.get(5));
	myTiles[6] = new Tile (par.get(6));
	myTiles[7] = new Tile (par.get(7));
	myTiles[8] = new Tile (par.get(8));
	myTiles[9] = new Tile (par.get(9));
	myTiles[10] = new Tile (par.get(10));
	myTiles[11] = new Tile (par.get(11));
	myTiles[12] = new Tile (par.get(12));
	myTiles[13] = new Tile (par.get(13));
	myTiles[14] = new Tile (par.get(14));
	myTiles[15] = new Tile (par.get(15));
	nullTileIndex = par.get(16);
	repaint();
    }

    public boolean checkWin(){
	for(int x = 0; x < myTiles.length - 1; x++){
	    if(myTiles[x].getValue() != x + 1)
		return false;
	}
	return myTiles[myTiles.length - 1].getValue() == 0;
    }

    public void left() {
	if(nullTileIndex % 4 < 3){
	    Tile temp = myTiles[nullTileIndex];
	    myTiles[nullTileIndex] = myTiles[nullTileIndex + 1];
	    myTiles[nullTileIndex + 1] = temp;
	    nullTileIndex++;
	    if(gameMode == 2)
		myMoves--;
	}
	
    }

    public void right() {
	if(nullTileIndex % 4 > 0){
	  Tile temp = myTiles[nullTileIndex];
	  myTiles[nullTileIndex] = myTiles[nullTileIndex - 1];
	  myTiles[nullTileIndex - 1] = temp;
	  nullTileIndex--;
	  if(gameMode == 2)
	      myMoves--;
	}
      

  }

  public void down() {
      if(nullTileIndex > 3){
	  Tile temp = myTiles[nullTileIndex];
	  myTiles[nullTileIndex] = myTiles[nullTileIndex - 4];
	  myTiles[nullTileIndex - 4] = temp;
	  nullTileIndex -= 4;
	  if(gameMode == 2)
	      myMoves--;
      }
      

  }

  public void up() {
      if(nullTileIndex < 12){
	  Tile temp = myTiles[nullTileIndex];
	  myTiles[nullTileIndex] = myTiles[nullTileIndex + 4];
	  myTiles[nullTileIndex + 4] = temp;
	  nullTileIndex+= 4;
	  if(gameMode == 2)
	      myMoves--;
      }

  }


  @Override
  public void paint(Graphics g) {
    super.paint(g);
    g.setColor(BG_COLOR);
    g.fillRect(0, 0, this.getSize().width, this.getSize().height);
    for (int y = 0; y < 4; y++) {
      for (int x = 0; x < 4; x++) {
        drawTile(g, myTiles[x + y * 4], x, y);
      }
    }
  }

  private void drawTile(Graphics g2, Tile tile, int x, int y) {
    Graphics2D g = ((Graphics2D) g2);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
    int value = tile.value;
    int xOffset = offsetCoors(x);
    int yOffset = offsetCoors(y);
    g.setColor(tile.getBackground());
    g.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE, 14, 14);
    g.setColor(tile.getForeground());
    final int size = value < 100 ? 36 : value < 1000 ? 32 : 24;
    final Font font = new Font(FONT_NAME, Font.BOLD, size);
    g.setFont(font);

    String s = String.valueOf(value);
    final FontMetrics fm = getFontMetrics(font);

    final int w = fm.stringWidth(s);
    final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

    if (value != 0)
      g.drawString(s, xOffset + (TILE_SIZE - w) / 2, yOffset + TILE_SIZE - (TILE_SIZE - h) / 2 - 2);

    if (myWin || myLose) {
      g.setColor(new Color(255, 255, 255, 30));
      g.fillRect(0, 0, getWidth(), getHeight());
      g.setColor(new Color(78, 139, 202));
      g.setFont(new Font(FONT_NAME, Font.BOLD, 32));
      if (myWin && trial == 2) {
	  g.drawString("End of test.", 68, 150);
      }else if(myWin){
	   g.drawString("Task Success!", 68, 150);
      }
      if (myLose) {
	  if(gameMode == 1){
	      g.drawString("Time Ran Out!", 50, 130);
	  }else if(gameMode == 2){
	      g.drawString("Out of moves!", 50, 130);
	  }
        g.drawString("Task Failure!", 64, 200);
      }
      if ((myWin || myLose) && trial == 2) {
        g.setFont(new Font(FONT_NAME, Font.BOLD, 18));
        g.setColor(new Color(128, 128, 128, 128));
        g.drawString("Press exit the program.", 80, getHeight() - 35);
      }else if(myWin || myLose){
	  g.setFont(new Font(FONT_NAME, Font.BOLD, 18));
	  g.setColor(new Color(128, 128, 128, 128));
	  g.drawString("Press ESC to proceed", 80, getHeight() - 35);
      }
    }
    g.setFont(new Font(FONT_NAME, Font.PLAIN, 18));
    if(gameMode == 1){
    	g.drawString("Group: " + type, 10, 365);
    	g.drawString("Trial: " + (trial % 3 + 1), 120, 365);		
	 g.drawString("Time Left: " + myTime, 200, 365);
    }else if(gameMode == 2){
    	g.drawString("Group: " + type, 10, 365);
    	g.drawString("Trial: " + (trial % 3 + 1), 120, 365);
	g.drawString("Moves Left: " + myMoves, 200, 365);
    }
   

  }

  private static int offsetCoors(int arg) {
    return arg * (TILES_MARGIN + TILE_SIZE) + TILES_MARGIN;
  }


    static class Tile {
	int value;

	public Tile() {
	    this(0);
	}

	public Tile(int num) {
	    value = num;
	}

	public int getValue(){
	    return value;
	}

	public boolean isEmpty() {
	    return value == 0;
	}

	public Color getForeground() {
	    return value < 16 ? new Color(0x776e65) :  new Color(0xf9f6f2);
	}

	public Color getBackground() {
	    return new Color(0xcdc1b4);
	}
    }

    public static ArrayList<Integer> randomInts(){
	ArrayList<Integer> arr = new ArrayList<Integer>();
	for(int x = 0; x < 16; x++){
	    arr.add(x);
	}
	int nullBlock = 0;
	ArrayList<Integer> par = new ArrayList<Integer>();
	for(int x = 0; x < 16; x++){
		int paramIndex = (int)(Math.random() * arr.size());
	    int param = arr.get(paramIndex);
	    if(param == 0){
	    	nullBlock = x;
	    }
	    par.add(param);
	    arr.remove(paramIndex);
	}	
	par.add(nullBlock);
	return par;
    }	
    
    public static ArrayList<Integer> nextTrial(){
    	trial++;
    	ArrayList<Integer> arr = new ArrayList<Integer>();
    	if(type == 1){
	    if (trial == -1){
		arr.add(3);
		arr.add(5);
		arr.add(4);
		arr.add(8);
		arr.add(1);
		arr.add(2);
		arr.add(12);
		arr.add(6);
		arr.add(9);
		arr.add(11);
		arr.add(0);
		arr.add(7);
		arr.add(13);
		arr.add(10);
		arr.add(15);
		arr.add(14);
		arr.add(10);
	   
	    }else if(trial % 3 == 0){
		arr.add(2);
		arr.add(9);
		arr.add(4);
		arr.add(14);
		arr.add(3);
		arr.add(5);
		arr.add(7);
		arr.add(8);
		arr.add(12);
		arr.add(13);
		arr.add(15);
		arr.add(11);
		arr.add(10);
		arr.add(0);
		arr.add(6);
		arr.add(1);
		arr.add(13);
	    }else if(trial % 3 == 1){
		arr.add(7);
		arr.add(5);
		arr.add(2);
		arr.add(14);
		arr.add(8);
		arr.add(9);
		arr.add(11);
		arr.add(13);
		arr.add(0);
		arr.add(4);
		arr.add(15);
		arr.add(3);
		arr.add(6);
		arr.add(1);
		arr.add(12);
		arr.add(10);
		arr.add(8);
	    }else if(trial % 3 == 2){
		arr.add(5);
		arr.add(12);
		arr.add(13);
		arr.add(3);
		arr.add(6);
		arr.add(7);
		arr.add(10);
		arr.add(15);
		arr.add(14);
		arr.add(0);
		arr.add(9);
		arr.add(8);
		arr.add(11);
		arr.add(2);
		arr.add(1);
		arr.add(4);
		arr.add(9);
	    }
    	}else if(type == 2){
	    if(trial == -1){
        	arr.add(3);
		arr.add(5);
		arr.add(4);
		arr.add(8);
		arr.add(1);
		arr.add(2);
		arr.add(12);
		arr.add(6);
		arr.add(9);
		arr.add(11);
		arr.add(0);
		arr.add(7);
		arr.add(13);
		arr.add(10);
		arr.add(15);
		arr.add(14);
		arr.add(10);
	    }else if(trial % 3 == 0){
		arr.add(7);
		arr.add(5);
		arr.add(2);
		arr.add(14);
		arr.add(8);
		arr.add(9);
		arr.add(11);
		arr.add(13);
		arr.add(0);
		arr.add(4);
		arr.add(15);
		arr.add(3);
		arr.add(6);
		arr.add(1);
		arr.add(12);
		arr.add(10);
		arr.add(8);
	    }else if(trial % 3 == 1){
		arr.add(2);
		arr.add(9);
		arr.add(4);
		arr.add(14);
		arr.add(3);
		arr.add(5);
		arr.add(7);
		arr.add(8);
		arr.add(12);
		arr.add(13);
		arr.add(15);
		arr.add(11);
		arr.add(10);
		arr.add(0);
		arr.add(6);
		arr.add(1);
		arr.add(13);
	    }else if(trial % 3 == 2){
		arr.add(9);
		arr.add(0);
		arr.add(13);
		arr.add(8);
		arr.add(1);
		arr.add(14);
		arr.add(2);
		arr.add(5);
		arr.add(10);
		arr.add(15);
		arr.add(11);
		arr.add(3);
		arr.add(12);
		arr.add(7);
		arr.add(4);
		arr.add(6);
		arr.add(1);
	    }
    	}
    	return arr;
    }
	

    public static void main(String[] args) {
	JFrame game = new JFrame();
	game.setTitle("15 Puzzle");
	game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	game.setSize(340, 400);
	game.setResizable(false);

	game.add(new Puzzle15(nextTrial()));
	game.setLocationRelativeTo(null);
	game.setVisible(true);
    }
}
