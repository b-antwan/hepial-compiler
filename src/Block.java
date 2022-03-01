import java.util.ArrayList;

public class Block extends Instruction {

    /**
     * The list of instructions
     */
    private ArrayList<Instruction> instructions = null;

    /**
     * Constructor with provided list
     */
    public Block(ArrayList<Instruction> instructions, String fl, int line, int col) {
        super(fl, line, col);
        this.setInstructions(instructions);
    }
    /**
     * Empty constructor
     */
    public Block(String fl, int line, int col) {
        this(new ArrayList<Instruction>(), fl, line, col);
    }

    /**
     * Get the list of instructions
     */
    public ArrayList<Instruction> getInstructions() {
        return this.instructions;
    }

    /**
     * Set the list of instructions
     */
    public void setInstructions(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
    }
    /**
     * Add an instruction to this Block
     */
    public void addInstruction(Instruction instr) {
        this.instructions.add(instr);
    }

    /**
     * Get the size of instructions
     */
    public int size() {
        return this.instructions.size();
    }

    /**
     * Accepts a AST visitor
     */
    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }
}
