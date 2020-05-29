package app;
import javax.swing.JFrame;

public class App {
    public static void main(String[] args) {
        Loteria l = new Loteria();
        l.setSize(800,600);
        l.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        l.setVisible(true);
    }
}