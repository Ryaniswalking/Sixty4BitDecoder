package com.codebind;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Base64;
import java.util.LinkedHashMap;

public class FullForm {
    private JPanel panel;
    private JPanel panel_output;
    private JTextArea textArea_Output;
    private JPanel panel_input;
    private JPanel panel_TestInfo;
    private JLabel label_testCaseName;
    private JTextField textField_TestCaseName;
    private JTextArea textArea_TestDescription;
    private JPanel panel_Tm4j;
    private JLabel label_tm4j;
    private JLabel label_TestCaseKey;
    private JLabel label_ReqTrace;
    private JLabel label_Delta;
    private JLabel label_Prereq;
    private JTextField textField_TestCaseKey;
    private JTextField textField_Delta;
    private JTextField textField_Prereq;
    private JTextField textField_ReqTrace;
    private JPanel panel_Tm4jSteps;
    private JLabel label_Tm4jSteps;
    private JLabel label_Step1;
    private JLabel label_TestData1;
    private JLabel label_ExpectedResult1;
    private JLabel label_Step2;
    private JLabel label_TestData2;
    private JLabel label_ExpectedResult2;
    private JTextField textField_Step1;
    private JTextField textField_TestData1;
    private JTextField textField_ExpectedResult1;
    private JTextField textField_Step2;
    private JTextField textField_TestData2;
    private JTextField textField_ExpectedResult2;
    private JPanel panel_request;
    private JLabel label_Request;
    private JTextArea textArea_Request;
    private JPanel panel_Reponse;
    private JLabel label_Response;
    private JTextArea textArea_Response;
    private JButton btn_Convert;
    public JPanel mainPanel;
    private JButton btn_Copy;

    public FullForm() {
        btn_Convert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String testCaseName = textField_TestCaseName.getText();
                String description = textArea_TestDescription.getText();
                String testCaseKey = textField_TestCaseKey.getText();
                String reqTrace = textField_ReqTrace.getText();
                String delta = textField_Delta.getText();
                String preReq = textField_Prereq.getText();
                String step1 = textField_Step1.getText();
                String testData1 = textField_TestData1.getText();
                String expRes1 = textField_ExpectedResult1.getText();
                String step2 = textField_Step2.getText();
                String testData2 = textField_TestData2.getText();
                String expRes2 = textField_ExpectedResult2.getText();
                String request = textArea_Request.getText();
                String response = textArea_Response.getText();

                JSONObject testDataJson = linkedHashMapJsonObject();
                JSONObject tm4jData = linkedHashMapJsonObject();
                JSONArray tm4jArraySteps = new JSONArray();
                JSONObject tm4jSteps1 = linkedHashMapJsonObject();
                JSONObject tm4jSteps2 = linkedHashMapJsonObject();
                JSONObject testDataInfo = linkedHashMapJsonObject();


                tm4jData.put("testCaseKey", testCaseKey);
                tm4jData.put("requirementTraceability", reqTrace);
                tm4jData.put("delta", delta);
                tm4jData.put("precondition", preReq);

                tm4jSteps1.put("step", step1);
                tm4jSteps1.put("testData", testData1);
                tm4jSteps1.put("expectedResult", expRes1);
                tm4jSteps2.put("step", step2);
                tm4jSteps2.put("testData", testData2);
                tm4jSteps2.put("expectedResult", expRes2);
                tm4jArraySteps.put(tm4jSteps1);
                tm4jArraySteps.put(tm4jSteps2);

                tm4jData.put("steps",tm4jArraySteps);

                testDataInfo.put("testCaseName", testCaseName);
                testDataInfo.put("description", description);
                testDataInfo.put("request", getNncodeTo64BitString(request));
                testDataInfo.put("response", getNncodeTo64BitString(response));
                testDataInfo.put("testCaseSteps", tm4jData);


                testDataJson.put(testCaseName, testDataInfo);

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonParser jp = new JsonParser();
                JsonElement je = jp.parse(String.valueOf(testDataJson));
                String prettyJsonString = gson.toJson(je);

                textArea_Output.setText(prettyJsonString);
            }
        });
        btn_Copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textArea_Output.getText();
                StringSelection stringSelection = new StringSelection(text);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });
    }

    private JSONObject linkedHashMapJsonObject(){
        JSONObject object = new JSONObject();
        try {
            Field changeMap = object.getClass().getDeclaredField("map");
            changeMap.setAccessible(true);
            changeMap.set(object, new LinkedHashMap<>());
            changeMap.setAccessible(false);
        }catch(IllegalAccessException | NoSuchFieldException e){
            System.out.println(e.getMessage());
        }

        return object;
    }

    private String getNncodeTo64BitString(String input){
        return Base64.getEncoder().encodeToString(input.getBytes());
    }
    public static void main(String args[]) {
//        JFrame frame = new JFrame("TPRDataCreator");
//        frame.setContentPane(new TPRDataForm().mainPanel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        frame.pack();//sets the size of the window to the size of the frames
//        frame.setSize(500,500);
//        frame.setVisible(true);
//        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        JFrame frame = new JFrame("TPRDataCreator");
        frame.setContentPane(new FullForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();//sets the size of the window to the size of the frames
        frame.setSize(1500, 1000);
        frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }
}
