package gui;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Saver {
    public static final String gameFile = "Game.save";
    public static final String logFile = "Log.save";
    public static final String robotFile = "Robot.save";
    public static final String targetFile = "Target.save";

    public static void saveWindowState(MainApplicationFrame frame, String name) {
        WindowData windowData = new WindowData();
        windowData.height = frame.getHeight();
        windowData.width = frame.getWidth();
        windowData.positionX = frame.getX();
        windowData.positionY = frame.getY();
        windowData.name = frame.getName();
        serialize(windowData, name);
    }

    private static void serialize(Object info, String name) {
        File file = new File(name);
        try (OutputStream os = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(os))) {
            oos.writeObject(info);
            oos.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static WindowData deserialize(String name) {
        WindowData restored = null;
        try (InputStream is = new FileInputStream(name);
             ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(is))) {
            restored = (WindowData) ois.readObject();
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
        return restored;
    }

    public static JInternalFrame loadWindowState(String name, JInternalFrame frame) throws PropertyVetoException {
        WindowData windowData = (WindowData) deserialize(name);
        if (windowData != null) {
            if (frame instanceof LogWindow){
                frame.setLocation(windowData.positionX, windowData.positionY);
                ((LogWindow) frame).setSize(windowData.width, windowData.height);
                frame.setIcon(windowData.isMinimized);
                frame.setMaximum(windowData.isMaximized);
            }
            frame.setLocation(windowData.positionX, windowData.positionY);
            frame.setSize(windowData.width, windowData.height);
            frame.setIcon(windowData.isMinimized);
            frame.setMaximum(windowData.isMaximized);
        }
        return windowData != null ? frame : null;
    }

    public static MainApplicationFrame loadWindowState(String name, MainApplicationFrame frame) {
        WindowData windowData = deserialize(name);
        if (windowData != null) {
            frame.setLocation(windowData.positionX, windowData.positionY);
            frame.setPreferredSize(new Dimension(windowData.width, windowData.height));
        }
        return windowData != null ? frame : null;
    }

    public static void saveRobot(Robot robot, String name) {
        serialize(robot, name);
    }

    public static Robot loadRobot(String name) {
        Robot robot = null;
        try (InputStream is = new FileInputStream(name);
             ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(is))) {
            robot = (Robot) ois.readObject();
        } catch (ClassNotFoundException | IOException ignored) { }
        return robot;
    }

//    public static void saveWindows(JInternalFrame[] frames){
//        try {
//            for (JInternalFrame frame: frames) {
//                String fileName = "";
//                if (frame instanceof LogWindow) {
//                    fileName = logFile;
//                }
//                if (frame instanceof GameWindow) {
//                    fileName = gameFile;
//                }
//                File file = new File(fileName);
//                FileOutputStream fos = new FileOutputStream(file);
//                ObjectOutputStream oos = new ObjectOutputStream(fos);
//                WindowData window = new WindowData(frame.getName(), frame.getWidth(),
//                        frame.getHeight(), frame.getX(), frame.getY());
//                oos.writeObject(window);
//                oos.close();
//                fos.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static WindowData loadWindow(String filename) throws IOException {
//        FileInputStream fis = new FileInputStream(filename);
//        ObjectInputStream ois = new ObjectInputStream(fis);
//        WindowData window = null;
//        while (true) {
//            Object obj = null;
//            try {
//                obj = ois.readObject();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//            if (obj == null) break;
//            window = (WindowData) obj;
//            System.out.println(window);
//        }
//        ois.close();
//        fis.close();
//        return window;
//    }
//
//    public static void setWindowState(JInternalFrame frame, HashMap<String, WindowData> windows){
//        WindowData windowData = windows.get(frame.getName());
//        frame.setSize(windowData.width, windowData.height);
//        frame.setLocation(windowData.positionX, windowData.positionY);
//    }
//
//    public static void saveObject(Object obj, String fileName){
//        try {
//            File file = new File(fileName);
//            FileOutputStream fos = new FileOutputStream(file);
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(obj);
//            oos.close();
//            fos.close();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void loadObject(String fileName){
//        try {
//            File file = new File(fileName);
//            FileInputStream fis = new FileInputStream(file);
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            Object obj = ois.readObject();
//            if (obj instanceof Robot){
//                Robot robot = (Robot) obj;
//            }
//            if (obj instanceof Target){
//                Target target = (Target) obj;
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
}
