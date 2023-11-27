package org.example;
import javax.swing.*;
import  java.awt.*;
import  java.awt.event.ActionEvent;
import  java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Main {
    public static void main(String[] args) {

        String path = "http://testeinfnet.000webhostapp.com/index3.php";

        JFrame frame = new JFrame("Envio JSON?");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,2));

        JTextField inputField1 = new JTextField();
        JTextField inputField2 = new JTextField();
        JTextField inputField3 = new JTextField();

        inputField1.setFont((new Font("Arial", Font.PLAIN, 30)));
        inputField2.setFont((new Font("Arial", Font.PLAIN, 30)));
        inputField3.setFont((new Font("Arial", Font.PLAIN, 30)));

        JLabel label1 = new JLabel("Nome:");
        label1.setFont((new Font("Arial", Font.BOLD, 30)));
        JLabel label2 = new JLabel("CPF:");
        label2.setFont((new Font("Arial", Font.BOLD, 30)));
        JLabel label3 = new JLabel("Telefone:");
        label3.setFont((new Font("Arial", Font.BOLD, 30)));

        panel.add(label1);
        panel.add(inputField1);
        panel.add(label2);
        panel.add(inputField2);
        panel.add(label3);
        panel.add(inputField3);

        String[] buttonLabels ={
                "ENVIAR", "SAIR"
        };

        for (String label: buttonLabels){
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            panel.add(button);

            button.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {

                    try {
                        String content = "{\"animal\": \"dog\", \"name\": \"biff\"}";

                        URL url = new URL(path);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setDoOutput(true);

                        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                        out.writeBytes(content);
                        out.flush();
                        out.close();

                        int responseCode = connection.getResponseCode();
                        System.out.println("Code:" + responseCode);
                        if (responseCode != HttpURLConnection.HTTP_OK) {
                            System.out.println("Got an unexpected response code");
                        }

                        BufferedReader in = new BufferedReader((new InputStreamReader(connection.getInputStream())));

                        String line;
                        while ((line = in.readLine()) != null) {
                            System.out.println(line);
                        }
                        in.close();

                    }
                    if (label.equals("SAIR")) {
                        System.exit(0);
                    }

                }
                 catch (Exception er){
                    System.out.println(er);
                }
            });
        }
        frame.add(panel);
        frame.setVisible(true);
    }
}

