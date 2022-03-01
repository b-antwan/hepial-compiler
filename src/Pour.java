import java.util.*;

public class Pour extends Instruction {

    protected Idf idf;

    protected Expression borneInf;

    protected Expression borneSup;

    protected ArrayList<Instruction> instr;

    /**
     * Constructor
     */
    public Pour(Idf idf, Expression borneInf, Expression borneSup, ArrayList<Instruction> instr, String fl, int line, int col){
        super(fl, line ,col);
        this.idf = idf;
        this.borneInf = borneInf;
        this.borneSup = borneSup;
        this.instr = instr;
    }

    public Idf getIdf() {
        return this.idf;
    }

    public Expression getBorneInf() {
        return this.borneInf;
    }

    public Expression getBorneSup() {
        return this.borneSup;
    }

    public ArrayList<Instruction> getInstr(){
        return this.instr;
    } 



    public void lierIdf(Idf idf) {
        this.idf = idf;
    }

    public void lierBorneSup(Expression borneSup) {
        this.borneSup = borneSup;
    }

    public void lierBorneInf(Expression borneInf) {
        this.borneInf = borneInf;
    }

    public void lierLstInstr(ArrayList<Instruction> lst){
        this.instr = lst;
    }


    @Override
    public String toString(){
        String val ="For " + this.idf + " from " + this.borneInf.toString() + " to " + this.borneSup.toString() + ": \n";
        for(Instruction i: this.instr){
            val += "\t" + i.toString() + "\n;";
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
