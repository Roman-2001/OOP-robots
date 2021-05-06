package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.*;

import log.Logger;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается. 
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 *
 */
public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    
    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);
        
        
        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

//    protected JMenuBar createMenuBar() {
//        JMenuBar menuBar = new JMenuBar();
//
//        //Set up the lone menu.
//        JMenu menu = new JMenu("Document");
//        menu.setMnemonic(KeyEvent.VK_D);
//        menuBar.add(menu);
//
//        //Set up the first menu item.
//        JMenuItem menuItem = new JMenuItem("New");
//        menuItem.setMnemonic(KeyEvent.VK_N);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_N, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("new");
////        menuItem.addActionListener((ActionListener) this);
//        menu.add(menuItem);
//
//        //Set up the second menu item.
//        menuItem = new JMenuItem("Quit");
//        menuItem.setMnemonic(KeyEvent.VK_Q);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("quit");
////        menuItem.addActionListener((ActionListener) this);
//        menu.add(menuItem);
//
//        return menuBar;
//    }

//    protected JMenu createAloneMenu(String name, int keyEvent){
//        JMenu menu = new JMenu("Document");
//        menu.setMnemonic(KeyEvent.VK_D);
//        return menu;
//    }
//
//    protected JMenuItem setUpMenuItem(String name, int keyEvent, ActionListener listenerFunction){
//        JMenuItem menuItem = new JMenuItem();
//        return menuItem;
//    }
    
    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();

        Menu lookAndFeelMenu = new Menu("Режим отображения",
                KeyEvent.VK_V, "Управление режимом отображения приложения");
        lookAndFeelMenu.addMenuItem("Системная схема", KeyEvent.VK_S, (event) -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                this.invalidate();
            });
        Menu testMenu = new Menu("Тесты", KeyEvent.VK_T, "Тестовые команды");
        testMenu.addMenuItem("Сообщение в лог", KeyEvent.VK_S, (event) -> {
                Logger.debug("Новая строка");
            });

        menuBar.add(lookAndFeelMenu.getMenu());
        menuBar.add(testMenu.getMenu());
        return menuBar;
    }

    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}
