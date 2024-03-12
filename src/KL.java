import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Implement JNH(Java Native hook) to capture keyboard events outside of this application

public class KL implements KeyListener {

   private Boolean keyPressed[] = new Boolean[128];

   public KL() {
      // Initialize all elements of the keyPressed array to false
      for (int i = 0; i < keyPressed.length; i++) {
         keyPressed[i] = false;
      }
   }

   int pressedKey = -1;

   @Override
   public void keyTyped(KeyEvent e) {

   }

   @Override
   public void keyPressed(KeyEvent e) {
      keyPressed[e.getKeyCode()] = true;
      pressedKey = e.getKeyCode();
   }

   @Override
   public void keyReleased(KeyEvent e) {
      keyPressed[e.getKeyCode()] = false;
      pressedKey = -1;
   }

   /**
    * Returns {@code true} if the specified key is pressed
    * @param keyCode - {@code Integer}
    */
   public boolean isKeyPressed(int keyCode) {
      return keyPressed[keyCode];
   }

   Integer keyNumb = 0;

   /**
    * Returns {@code true} if any key is pressed
    */
   public boolean isPressed() {
      if (pressedKey == -1) {
         return false;
      }
      return true;
   }

   /**
    * Returns the key that is currently pressed. Returns {@code -1} if no key is being pressed
    * @return {@code Integer}
    */
   public int pressedKey() {
      return pressedKey;
   }

}
