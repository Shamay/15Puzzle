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



/**
 * @author Konstantin Bulenkov
 */
public class Puzzle15 extends JPanel{
    private static final Color BG_COLOR = new Color(0xbbada0);
    private static final String FONT_NAME = "Arial";
    private static final int TILE_SIZE = 64;
    private static final int TILES_MARGIN = 16;
    private static final int TIME = 300;
    private static final int MOVES = 300;

    private Tile[] myTiles;
    private int nullTileIndex;
    private boolean myWin = false;
    private boolean myLose = false;
    private int gameMode; //gameMode 1 is timed. gameMode 2 is not
    private int myMoves = TIME;
    private int myTime = MOVES;

  public Puzzle15(int gameMode) {
      this.gameMode = gameMode;
      setFocusable(true);
      addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        
	  if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
	      resetGame(2);
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
      resetGame(gameMode);
  }

    public Puzzle15(int gameMode, int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, int k, int l, int m , int n, int o, int p, int q){
	this.gameMode = gameMode;
	setFocusable(true);
	addKeyListener(new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
		    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			resetGame(2);
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

	resetGame(gameMode,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q);	
    }

    public void resetGame(int gameMode) {
	if(gameMode == 1){
	    myTime = TIME;
	    ActionListener taskPerformer = new ActionListener() {
		    public void actionPerformed(ActionEvent evt) {
			if((!myWin && !myLose) && myTime > 0)
			    myTime--;
			else if(!myWin)
			    myLose=true;
			repaint();
		    }
		};
	    new Timer(1000, taskPerformer).start();
	}else if(gameMode == 2){
	    myMoves = MOVES;
	}
	myWin = false;
	myLose = false;
	myTiles = new Tile[4 * 4];
	for (int i = 0; i < myTiles.length; i++) {
	    myTiles[i] = new Tile(i);
	}
	nullTileIndex = 0;
	repaint();
    }

    public void resetGame(int gameMode, int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, int k, int l, int m , int n, int o, int p, int q){
	if(gameMode == 1){
	    myTime = TIME;
	    ActionListener taskPerformer = new ActionListener() {
		    public void actionPerformed(ActionEvent evt) {
			if((!myWin && !myLose) && myTime > 0)
			    myTime--;
			else if(!myWin)
			    myLose=true;
			repaint();
		    }
		};
	    new Timer(1000, taskPerformer).start();
	}else if(gameMode == 2){
	    myMoves = MOVES;
	}
	myWin = false;
	myLose = false;
	myTiles = new Tile[4 * 4];
	myTiles[0] = new Tile(a);
	myTiles[1] = new Tile(b);
	myTiles[2] = new Tile(c);
	myTiles[3] = new Tile (d);
	myTiles[4] = new Tile (e);
	myTiles[5] = new Tile (f);
	myTiles[6] = new Tile (g);
	myTiles[7] = new Tile (h);
	myTiles[8] = new Tile (i);
	myTiles[9] = new Tile (j);
	myTiles[10] = new Tile (k);
	myTiles[11] = new Tile (l);
	myTiles[12] = new Tile (m);
	myTiles[13] = new Tile (n);
	myTiles[14] = new Tile (o);
	myTiles[15] = new Tile (p);
	nullTileIndex = q;
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
	}
	myMoves--;
    }

    public void right() {
	if(nullTileIndex % 4 > 0){
	  Tile temp = myTiles[nullTileIndex];
	  myTiles[nullTileIndex] = myTiles[nullTileIndex - 1];
	  myTiles[nullTileIndex - 1] = temp;
	  nullTileIndex--;
      }
      myMoves--;

  }

  public void down() {
      if(nullTileIndex > 3){
	  Tile temp = myTiles[nullTileIndex];
	  myTiles[nullTileIndex] = myTiles[nullTileIndex - 4];
	  myTiles[nullTileIndex - 4] = temp;
	  nullTileIndex -= 4;
      }
      myMoves--;

  }

  public void up() {
      if(nullTileIndex < 12){
	  Tile temp = myTiles[nullTileIndex];
	  myTiles[nullTileIndex] = myTiles[nullTileIndex + 4];
	  myTiles[nullTileIndex + 4] = temp;
	  nullTileIndex+= 4;
      }
      myMoves--;

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
      if (myWin) {
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
      if (myWin || myLose) {
        g.setFont(new Font(FONT_NAME, Font.BOLD, 18));
        g.setColor(new Color(128, 128, 128, 128));
        g.drawString("Press ESC to play again", 80, getHeight() - 35);
      }
    }
    g.setFont(new Font(FONT_NAME, Font.PLAIN, 18));
    if(gameMode == 1){
	 g.drawString("Time Left: " + myTime, 200, 365);
    }else if(gameMode == 2){
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

    public static void main(String[] args) {
	JFrame game = new JFrame();
	game.setTitle("15 Puzzle");
	game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	game.setSize(340, 400);
	game.setResizable(false);

	game.add(new Puzzle15(2,1,2,3,4,5,6,7,8,9,10,11,12,13,14,0,15,14));

	game.setLocationRelativeTo(null);
	game.setVisible(true);
    }
}
