/*
 * Represent an Inf-eq comparaison expression node inside the AST.
*/

public class InfEgal extends Relation {
    /**
     * Constructor
     */
    public InfEgal(String fl, int line, int col) {
        super(fl, line, col);
    }

    /**
     * Get the binary operator
     */
    public String operateur() {
        return "<=";
    }

    @Override
    public String toString(){
        return "<=";
    }

    public int apply(int left, int right){
        return left <= right ? 1 : 0;
    }

    public boolean apply(boolean left, boolean right){
        return (left ? 1 : 0) <= (right ? 1 : 0);
    }

    /**
     * Accepts a AST visitor
     */
    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }
}
