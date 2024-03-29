import java.io.*;
import java.util.*;
/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

public class NameSurferDataBase implements NameSurferConstants {
	
/* Constructor: NameSurferDataBase(filename) */
/**
 * Creates a new NameSurferDataBase and initializes it using the
 * data in the specified file.  The constructor throws an error
 * exception if the requested file does not exist or if an error
 * occurs as the file is being read.
 */
	
	private Map<String, NameSurferEntry> _database;

    public NameSurferDataBase(String filename) 
    {
        _database = new HashMap<>();
        loadData(filename);
    }

    private void loadData(String filename) 
    {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) 
        {
            String line;
            	while ((line = br.readLine()) != null) 
            	{
                NameSurferEntry entry = new NameSurferEntry(line);
                _database.put(entry.getName().toLowerCase(), entry);
            	}
        } 
        catch (Exception e) 
        {
            e.fillInStackTrace();
        }
    }
	
/* Method: findEntry(name) */
/**
 * Returns the NameSurferEntry associated with this name, if one
 * exists.  If the name does not appear in the database, this
 * method returns null.
 */
	public NameSurferEntry findEntry(String name) {
		
		 NameSurferEntry entry = _database.get(name.toLowerCase());
	     if(entry!=null)
	     {
	    	 return entry;
	     }
		 return null;
	}
}

