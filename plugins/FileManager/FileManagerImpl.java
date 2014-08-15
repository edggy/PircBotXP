package FileManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.events.Init;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.Event;

import PircBotXP.AbstractIrcPlugin;

@PluginImplementation
public class FileManagerImpl extends AbstractIrcPlugin implements FileManager {

	private static Map<String, String> files = new TreeMap<String, String>();
	private static Map<String, BufferedReader> openFilesRead = new TreeMap<String, BufferedReader>();
	private static Map<String, BufferedWriter> openFilesWrite = new TreeMap<String, BufferedWriter>();
	private static boolean loaded = false;

	public FileManagerImpl() {
		super();
		try {
			loadLoader();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Init
	public void init() {
		try {
			loadLoader();
			System.out.println("FileManager loaded");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void load(PircBotX bot) {
		super.load(bot);
		if(!loaded) init();
	}

	public void unload() {
		Collection<BufferedReader> read = openFilesRead.values();
		Collection<BufferedWriter> write = openFilesWrite.values();
		Iterator<BufferedReader> readitr = read.iterator();
		Iterator<BufferedWriter> writeitr = write.iterator();
		while(readitr.hasNext()) {
			BufferedReader cur = readitr.next();
			try {
				cur.close();
			} catch (IOException e) {
			}
		}
		while(writeitr.hasNext()) {
			BufferedWriter cur = writeitr.next();
			try {
				cur.close();
			} catch (IOException e) {
			}
		}

	}

	/**
	 * Loads all the files from files.txt
	 * @throws IOException if files.txt cannot be read
	 */
	private void loadLoader() throws IOException {
		File main = new File("files.txt");
		if(!main.exists()) main.createNewFile();
		BufferedReader br = new BufferedReader(new FileReader(main));
		String line;
		String[] lineSplit;
		while((line = br.readLine()) != null) {
			lineSplit = line.split(":\t");
			if(lineSplit.length == 2){
				files.put(lineSplit[0], lineSplit[1]);
				File f = new File(lineSplit[1]);
				if(!f.exists()) f.createNewFile();
			}
		}
		br.close();
		loaded = true;
	}

	/**
	 * 
	 * @param name the name of the file to read
	 * @return a BufferedReader for name
	 * @throws FileNotFoundException if file is not found
	 */
	public BufferedReader read(String name) throws FileNotFoundException {
		if(!loaded)
			try {
				loadLoader();
			} catch (IOException e) {
				e.printStackTrace();
			}
		if(openFilesRead.containsKey(name)) {
			return new BufferedReader(openFilesRead.get(name));
		}
		else {
			String filename = files.get(name);
			if(filename == null) throw new FileNotFoundException(filename);
			BufferedReader newReader = new BufferedReader(new FileReader(filename));
			openFilesRead.put(name, newReader);
			return newReader;
		}
	}

	/**
	 * 
	 * @param name the name of the file to write
	 * @return a BufferedWriter for name
	 * @throws IOException if file is not found
	 */
	public BufferedWriter write(String name) throws IOException {
		if(!loaded)
			try {
				loadLoader();
			} catch (IOException e) {
				e.printStackTrace();
			}
		if(openFilesWrite.containsKey(name)) {
			return new BufferedWriter(openFilesWrite.get(name));
		}
		else {
			String filename = files.get(name);
			if(filename == null) throw new FileNotFoundException(filename);
			BufferedWriter newWriter = new BufferedWriter(new FileWriter(filename));
			openFilesWrite.put(name, newWriter);
			return newWriter;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onEvent(Event e) {
		return;
	}
}
