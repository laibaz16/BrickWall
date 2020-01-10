import java.applet.*; 
import java.awt.*; 
import java.awt.event.*;
import java.net.*;

public class brickwall extends Applet implements Runnable, KeyListener
{ 
  int x=200, y=223, radius=6, xsp=0, ysp=0, sizex=400, sizey=300, lives=3, score=0, h=0;
  int recx=170,recy=230,recx_size=60, recy_size=5, rec_xsp=1, rec_ysp=1;
  int  bricky=50, brickx_size=30, bricky_size=15;
  int a=0, random=0,random2=0, counter=0, zero=0, draw=0, win=10;
  int[] hit; 
  int r=255, g=255, b=255;
  int restart=0;
  int key=0;
  Color color=new Color(r,g,b);
  Color color2=new Color(r,g,b);
  Color color3=new Color(r,g,b), color4=new Color(r,g,b), color5=new Color(r,g,b);
  
  
  
  String start="Click on the Screen", spacebar="Press spacebar to start.", left="";
  
  private Image dbImage; 
  private Graphics dbg; 
  
  public void init()
  {
    
  }
  public void start(){     
    addKeyListener(this);
    Thread th=new Thread (this); 
    th.start (); 
    hit=new int[10];
    for(int f=0;f<10;f++)
      hit[f]=0;
  }
  
  public void stop() {} 
  
  public void destroy() {} 
  
  public void keyReleased (KeyEvent e){}   
  public void keyTyped(KeyEvent e){}  
  public void keyPressed (KeyEvent e)
  {
    int code =e.getKeyCode(); 
    
    //  System.out.print(code); 
    
    if (code == KeyEvent.VK_LEFT) 
    { 
      recx=+(recx-3);
      recx-=rec_xsp;
      if( recx<0)
      {
        recx=0;
      }
    } 
    
    else if (code == KeyEvent.VK_RIGHT)   
    {        
      recx=+(recx+3);
      recx+=rec_xsp;
      if( recx>sizex-recx_size)
      {
        recx=330;
      }   
    }
    else if (code == KeyEvent.VK_SPACE)
    {
      if( xsp==0 && ysp==0 && key==0 )
      {
        xsp = -3;
        ysp = -2;
        start="";
        spacebar="";
        left="";
      }
    }
    else if (code == KeyEvent.VK_ENTER)
    {
      key=0;
      hit=new int[10];
      for(int f=0;f<10;f++)
      { hit[f]=0;}
      xsp = -3;
      ysp = -2;
      draw=1;
      lives=3;
      score=0;
      x=200;
      y=223;
      recx=170;
      recy=230;
      start="";
      spacebar="";
      left="";
    }
  }
  
  public void run () { 
    Thread.currentThread().setPriority(Thread.MIN_PRIORITY); 
    while (true) 
    {
      if (x>sizex - radius) 
      {  
        xsp = -3;    
      } 
      else if (x < radius) 
      { 
        xsp = +3;
      } 
      x += xsp;
      if (y>sizey - radius) 
      {  
        ysp = -3;
      } 
      else if (y < radius) 
      { 
        ysp = +3; 
      } 
      y += ysp;      
      
      if(y+radius>=recy && y+radius<=recy+recy_size  && x+radius>=recx && x+radius<=+(recx_size+recx+15))
      {       
        random2=(int)(Math.random ()*3)-3;
        xsp=random2;
        ysp = -2;
      }
      if(y+radius>=300)
      {
        x=200;
        y=223;
        xsp=0;
        ysp=0;
        recx=170;
        lives=+(lives-1);
        left=""+lives+"live(s) left.";
        spacebar=" To continue press space bar";
        if(lives==0)
        {
          left="GAME OVER. ";
          spacebar="To play again press ENTER KEY";
          xsp=0;
          ysp=0;
          rec_xsp=0;
          rec_ysp=0;
          lives=0;
          key=1;
        }
      }
      counter=0;
      if(draw==1)
      {
        hit[counter]=0;
        draw=0;
      }
      for(zero=5; zero<400; zero+=40)
      {
        if(x>=zero && x<=zero+brickx_size && y>=bricky && y<=bricky+bricky_size&& hit[counter]==0 )
        {
          random=(int)(Math.random ()*3)+2;
          xsp = random;
          ysp = +2;
          score=+(score+10);
          hit[counter]=1;
          //  draw=0;
          r=(int)(Math.random ()*125)+130;
          g=(int)(Math.random ()*125)+130;
          b=(int)(Math.random ()*125)+130;
          
          color=new Color(r,g,b);
          color2=new Color(g,b,r);
          color3=new Color(b,r,g);
          color4=new Color(b-40,r+10,g-40);
          color5=new Color(r-30,g-30,b-20);
          win=+(win-1);
          
          if(y<bricky +bricky_size)
          {
            ysp=-2;
            hit[counter]=1;
          }
        }
        counter++;
      }
      if(win==0)
      {
        xsp=0;
        ysp=0;
        key=1;
      }
      repaint();     
      try 
      { 
        Thread.sleep (20); 
      } 
      catch (InterruptedException ex) 
      {
      } 
      Thread.currentThread().setPriority(Thread.MAX_PRIORITY); 
    }
  } 
  public void paint (Graphics g) {
    if (win==0)
    {   start="Well played. No more bricks left";
      spacebar="To play again press ENTER key"; }
    
    counter=0;
    for(a=5; a<400; a+=40)
    {
      if(hit[counter]==0 || draw==1)
      {
        g.setColor(color);
        g.fillRect(a, bricky,brickx_size,bricky_size);
      }
      counter++;
    }
    
    setBackground(Color.BLACK);
    g.setColor(color2);
    g.fillOval (x - radius, y - radius, 2 * radius, 2 * radius);
    g.setColor(color3);
    g.fillRect(recx,recy,recx_size,recy_size);
    g.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
    g.setColor(color4);
    g.drawString("LIVE(S)"+lives, 0, 270);
    g.drawString(""+score,0,290);
    g.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
    g.setColor(color5);
    g.drawString(""+start,120,150);
    g.drawString(""+left,170,150);
    g.drawString(""+spacebar,100,170);
  } 
  
  public void update (Graphics g) 
  { 
    if (dbImage == null) 
    { dbImage = createImage (this.getSize().width, this.getSize().height); 
      dbg = dbImage.getGraphics (); 
    } 
    
    dbg.setColor (getBackground ()); 
    dbg.fillRect (0, 0, this.getSize().width, this.getSize().height); 
    
    dbg.setColor (getForeground()); 
    paint (dbg); 
    
    g.drawImage (dbImage, 0, 0, this); 
  } 
} 
