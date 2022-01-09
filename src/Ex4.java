import game.Manager;

/**
 * This class is the main class for Ex4
 */
public class Ex4 {

    public static void main(String[] args){
        Manager m = new Manager();
        m.init();
        m.update();
        m.show();
        m.main_loop();
        System.out.println("Game Over");
    }
}