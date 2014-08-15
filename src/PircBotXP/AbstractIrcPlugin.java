/**
 * 
 */
package PircBotXP;

import net.xeoh.plugins.base.PluginManager;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.Event;

/**
 * @author Ethan
 *
 */
public abstract class AbstractIrcPlugin implements IrcPlugin {

	protected static PircBotX bot;
	public static PluginManager pm;
	/* (non-Javadoc)
	 * @see PircBotXP.IrcPlugin#load(org.pircbotx.PircBotX)
	 */
	@Override
	public void load(PircBotX bot) {
		AbstractIrcPlugin.bot = bot;
		AbstractIrcPlugin.pm = PircBotXP.pm;
	}

	/* (non-Javadoc)
	 * @see PircBotXP.IrcPlugin#onEvent(org.pircbotx.hooks.Event)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public abstract void onEvent(Event arg0) throws InactiveException;

	/* (non-Javadoc)
	 * @see PircBotXP.IrcPlugin#start()
	 */
	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see PircBotXP.IrcPlugin#status()
	 */
	@Override
	public String status() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see PircBotXP.IrcPlugin#stop()
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see PircBotXP.IrcPlugin#unload()
	 */
	@Override
	public void unload() {
		// TODO Auto-generated method stub

	}

}
