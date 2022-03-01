public class Lire extends Instruction{
    private Idf idf;

    public Lire(Idf idf, String fl, int line, int col){
        super(fl, line, col);
        this.idf = idf;
    }

    public Idf getIdf(){
        return this.idf;
    }

    public void lierIdf(Idf idf){
        this.idf = idf;
    }


    @Override
    public String toString(){
        return "Lire:";
    }

    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }
}
