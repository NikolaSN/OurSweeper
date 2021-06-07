/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minetestmain;

/**
 *
 * @author NikNik
 */
public class MineTestMain implements Runnable{
 MineGui ob = new MineGui();
 
   
    public static void main(String[] args) {
      new Thread(new MineTestMain()).start();
    }

    @Override
    public void run() {
     while(true){
         ob.repaint();
         if(ob.reseter == false){
         ob.checkVictoryStatus();
             System.out.println("victory"+ ob.victory+ "Defeat"+ ob.defeat);
         }
     }
    }
    
}
