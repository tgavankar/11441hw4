import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class LogisticRegressionRunner {
	public static void main(String[] args) {
		// Load config
		DataParser dp = new DataParser("data.txt");
		
		// Read training data
		List<AbstractEntry> data = dp.parseTrain();		
		
		List<LogisticRegression> lrl = new ArrayList<LogisticRegression>();
		
		long startTime = System.currentTimeMillis();
		
		for(int i=1; i<2; i++) {
			LogisticRegression lr = new LogisticRegression(i, 0.01, Double.parseDouble(dp.config.get("c")), 0.0001, dp.maxTermSize);
		
			// Comment/uncomment the appropriate training method desired
			lr.train(data);
			//lr = loadFromDisk("w" + i);
		
			lrl.add(lr);
		}
		
		long endTime = System.currentTimeMillis();
		
		// Uncomment this if you want to save the learned classifiers to disk
		//flushToDisk(lr, "w");
	
		// Training data is no longer required, replace it with parsed test data
		data = dp.parseTest();
		
		System.out.println(endTime - startTime);
		
		// Classify test data
		for(int i=0; i<data.size(); i++) {
			AbstractEntry entry = data.get(i);
			
			// We want to find the maximum sigmoid value and its label
			double max = Double.NEGATIVE_INFINITY;
			int maxLabel = 0;
			
			double pred = lrl.get(0).classify(entry.getList());
			
			// Output predicted label
			System.out.println(pred);			
		}
		
	}
	
	/**
	 * Writes the LogisticRegression object to file of given name
	 * @param e LogisticRegression object
	 * @param name name of output file (without filetype)
	 */
	@SuppressWarnings("unused")
	private static void flushToDisk(LogisticRegression e, String name) {
		try {
			FileOutputStream fileOut = new FileOutputStream(name + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(e);
			out.close();
			fileOut.close();
		} 
		catch(IOException i) {
			i.printStackTrace();
		}
	}
	
	/**
	 * Reads the LogisticRegression object from disk from given file
	 * @param name filename without extension
	 * @return LogisticRegression object from disk, or null if Exception
	 */
	@SuppressWarnings("unused")
	private static LogisticRegression loadFromDisk(String name) {
		LogisticRegression lr = null;  
		try {
	        FileInputStream fileIn = new FileInputStream(name + ".ser");
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        lr = (LogisticRegression) in.readObject();
	        in.close();
	        fileIn.close();
	    } 
	    catch(Exception i) {
	        i.printStackTrace();
	    }
		return lr;
	}
}
