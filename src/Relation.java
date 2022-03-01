/*
 * Base class that represent a binary relational expression node inside the AST.
 */

public abstract class Relation extends Binary {
    /**
     * Constructor
     */
    public Relation(String fl, int line, int col) {
        super(fl, line, col);
    }

    public String toString(){
        return "Relation: ";
    }
    
}
