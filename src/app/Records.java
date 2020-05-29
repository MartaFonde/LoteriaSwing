package app;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import java.awt.*;

public class Records extends JDialog {
    private JTextArea txaRecords;
    private String home = System.getProperty("user.home");

    public Records() {
        setLayout(new FlowLayout());
        setTitle("Récords");

        txaRecords = new JTextArea();
        txaRecords.setEditable(false);
        lectura();
        add(txaRecords);
    }

    public void lectura() {
        try (Scanner sc = new Scanner(new File(home + "/.records.txt"))) {
            while (sc.hasNext()) {
                txaRecords.setText(txaRecords.getText() + "\n" + sc.nextLine());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Non se pode ler o archivo", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}