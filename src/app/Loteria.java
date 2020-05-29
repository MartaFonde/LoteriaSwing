package app;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;

public class Loteria extends JFrame implements ItemListener, ActionListener {
    private JCheckBox[] chkConj;
    private JButton btnJugar;
    private ArrayList<Integer> seleccion;
    private int cont;
    private int num;
    private int[] aleatorios;
    private JLabel lblNumerosAleat;
    private String numeros;
    private Timer tempTitulo;
    private JMenuBar mnuPrincipal;
    private JMenu mnuOpciones;
    private JMenuItem mnuGuardar;
    private JMenuItem mnuRecords;
    ArrayList<String> aciertos;
    private JButton btnReset;


    public Loteria() {
        setLayout(null);

        int x = 130;
        int y = 50;
        chkConj = new JCheckBox[49];
        for (int i = 0; i < chkConj.length; i++) {
            JCheckBox c = new JCheckBox((i + 1) + "");
            c.setSize(c.getPreferredSize());
            c.setLocation(x, y);
            chkConj[i] = c;
            c.setActionCommand(c.getText());
            c.addItemListener(this);
            add(c);
            x += 50;
            if ((i + 1) % 10 == 0) {
                y += 50;
                x = 130;
            }
        }
        seleccion = new ArrayList<Integer>();
        cont = 0;

        btnJugar = new JButton("Xogar");
        btnJugar.setSize(btnJugar.getPreferredSize());
        btnJugar.setLocation(280, 300);
        btnJugar.setEnabled(false);
        btnJugar.addActionListener(this);
        add(btnJugar);

        btnReset = new JButton("Reset");
        btnReset.setSize(btnReset.getPreferredSize());
        btnReset.setLocation(380,300);
        btnReset.addActionListener(this);
        add(btnReset);

        numeros = "Os números premiados son: \n";
        lblNumerosAleat = new JLabel();
        lblNumerosAleat.setSize(lblNumerosAleat.getPreferredSize());
        lblNumerosAleat.setLocation(280, 350);
        add(lblNumerosAleat);

        aleatorios = new int[6];

        String titulo = "Lotería";
        
        tempTitulo = new Timer(300, new ActionListener(){
            int letra = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if(letra <=  titulo.length()-1){
                    setTitle(getTitle()+titulo.charAt(letra)+"");
                    letra++;
                } else {
                    setTitle("");
                    letra = 0;
                }

            }
        });
        tempTitulo.start();

        mnuGuardar = new JMenuItem("Guardar última partida");
        mnuGuardar.setMnemonic('G');
        mnuGuardar.addActionListener(this);

        mnuRecords = new JMenuItem("Ver records");
        mnuRecords.setMnemonic('R');
        mnuRecords.addActionListener(this);

        mnuOpciones = new JMenu("Opciones");
        mnuOpciones.setMnemonic('O');
        mnuOpciones.addActionListener(this);
        mnuOpciones.add(mnuGuardar);
        mnuOpciones.add(mnuRecords);

        mnuPrincipal = new JMenuBar();
        mnuPrincipal.add(mnuOpciones);
        this.setJMenuBar(mnuPrincipal);

        aciertos = new ArrayList<String>();

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            ((JCheckBox) e.getItem()).setSelected(true);
            seleccion.add(Integer.parseInt(((JCheckBox) e.getItem()).getText()));
            cont++;
            if (cont > 6) {
                ((JCheckBox) e.getItem()).setSelected(false);
            }
        }
        if (e.getStateChange() == ItemEvent.DESELECTED) {
            cont--;
            for (int i = 0; i < seleccion.size(); i++) {
                if (seleccion.get(i) == Integer.parseInt(((JCheckBox) e.getItem()).getText())) {
                    seleccion.remove(i);
                }
            }
        }
        if (cont == 6) {
            btnJugar.setEnabled(true);
        } else {
            btnJugar.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnJugar) {
            numAleatorios();
            comprobacion();
        }

        if(e.getSource() == mnuGuardar){
            Guardar s = new Guardar(this);
            s.pack();
            s.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            s.setVisible(true);
        }

        if(e.getSource() == mnuRecords){
            Records r = new Records();
            r.setSize(500,500);
            r.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            r.setVisible(true);
        }

        if(e.getSource() == btnReset){
            reseteo();
        }
    }

    public void comprobacion(){
        aciertos.clear();
        for(int i = 0; i < chkConj.length; i++){
            chkConj[i].setForeground(Color.BLACK);
        }
        for (int i = 0; i < aleatorios.length; i++) {
            for (int j = 0; j < seleccion.size(); j++) {
                if (aleatorios[i] == seleccion.get(j)) {
                    for (int k = 0; k < chkConj.length; k++) {
                        if (Integer.parseInt(chkConj[k].getText()) == seleccion.get(j)) {
                            chkConj[k].setForeground(Color.green);
                            aciertos.add(chkConj[k].getText());
                        }
                    }
                } else {
                    for (int k = 0; k < chkConj.length; k++) {
                        if (Integer.parseInt(chkConj[k].getText()) == seleccion.get(j)) {
                            if (chkConj[k].getForeground() != Color.green) {
                                chkConj[k].setForeground(Color.red);
                            } 
                        } 
                    }
                }
            }
        }
        
    }

    public void numAleatorios() {
        lblNumerosAleat.setText("<html><body>" + numeros + "<br>");
        for (int i = 0; i < 6; i++) {
            num = (int) (Math.random() * 49 + 1);
            for (int z = 0; z < aleatorios.length; z++) {
                while (num == aleatorios[z]) {
                    num = (int) (Math.random() * 49 + 1);
                }
            }
            aleatorios[i] = num;
            lblNumerosAleat.setText(lblNumerosAleat.getText() + num + "<br>");
        }
        lblNumerosAleat.setText(lblNumerosAleat.getText() + "</body></html>");
        lblNumerosAleat.setSize(lblNumerosAleat.getPreferredSize());
        lblNumerosAleat.setVisible(true);
    }

    public void reseteo(){
        for(int i = 0; i < chkConj.length; i++){
            chkConj[i].setSelected(false);
            chkConj[i].setForeground(Color.BLACK);
        }
        lblNumerosAleat.setText("");
        seleccion.clear();
        aciertos.clear();
    }
}