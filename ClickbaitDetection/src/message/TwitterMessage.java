package message;

import java.util.Date;

import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

public class TwitterMessage extends Message {

	public TwitterMessage(String JSONString) {
		try {
			tweet = TwitterObjectFactory.createStatus(JSONString);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String getText() {
		String text=new String(tweet.getText());
		return text;
	}

	public boolean hasMedia(){
		if(tweet.getMediaEntities().length>0)
		return true;
		else return false;
	}
	@Override
	public String getMediaType() {
		if(hasMedia())
		return tweet.getMediaEntities()[0].getType();
		else return "noMedia";
	}

	@Override
	public String getCreator() {
		String creator= tweet.getUser().getName();
		return creator;
	}

	@Override
	public String getTargetLink() {
		String link=tweet.getURLEntities()[0].getExpandedURL();
		return link;
	}

	@Override
	public String getID() {

		return String.valueOf(tweet.getId());
	}

	@Override
	public boolean isTweet() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getHTMLView() {

		String html = "<html><body><h1>" + tweet.getText() + "</h1><br>";
		if (tweet.getMediaEntities().length > 0) {
			
			for (MediaEntity media : tweet.getMediaEntities()) {				
				if (media.getType().equals("photo")){					
				html += "<img style=\"max-width:50%; height:auto\" src=\""
						+ media.getMediaURL() + "\">";
				}
				else{
					html+="<h3>"+media.getType()+" Media Entity included </h3>";
				}
			}
		}
		html += "</body></html>";		
		return html;
	}

	public Status tweet;

	@Override
	public String getMediaEntitiy() {
		String mediaEntitiy=tweet.getMediaEntities()[0].getExpandedURL();
		return mediaEntitiy;
	}

	@Override
	public String getAsscociatedMedia() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getCreatedAt() {
		// TODO Auto-generated method stub
		return tweet.getCreatedAt();
	}
	

}
