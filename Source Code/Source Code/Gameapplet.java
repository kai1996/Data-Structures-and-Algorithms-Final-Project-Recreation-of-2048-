
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*; // for Stack

public class Gameappl3 extends Applet implements KeyListener, ActionListener {

	private static final long serialVersionUID = 1L;
	
	Label score;
	protected BoardCanvas c;
    
   
   
   // local color constants
   static final Color black = Color.black;
   static final Color white = Color.white;
   static final Color red = Color.red;
   static final Color green = Color.green;
   static final Color blue = Color.blue;
   static final Color yellow = Color.yellow;
   static final Color dred = new Color(160, 0, 100);
   static final Color dgreen = new Color(0, 120, 90);
   static final Color dblue = Color.blue.darker();
   
   
     public void init () {
    	 
    	 addKeyListener(this);
    	 setFocusable(true);
    	 setLayout(new BorderLayout());
     	 add("North", scoreLabel());
     	 add("South", bottomBtns());
     	 c = new BoardCanvas();
         add("Center",c); 
         c.addKeyListener(this);         
     }   
     
   
     public Panel scoreLabel(){  	    
  	   
  	   Panel top=new Panel();
  	   top.setLayout(new BorderLayout());
  	   top.setBackground(black); 	   
  	 
  	   Label labelwest=new Label("");
  	   Label labelsouth=new Label("");
  	   Label labelnorth=new Label(""); 	   
  	  
  	   score = new Label("0", Label.RIGHT); 
  	   score.setBackground(white);
  	   score.setForeground(blue);
  	   score.setFont(new Font("Times New Roman", Font.PLAIN, 70)); 	   
  	   
  	   top.add("North",labelnorth );
  	   top.add("South",labelsouth );
  	
  	   top.add("West",labelwest );
  	   top.add("Center", score);
  	   
       return top;         
         
     }
     
     public Panel bottomBtns(){
    	 Panel bottom = new Panel(new GridLayout(1,3));
    	 
    	 Label dummyL = new Label("");
 		 Label dummyR = new Label("");
 		
 		 Button reset = new Button("New Game");
 		 Font font = new Font("Serif", Font.BOLD, 30);
 		 reset.setFont(font);
 		 reset.setBackground(new Color(104,204,180));
 		 
 		 bottom.add(dummyL);
 		 bottom.add(reset);
 		 bottom.add(dummyR);
 		 
 		 reset.addActionListener(this); 		 
 		 return bottom; 		 
     }
     
     public void actionPerformed(ActionEvent e){
    	 if (e.getSource() instanceof Button){
    		 c.drawNew();
    	 }
    	    	 
     }
    
     
     public void keyPressed(KeyEvent e) {
    	 
    	 
         int keyCode = e.getKeyCode();   
         //System.out.println(5);
         if (keyCode == KeyEvent.VK_UP) { // up arrow
        	 c.slideU();
        	 show(c.getScore());
         } else if (keyCode == KeyEvent.VK_DOWN) {
        	 c.slideD();
        	 show(c.getScore());
         } else if (keyCode == KeyEvent.VK_LEFT ) {
        	 c.slideL();
        	 show(c.getScore());
        	 
         } else if (keyCode == KeyEvent.VK_RIGHT ) {
        	 c.slideR();
        	 show(c.getScore());
         }
         
     }
     
     public void keyTyped(KeyEvent e) { }
     public void keyReleased(KeyEvent e) { }
     
     protected void show(String getScore) {
	     
		 score.setText(getScore);             
     }
}


class BoardCanvas extends Canvas{
	private static final long serialVersionUID = 1L;
	
	Board myBoard;
	
	public BoardCanvas(){
		myBoard = new Board();
		randomAdd();
		randomAdd();
	}
	
	
	 public boolean isGameOver(){
		   return myBoard.isGameOver();	    
	 		
	} 
	 
	public void paint(Graphics g){
		
		Dimension d = getSize();
		int w = d.width;
		int h = d.height;
		
		if (!isGameOver()){
			drawBoard(g, myBoard, w, h);
		}else{
			drawGameOver(g, w,h);
		}
		
	}
	
	public void drawGameOver(Graphics g, int w, int h){
		g.setColor(Color.green);
		g.fillRect(0, 0, w, h);
		String text = "Game Over";
		
		g.setFont(new Font ("Courier New", 1, 120));
		FontMetrics fm = g.getFontMetrics(g.getFont());
    	int xs = w/2 - fm.stringWidth(text)/2 + 1;        	
    	int ys = h/2 + fm.getAscent()/3 + 1;
    	
		g.setColor(Color.gray);
		g.drawString(text, xs, ys);
	}
	
	
	public void drawBoard(Graphics g, Board board, int w, int h){
		
		for (int row = 0; row < 4; row++)
			for (int col = 0; col < 4; col++){
				Tile tile = board.getTile(row, col);
				drawTile(g, tile, row, col, w, h);
			}
	}
	
	public void drawTile(Graphics g, Tile tile, int r, int c, int w, int h){
		int x = (w/4)*c; // x pos of top left corner of tile
		int y = (h/4)*r; // y pos of top left corner of tile
		int val = tile.getVal();
		
		int colorVal=(int)Math.log(val)*4;//Math.log gives double //can't go practically to 2^64    	
    	 
    	Color tileColor= new Color(100, 204-colorVal,180+colorVal);
	
		String v = Integer.toString(val);// convert to string
		
		Font tileFont = new Font ("Courier New", 1, 64-4*v.length()); 
		
		g.setColor(tileColor);
		g.fillRect(x, y, w/4-10, h/4-10);

		g.setFont(tileFont);
		
		if (val != 0){
			// center text, taken from exam 2 code
			FontMetrics fm = g.getFontMetrics(g.getFont());
        	int xs = w/8 + x - fm.stringWidth(v)/2 + 1;        	
        	int ys = h/8 + y + fm.getAscent()/3 + 1;
        	
        	g.setColor(Color.black);
        	g.drawString(v, xs, ys);
		}
	}
	
	public void randomAdd() {
		Vector<int[]> emptyslot= myBoard.getEmptySlots();
		if(emptyslot.size()==0)	return;		 
		
	   	 Random number=new Random();   	 
	   	 int randomindex = number.nextInt(emptyslot.size());   	 
	   	 int[] currentPos= emptyslot.get(randomindex);
	   	 int tileVal = (number.nextInt(2) +1) * 2;
	   	 myBoard.putTile(currentPos[0],currentPos[1],tileVal,false);
	 
	}
	
	public void slideU(){
		myBoard.slideUp();
		myBoard.clearCombine();
		randomAdd();
		repaint();
	} 
	
	public void drawNew(){
		myBoard = new Board();
		randomAdd();
		randomAdd();
		repaint();
	}
	
	public String getScore(){
		String score = Integer.toString(myBoard.getScore());
		if (myBoard.has2048()) score = "Bravo!!   " + score;
		return score;
		
	}
	
	public void slideD(){
		myBoard.slideDown();
		myBoard.clearCombine();
		randomAdd();
		repaint();
	}
	
	public void slideR(){
		myBoard.slideRight();
		myBoard.clearCombine();
		randomAdd();
		repaint();
	}
	
	public void slideL(){
		myBoard.slideLeft();
		myBoard.clearCombine();
		randomAdd();
		repaint();
	}
}
