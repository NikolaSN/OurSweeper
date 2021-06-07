/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minetestmain;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 *
 * @author NikNik
 */
public class MineGui extends JFrame {
    
    int spacing = 5;
    
    int neigh=0;
    
    int mx= -100;
    int my= -100;
    
    public int resetX =280;
    public int resetY =750;
    
    public int resCenterX= resetX+35;
    public int resCenterY= resetY+35;
   
    public boolean reseter = false;
    
    public boolean victory = false;
    
    public boolean defeat = false;
    
    Random rand = new Random();
    
    
    int [][]mines = new int [8][8]; //stoinost 0 ako nqma mina, 1 ako ima mina
    int [][]neighbours = new int [8][8];// stoinosti ot 0 do 8 sprqmo kolko mini ima okolo kutiqta
    boolean [][]revealed = new boolean [8][8];//
    boolean [][] flagged = new boolean [8][8];
    
   
    
    
    public MineGui(){
    this.setTitle("MInesweeper");
    this.setSize(650,900);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
    this.setResizable(false);
    
      for (int i = 0; i < 8; i++){
            for( int j = 0 ; j < 8; j++){
               if(rand.nextInt(100)<20){// random chislo ot 0-99, ako e pod 20 e bomba demek okolo 1/5 sa bombi
                   mines[i][j]= 1;
               }else{ 
                   mines[i][j]=0;               
               }
               
            revealed[i][j] = false;   
            
            }
    }
    
        for (int i = 0; i < 8; i++){
            for( int j = 0 ; j < 8; j++){
                neigh=0;
                   for (int m = 0; m < 8; m++){
                        for( int n = 0 ; n < 8; n++){
                            if(!(m==i && n==j)){
                            if(isN(i,j,m,n)==true){
                     neigh++;
                    }
               }
            }
    }
                   neighbours[i][j]=neigh;
            }
    }
      
      
    
    
    Board board = new Board();
    this.setContentPane(board);
    
    Move move = new Move();
    this.addMouseMotionListener(move);
    
    Click click = new Click();
    this.addMouseListener(click);
    
    
    
    }
    public class Board extends JPanel{
    public void paintComponent(Graphics g){
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, 650, 900);
       // g.setColor(Color.gray);
        for (int i = 0; i < 8; i++){
            for( int j = 0 ; j < 8; j++){
                g.setColor(Color.gray);
                
                if(mines[i][j]==1){
                    g.setColor(Color.yellow);
                }
                
                 if(revealed[i][j]==true){
                    g.setColor(Color.white);
                    if(mines[i][j]==1){
                       g.setColor(Color.red);
                    }
                }
                
                if(mx>=spacing+i*80&& mx < spacing+i*80+80-2*spacing && my >= spacing + j*80+80 && my < spacing+j*80+80+80-2*spacing){
                g.setColor(Color.orange);    
                }
                
                g.fillRect(spacing+i*80, spacing + j*80, 80-2*spacing, 80-2*spacing);
               
                if(revealed[i][j]==true){
                    g.setColor(Color.black);
                    if(mines[i][j] ==0 && neighbours[i][j]!=0){
                        if(neighbours[i][j]==1){
                            g.setColor(Color.blue);
                        }else if(neighbours[i][j]==2){
                            g.setColor(Color.green);
                        }else if(neighbours[i][j]==3){
                            g.setColor(Color.red);
                        }else if(neighbours[i][j]==4){
                            g.setColor(new Color(0,0,128));
                        }else if(neighbours[i][j]==5){
                            g.setColor(new Color(178,34,34));
                        }else if(neighbours[i][j]==6){
                            g.setColor(new Color(72,209,204));
                        }else if(neighbours[i][j]==8){
                            g.setColor(Color.darkGray);
                        }
                        g.setFont(new Font("Tahoma",Font.BOLD, 40));
                        g.drawString(Integer.toString(neighbours[i][j]), i*80+30, j*80+50);
                    }else if(mines[i][j] ==1) {
                      g.fillRect(i*80+10+20,j*80+20, 20, 40);
                      g.fillRect(i*80+20,j*80+10+20, 40, 20);
                      g.fillRect(i*80+5+20,j*80+20+5, 30, 30);
                      
                    }
                }
            }
    }
 
        //resetbuton
        g.setColor(Color.cyan);
        g.fillRect(resetX, resetY, 70, 70);
        
    }
    }
    public class Move implements MouseMotionListener{

            @Override
            public void mouseDragged(MouseEvent me) {
                
            }

            @Override
            public void mouseMoved(MouseEvent me) {
                mx= me.getX();
                my= me.getY();
//                System.out.println("Mouse is moved");
//                mx= me.getX();
//                my= me.getY();
//                System.out.println("X:" + mx+ "Y:"+ my );
            }
        
    }
    
    public class Click implements MouseListener{

            @Override
            public void mouseClicked(MouseEvent me) {
               if(inBoxX()!=-1 && inBoxY()!=-1){
               revealed[inBoxX()][inBoxY()]  = true;
               }
 
                if(inBoxX()!=-1 && inBoxY()!=-1){
                    System.out.println("mouse is in["+inBoxX()+ ","+ inBoxY()+"], Number of mine neigh "+ neighbours[inBoxX()][inBoxY()]);
                }else{
                    System.out.println("mouse is not in box");
                }
                System.out.println("Mouse click");
                
//                if(inReset() ==true){
//                    System.out.println("mouse is in buton");
//                }else{
//                    System.out.println("mouse is not buton");
//                } 
                    
                   if(inReset() ==true){
                       reset();
                   }
            }

            @Override
            public void mousePressed(MouseEvent me) {
               
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
               
            }

            @Override
            public void mouseExited(MouseEvent me) {
               
            }
        
    }
    
    public void checkVictoryStatus(){ 
        for (int i = 0; i < 8; i++){
            for( int j = 0 ; j < 8; j++){
             if(revealed[i][j] == true && mines[i][j]==1){
             defeat = true ;    
             }
    }
   }  
       if(totalBoxesRevealed() >= 64 - totalMines()){
           victory = true;
       }
        
        
    }
    
    public int totalMines(){
        int total = 0; 
        for (int i = 0; i < 8; i++){
            for( int j = 0 ; j < 8; j++){
              if(mines[i][j]==1){
                  total++;              }
            }
    }
        
        return total;
    }
    
    public int totalBoxesRevealed(){
        int total = 0; 
        for (int i = 0; i < 8; i++){
            for( int j = 0 ; j < 8; j++){
              if(revealed[i][j]==true){
                  total++;              }
            }
    }
        
        return total;
    }
    
    public void reset(){
        
        reseter = true;
        
        victory = false;
        defeat= false;
        
              for (int i = 0; i < 8; i++){
            for( int j = 0 ; j < 8; j++){
               if(rand.nextInt(100)<20){// random chislo ot 0-99, ako e pod 20 e bomba demek okolo 1/5 sa bombi
                   mines[i][j]= 1;
               }else{ 
                   mines[i][j]=0;               
               }
               
            revealed[i][j] = false;   
            flagged[i][j] = false;
            }
    }
    
        for (int i = 0; i < 8; i++){
            for( int j = 0 ; j < 8; j++){
                neigh=0;
                   for (int m = 0; m < 8; m++){
                        for( int n = 0 ; n < 8; n++){
                            if(!(m==i && n==j)){
                            if(isN(i,j,m,n)==true){
                     neigh++;
                    }
               }
            }
    }
                   neighbours[i][j]=neigh;
            }
    }
        reseter = false;
    }
    
    public boolean inReset(){
        int dif = (int) Math.sqrt(Math.abs(mx-resCenterX)*Math.abs(mx-resCenterX)+Math.abs(my-resCenterY)*Math.abs(my-resCenterY));
        if(dif < 35){
            return true; 
        }
        return false;
    }
    
    public int inBoxX(){
           for (int i = 0; i < 8; i++){
            for( int j = 0 ; j < 8; j++){
                if(mx>=spacing+i*80&& mx < spacing+i*80+80-2*spacing && my >= spacing + j*80+80 && my < spacing+j*80+80+80-2*spacing){
                 return i;
                }

               
            }
    }
           return-1;
    }
    
    public int inBoxY(){
          for (int i = 0; i < 8; i++){
            for( int j = 0 ; j < 8; j++){
                if(mx>=spacing+i*80&& mx < spacing+i*80+80-2*spacing && my >= spacing + j*80+80 && my < spacing+j*80+80+80-2*spacing){
                 return j;
                }

               
            }
    }
           return-1;
    }
    
    public boolean isN(int X, int Y, int x, int y){// glavnotot X i Y sa koordin na kutiqta koqto proverqvame, malkoto x i y sa koordin na vs kutii i nie proverqvame dali sa susedi
        if(X - x < 2 && X - x> -2 && Y - y < 2 && Y - y> -2 && mines[x][y]==1){//tuk proverqvame dali razlikite na koordinatite na dvete sravnqvani kutii sa v definicionntata oblast(-1;1),zashtoto ako sa s razlika v tova dm znachi che se dopirat (shte go obqsnq po dobre irl)
            return true;
        }
        return false;
    }
    
    
}
