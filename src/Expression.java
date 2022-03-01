/*
 * Base class that represent an expression node inside the AST.
*/

public abstract class Expression extends ASTNode {

    /**
     * Constructor
     */
    public Expression(String fl, int line, int col) {
        super(fl, line, col);
    }
}
