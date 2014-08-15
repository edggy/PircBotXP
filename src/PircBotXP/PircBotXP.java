package PircBotXP;
import java.io.File;
import java.util.Collection;

import org.pircbotx.PircBotX;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;

/**
 * @author Ethan
 *
 */
public class PircBotXP {

	@InjectPlugin
	public static PluginManager pm = PluginManagerFactory.createPluginManager();
	private static PircBotX bot = new PircBotX();
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		pm.addPluginsFrom(new File("plugins/").toURI());
		Collection<IrcPlugin> ircPlugins = new PluginManagerUtil(pm).getPlugins(IrcPlugin.class);
		bot.getListenerManager().addListener(new Listener(ircPlugins));
		for ( IrcPlugin plugin : ircPlugins ) {
			plugin.load(bot);
		}
	}

}
