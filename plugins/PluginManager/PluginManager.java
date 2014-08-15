/**
 * 
 */
package PluginManager;

import java.util.Set;
import java.util.TreeSet;

import PircBotXP.IrcPlugin;

/**
 * @author Ethan
 *
 */
public interface PluginManager extends IrcPlugin {

	public Set<String> getAllPluginNames();
	
	public boolean loadPlugin(String pluginName);
	public boolean unloadPlugin(String pluginName);
	public boolean reloadPlugin(String pluginName);
	
	public boolean loadAll();
	public boolean unloadAll();
	public boolean reloadAll();
	
}
