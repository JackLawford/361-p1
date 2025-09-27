package fa.dfa;

import fa.State;
import java.util.HashMap;
import java.util.Map;

public class DFAState extends State {
    private final Map<Character, DFAState> row = new HashMap<>();

    public DFAState(String name) {
        super(name);
    }

    public void putTransition(char c, DFAState to) {
        row.put(c, to);
    }

    public DFAState next(char c) {
        return row.get(c);
    }
}
