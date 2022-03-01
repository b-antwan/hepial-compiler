import java.util.*;

public class TantQue extends Instruction {

    protected Expression expr;

    protected ArrayList<Instruction> instr;

    /**
     * Constructor
     */
    public TantQue(Expression expr, ArrayList<Instruction> instr, String fl, int line, int col){
        super(fl, line ,col);
        this.expr = expr;
        this.instr = instr;
    }

    public Expression getExpr() {
        return this.expr;
    }

    public List<Instruction> getInstr(){
        return new ArrayList<>(this.instr);
    }


    public void lierExpr(Expression expr) {
        this.expr = expr;
    }

    public void lierLstInstr(ArrayList<Instruction> lst){
        this.instr = lst;
    }

    @Override
    public String toString(){
        String val =  "Tant que: " + this.expr.toString() + ":\n";
        for(Instruction i: this.instr){
            val += "\t"+ i.toString() + "\n";
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
