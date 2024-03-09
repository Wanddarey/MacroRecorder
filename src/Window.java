import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

public class Window extends JFrame implements Runnable{

   Boolean recording = false;
   KL KeyListener = new KL();

   public Window() {
      FlowLayout flw = new FlowLayout();
      this.setLayout(flw);
      this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
      this.setTitle(Constants.SCREEN_TITLE);
      this.setResizable(false);
      this.setVisible(true);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.addKeyListener(KeyListener);
      JButton button = new JButton("Click Me");
      this.add(button);
      button.setBounds(350, 100, 100, 50);
      this.setVisible(true);

      button.addActionListener(e -> {
         System.out.println("im a button");
      });
   }
   public void update(double dt) {
      //System.out.println(1 / dt + " fps");
      if(KeyListener.isPressed()) {
         System.out.println("pressed key: "  + KeyListener.pressedKey());
      }
   }

   public void run() {
      double lastFrameTime = 0.0;
      while (true) {
         update(Time.getDT(lastFrameTime));

         lastFrameTime = Time.getTime();
         try {
            Thread.sleep(15);
         } catch (Exception e) {
            System.out.println("error");
         }        
      }
   }
   
}