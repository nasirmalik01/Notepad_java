package com.company;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.StringTokenizer;

public class FontHelper extends JDialog implements ListSelectionListener {

    JPanel pan1, pan2, pan3;
    JLabel fontLabel, sizeLabel, typeLabel, previewLabel;
    JTextField label, fonttext, sizetext, typetext;
    JScrollPane fontScroll, typeScroll, sizeScroll;
    JList fontlist, sizelist, typelist;
    JButton ok, cancel;
    GridBagLayout gbl;
    GridBagConstraints gbc;

    public FontHelper() {
        setTitle("Choose Font");
        setSize(300, 400);
        setResizable(false);
        gbl = new GridBagLayout();
        setLayout(gbl);
        gbc = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        fontLabel = new JLabel("Fonts");
        getContentPane().add(fontLabel, gbc);


        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        sizeLabel = new JLabel("Sizes");
        getContentPane().add(sizeLabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        typeLabel = new JLabel("Types");
        getContentPane().add(typeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        fonttext = new JTextField("Arial", 12);
        getContentPane().add(fonttext, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        sizetext = new JTextField("8", 4);
        getContentPane().add(sizetext, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        typetext = new JTextField("Regular", 6);
        getContentPane().add(typetext, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontlist = new JList(fonts);
        fontlist.setFixedCellWidth(110);
        fontlist.addListSelectionListener(this);
        fontlist.setSelectedIndex(0);
        fontScroll = new JScrollPane(fontlist);
        getContentPane().add(fontScroll, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        String[] sizes = {"8", "10", "12", "16", "18", "20", "24", "28", "32", "48", "72"};
        sizelist = new JList(sizes);
        sizelist.setFixedCellWidth(20);
        sizelist.addListSelectionListener(this);
        sizelist.setSelectedIndex(0);
        sizeScroll = new JScrollPane(sizelist);
        getContentPane().add(sizeScroll, gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        String[] types = {"Regular", "Bold", "Italic", "Bold Italic"};
        typelist = new JList(types);
        typelist.setFixedCellWidth(60);
        typelist.addListSelectionListener(this);
        typelist.setSelectedIndex(0);
        typeScroll = new JScrollPane(typelist);
        getContentPane().add(typeScroll, gbc);


        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        pan1 = new JPanel();
        pan1.setLayout(new FlowLayout());
        previewLabel = new JLabel("Preview");
        pan1.add(previewLabel);
        getContentPane().add(pan1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        pan2 = new JPanel();
        pan2.setLayout(new FlowLayout());
        label = new JTextField("AaBbCcDdEeFfGgHhIiJj");
        label.setEnabled(false);
        label.setBorder(BorderFactory.createEtchedBorder());
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        pan2.add(label);
        getContentPane().add(pan2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        pan3 = new JPanel();
        pan3.setLayout(new FlowLayout());
        ok = new JButton("Ok");
        cancel = new JButton("Cancel");
        pan3.add(ok);
        pan3.add(cancel);
        getContentPane().add(pan3, gbc);


    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

        try {
            if (e.getSource() == fontlist) {
                Font f1 = new Font(String.valueOf(fontlist.getSelectedValue()), typelist.getSelectedIndex(), Integer.parseInt(String.valueOf(sizelist.getSelectedValue())));
                fonttext.setText(String.valueOf(fontlist.getSelectedValue()));
                label.setFont(f1);
            } else if (e.getSource() == sizelist) {
                Font f2 = new Font(String.valueOf(fontlist.getSelectedValue()),
                        typelist.getSelectedIndex(),
                        Integer.parseInt(String.valueOf(sizelist.getSelectedValue())));
                sizetext.setText(String.valueOf(sizelist.getSelectedValue()));
                label.setFont(f2);


            } else {
                Font f3 = new Font(String.valueOf(fontlist.getSelectedValue()),
                        typelist.getSelectedIndex(),
                        Integer.parseInt(String.valueOf(sizelist.getSelectedValue())));
                typetext.setText(String.valueOf(typelist. getSelectedValue()));

                label.setFont(f3);
            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

        public Font font ()
        {
            Font font = new Font(String.valueOf(fontlist.getSelectedValue()),
                    typelist.getSelectedIndex(),
                    Integer.parseInt(String.valueOf(sizelist.getSelectedValue())));
            return font;
        }

        public JButton getok ()
        {
            return ok;
        }

        public JButton getCancel ()
        {
            return cancel;
        }
    }
