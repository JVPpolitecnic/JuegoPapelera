package Form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import static java.awt.event.KeyEvent.*;

public class Main {
    private JPanel panelMain;

    private JLabel labelTrash = new JLabel();
    private JLabel labelPaper = new JLabel();

    private JLabel labelHeart1 = new JLabel();
    private JLabel labelHeart2 = new JLabel();

    private JLabel labelHeart3 = new JLabel();
    private JButton buttonPausa;

    private JLabel points = new JLabel();

    int point_counter;
    private int seconds = 0;

    private int ballGone = 0;

    private boolean dead;
    private int y;

    public Main() {

        panelMain.setPreferredSize(new Dimension(800, 600));
        panelMain.setSize(new Dimension(800, 600));
        panelMain.setLayout(null);


        JPanel panelTitle = new JPanel();
        panelTitle.setLocation(0, 0);
        panelTitle.setSize(panelMain.getWidth(), 50);
        panelTitle.setBackground(Color.LIGHT_GRAY);

        panelMain.add(panelTitle);

        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(null);
        panelCenter.setLocation(0, panelTitle.getHeight());
        panelCenter.setSize(panelMain.getWidth(), panelMain.getHeight() - panelTitle.getHeight());

        panelMain.add(panelCenter);
        JLabel labelTimer = new JLabel();
        labelTimer.setText("0 segundos");
        panelTitle.add(labelTimer);
        panelMain.setFocusable(true);

        buttonPausa = new JButton("Pausa");
        buttonPausa.setLayout(null);

        buttonPausa.setBackground(Color.BLACK);
        buttonPausa.setForeground(Color.WHITE);
        buttonPausa.setLocation(panelTitle.getWidth() / 2 - buttonPausa.getWidth(), panelTitle.getHeight() / 2 - buttonPausa.getHeight() / 2);
        panelTitle.add(buttonPausa);

        panelMain.addKeyListener(new PanelMainListener());
        labelHeart1.setSize(30, 30);
        ImageIcon image_empty_heart = new ImageIcon("src/images/heart_empty.png");
        Icon icon_empty_heart = new ImageIcon(image_empty_heart.getImage().getScaledInstance(labelHeart1.getWidth(), labelHeart1.getHeight(), Image.SCALE_DEFAULT));
        ImageIcon image_full_heart = new ImageIcon("src/images/heart_full.png");
        Icon icon_full_heart = new ImageIcon(image_full_heart.getImage().getScaledInstance(labelHeart1.getWidth(), labelHeart1.getHeight(), Image.SCALE_DEFAULT));
        labelHeart1.setIcon(icon_full_heart);
        labelHeart1.setLocation(panelTitle.getWidth() / 2 - labelHeart1.getWidth() / 2 + panelTitle.getWidth() / 3, panelTitle.getHeight() / 2 - labelHeart1.getHeight() / 2);
        labelHeart2.setSize(30, 30);
        labelHeart2.setIcon(icon_full_heart);
        labelHeart2.setLocation(panelTitle.getWidth() / 2 - labelHeart1.getWidth() / 2 + panelTitle.getWidth() / 3 + 40, panelTitle.getHeight() / 2 - labelHeart1.getHeight() / 2);
        labelHeart3.setSize(30, 30);
        labelHeart3.setIcon(icon_full_heart);
        labelHeart3.setLocation(panelTitle.getWidth() / 2 - labelHeart1.getWidth() / 2 + panelTitle.getWidth() / 3 + 80, panelTitle.getHeight() / 2 - labelHeart1.getHeight() / 2);
        ;
        panelTitle.add(labelHeart3);
        panelTitle.add(labelHeart2);
        panelTitle.add(labelHeart1);
        labelTrash.setSize(80, 80);
        ImageIcon imageLogo = new ImageIcon("src/images/trash.png");
        Icon icon = new ImageIcon(imageLogo.getImage().getScaledInstance(labelTrash.getWidth(), labelTrash.getHeight(), Image.SCALE_DEFAULT));
        labelTrash.setIcon(icon);
        labelTrash.setLocation(panelCenter.getWidth() / 2 - labelTrash.getWidth() / 2, panelCenter.getHeight() - labelTrash.getHeight());
        panelCenter.add(labelTrash);
        System.out.println(MouseInfo.getPointerInfo());

        Timer timer = new Timer(1000, new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;

                labelTimer.setText(seconds + " segundos");


            }
        });


        timer.start();
        dead = false;


        Timer timerPaper = new Timer(10, new ActionListener() {
            int y = 0;


            @Override
            public void actionPerformed(ActionEvent e) {

                dead = false;

                if (y == 0) {
                    generatePaperBall(panelCenter);
                }
                y += 1;

                labelPaper.setLocation(labelPaper.getX(), y);
                if (ballGone == 1) {
                    labelHeart3.setIcon(icon_empty_heart);
                } else if (ballGone == 2) {

                    labelHeart2.setIcon(icon_empty_heart);
                }
                if (y == labelTrash.getY() && labelTrash.getX() + labelTrash.getWidth() >= labelPaper.getX() && labelTrash.getX() - labelTrash.getWidth() <= labelPaper.getX()) {
                    y = 0;
                    point_counter++;
                    points.setText(point_counter + " points");
                }
                if (y >= panelCenter.getHeight()) {
                    ballGone++;
                    ((Timer) e.getSource()).start();
                    y = 0;
                    if (ballGone >= 3) {
                        labelHeart1.setIcon(icon_empty_heart);
                        dead = true;
                        timer.stop();
                        ((Timer) e.getSource()).stop();
                        JOptionPane.showMessageDialog(null, "Game Over");

                        buttonPausa.setText("Reiniciar");
                        ballGone = 0;


                    }


                }

                if (point_counter <= 5) {
                    points.setForeground(Color.BLUE);
                } else if (point_counter > 5 && point_counter <= 10) {
                    points.setForeground(Color.YELLOW);
                } else if (point_counter > 10 && point_counter <= 15) {
                    points.setForeground(Color.ORANGE); } else {

                    points.setForeground(Color.RED);
                }
            }


        });
        timerPaper.start();
        buttonPausa.addMouseListener(new ButtonPauseListener(timer, timerPaper));
        points.setText(point_counter + " points");
        panelTitle.add(points);

    }

    private void generatePaperBall(JPanel panelCenter) {
        Random rand = new Random();
        y = 0;

        int max = panelCenter.getWidth() - labelTrash.getWidth();
        int min = 0 + labelTrash.getWidth();
        int x = rand.nextInt(max - min + 1) + min;

        y++;

        labelPaper.setSize(40, 40);
        ImageIcon paper = new ImageIcon("src/images/paper.png");
        Icon iconpaper = new ImageIcon(paper.getImage().getScaledInstance(labelPaper.getWidth(), labelPaper.getHeight(), Image.SCALE_DEFAULT));
        labelPaper.setIcon(iconpaper);
        panelCenter.add(labelPaper);
        labelPaper.setLocation(x, y);
    }

    private class ButtonPauseListener extends MouseAdapter {
        Timer timer;
        Timer timer2;

        public ButtonPauseListener(Timer timer, Timer timerPaper) {
            this.timer = timer;
            this.timer2 = timerPaper;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (buttonPausa.getText().equals("Pausa")) {
                timer.stop();
                timer2.stop();
                buttonPausa.setText("Start");
            } else if (buttonPausa.getText().equals("Reiniciar")) {
                timer.start();
                timer2.start();
                y = panelMain.getHeight();

                buttonPausa.setText("Pausa");
                panelMain.requestFocus();
            } else {
                timer.start();
                timer2.start();
                buttonPausa.setText("Pausa");
                panelMain.requestFocus();
            }

        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setLayout(null);
        frame.setLocation(800, 600);
        Toolkit pantalla = Toolkit.getDefaultToolkit();
        Image icono = pantalla.getImage("src/images/politecnics.png");
        frame.setIconImage(icono);

        frame.setVisible(true);

    }

    private class PanelMainListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);

            int x = labelTrash.getX();

            switch (e.getKeyCode()) {
                case VK_RIGHT -> x += 5;
                case VK_LEFT -> x -= 5;
            }
            if (x >= 0 && x <= panelMain.getWidth() - labelTrash.getWidth())
                labelTrash.setLocation(x, labelTrash.getY());
        }
    }

}
