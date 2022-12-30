// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package controller;

import ocsf.client.*;
import common.*;
import entity.Subscriber;
import gui.ClientMenuController;
import gui.ClientUI;
import gui.LoginFrameController;

import java.io.*;

import Entities.*;


/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * 
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI;
  public static Message msgServer; //Data from server visible to all classes in client folder

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
    
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) {

	msgServer = (Message) msg;
	//System.out.println("ChatClientMessage: "+ msgServer.getMessageData().toString() +" --"+ msgServer.getMessageType().toString());	
	 /*// if(msg instanceof Message) {
		  //msgServer = (Message) msg;
			 switch (msgServer.getMessageType()) { 
				case login:
					logframe.setAlertLbl(msgServer.getMessageData());
					break;
				default:
					break;
			  }	//end of case
  
 */ }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(Object message)  
  {
    try
    {
    	sendToServer(message);  
    }
    catch(IOException e)
    {
    e.printStackTrace();	
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ChatClient class
