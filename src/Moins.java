public class Moins extends Unary{
    
    public Moins(String fl, int line, int col) {
        super(fl, line, col);
    }

    public String operator(){
        return "-";
    }

    @Override
    public String toString(){
        return "Moins: - \n";
    }

    
    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }
}
