import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DataParser {
	public Map<String, String> config;
	public int maxTermSize = 0;
	
	/**
	 * Reads parameters from file (confFile) into config map,
	 * and initializes for parsing data.
	 * @param confFile path to config file
	 */
	public DataParser(String confFile) {
		BufferedReader br;
		config = new HashMap<String, String>();
		try {
			br = new BufferedReader(new FileReader(confFile));
			String line = null;  
			while ((line = br.readLine()) != null) {    
			   String[] s = line.split("=");
			   config.put(s[0], s[1]);
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	/**
	 * Parses the data of the given type (train/test). Filepath is
	 * read from config.
	 * @param type key in config to get file path
	 * @return list of parsed entries
	 */
	private List<AbstractEntry> parse(String type,  boolean normalize) {
		int termListSize = getNumTerms(type);
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(config.get(type)));
			String line = null;  
			int id = 1;
			List<AbstractEntry> out = new ArrayList<AbstractEntry>();
			while ((line = br.readLine()) != null) {    
			   String[] split = line.split(" ");
			   AbstractEntry ae = new AbstractEntry();
			   SparseTermList ste = new SparseTermList(termListSize);
			   ste.put(0, 1); // Initialize [0] to 1
			   for(int i=2; i<split.length; i++) {
				   if(split[i].charAt(0) == '#') {
					   break;
				   }
				   String[] splitEntry = split[i].split(":");
				   ste.put(Integer.parseInt(splitEntry[0]), Double.parseDouble(splitEntry[1]));
			   }
			   
			   if(normalize) {
				   ste.normalize();
			   }
			   ae.setId(id);
			   ae.setActualLabel(Integer.parseInt(split[0]));
			   ae.setList(ste);
			   out.add(ae);
			   id++;
			}
			br.close();
			return out;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private int getNumTerms(String type) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(config.get(type)));
			String line = null;  
			int max = 0;
			while ((line = br.readLine()) != null) {    
			   String[] split = line.split(" ");
			   
			   String maxForLine = null;
			   for(int i=2; i<split.length; i++) {
				   if(split[i].charAt(0) == '#') {
					   break;
				   }
				   String[] splitEntry = split[i].split(":");
				   maxForLine = splitEntry[0];
			   }
			   int maxLineTerms = Integer.parseInt(maxForLine);
			   if(maxLineTerms > max) {
				   max = maxLineTerms;
			   }
			}
			br.close();
			max++; // For [0]
			this.maxTermSize = max;
			return max;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Public method for parsing training data.
	 * @return list of data points
	 */
	public List<AbstractEntry> parseTrain() {
		return parseTrain(true);
	}
	
	public List<AbstractEntry> parseTrain(boolean normalize) {
		return parse("train", normalize);
	}
	
	/**
	 * Public method for parsing test data.
	 * @return list of data points
	 */
	public List<AbstractEntry> parseTest() {
		return parseTest(true);
	}
	
	public List<AbstractEntry> parseTest(boolean normalize) {
		return parse("test", normalize);
	}
}
