package PircBotXP;

import java.util.Collection;

import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.ListenerAdapter;

/**
 * @author Ethan
 *
 */
@SuppressWarnings("rawtypes")
public class Listener extends ListenerAdapter {

	Collection<IrcPlugin> plugins;
	public Listener(Collection<IrcPlugin> plugins) {
		this.plugins = plugins;
	}
	
	public void onEvent(Event event) {
		for ( IrcPlugin plugin : plugins ) {
			try {
				plugin.onEvent(event);
			} catch (InactiveException e) {
			}
		}
	}
}
