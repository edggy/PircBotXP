package RoomLogger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.events.*;
import FileManager.FileManager;
import PircBotXP.AbstractIrcPlugin;

@PluginImplementation
public class RoomLoggerImpl extends AbstractIrcPlugin implements RoomLogger {

	@InjectPlugin
	public FileManager man;

	public RoomLoggerImpl() {
		super();
	}

	@Override
	public void load(PircBotX bot) {
		super.load(bot);
		this.bot = bot;
		//BufferedReader reader = man.read("RoomLogger");
		System.out.println("RoomLogger loaded");
	}

	@Override
	public void unload() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEvent(Event event) {
		String room = null;
		long time = event.getTimestamp();
		User user = null;
		//String login = null;
		//String mask = null;
		//String nick = null;
		String message = null;
		if(event instanceof ActionEvent){
			ActionEvent e = (ActionEvent)event;
			user = e.getUser();
			message = e.getMessage();
		}
		else if(event instanceof JoinEvent){
			JoinEvent e = (JoinEvent)event;
			room = e.getChannel().getName();
			user = e.getUser();
			message = "Joined";
		}
		else if(event instanceof KickEvent){
			KickEvent e = (KickEvent)event;
			room = e.getChannel().getName();
			user = e.getRecipient();
			message = "Got Kicked by " + e.getSource().getHostmask() + "Reason: " + e.getReason();
		}
		else if(event instanceof MessageEvent){
			MessageEvent e = (MessageEvent)event;
			room = e.getChannel().getName();
			user = e.getUser();
			message = e.getMessage();
		}
		else if(event instanceof NickChangeEvent){
			NickChangeEvent e = (NickChangeEvent)event;
			room = "Global";
			user = e.getUser();
			message = "Changed nick from" + e.getOldNick() + " to " + e.getNewNick();
		}
		else if(event instanceof PartEvent){
			PartEvent e = (PartEvent)event;
			room = e.getChannel().getName();
			user = e.getUser();
			message = "Parted: " + e.getReason();
		}
		else if(event instanceof PrivateMessageEvent){
			PrivateMessageEvent e = (PrivateMessageEvent)event;
			room = "PrivateMessage";
			user = e.getUser();
			message = e.getMessage();
		}
		else if(event instanceof QuitEvent){
			QuitEvent e = (QuitEvent)event;
			room = "Global";
			user = e.getUser();
			message = "Quit: " + e.getReason();
		}
		/*else if(event instanceof TimeEvent){
			TimeEvent e = (TimeEvent)event;
			room = e.getChannel().getName();
			mask = e.getUser().getHostmask();
			message = e.
		}*/
		else if(event instanceof TopicEvent){
			TopicEvent e = (TopicEvent)event;
			room = e.getChannel().getName();
			user = e.getUser();
			message = e.getTopic();
		}
		if(room != null && user != null && message != null){
			//user!login@hostmask
			String nick = user.getNick() + "!" + user.getLogin() + "@" + user.getHostmask();
			BufferedWriter bw;
			try {
				bw = new BufferedWriter(new FileWriter("logs/" + room + ".log", true));
				write(bw, time, nick, message);
			} catch (IOException e) {
			}
		}


	}

	@Override
	public void write(BufferedWriter room, long time, String nick, String message) {
		try {
			room.write("\n" + time + "\t" + nick + "\t" + message);
			room.flush();
		} catch (IOException e) {
		}
	}

}
