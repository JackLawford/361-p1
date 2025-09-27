package fa.dfa;

import fa.State;

import java.util.*;

public class DFA implements DFAInterface {
    private final LinkedHashSet<DFAState> states = new LinkedHashSet<>();
    private final LinkedHashSet<Character> sigma = new LinkedHashSet<>();
    private final LinkedHashSet<DFAState> finals = new LinkedHashSet<>();

    private final Map<String, DFAState> byName = new LinkedHashMap<>();

    private DFAState start;

    @Override
    public boolean addState(String name) {
        if (name == null || byName.containsKey(name)) return false;
        DFAState s = new DFAState(name);
        states.add(s);
        byName.put(name, s);
        return true;
    }

    @Override
    public boolean setFinal(String name) {
        DFAState s = getDFAState(name);
        if (s == null) return false;
        finals.add(s);
        return true;
    }

    @Override
    public boolean setStart(String name) {
        DFAState s = getDFAState(name);
        if (s == null) return false;
        start = s;
        return true;
    }

    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);
    }

    @Override
    public boolean accepts(String s) {
        if (start == null) return false;
        DFAState cur = start;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!sigma.contains(c)) return false; // symbol not in alphabet
            DFAState nxt = cur.next(c);
            if (nxt == null) return false; // missing transition
            cur = nxt;
        }
        return finals.contains(cur);
    }

    @Override
    public Set<Character> getSigma() {
        return new LinkedHashSet<>(sigma);
    }

    @Override
    public State getState(String name) {
        return getDFAState(name);
    }

    @Override
    public boolean isFinal(String name) {
        DFAState s = getDFAState(name);
        return s != null && finals.contains(s);
    }

    @Override
    public boolean isStart(String name) {
        DFAState s = getDFAState(name);
        return s != null && s == start;
    }

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        if (!sigma.contains(onSymb)) return false;
        DFAState from = getDFAState(fromState);
        DFAState to = getDFAState(toState);
        if (from == null || to == null) return false;
        from.putTransition(onSymb, to);
        return true;
    }

    @Override
    public DFA swap(char symb1, char symb2) {
        DFA d = new DFA();

        for (char c : this.sigma) d.addSigma(c);

        for (DFAState s : this.states) d.addState(s.getName());

        if (this.start != null) d.setStart(this.start.getName());
        for (DFAState f : this.finals) d.setFinal(f.getName());

        for (DFAState s : this.states) {
            for (char c : this.sigma) {
                DFAState tgt = s.next(c);
                if (tgt == null) continue;
                char out = (c == symb1) ? symb2 : (c == symb2) ? symb1 : c;
                d.addTransition(s.getName(), tgt.getName(), out);
            }
        }
        return d;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Q
        sb.append("Q = {");
        for (DFAState s : states) sb.append(' ').append(s.getName());
        if (!states.isEmpty()) sb.append(' ');
        sb.append("}\n");

        // Sigma
        sb.append("Sigma = {");
        for (char c : sigma) sb.append(' ').append(c);
        if (!sigma.isEmpty()) sb.append(' ');
        sb.append("}\n");

        // delta table
        sb.append("delta =\n");
        sb.append("\t");
        for (char c : sigma) sb.append(c).append('\t');
        sb.append("\n");
        for (DFAState s : states) {
            sb.append(s.getName()).append('\t');
            for (char c : sigma) {
                DFAState t = s.next(c);
                // prints hyphen if cell is empty
                sb.append(t == null ? "-" : t.getName()).append('\t');
            }
            sb.append('\n');
        }

        // q0
        sb.append("q0 = ").append(start == null ? "-" : start.getName()).append('\n');

        // F
        sb.append("F = {");
        for (DFAState f : finals) sb.append(' ').append(f.getName());
        if (!finals.isEmpty()) sb.append(' ');
        sb.append("}");

        return sb.toString();
    }

    private DFAState getDFAState(String name) {
        return byName.get(name);
    }
}
