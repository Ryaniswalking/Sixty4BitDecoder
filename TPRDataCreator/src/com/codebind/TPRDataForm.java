package com.codebind;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Base64;

public class TPRDataForm {
    private JButton btn_Convert;
    private JPanel mainPanel;
    private JTextArea textArea_input;
    private JTextArea textArea_output;
    private JButton btn_Decode;
    private JButton btn_CopyToClipboard;
    private JButton btn_Clear;

    public TPRDataForm() {
        btn_Convert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textArea_input.getText();
                textArea_input.setText("");
                String encodedText = Base64.getEncoder().encodeToString(text.getBytes());
                textArea_output.setText(encodedText);
            }
        });
        btn_Decode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textArea_input.getText();
                textArea_input.setText("");
                byte[]textDecodedBytes = Base64.getDecoder().decode(text);
                String decodedText = new String(textDecodedBytes);
                textArea_output.setText(decodedText);
            }
        });
        btn_CopyToClipboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textArea_output.getText();
                StringSelection stringSelection = new StringSelection(text);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });
        btn_Clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea_input.setText("");
                textArea_output.setText("");
            }
        });
    }
    public static void main(String args[]){
        JFrame frame = new JFrame("TPRDataCreator");
        frame.setContentPane(new TPRDataForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();//sets the size of the window to the size of the frames
        frame.setSize(500,500);
        frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }
}
