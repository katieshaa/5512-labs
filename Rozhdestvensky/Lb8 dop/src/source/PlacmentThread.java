package source;


public class PlacmentThread extends Thread{
    private long solve;
    private int n;
    private int start;
    private int end;

    public PlacmentThread(long solve_, int n_, int start_, int end_){
        solve = solve_;
        n = n_;
        start = start_;
        end = end_;
    }

    public long getSolve(){
        return solve;
    }


    @Override
    public void run() {

        for(int i = start; i < end; i++) {
            solve *= n;
        }
    }
}
