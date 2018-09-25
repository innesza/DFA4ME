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
        transitions = new ArrayList<Transition>();
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

    //Sets a transition
    //Input: Transitional statement of the form state1:symbol:state2
    //Output: N/A
    public void SetTransition(String s){
        String[] parsed = s.split(":", 3);
        State transStart = null, transEnd = null;
        for(int k = 0; k < states.size(); k++){
            if(states.get(k).name.equals(parsed[0]))
                transStart = states.get(k);
            if(states.get(k).name.equals(parsed[2]))
                transEnd = states.get(k);
        }
        transStart.transitions.add(new Transition(parsed[1], transEnd));
    }

    //Tests a string to see if it is accepted by the dfa
    //Input: String to be tested and the current state the dfa is in
    //Output: True if the string is accepted, false otherwise
    public boolean TestString (String s, State current) {
        if(s.length() == 0) {
            return current.isAccepting;
        } else {
            int l;
            for (l = 0; l < current.transitions.size(); l++){
                if(s.startsWith(current.transitions.get(l).symbol))
                    break;
            }
            return TestString(s.substring(1), current.transitions.get(l).tranState);
        }
    }

    //Makes a regular expression from a dfa; NOTE: This will destroy the dfa in the process!
    //Input: End state of the dfa
    //Output: A String Regex which is the regex for strings from the start to the given end state
    public String MakeOneRegEx (State end) {
        return null;
    }
}


public class Main {
    public static void main (String[] args){
        File file = new File(args[0]);
        DFA dfa, regexDFA;
        try {
            FileInputStream in = new FileInputStream(file);
            Scanner s = new Scanner(in);
            String[] alphabet = s.nextLine().split(","); //Holds the alphabet for the DFA
            String[] stateNames = s.nextLine().split(","); //Holds names of states
            String startState = s.nextLine(); //Holds the name of the start state
            String[] finalStates = s.nextLine().split(","); //Holds the names of the final states
            dfa = new DFA(alphabet, stateNames, startState, finalStates);
            //Setting all transitions
            while(s.hasNextLine())
                dfa.SetTransition(s.nextLine());
            s.close();
            in.close();
            file = new File (args[1]);
            in = new FileInputStream(file);
            s = new Scanner(in);
            //Testing each string
            while(s.hasNextLine()){
                String temp = s.nextLine();
                if(dfa.TestString(temp, dfa.start))
                    System.out.println(temp + ": Accepted");
                else
                    System.out.println(temp + ": Declined");
            }
            //Finding the regex for the given dfa
            /*for(int i = 0; i < dfa.states.size(); i++){
                regexDFA = dfa;
            }*/
            s.close();
            in.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}