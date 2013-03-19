
package ircbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class IrcBotDebug {
    
    public String server;
    public String owner;
    public String nick;   
    public String channel;
    public PrintWriter output;
    
    public IrcBotDebug(String server, String owner, String nick, String channel) throws IOException{
        this.server = server;
        this.owner = owner;
        this.nick = nick;
        this.channel = channel;
        Start();
    }
    
    private void Start()  throws IOException{
        String str;
        Socket sock = new Socket(this.server, 6667);
        BufferedReader input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        output = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
        
        send( "USER " + this.nick + " 0 * :" + this.owner + "\r\n" + "NICK " + this.nick + "\r\n");
        
        while(sock.isConnected()){
            
            str = input.readLine();
            System.out.println(str);
            
            if(str.startsWith("PING ")){
                send("PONG ");
            }
            if(str.split(" ")[1].equals("001")) {
                send("MODE " + this.nick + " +B\r\n" + "JOIN " + this.channel + "\r\n");
            }
        }
        sock.close();
    }
    public void send(String string){
        System.out.println(string);
        output.print(string);
        output.flush();
    }
}