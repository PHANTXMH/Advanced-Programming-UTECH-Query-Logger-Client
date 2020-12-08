package covid.client.models;

import java.io.ByteArrayInputStream;

public class ChatDataRecieved
{
	private String name;
	private String message;
	private long from;
	private Long to;
	
	public ChatDataRecieved()
	{
		
	}
	
	public ChatDataRecieved(String name, String message,Long from, Long to)
	{
		this.name = name;
		this.message = message;
		this.from = from;
		this.to = to;
	}
	
	public ChatDataRecieved(ByteArrayInputStream data)
	{
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getFrom() {
		return from;
	}

	public void setFrom(Long from) {
		this.from = from;
	}

	public Long getTo() {
		return to;
	}

	public void setTo(Long to) {
		this.to = to;
	}
	
	
}
