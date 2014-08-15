/**
 * 
 */
package FileManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import PircBotXP.IrcPlugin;

/**
 * @author Ethan
 *
 */
public interface FileManager extends IrcPlugin {

	public BufferedReader read(String name) throws FileNotFoundException;
	public BufferedWriter write(String name) throws IOException;
}
