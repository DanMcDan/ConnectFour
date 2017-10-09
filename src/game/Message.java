package game;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * public message class. Takes a message in string form, and wraps it up for transport.
 * @author Daniel McCann
 *
 */
public class Message implements Serializable{
	private static final long serialVersionUID = 286658261238079800L;
	
	private String text;
	private String name;
	
	/**
	 * Message object, meant to be sent through object streams, and recieved through InputListener objects.
	 * @param text text is the string containing the actual contents of the message.
	 * @param name name is the chat name of the person who sent the message.
	 */
	public Message(String text, String name) {
		this.text = text;
		this.name = name;
	}
	
	@Override
	public String toString() {
		String out = "";
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
		out += (name + ": " + text + "\n"+ simpleDateFormat.format(Calendar.getInstance().getTime())+"\n\n");
		
		return out;
	}
}
