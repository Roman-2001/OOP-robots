package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

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
        close();

    }

    protected void close(){
        JFrame frame = this;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                if (ClosingWindow.confirmExit(frame) == 0){
                    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                }
            }
        });
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
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                int exitCode = ClosingWindow.confirmExit(frame);
                if (exitCode == JOptionPane.YES_OPTION){
                    frame.dispose();
                }
            }
        });
        frame.setVisible(true);
    }
    
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

        Menu exitMenu = new Menu("Выход", KeyEvent.VK_T, "Завершить работу");
        exitMenu.addMenuItem("Выйти", KeyEvent.VK_S, (event) -> {
            if (ClosingWindow.confirmExit(menuBar) == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        menuBar.add(lookAndFeelMenu.getMenu());
        menuBar.add(testMenu.getMenu());
        menuBar.add(exitMenu.getMenu());
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
