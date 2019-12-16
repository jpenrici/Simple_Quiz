package controller;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import view.Welcome;

public class ControllerWelcome {

    private final static String LOCAL = Util.userDir();
    private final static String PROP = LOCAL + "/resources/resources.properties";

    private String pathImagesGui;
    private Welcome guiWelcome = null;

    public ControllerWelcome() {

        // checar recursos
        checkBaseFiles();

        // GUI Welcome
        guiWelcome = new Welcome();
        guiWelcome.eventGuiWelcome(new ActionsGuiWelcome());
        guiWelcome.setAlwaysOnTop(true);
        guiWelcome.setResizable(false);
        guiWelcome.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                exitWelcome();
            }
        });

        guiWelcome.lblWelcome1.setText("Editar Teste");
        guiWelcome.lblWelcome2.setText("Criar Teste");
        guiWelcome.lblWelcome3.setText("Quiz");

        updateImageButtons(guiWelcome.btnWelcome1, "btnWelcome_1.png");
        updateImageButtons(guiWelcome.btnWelcome2, "btnWelcome_2.png");
        updateImageButtons(guiWelcome.btnWelcome3, "btnWelcome_3.png");

        System.out.println("open welcome ...");
        guiWelcome.setVisible(true);
    }

    private void checkBaseFiles() {
        try {
            pathImagesGui = LOCAL + Util.property(PROP, "PATHIMAGESBTN");
        } catch (FileNotFoundException ex) {
            System.err.println("error loading properties ...");
        }
    }

    private void close() {
        System.out.println("close welcome ...");
        guiWelcome.setVisible(false);
    }

    private void exitWelcome() {
        // saída padrão
        System.out.println("close application ...");
        System.exit(0);
    }

    class ActionsGuiWelcome implements ActionListener {
        // controle dos botões do GUI Welcome

        @Override
        public void actionPerformed(ActionEvent ev) {
            Object source = ev.getSource();
            if (source == guiWelcome.btnWelcome1) { // Editar Teste existente
                close();
                ControllerEdit newController = new ControllerEdit(false);
            }
            if (source == guiWelcome.btnWelcome2) { // Criar Novo Teste
                close();
                ControllerEdit newController = new ControllerEdit(true);
            }
            if (source == guiWelcome.btnWelcome3) { // Abrir Quiz
                close();
                ControllerQuiz newController = new ControllerQuiz();
            }
        }
    }

    private void updateImageButtons(JButton button, String image) {

        try {
            BufferedImage imgOriginal = ImageIO.read(new File(pathImagesGui + image));
            Image img = imgOriginal.getScaledInstance(
                    button.getWidth(),
                    button.getHeight(),
                    Image.SCALE_SMOOTH);
            ImageIcon imgBtn = new ImageIcon(img);
            button.setText("");
            button.setIcon(imgBtn);
        } catch (IOException ex) {
            button.setText("#");
            button.setIcon(null);
        }
    }
}