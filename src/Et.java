/*
 * Represent an equal comparaison expression node inside the AST.
*/

public class Et extends Relation {
    /**
     * Constructor
     */
    public Et(String fl, int line, int col) {
        super(fl, line, col);
    }

    /**
     * Get the binary operator
     */
    public String operateur() {
        return "et";
    }

    @Override
    public String toString(){
        return this.operateur();
    }

    public int apply(int left, int right){
        return left & right;
    }

    public boolean apply(boolean left, boolean right){
        return left && right;
    }

    /**
     * Accepts a AST visitor
     */
    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }
}
