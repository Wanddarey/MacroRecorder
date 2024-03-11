public class Action {
   public String type;
   public Integer Key;
   public double cumTime;

   /**
    * Add key press
    * @param TKey
    * @param TcumTime
    */
   public Action(int TKey, double TcumPressTime) {
      type = "KeyPress";
      Key = TKey;
      cumTime = TcumPressTime;
   }

   /**
    * Add wait period
    * @param TcumPressTime
    */
   public Action(double TcumTime) {

      type = "Wait";
      cumTime = TcumTime;

   }

   /**
    * Converts the information of the {@code Action} into XML
    * @return XML
    */
   public String toXML() {
      if (type == "KeyPress") {
         return "<KeyPress><Key>" + Key + "</Key><PressTime>" + cumTime + "</PressTime></KeyPress>";
      } else if (type == "Wait") {
         return "<Wait><WaitTime>" + cumTime + "</WaitTime></Wait>";
      }
      return "error";
   }
}
