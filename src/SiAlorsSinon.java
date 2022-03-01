import java.util.*;

public class SiAlorsSinon extends Instruction {

    protected Expression expr;

    protected ArrayList<Instruction> instr;

    protected ArrayList<Instruction> instr2;

    /**
     * Constructor
     */
    public SiAlorsSinon(Expression expr, ArrayList<Instruction> instr, ArrayList<Instruction> instr2, String fl, int line, int col){
        super(fl, line ,col);
        this.expr = expr;
        this.instr = instr;
        this.instr2 = instr2;

    }

    public Expression getExpr() {
        return this.expr;
    }

    public List<Instruction> getInstr(){
        return new ArrayList<>(this.instr);
    }

    public List<Instruction> getInstr2(){
        return new ArrayList<>(this.instr2);
    }


    public void lierExpr(Expression expr) {
        this.expr = expr;
    }

    public void lierLstInstr(ArrayList<Instruction> lst){
        this.instr = lst;
    }

    public void lierLstInstr2(ArrayList<Instruction> lst){
        this.instr2 = lst;
    }



    @Override
    public String toString(){
        String val = "If " + expr.toString() + " Then: \n";

        for(Instruction i: this.instr){
            val += "\t" + i.toString() + ";\n";
        }

        val += "Else:\n";

        for(Instruction i: this.instr2){
            val += "\t" + i.toString() + ";\n";
        }
        return val;
    }


    /**
     * Accepts a AST visitor
     */
    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }
}
