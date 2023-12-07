package com.example.chatapp;

import javafx.scene.layout.Background;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.net.*;
import java.io.*;


public class Client implements ActionListener {
    JTextField text;
    static JPanel a1;
    static Box vertical=Box.createVerticalBox();

    static DataOutputStream dout;

    static JFrame f= new JFrame();

    Client(){
        f.setLayout(null);
        JPanel p1= new JPanel();
        p1.setBackground(Color.black);
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);
        ImageIcon i1 = new ImageIcon(Objects.requireNonNull(Server.class.getResource("/com/example/chatapp/arrow.png")));
        Image i2= i1.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT);
        ImageIcon i3= new ImageIcon(i2);
        JLabel back= new JLabel(i3);
        back.setBounds(5,20,20,20);
        p1.add(back);
        back.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        ImageIcon i4 = new ImageIcon(Objects.requireNonNull(Server.class.getResource("/com/example/chatapp/client.png")));
        Image i5= i4.getImage().getScaledInstance(55,55,Image.SCALE_DEFAULT);
        ImageIcon i6= new ImageIcon(i5);
        JLabel profile= new JLabel(i6);
        profile.setBounds(40,10,55,55);
        p1.add(profile);

        ImageIcon i7 = new ImageIcon(Objects.requireNonNull(Server.class.getResource("/com/example/chatapp/call.png")));
        Image i8= i7.getImage().getScaledInstance(30,34,Image.SCALE_DEFAULT);
        ImageIcon i9= new ImageIcon(i8);
        JLabel call= new JLabel(i9);
        call.setBounds(300,20,30,34);
        p1.add(call);

        ImageIcon i10 = new ImageIcon(Objects.requireNonNull(Server.class.getResource("/com/example/chatapp/video.png")));
        Image i11= i10.getImage().getScaledInstance(35,35,Image.SCALE_DEFAULT);
        ImageIcon i12= new ImageIcon(i11);
        JLabel video= new JLabel(i12);
        video.setBounds(360,20,35,35);
        p1.add(video);
        JLabel name=new JLabel("Client");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.white);
        name.setFont(new Font("sans_serif", Font.PLAIN, 18));
        p1.add(name);
        JLabel status=new JLabel("Active");
        status.setBounds(110,37,100,18);
        status.setForeground(Color.white);
        status.setFont(new Font("sans_serif", Font.PLAIN, 14));
        p1.add(status);

        a1= new JPanel();
        a1.setBounds(5,75,440,570);
        f.add(a1);
        text= new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont( new Font("sans_serif", Font.PLAIN, 16));
        f.add(text);

        JButton send= new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setForeground(Color.white);
        send.setBackground(new Color(0,0,0));
        send.setOpaque(true);
        send.setContentAreaFilled(true);
        send.setBorderPainted(false);

        send.addActionListener(this);
        f.add(send);



        f.setSize(450,700);
        f.getContentPane().setBackground(new Color(211,211,211));
        f.setLocation(700,50);
        f.setUndecorated(true);
        f.setVisible(true);

    }
    public void actionPerformed(ActionEvent ae) {
        try{String out = text.getText();
        JPanel p2 = formatLabel(out);
        a1.setLayout(new BorderLayout());


        JPanel right = new JPanel(new BorderLayout());
        right.add(p2, BorderLayout.LINE_END);

        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        a1.removeAll();
        a1.add(vertical, BorderLayout.PAGE_START);
        dout.writeUTF(out);
        text.setText(" ");


        a1.revalidate();
        a1.repaint();
    }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out){
        JPanel panel= new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel output= new JLabel("<html><p style=\"width:150px\">"+out+" </html>");

        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(183,201,226));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,15));
        panel.add(output);

        Calendar cal= Calendar.getInstance();
        SimpleDateFormat sdf= new SimpleDateFormat("HH:mm");
        JLabel time= new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;

    }

    public static void main(String args[]) {
        new Client();
        try{
            Socket s= new Socket("127.0.0.1",6001);
            DataInputStream din= new DataInputStream(s.getInputStream());
            dout= new DataOutputStream(s.getOutputStream());

            while(true){
                a1.setLayout(new BorderLayout());
                String msg= din.readUTF();
                JPanel panel= formatLabel(msg);
                JPanel left= new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);
                f.validate();

            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

