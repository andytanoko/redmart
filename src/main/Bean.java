package main;

public class Bean {
    private int val = 0;
    private boolean visited = false;
    private boolean visitable = false;

    public boolean isVisitable() {
	return visitable;
    }

    public void setVisitable(boolean visitable) {
	this.visitable = visitable;
    }

    public int getVal() {
	return val;
    }

    public void setVal(int val) {
	this.val = val;
    }

    public boolean isVisited() {
	return visited;
    }

    public void setVisited(boolean visited) {
	this.visited = visited;
    }

    public String toString() {
	return val + "," + visited;
    }

}
