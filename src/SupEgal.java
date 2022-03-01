/*
 * Represent an equal comparaison expression node inside the AST.
*/

public class SupEgal extends Relation {
    /**
     * Constructor
     */
    public SupEgal(String fl, int line, int col) {
        super(fl, line, col);
    }

    /**
     * Get the binary operator
     */
    public String operateur() {
        return ">=";
    }

    /**
     * Accepts a AST visitor
     */
    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }
}
