/*
 * Represent an addition expression node inside the AST.
*/

public class Addition extends Arithmetique {
    /**
     * Constructor
     */
    public Addition(String fl, int line, int col) {
        super(fl, line, col);
    }

    /**
     * Get the binary operator
     */
    public String operateur() {
        return "+";
    }

    @Override
    public String toString(){
        return "Addition: ";
    }
    
    public int apply(int left, int right){
        return left + right;
    }

    public boolean apply(boolean left, boolean right){
        return false;
    }

    /**
     * Accepts a AST visitor
     */
    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }
}
