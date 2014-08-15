/**
 * 
 */
package BasicIrcPlugin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.events.Init;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.yaml.snakeyaml.Yaml;

import FileManager.FileManager;
import PircBotXP.AbstractIrcPlugin;
import PircBotXP.IrcPlugin;

/**
 * @author Ethan
 *
 */
@PluginImplementation
public class BasicIrcImpl extends AbstractIrcPlugin implements BasicIrc {

	@InjectPlugin
	public FileManager man;
	Map<String, String> config;
	/**
	 * 
	 */
	public BasicIrcImpl() {
		super();
	}

	/* (non-Javadoc)
	 * @see PircBotXP.Plugin#load()
	 */
	@Override
	public void load(PircBotX bot) {
		super.load(bot);
		//pm.getPlugin(man.getClass(), null);
		init();
		System.out.println("BasicIrc loaded");
	}

	public void init() {
		try {
			BufferedReader reader = man.read("BasicIrc");
			Yaml yaml = new Yaml();
			config = (Map<String, String>) yaml.load(reader);
			setName();
			if (connect()) {
				identify();
				joinChannel();
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see PircBotXP.Plugin#unload()
	 */
	@Override
	public void unload() {
		bot.disconnect();
	}

	/* (non-Javadoc)
	 * @see PircBotXP.Plugin#onEvent(org.pircbotx.hooks.Event)
	 */
	@Override
	public void onEvent(Event e) {
		// TODO Auto-generated method stub
		String[] args = {""};
		if(e instanceof PrivateMessageEvent) {
			PrivateMessageEvent event = (PrivateMessageEvent) e;
			args = Colors.removeFormattingAndColors(event.getMessage()).split(" ");
			String prefix = config.get("CommandPrefix");
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase(prefix + config.get("ChangeNickCommand"))) {
					setNick(args[1]);
				}
				else if(args[0].equalsIgnoreCase(prefix + config.get("IdentifyCommand"))) {
					identify(args[1]);
				}
				else if(args[0].equalsIgnoreCase(prefix + config.get("ChangeServerCommand"))) {
					connect(args[1]);
				}
				else if(args[0].equalsIgnoreCase(prefix + config.get("JoinChannelCommand"))) {
					joinChannel(args[1]);
				}
				else if(args[0].equalsIgnoreCase(prefix + config.get("PartChannelCommand"))) {
					partChannel(args[1]);
				}
			}
			else if(args.length == 3) {
				if(args[0].equalsIgnoreCase(prefix + config.get("PartChannelCommand"))) {
					partChannel(args[1], args[2]);
				}
			}
		}
	}

	//bot.setName(config.get("Nick"));
	public void setName() {
		setName(config.get("Nick"));
	}
	public void setName(String nick) {
		bot.setName(nick);
	}

	public void setNick(String nick) {
		bot.changeNick(nick);
	}

	//bot.connect(config.get("Server"));
	public boolean connect() {
		return connect(config.get("Server"));
	}
	public boolean connect(String server) {
		try {
			bot.connect(server);
		} catch (IOException | IrcException e) {
			return false;
		}
		return true;
	}

	//bot.identify(config.get("Pass"));
	public void identify() {
		identify(config.get("Pass"));
	}
	public void identify(String pass) {
		bot.identify(pass);
	}

	//bot.joinChannel(config.get("Channel"));
	public void joinChannel() {
		joinChannel(config.get("Channel"));
	}

	public void joinChannel(String channel) {
		bot.joinChannel(channel);
	}

	public void partChannel(String channel) {
		bot.partChannel(bot.getChannel(channel));
		System.out.println("Parting " + channel);
	}

	public void partChannel(String channel, String reason) {
		bot.partChannel(bot.getChannel(channel), reason);
		System.out.println("Parting " + channel);
	}
}
