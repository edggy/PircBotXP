/**
 * 
 */
package BasicIrcPlugin;

import PircBotXP.IrcPlugin;

/**
 * @author Ethan
 *
 */
public interface BasicIrc extends IrcPlugin {
	
	public void setName(String nick);
	
	public void setNick(String nick);
	
	public boolean connect(String server);
	
	public void identify(String pass);
	
	public void joinChannel(String channel);
	
	public void partChannel(String channel);
	
	public void partChannel(String channel, String reason);
}
