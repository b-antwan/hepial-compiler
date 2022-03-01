/*
 * Represent an equal comparaison expression node inside the AST.
*/

public class Different extends Relation {
    /**
     * Constructor
     */
    public Different(String fl, int line, int col) {
        super(fl, line, col);
    }

    /**
     * Get the binary operator
     */
    public String operateur() {
        return "!=";
    }


    public int apply(int left, int right){
        return left != right ? 1 : 0; 
    }

    public boolean apply(boolean left, boolean right){
        return left != right;
    }

    /**
     * Accepts a AST visitor
     */
    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }
}
