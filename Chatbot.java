package com;
import java.io.File;
import java.io.IOException;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Chatbot extends JFrame implements ActionListener
{
	public JTextField txtMessage;
	public JTextArea txtChat;
	public JScrollPane scroll;
	public JButton btnSend;
	private static final boolean TRACE_MODE = false;
	static String botName = "super";

	String resourcesPath;
	String textLine = "";
	Bot bot;
	Chat chatSession;
	
	Chatbot()
	{
		super("Voice Bot");
		setLayout(null);
		setSize(520,510);
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		setResizable(false);
		
		txtMessage = new JTextField(30);
		txtChat = new JTextArea();
		//txtChat.setBackground(Color.black);
		Font font = new Font("SansSerif", Font.BOLD, 14);
		txtChat.setFont(font);
		//txtChat.setForeground(Color.WHITE);

		ImageIcon start = new ImageIcon("images.png");
		
		btnSend = new JButton(start);
		scroll = new JScrollPane(txtChat);
	    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	//	scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		txtChat.setLineWrap(true);//Breaks the line in small parts
		txtChat.setEditable(false);//can not edit chat

		scroll.setBounds(0, 0, 520, 420);
		txtMessage.setBounds(30, 440, 260, 30);
		btnSend.setBounds(300,425,50,50);
		add(txtMessage);
		add(scroll);
		add(btnSend);
		
		btnSend.addActionListener(this);
		resourcesPath = getResourcesPath();
		txtChat.append(/*resourcesPath*/ "\t\tDELICIOUS WELCOMES YOU...\n\n[ DELICIOUS ] :"
				+ " \n\n Hi there, My Name is Delicious. How may I help you?\n\n"
				+ "\n________________________________"
				+ "\n1.Information about restauarant."
				+ "\n2.Menu."
				+ "\n________________________________"
				+ "\n\nYou can say or type the option.");
		
		bot = new Bot("super", resourcesPath);
		chatSession = new Chat(bot);
		bot.brain.nodeStats();
			
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==btnSend)
		{
			if(txtMessage.getText().equals(""))
			{
			//	JOptionPane.showMessageDialog(null, "Text box is Hungry please enter a message...");
			//	txtMessage.requestFocus();
				SpeechToText sp = new SpeechToText();
				try 
				{
					TextToSpeech tp = new TextToSpeech();
					String word = sp.getWord();
					textLine = word;
					
					txtChat.append("\n[ YOU ] : "+textLine+"\n");
					
					String response = getResponse(textLine);
					
					txtChat.append("[ DELICIOUS ] : ");
					txtChat.append(""+response+"\n");
					txtChat.setCaretPosition(txtChat.getDocument().getLength());//Auto Scroll							
					tp.speak(response);
					
				
				} catch (IOException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				txtMessage.setText("");
				txtMessage.requestFocus();
				
			}
			else
			{
				try
				{		TextToSpeech tp = new TextToSpeech();
						
					
						textLine = txtMessage.getText().toString();
//						textLine.setForeground(Color.blue);
						txtChat.append("\n[ YOU ] : "+textLine+"\n");
						
						String response = getResponse(textLine);
						
							txtChat.append("[ DELICIOUS ] : ");
							txtChat.append(""+response+"\n");
							txtChat.setCaretPosition(txtChat.getDocument().getLength());//Auto Scroll							
							tp.speak(response);
					}
					catch(Exception ee)
					{
					
					}
				txtMessage.setText("");
				txtMessage.requestFocus();
			}
		}		
	}
	
	public static void main(String[] args) 
	{
			new Chatbot();	
	}

	private static String getResourcesPath() 
	{
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		path = path.substring(0, path.length() - 2);
	//	txtChat.append("\n"+path);
		String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
		return resourcesPath;
	}
	
	
	public String getResponse(String req)
	{
		String resp = "Sorry";
		textLine = req;
		MagicBooleans.trace_mode = TRACE_MODE;
		if ((textLine == null) || (textLine.length() < 1))
			textLine = MagicStrings.null_input;
		if (textLine.equals("q")) 
		{
			System.exit(0);
		} 
		else if (textLine.equals("wq")) 
		{
			bot.writeQuit();
			System.exit(0);
		} else 
		{
			String request = textLine;
			if (MagicBooleans.trace_mode)
				txtChat.append("STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0) + ":TOPIC=" + chatSession.predicates.get("topic"));
			String response = chatSession.multisentenceRespond(request);
			while (response.contains("&lt;"))
				response = response.replace("&lt;", "<");
			while (response.contains("&gt;"))
				response = response.replace("&gt;", ">");
			resp  = response;
		
		}
	
		return resp;	
	}
}