//import javax.faces.bean.ManagedBean;

//@ManagedBean(name = "helloWorld")
public class HelloWorld {
	
	int i = 0;
   public HelloWorld() {
      System.out.println("HelloWorld started!");
   }
   
   
   public String eatMeal(){
	   System.out.println("Works");
	   return "Eating";
   }
   public String getMessage() {
      return "Hello World!";
   }
}