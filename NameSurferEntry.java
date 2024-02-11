import java.awt.Color;

/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

public class NameSurferEntry implements NameSurferConstants {

/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
	private Color _color;
	private String name;
    private int[] ranks;
	public NameSurferEntry(String line) 
	{
		String[] parts = line.split(" ");
        name = parts[0].toLowerCase();
        ranks = new int[NDECADES];
        
        for (int i = 0; i < NDECADES; i++) 
        {
            ranks[i] = Integer.parseInt(parts[i + 1]);
        }
	}

/* Method: getName() */
/**
 * Returns the name associated with this entry.
 */
	public String getName() 
	{
		return name;
	}

/* Method: getRank(decade) */
/**
 * Returns the rank associated with an entry for a particular
 * decade.  The decade value is an integer indicating how many
 * decades have passed since the first year in the database,
 * which is given by the constant START_DECADE.  If a name does
 * not appear in a decade, the rank value is 0.
 */
	public int getRank(int decade) {
		int index = (decade - START_DECADE)/10;
        if (index >= 0 && index < NDECADES) {
            return ranks[index];
        }
        return -1; // Return -1 if decade is out of range
	}

/* Method: toString() */
/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
	public String toString() {
		
		String dataLine=name+" ";
		dataLine+="[";
		for(int i=0;i<ranks.length;i++)
		{
			dataLine+=ranks[i];
			if(i!=ranks.length-1) //writes spaces until the last rank
			{
			dataLine+=" ";
			}
		}
		dataLine+="]";
		return dataLine;
	}
	
	public Color getColor()
	{
		return _color;
	}
	public void setColor(Color color)
	{
		_color=color;
	}
}

