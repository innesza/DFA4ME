import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;

class Transition {
    String symbol;
    State tranState;
    public Transition (String s, State t){
        symbol = s;
        tranState = t;
    }
}


class State {
    boolean isAccepting;
    ArrayList<Transition> transitions;
    String name;

    public State(String _name){
        name = _name;
    }
}

class DFA {
    String[] alphabet, finalNames, stateNames;
    String startName;
    State start, current;
    ArrayList<State> states;
    public DFA(String[] a, String[] allStates, String startState, String[] finalStates) { //n is number of states, a is the alphabet
        alphabet = a;
        states = new ArrayList<State>();
        stateNames = allStates;
        startName = startState;
        finalNames = finalStates;
        //Hold all states before transitions are set up
        for(int i = 0; i < allStates.length; i++){
            states.add(new State(allStates[i]));
            //Check if state is a final state when adding it
            for(int j = 0; j < finalStates.length; j++){
                if(states.get(i).name.equals(finalStates[j]))
                    states.get(i).isAccepting = true;
            }
        //Setting the start state
        if(states.get(i).name.equals(startName))
            start = states.get(i);
        }
    }

    public void SetTransition(String s){
        String[] parsed = s.split(":", 3);
        State transStart = null, transEnd = null;
        for(int k = 0; k < states.size()-1; k++){
            if(states.get(k).equals(parsed[0]))
                transStart = states.get(k);
            if(states.get(k).equals(parsed[2]))
                transEnd = states.get(k);
        }
        transStart.transitions.add(new Transition(parsed[1], transEnd));
    }

    public boolean TestString () {
        return false;
    }
}


public class Main {
    public static void main (String[] args){
        File file = new File(args[0]);
        DFA dfa;
        try {
            FileInputStream in = new FileInputStream(file);
            Scanner s = new Scanner(in);
            String[] alphabet = s.nextLine().split(","); //Holds the alphabet for the DFA
            String[] stateNames = s.nextLine().split(","); //Holds names of states
            String startState = s.nextLine(); //Holds the name of the start state
            String[] finalStates = s.nextLine().split(","); //Holds the names of the final states
            dfa = new DFA(alphabet, stateNames,startState, finalStates);
            s.close();
            in.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}