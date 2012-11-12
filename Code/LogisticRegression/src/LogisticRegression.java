import java.io.Serializable;
import java.util.List;


/**
 * Binary logistic regression classifier that classifies boolean for a given
 * label. Performs regularization with the given C value and uses the given learn
 * rate. Stops training (converges) on given convergence distance. 
 */
public class LogisticRegression implements Serializable {
	private static final long serialVersionUID = -7920030923981484995L;
	private int label;
	private SparseTermList W;
	private double learnRate;
	private double C;
	private double converges;
	
	/**
	 * Constructor sets given values and initializes weight vector to all
	 * 0's.
	 * 
	 * @param label desired label to bool classify on
	 * @param learnRate learning rate of gradient descent
	 * @param C regularization term weight
	 * @param converges convergence delta
	 */
	public LogisticRegression(int label, double learnRate, double C, double converges, int termListSize) {
		this.label = label;
		this.W = new SparseTermList(termListSize);
		this.learnRate = learnRate;
		this.C = C;
		this.converges = converges;
	}
	
	/**
	 * Trains this classifier on given data using gradient descent and
	 * regularization.
	 * 
	 * @param data training data
	 */
	public void train(List<AbstractEntry> data) {
		double dist = Double.MAX_VALUE;
				
		// Save old weight vector for delta calculation
		SparseTermList prevW = W.deepCopy();
		do {
			for(int i=0; i<data.size(); i++) {
				AbstractEntry entry = data.get(i);
				SparseTermList X = entry.getList();
				// Get boolean value from desired label
				double y = (entry.getActualLabel() == this.label) ? 1 : 0;
				// Optimization, calculate sigmoid once
				double hx = sigmoid(W, X);
				
				for(int j=0; j<W.length; j++) {
					W.put(j, W.get(j) + this.learnRate * (y - hx) * X.get(j) - this.learnRate * (C * W.get(j)));
				}
			}
			// Get distance between previous and current weight vectors
			dist = dist(prevW, W);
			// Resave old weight vector
			prevW = W.deepCopy();
		} while(dist > this.converges);
	}
	
	private double sigmoid(SparseTermList W, SparseTermList X) {
		return 1.0 / (1.0 + Math.exp(-1.0 * dotp(W, X)));
	}
	
	/**
	 * Perform vector dot product with short circuiting on X[i]==0,
	 * since X is usually sparse. Input vectors must have same dimension.
	 * 
	 * @param W weight vector
	 * @param X vector
	 * @return dot product value
	 */
	private double dotp(SparseTermList W, SparseTermList X) {
		double out = 0;
		for(int i=0; i<W.length; i++) {
			if(X.get(i) == 0) {
				continue;
			}
			out += W.get(i) * X.get(i);
		}
		return out;
	}
	
	/**
	 * Euclidean distance between two vectors. Both vectors must be the 
	 * same dimension.
	 * 
	 * @param W1 vector
	 * @param W2 vector
	 * @return
	 */
	private double dist(SparseTermList W1, SparseTermList W2) {
		double out = 0.0;
		for(int i=0; i<W1.length; i++) {
			out += Math.pow(W1.get(i) - W2.get(i), 2);
		}
		return Math.sqrt(out);
	}
	
	private double norm(SparseTermList W) {
		double out = 0.0;
		for(int i=0; i<W.length; i++) {
			out += Math.pow(W.get(i), 2);
		}
		
		return Math.sqrt(out);
	}
	
	public SparseTermList getW() {
		return W;
	}
	
	public void setW(SparseTermList w) {
		this.W = w;
	}
	
	/**
	 * Classification function returns the value of sigmoid(W, X)
	 * rather than a boolean (as the classifier is for). This allows for
	 * confidence comparison between different binary classifiers. 
	 * 
	 * @param X test data vector
	 * @return double
	 */
	public double classify(SparseTermList X) {
		return sigmoid(this.W, X);
	}
}
