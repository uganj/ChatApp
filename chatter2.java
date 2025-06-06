import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Chatter2 extends Frame implements Runnable,ActionListener {
    TextField textfield;
    TextArea textarea;
    Button send;
    Socket socket;
    ServerSocket serversocket;
    DataInputStream datainputstream;
    DataOutputStream dataoutputstream;
    Thread chat;
    Chatter2(){
        textfield = new TextField();
        textfield.setBounds(210,130,100,30);
        textarea = new TextArea();
        textarea.setBounds(170,170,200,100);
        send = new Button("Send");
        send.setBounds(250,290,50,30);
        send.addActionListener(this);
        add(textfield);
        add(textarea);
        add(send);

        try {
            socket = new Socket("localhost",99);
            datainputstream = new DataInputStream(socket.getInputStream());
            dataoutputstream = new DataOutputStream(socket.getOutputStream());
        }
        catch(IOException a){

        }
        chat = new Thread(this);
        chat.start();

        setSize(500,500);
        setLayout(null);
        setTitle("Chatter2");
        setVisible(true);



    }
    public void actionPerformed(ActionEvent e){
        String msg = textfield.getText();
        textarea.append("Chatter2:"+msg+"\n");
        textfield.setText("");
        try{
            dataoutputstream.writeUTF(msg);
            dataoutputstream.flush();
        }
        catch(IOException c){

        }

    }
    public void run(){
        while(true){
            try{
                String msg = datainputstream.readUTF();
                textarea.append("Chatter1:"+msg+"\n");
            }
            catch(IOException b){

            }
        }

    }
    public static void main(String[] args) {
        new Chatter2();
    }
}
