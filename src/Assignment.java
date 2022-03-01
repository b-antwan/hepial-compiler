/*
 * Represent an assignment node inside the AST.
 */

public class Assignment extends Instruction {
    /**
     * The source operand (at its right)
     */
    protected Expression source;
    /**
     * The destination variable or array (at its left)
     */
    protected Idf destination;

    /**
     * Constructor
     */
    public Assignment(Idf dest, Expression src, String fl, int line, int col) {
        super(fl, line, col);
        this.lierSource(src);
        this.lierDestination(dest);
    }

    /**
     * Get the source operand (at its right)
     */
    public Expression getSource() {
        return this.source;
    }
    /**
     * Get the destination variable or array (at its left)
     */
    public Idf getDestination() {
        return this.destination;
    }

    /**
     * Set the source operand (at its right)
     */
    public void lierSource(Expression exp) {
        this.source = exp;
    }
    /**
     * Set the destination variable or array (at its left)
     */
    public void lierDestination(Idf exp) {
        this.destination = exp;
    }


    @Override
    public String toString(){
        return destination.toString() + " = " +  source.toString();
    }
    
    /**
     * Accepts a AST visitor
     */
    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }
}
