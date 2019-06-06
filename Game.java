import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.*;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.*;
public class Chaudhari{
public static void main(String[] args) {
		JFrame f=new JFrame();
	    Playgame playgame=new Playgame();
		f.setBounds(10,10,700,600);
		f.setTitle("Breakout Ball");
		f.setResizable(false);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(playgame);
}
}
class Playgame extends JPanel implements KeyListener,ActionListener{
   private boolean play=false;
  private   int score =0;
   private  int  totalbricks=21;
  private  Timer timer;
    private int delay =8;   // speed of ball
   private  int playerX=310;
   private  int ballposX=120;
   private  int ballposY= 350;
    private int ballXdir=-1;
   private  int ballYdir=-2;
   private MapGenerator map;

            public Playgame()
    {
           map=new MapGenerator(3,7);

        addKeyListener(this);
         setFocusable(true);
       setFocusTraversalKeysEnabled(false);
       timer = new Timer(delay,this);
        timer.start();

    }

    public void paint (Graphics g){
     // background
     g.setColor(Color.black);
     g.fillRect(1,1,692,592);
     // brick draw
      map.draw((Graphics2D)g);       // passing object of class MapGenerator with converting it into 2d graphics.
      // borders

      g.setColor(Color.yellow);
      g.fillRect(0,0,3,592);
      g.fillRect(0,0,692,3);
      g.fillRect(691,0,3,592);
      // score
      g.setColor(Color.white);
      g.setFont(new Font("serif",Font.BOLD,25));
      g.drawString(""+score,590,30);

       //paddle
       g.setColor(Color.green);
       g.fillRect(playerX,550,100,8);

       //ball
        g.setColor(Color.yellow);
       g.fillOval(ballposX,ballposY,20,20);
       if(totalbricks<=0){
 play=false;ballXdir=0;
            ballYdir=0;
            g.setColor(Color.red);
             g.setFont(new Font("serif",Font.BOLD,30));
           g.drawString("You Won",190,300);
             g.setFont(new Font("serif",Font.BOLD,20));
           g.drawString("press enter to restart",230,350);

       }
       if(ballposY>570)   // when ball goes down.
      {     
          play=false;ballXdir=0;
            ballYdir=0;
            g.setColor(Color.red);
             g.setFont(new Font("serif",Font.BOLD,30));
           g.drawString("Game over, Score:",190,300);
             g.setFont(new Font("serif",Font.BOLD,30));
           g.drawString("press enter to restart",230,350);
       }              g.dispose();
    }
   
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))){
                ballYdir=-ballYdir;
                }
              A: for(int i=0;i<map.map.length;i++){  // label for break statement

                   for(int j=0;j<map.map[0].length;j++){
                       if(map.map[i][j]>0){
                        int brickX=j*map.brickWidth+80;
                        int brickY=i*map.brickHeight+50;
                        int brickWidth=map.brickWidth;
                        int brickHeight=map.brickHeight;
                        Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        Rectangle ballRect=new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickRect=rect;

                           if(ballRect.intersects(brickRect)){
                               map.setBrickValue(0,i,j);
                               totalbricks--;
                               score+=5;
                               if(ballposX+19<=brickRect.x||ballposX+1>=brickRect.x+brickRect.width){
                                   ballXdir=-ballXdir;

                               }else{
                                   ballYdir=-ballYdir;

                               }
                               break A;
                           }
                       }
                       }
                   }
               
            ballposX+=ballXdir;
			ballposY+=ballYdir;
			if(ballposX<0){
				ballXdir=-ballXdir;
			}
			if(ballposY<0){
				ballYdir=-ballYdir;
				}
			if(ballposX>670){
				ballXdir=-ballXdir;
			}
		}
		
	repaint();// re call paint function and re draw all thing again. // because of movement od paddle.


    }   
    public void keyTyped(KeyEvent e) {}


	public void keyReleased(KeyEvent e) {
		
		
	}

	public void keyPressed(KeyEvent e) {
    // detect arrowkey
    if(e.getKeyCode()== KeyEvent.VK_RIGHT){
        if(playerX >600){    // border condition
            playerX=600;
        }else {
             moveRight();
        }
    }
    if(e.getKeyCode()== KeyEvent.VK_LEFT){
        if(playerX <10){    // border condition
            playerX=10;
        }else {
           moveLeft();
        }
    }
    if(e.getKeyCode()== KeyEvent.VK_ENTER){
         if(!play){          
             play=true;           // gameover, enter for restart.
              Random r= new Random();    // random function will give random ball pos whenever game will over .
              ballposX=r.nextInt(500);
              ballposY=r.nextInt(500);
             ballXdir=-1;
             ballYdir=-2;
             playerX=r.nextInt(310);
             score=0;
             totalbricks=21;
             map=new MapGenerator(3,7);
             repaint();

         }


    }
    }


    public void moveRight(){
        play=true;
        playerX+=20;

    }
    public void moveLeft(){
        play=true;
        playerX-=20;
        
    }
}

class MapGenerator
{
public int map[][];
public int brickWidth;
public int brickHeight;
public MapGenerator(int row,int col)
{
	map=new int [row][col];
	for(int i=0;i<map.length;i++){
		for(int j=0;j<map[0].length;j++){
			map[i][j]=1; // dects tht brick is not intersected with ball.
			
	}
}
	brickWidth=540/col;
	brickHeight=150/row;

}
public void draw(Graphics2D g){
	for(int i=0;i<map.length;i++){
		for(int j=0;j<map[0].length;j++){
		if(map[i][j]>0){
			g.setColor(Color.white);
			g.fillRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
			// blackborder between bricks
			g.setStroke(new BasicStroke(3));
			g.setColor(Color.BLACK);
			g.drawRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
			
		}
		}
	}
	
}

public void setBrickValue(int value,int row,int col){
	map[row][col]=value;
	
}
}
