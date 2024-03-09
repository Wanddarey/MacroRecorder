public class App {
    public static void main(String[] args) throws Exception {
        Window window = new Window();
        Thread t1 = new Thread(window);
        t1.start();
    }
}
