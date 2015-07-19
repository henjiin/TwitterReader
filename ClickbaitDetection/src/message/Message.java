package message;

import java.util.Date;
public abstract class Message {
	public abstract String getText();
	public abstract String getAsscociatedMedia();
	public abstract String getCreator();
	public abstract String getTargetLink();
	public abstract String getID();
	public abstract boolean isTweet();
	public abstract  String getHTMLView();
	public abstract boolean hasMedia();
	public abstract String getMediaEntitiy();
	public abstract String getMediaType();
	public abstract Date getCreatedAt();	
}
