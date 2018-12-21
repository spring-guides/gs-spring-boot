package hello;




public class Application {

    public static void main(String[] args) {
        
        open();
        baseSleep();
        open();
        baseSleep();
        open();
        baseSleep();
        open();
        baseSleep();

        baseSleep1();
        baseSleep2();
        }




     public static void open (){

            System.out.print("Open new Window");
            System.out.print("Open new Window");
            System.out.print("Open new Window");
            System.out.print("Open new Window");
     }


      public static void baseSleep() {

        try {

            Thread.sleep(10000);


        } catch (InterruptedException e) {

            System.out.println("main sleep interrupted");
        }


    }



      public static void baseSleep1() {

        try {

            Thread.sleep(10000);
            baseSleep();


        } catch (InterruptedException e) {

            System.out.println("main sleep interrupted");
        }


    }


     public static void baseSleep2() {

        try {

                baseSleep();
            Thread.sleep(10000);


        } catch (InterruptedException e) {

            System.out.println("main sleep interrupted");
        }


    }

}
