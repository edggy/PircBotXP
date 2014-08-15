/**
 * 
 */
package PluginManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.yaml.snakeyaml.Yaml;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;
import net.xeoh.plugins.base.util.PluginManagerUtil;
import FileManager.FileManager;
import PircBotXP.AbstractIrcPlugin;
import PircBotXP.IrcPlugin;

/**
 * @author Ethan
 *
 */
@PluginImplementation
public class PluginManagerImpl extends AbstractIrcPlugin implements PluginManager {

	Collection<IrcPlugin> ircPlugins;
	//ircPlugins = new PluginManagerUtil(pm).getPlugins(IrcPlugin.class);
	Map<String, String> config;
	protected String status;
	
	@InjectPlugin
	public FileManager man;
	
	/**
	 * 
	 */
	public PluginManagerImpl() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see PircBotXP.Plugin#load()
	 */
	@Override
	public void load(PircBotX bot) {
		super.load(bot);
		init();
		System.out.println("PluginManager loaded");
	}
	
	@SuppressWarnings("unchecked")
	public void init() {
		BufferedReader reader;
		try {
			reader = man.read("PluginManager");
			Yaml yaml = new Yaml();
			config = (Map<String, String>) yaml.load(reader);
			status = "Active";
		} catch (FileNotFoundException e) {
			status = "Inactive";
		}
		
	}
	
	private IrcPlugin getPlugin(String pluginName) {
		for ( IrcPlugin plugin : ircPlugins ) {
			if(plugin.getClass().getName().matches(".*" + pluginName + ".*")){
				return plugin;
			}
		}
		return null;
	}
	
	public Set<String> getAllPluginNames() {
		Set<String> res = new TreeSet<String>();
		for ( IrcPlugin plugin : ircPlugins ) {
			res.add(plugin.getClass().getName());
		}
		return res;
	}

	public boolean loadPlugin(String pluginName) {
		IrcPlugin plugin = getPlugin(pluginName);
		plugin.load(bot);
		return true;
	}
	
	public boolean unloadPlugin(String pluginName) {
		IrcPlugin plugin = getPlugin(pluginName);
		plugin.load(bot);
		return true;
	}
	
	public boolean reloadPlugin(String pluginName) {
		boolean res = unloadPlugin(pluginName);
		res = res && loadPlugin(pluginName);
		return res;
	}
	
	public boolean loadAll() {
		ircPlugins = new PluginManagerUtil(pm).getPlugins(IrcPlugin.class);
		for ( IrcPlugin plugin : ircPlugins ) {
			plugin.load(bot);
		}
		return true;
	}

	@Override
	public boolean unloadAll() {
		ircPlugins = new PluginManagerUtil(pm).getPlugins(IrcPlugin.class);
		for ( IrcPlugin plugin : ircPlugins ) {
			plugin.unload();
		}
		return true;
	}

	@Override
	public boolean reloadAll() {
		boolean res = unloadAll();
		res = res && loadAll();
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void onEvent(Event e) {
		if(status == "Inactive") return;
		String[] args = {""};
		if(e instanceof PrivateMessageEvent) {
			PrivateMessageEvent event = (PrivateMessageEvent) e;
			args = Colors.removeFormattingAndColors(event.getMessage()).split(" ");
			String prefix = config.get("CommandPrefix");
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase(prefix + config.get("LoadAllCommand"))) {
					loadAll();
				}
				
				else if(args[0].equalsIgnoreCase(prefix + config.get("UnloadAllCommand"))) {
					unloadAll();
				}
				
				else if(args[0].equalsIgnoreCase(prefix + config.get("ReloadAllCommand"))) {
					reloadAll();
				}
			}
			else if(args.length == 2) {
				if(args[0].equalsIgnoreCase(prefix + config.get("LoadCommand"))) {
					loadPlugin(args[1]);
				}
				else if(args[0].equalsIgnoreCase(prefix + config.get("UnloadCommand"))) {
					unloadPlugin(args[1]);
				}
				else if(args[0].equalsIgnoreCase(prefix + config.get("ReloadCommand"))) {
					reloadPlugin(args[1]);
				}
			}
		}
	}

	@Override
	public void start() {
		status = "Active";
	}

	@Override
	public void stop() {
		status = "Inactive";
	}

	@Override
	public String status() {
		return status;
	}

}
