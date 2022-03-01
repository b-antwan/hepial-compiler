public class Non extends Unary{
    
    public Non(String fl, int line, int col) {
        super(fl, line, col);
    }

    public String operator(){
        return "!";
    }

    @Override
    public String toString(){
        return "Non: ! \n";
    }

    
    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }
}
