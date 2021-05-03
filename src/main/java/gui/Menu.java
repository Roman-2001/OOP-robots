package gui;

import javax.swing.*;

import java.awt.event.ActionListener;

public class Menu {
    private final JMenu menu;

    Menu(String name, int keyEvent, String description){
        this.menu = new JMenu(name);
        this.menu.setMnemonic(keyEvent);
        this.menu.getAccessibleContext().setAccessibleDescription(description);
    }

    void addMenuItem(String name, int keyEvent, ActionListener listenerFunction){
        JMenuItem item = new JMenuItem(name, keyEvent);
        item.addActionListener(listenerFunction);
        this.menu.add(item);
    }

    JMenu getMenu(){
        return menu;
    }
}
