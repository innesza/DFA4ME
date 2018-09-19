import java.util.Scanner;
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
    Transition[] transitions;
    public State(int n) { //n is size of alphabet
        transitions = new Transition[n];
    }
}

class DFA {
    String[] alphabet;
    State[] states;
    public DFA(int n, String[] a) { //n is number of states, a is the alphabet
        alphabet = a;
        states = new State[n];
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
            String[] tempAlpha = s.nextLine().split(","); //temporarily holds the alphabet for the DFA
            int nStates = s.nextInt();
            s.nextLine(); //Stops java from getting confused
            dfa = new DFA(nStates, tempAlpha); //Creates the dfa
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}