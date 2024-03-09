public class Time {
   public static double timeStarted = System.nanoTime();

   public static double getTime() { return (System.nanoTime() - timeStarted) * 1E-9;}

   public static double getDT(double lastFrameTime) {
      double time = (System.nanoTime() - timeStarted) * 1E-9;
      return time - lastFrameTime;
   }
}
