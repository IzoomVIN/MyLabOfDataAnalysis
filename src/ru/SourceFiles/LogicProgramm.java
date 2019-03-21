package ru.SourceFiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ru.GUI.*;

public class LogicProgramm{
    private MainMenuWindow mainMenu = new MainMenuWindow();
    private AddTableViewWindow aTView = new AddTableViewWindow();

    LogicProgramm(){
        addALtoATV();
        addALtoMM();

        mainMenu.start();
    }

    private void addALtoMM(){
        mainMenu.setActionListenerID(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenu.stop();
                aTView.start();
            }
        });
    }

    private void addALtoATV(){
        aTView.setActionListenerCancel(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aTView.stop();
                mainMenu.start();
            }
        });
    }

    public static void main(String args[]){
        LogicProgramm lp = new LogicProgramm();

    }
}
