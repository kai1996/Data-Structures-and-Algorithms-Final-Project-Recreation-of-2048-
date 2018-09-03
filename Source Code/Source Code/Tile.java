// B. Ghimire, K. Pandey
public class Tile {
	
	protected int value;
	protected boolean hasCombined; 
	
	// constructors
	public Tile(int val, boolean com){
		value = val;
		hasCombined = com;
	}
	
	public Tile(int val){
		new Tile(val, false);
	}
	
	public Tile(){
		new Tile(2);
	}
	
	// methods
	public int getVal(){
	// get tile value
		return value;
	}
	
	public void setAttr(int val, boolean com){
	// set tile attributes
		value = val;
		hasCombined = com;
	}
	
	public boolean canCombine(){
	// check if a tile can combine
		return !hasCombined;
	}
	
	public String toString(){
		String tile = " " + value + " ";
		return tile;
	}
	
}
