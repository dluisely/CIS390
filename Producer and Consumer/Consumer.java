import java.util.*;

public class Consumer implements Runnable
{
	private  Buffer<Date> buffer;

   public Consumer(Buffer<Date> buffer) { 
      this.buffer = buffer;
   }
   
   public void run()
   {
   Date message;
   
     while (true)
      {
         System.out.println("Consumer napping");
	 SleepUtilities.nap(); 
         
         // consume an item from the buffer
         System.out.println("Consumer wants to consume.");
           
         message = buffer.remove();
         System.out.println("Consumer  consumed " + message);
      }
   }
   
}
