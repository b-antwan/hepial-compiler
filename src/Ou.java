public class Ou extends Relation {
    /**
     * Constructor
     */
    public Ou(String fl, int line, int col) {
        super(fl, line, col);
    }

    /**
     * Get the binary operator
     */
    public String operateur() {
        return "|";
    }

    @Override
    public String toString(){
        return "Or: |";
    }

    public int apply(int left, int right){
        return left | right;
    }

    public boolean apply(boolean left, boolean right){
        return left | right;
    }

    /**
     * Accepts a AST visitor
     */
    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }
}
