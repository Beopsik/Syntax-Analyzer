package lexical_analyzer.dfa;

//Class that stores information about state
public class State {
    //variable that stores whether the state is final or not
    private boolean isFinalState;

    //variable that checks if the state can proceed to the next state
    private boolean isRunningState;

    //variable that stores which state it is
    private int stateLocation;

    public State() {
        this.isFinalState = false;
        this.isRunningState = true;
        stateLocation = 0;
    }

    public void setIsFinalState(boolean isFinalState) {
        this.isFinalState = isFinalState;
    }

    public boolean getIsFinalState() {
        return isFinalState;
    }

    public void setisRunningState(boolean isRunningState) {
        this.isRunningState = isRunningState;
    }

    public boolean getisRunningState() {
        return isRunningState;
    }

    public void setStateLocation(int stateLocation) {
        this.stateLocation = stateLocation;
    }

    public int getStateLocation() {
        return stateLocation;
    }

    //Initialize to start state
    public void clear() {
        this.isFinalState = false;
        this.isRunningState = true;
        stateLocation = 0;
    }
}
