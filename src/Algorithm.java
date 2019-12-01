import java.util.ArrayList;

public abstract class Algorithm {
    private Problem problem;
    Problem getMonProblem(){
        return problem;
    }
    void setMonProblem(Problem pb){
        this.problem = pb;
    }
    abstract ArrayList<Integer> executer();
}
