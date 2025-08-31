package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends JPanel implements ActionListener {
    private JButton startButton;
    private JButton exitButton;
    private GamePanel gamePanel;

    public StartMenu(GamePanel gp) {
        this.gamePanel = gp;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("BOMBERMAN GAME", JLabel.CENTER);
        titleLabel.setFont(new Font("Verdana",Font.BOLD |Font.ITALIC,60));
        titleLabel.setForeground(Color.decode("#90EE90"));
        titleLabel.setBorder(BorderFactory.createLineBorder(Color.decode("#90EE90"), 5));
        add(titleLabel, BorderLayout.NORTH);

        startButton = new JButton("START GAME");
        startButton.addActionListener(this);
        startButton.setFont(new Font("Verdana", Font.PLAIN|Font.BOLD, 30));
        startButton.setForeground(Color.black);
        startButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        startButton.setOpaque(true);
        add(startButton, BorderLayout.CENTER);

        exitButton = new JButton("QUIT");
        exitButton.addActionListener(e -> System.exit(0));
        exitButton.setFont(new Font("Verdana", Font.PLAIN|Font.BOLD, 30));
        exitButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        startButton.setForeground(Color.black);
        startButton.setOpaque(true);
        add(exitButton, BorderLayout.SOUTH);

        setBackground(Color.BLACK);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            gamePanel.removeMenu();
        }
    }

}
