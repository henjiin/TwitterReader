package feature;

import message.Message;
import twitter4j.Status;

public abstract class Feature {

	public abstract String getArffHeader();

	public abstract String getCVSHeader();

	public abstract String getFeature(Status tweet);

	public abstract String getFeature(Message message);
}
