package br.com.mariojp.figureeditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class App {

    private static Color currentColor = Color.BLACK;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Figure Editor Swing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            DrawingPanel panel = new DrawingPanel();
            panel.setBackground(Color.WHITE);

            JToolBar toolBar = new JToolBar();
            JButton colorButton = new JButton("Cor...");
            colorButton.addActionListener((ActionEvent e) -> {
                Color newColor = JColorChooser.showDialog(frame, "Escolha a cor", currentColor);
                if (newColor != null) {
                    currentColor = newColor;
                    panel.setCurrentColor(newColor);
                }
            });

            toolBar.add(colorButton);

            frame.add(toolBar, BorderLayout.NORTH);
            frame.add(panel, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}
