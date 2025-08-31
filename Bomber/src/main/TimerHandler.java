package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class TimerHandler implements ActionListener {
    public int timeLeft = 120;
    public Timer timer;

    public TimerHandler() {
        timer = new Timer(1000, this);
    }
    public void startTimer() {
        timer.start();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        timeLeft--;
        System.out.println("Time left: " + timeLeft);
        if (timeLeft <= 0) {
            timer.stop();

        }

    }
    public int getTimeLeft() {
        return timeLeft;
    }

}

