package chat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Message implements Serializable{
	private static final long serialVersionUID = 286658261238079800L;
	
	private String text;
	private String name;
	
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
