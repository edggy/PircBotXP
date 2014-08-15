/**
 * 
 */
package RoomLogger;

import java.io.BufferedWriter;

import PircBotXP.IrcPlugin;

/**
 * @author Ethan
 *
 */
public interface RoomLogger extends IrcPlugin {

	public void write(BufferedWriter room, long time, String nick, String message);
}
