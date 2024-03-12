import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.print.attribute.standard.DateTimeAtCreation;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Window extends JFrame implements Runnable {

   Boolean recording = false;
   NKL KeyListener = new NKL();

   public Window() {
      try {
         GlobalScreen.registerNativeHook();
      }catch(NativeHookException ex) {
         System.err.println("There was a problem registering the native hook.");
         System.err.println(ex.getMessage());
   
         System.exit(1);
      }

      FlowLayout flw = new FlowLayout();
      this.setLayout(flw);
      this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
      this.setTitle(Constants.SCREEN_TITLE);
      this.setResizable(false);
      this.setVisible(true);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      // this.addKeyListener(KeyListener);
      GlobalScreen.addNativeKeyListener(KeyListener);
      JButton button = new JButton("Click Me");
      this.add(button);
      button.setBounds(350, 100, 100, 50);
      this.setVisible(true);

      button.addActionListener(e -> {
         System.out.println("im a button");
         recording = !recording;

         if (!recording) {
            save();
         }

         requestFocus();
      });
   }

   /**
    * In the name mah nigga
    * 
    * @param filename self explanatory
    */
   public void saveToFile(String filename) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter("scripts" + File.separator + filename))) {
         for (Action item : record) {
            writer.write(item.toXML());
            writer.newLine();
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Called uppon switching {@code recording} to {@code false}. Saves the current
    * action in progress and asks user for filename etc...
    */
   public void save() {
      if (cumPressTime != 0) {
         if (pressedKey == -1) {
            record.add(new Action(cumPressTime));
         } else {
            record.add(new Action(pressedKey, cumPressTime));
         }
      }
      pressedKey = -1;
      cumPressTime = 0;
      String userInput = JOptionPane.showInputDialog(null,
            "File name (if null then a name will be given automatically):");
      // TODO: FIX THIS BULLSHIT (userInput != "" isn't working for some reason)
      if (userInput != null && userInput != "") {
         String filenameRegex = "^[a-zA-Z0-9._-]+$";
         Pattern pattern = Pattern.compile(filenameRegex);
         Matcher matcher = pattern.matcher(userInput);
         if (matcher.matches()) {
            saveToFile(userInput + ".xml");
         } else {
            System.out.println("Invalid filename");
         }
      } else if (userInput == "") {
         LocalDateTime now = LocalDateTime.now();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd+HH_mm_ss");
         String formattedDateTime = now.format(formatter);
         saveToFile("script_" + formattedDateTime + ".xml");
      }

      for (Action item : record) {
         System.out.println(item.toXML());
      }
   }

   double cumPressTime = 0;
   Stack<Action> record = new Stack<>();
   int pressedKey = -1;

   public void update(double dt) {
      // System.out.println(1 / dt + " fps");
      if (KeyListener.isPressed()) {
         if (pressedKey == -1) {
            record.add(new Action(cumPressTime));
            cumPressTime = 0;
         }
         cumPressTime += dt;
         pressedKey = KeyListener.pressedKey;
         System.out.println("pressed key: " + KeyListener.pressedKey());
      } else {
         if (pressedKey != -1) {
            record.add(new Action(pressedKey, cumPressTime));
            pressedKey = -1;
            cumPressTime = 0;
         }
         cumPressTime += dt;
         System.out.println("Waiting for " + cumPressTime + "s");
      }
   }

   public void run() {
      double lastFrameTime = 0.0;
      while (true) {

         if (KeyListener.pressedKey == 112) {
            recording = !recording;
            if (!recording)
               save();
         }
         if (recording)
            update(Time.getDT(lastFrameTime));

         lastFrameTime = Time.getTime();
         try {
            Thread.sleep(1);
         } catch (Exception e) {
            System.out.println("error");
         }
      }
   }

}