package feather.rs.example.hw.db;

import java.util.List;
import java.util.ArrayList;

/**
 * Database which stores hello world messages.
 * 
 * @author sheenobu
 *
 */
public class HelloWorldDatabase {

	/**
	 * Get the hello world message, given the specific language.
	 * 
	 * @param lang The two-letter language code (en, cn, fr).
	 * @return The hello world message as text.
	 */
    public String getHelloWorldMessage(String lang) {
        if(lang.equals("en"))
        {
            return "hello world";
        }
        else if (lang.equals("cn")){
            return "您好世界";
        }else if (lang.equals("fr")) {
            return "Bonjour tout le monde";
        }
        throw new NotFoundException();
        
    }

    /**
     * Get the list of supported languages, as two-letter language codes.
     * 
     * @return The list of supported languages.
     */
    public List<String> getSupportedLanguages() {
        List<String> l = new ArrayList<String>();
        l.add("en");
        l.add("cn");
        l.add("fr");
        l.add("xx");
        return l;
    }

}