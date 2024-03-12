import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class NKL implements NativeKeyListener {

    private Boolean keyPressed[] = new Boolean[128];

    public NKL() {
        // Initialize all elements of the keyPressed array to false
        for (int i = 0; i < keyPressed.length; i++) {
            keyPressed[i] = false;
        }
    }

    int pressedKey = -1;

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        keyPressed[e.getKeyCode()] = true;
        pressedKey = e.getKeyCode();
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        keyPressed[e.getKeyCode()] = false;
        pressedKey = -1;
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {

    }

    /**
     * Returns {@code true} if the specified key is pressed
     * 
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
     * Returns the key that is currently pressed. Returns {@code -1} if no key is
     * being pressed
     * 
     * @return {@code Integer}
     */
    public int pressedKey() {
        return pressedKey;
    }

}
