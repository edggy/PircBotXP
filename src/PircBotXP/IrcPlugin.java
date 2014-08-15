package PircBotXP;
import net.xeoh.plugins.base.Plugin;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.Event;



/**
 * 
 */

/**
 * @author Ethan
 *
 */
public interface IrcPlugin extends Plugin {
    public void load(PircBotX bot);
    
    public void unload();
    
    public void start();
    
    public void stop();
    
    public String status();
    
    @SuppressWarnings("rawtypes")
	public void onEvent(Event event) throws InactiveException;
}
