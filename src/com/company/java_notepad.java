package com.company;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.StringTokenizer;

public class java_notepad extends JFrame {
    private JTextArea textArea;
    private JMenuBar menuBar;
    private JMenu menuFile, menuEdit, menuFormat, menuHelp;
    private JMenuItem itemnew, itemopen, itemsave, itmsaveas, itmexit, itemcut, itemcopy, itempaste,itemfontcolor,background,itemfont;
    String filename, fileContent;
    JFileChooser jc;
    UndoManager undo;
    UndoAction undoAction;
    RedoAction redoAction;
    JCheckBoxMenuItem wordWrap;
    FontHelper font;
    JToolBar toolBar;
    JButton btnOpen;

    public java_notepad() {
        initialization();
        itemsave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });

        itmsaveas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAs();
            }
        });

        itemopen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });

        itemnew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                open_new();
            }
        });

        itmexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        itemcut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.cut();
            }
        });

        itemcopy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.copy();
            }
        });

        itempaste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.paste();
            }
        });

        textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undo.addEdit(e.getEdit());
                undoAction.update();
                redoAction.update();
            }
        });

        wordWrap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(wordWrap.isSelected())
                {
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                }else
                {
                    textArea.setLineWrap(false);
                    textArea.setWrapStyleWord(false);
                }
            }
        });

        itemfontcolor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c =  JColorChooser.showDialog(rootPane, "Choose Font Color" , Color.YELLOW);
                textArea.setForeground(c);
            }
        });

        itemfont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                font.setVisible(true);
            }
        });

        font.getok().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setFont(font.font());
                font.setVisible(false);
            }
        });
        font.getCancel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                font.setVisible(false);
            }
        });

        background.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c =  JColorChooser.showDialog(rootPane, "Choose Background Color" , Color.WHITE);
                textArea.setBackground(c);
            }
        });

        btnOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                open();
            }
        });

    }

    public void initialization() {
        jc = new JFileChooser(".");
        textArea = new JTextArea();
        wordWrap = new JCheckBoxMenuItem("Word Wrap");
        font = new FontHelper();
        toolBar = new JToolBar();
        getContentPane().add(toolBar , BorderLayout.NORTH);
        getContentPane().add(textArea);
        getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
        setTitle("Unititled Notepad");
        setSize(800, 600);
        undo = new UndoManager();
        //Menu bar
        menuBar = new JMenuBar();

        //Menu
        menuFile = new JMenu("File");
        menuEdit = new JMenu("Edit");
        menuFormat = new JMenu("Format");
        menuHelp = new JMenu("Help");

        //Adding icons
        ImageIcon newicon = new ImageIcon(getClass().getResource("new.png"));
        ImageIcon openicon = new ImageIcon(getClass().getResource("open.png"));
        ImageIcon saveicon = new ImageIcon(getClass().getResource("save.png"));
        ImageIcon saveasicon = new ImageIcon(getClass().getResource("saveas.png"));
        ImageIcon exiticon = new ImageIcon(getClass().getResource("exit.png"));
        ImageIcon copyicon = new ImageIcon(getClass().getResource("copy.png"));
        ImageIcon cuticon = new ImageIcon(getClass().getResource("cut.png"));
        ImageIcon pasteicon = new ImageIcon(getClass().getResource("paste.png"));
        ImageIcon undoIcon = new ImageIcon(getClass().getResource("undo.png"));
        ImageIcon redoIcon = new ImageIcon(getClass().getResource("redo.png"));
        ImageIcon fontIcon = new ImageIcon(getClass().getResource("font.png"));
        ImageIcon colorIcon = new ImageIcon(getClass().getResource("color.png"));
        ImageIcon backIcon = new ImageIcon(getClass().getResource("background.png"));



        btnOpen = new JButton(openicon);
        toolBar.add(btnOpen);


        undoAction = new UndoAction(undoIcon);
        redoAction = new RedoAction(redoIcon);
        //MenuItems
        itemnew = new JMenuItem("New", newicon);
        itemopen = new JMenuItem("Open", openicon);
        itemsave = new JMenuItem("Save", saveicon);
        itmsaveas = new JMenuItem("Save As", saveasicon);
        itmexit = new JMenuItem("Exit", exiticon);
        itemcut = new JMenuItem("Cut", cuticon);
        itemcopy = new JMenuItem("Copy", copyicon);
        itempaste = new JMenuItem("Paste", pasteicon);
        itemfont = new JMenuItem("Font", fontIcon);
        itemfontcolor = new JMenuItem("Font Color",colorIcon);
        background = new JMenuItem("Background Color",backIcon);


        //Adding shortcuts
        itemnew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        itemopen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        itemsave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        itemcut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        itemcopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        itempaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));

        menuFile.add(itemnew);
        menuFile.add(itemopen);
        menuFile.add(itemsave);
        menuFile.add(itmsaveas);
        menuFile.addSeparator();
        menuFile.add(itmexit);

        menuEdit.add(undoAction);
        menuEdit.add(redoAction);
        menuEdit.add(itemfont);
        menuEdit.add(itemcut);
        menuEdit.add(itemcopy);
        menuEdit.add(itempaste);


        menuFormat.add(wordWrap);
        menuFormat.add(itemfontcolor);
        menuFormat.add(background);

        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuFormat);
        menuBar.add(menuHelp);

        setJMenuBar(menuBar);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void save() {
        PrintWriter fout = null;
        try {
            if (filename == null) {
                saveAs();
            } else {
                fout = new PrintWriter(new FileWriter(filename));
                String s = textArea.getText();
                StringTokenizer st = new StringTokenizer(s, System.lineSeparator());
                while (st.hasMoreElements()) {
                    fout.println(st.nextToken());
                }
                JOptionPane.showMessageDialog(null, "File saved successfully");
                fileContent = textArea.getText();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fout != null)
                fout.close();
        }
    }

    public void saveAs() {
        PrintWriter fout = null;
        try {
            int retval = -1;
            retval = jc.showSaveDialog(this);
            if (retval == JFileChooser.APPROVE_OPTION) {
                if(jc.getSelectedFile().exists())
                {
                    int option = JOptionPane.showConfirmDialog(rootPane , "Do you want to replace this file" , "Confirmation" , JOptionPane.OK_CANCEL_OPTION);
                    if(option == 0)
                    {
                        fout = new PrintWriter(new FileWriter(jc.getSelectedFile()));
                        String s = textArea.getText();
                        StringTokenizer st = new StringTokenizer(s, System.lineSeparator());
                        while (st.hasMoreElements()) {
                            fout.println(st.nextToken());
                        }
                        JOptionPane.showMessageDialog(null, "File saved successfully");
                        fileContent = textArea.getText();
                        filename = jc.getSelectedFile().getName();
                        setTitle(jc.getSelectedFile().getName());
                    }
                    else{
                        saveAs();

                    }
                }else
                {
                    fout = new PrintWriter(new FileWriter(jc.getSelectedFile()));
                    String s = textArea.getText();
                    StringTokenizer st = new StringTokenizer(s, System.lineSeparator());
                    while (st.hasMoreElements()) {
                        fout.println(st.nextToken());
                    }
                    JOptionPane.showMessageDialog(null, "File saved successfully");
                    fileContent = textArea.getText();
                    filename = jc.getSelectedFile().getName();
                    setTitle(jc.getSelectedFile().getName());
                }
                }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fout.close();
        }
    }

    public void open() {
        try {
            int retval = jc.showOpenDialog(this);
            if (retval == JFileChooser.APPROVE_OPTION) {
                textArea.setText(null);
                Reader in = new FileReader(jc.getSelectedFile());
                char buff[] = new char[100000];
                int n;
                while ((n = in.read(buff, 0, buff.length)) != -1) {
                    textArea.append(new String(buff, 0, n));
                }
                filename = jc.getSelectedFile().getName();
                setTitle(filename);


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open_new() {
        if (!textArea.getText().equals("") && !textArea.getText().equals(fileContent)) {
            if (filename == null) {
                int option = JOptionPane.showConfirmDialog(rootPane, "Do you want to save the changes");
                if (option == 0) {
                    saveAs();
                    clear();
                } else if (option == 2) {
                } else {
                    clear();
                }
            } else {
                int option = JOptionPane.showConfirmDialog(rootPane, "Do you want to save the changes");
                if (option == 0) {
                    save();
                    clear();
                } else if (option == 2) {
                } else {
                    clear();
                }
            }
        }
    }

    private void clear() {
        textArea.setText(null);
        setTitle("Untitled Notepad");
        filename = null;
        fileContent = null;

    }

    class UndoAction extends AbstractAction {

        public UndoAction(ImageIcon undoIcon) {
            super("Undo", undoIcon);
            setEnabled(false);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                undo.undo();
            } catch (CannotUndoException ex) {
                ex.printStackTrace();
            }
            update();
            redoAction.update();
        }

        private void update() {
            if (undo.canUndo()) {
                setEnabled(true);
                putValue(Action.NAME, "Undo");
            } else {
                setEnabled(false);
                putValue(Action.NAME , "Undo");
            }
        }
    }

    class RedoAction extends AbstractAction {

        public RedoAction(ImageIcon redoIcon) {
            super("Redo", redoIcon);
            setEnabled(false);

        }

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                undo.redo();
            } catch (CannotRedoException ex) {
                ex.printStackTrace();
            }
            update();
            undoAction.update();
        }

        private void update() {
            if (undo.canRedo()) {
                setEnabled(true);
                putValue(Action.NAME, "Redo");
            }
            else {
                setEnabled(false);
                putValue(Action.NAME , "Redo");
            }
        }
    }
}
