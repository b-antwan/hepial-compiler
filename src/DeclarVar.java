import java.util.*;

public class DeclarVar  extends Instruction{

    private ArrayList<Idf> idfs;

    public DeclarVar(ArrayList<Idf> idfs, String fl, int line, int col){
        super(fl, line, col);
        this.idfs = idfs;
    }

    public ArrayList<Idf> getIds(){
        return this.idfs;
    }

    public void lierIds(ArrayList<Idf> idfs){
        this.idfs = idfs;
    }

    @Override
    public String toString(){
        return "Declaration de Variable: ";
    }

    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }
}
