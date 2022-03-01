/*
 * Base class that represent an instruction node inside the AST.
*/

public abstract class Instruction extends ASTNode {

    /**
     * Constructor
     */
    public Instruction(String fl, int line, int col) {
        super(fl, line, col);
    }

    @Override
    public String toString(){
        return "Instruction: ";
    }
}
