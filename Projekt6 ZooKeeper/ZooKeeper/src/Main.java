import static java.lang.System.arraycopy;
import static java.lang.System.exit;


public class Main {

    public static void main(String[] args) {
        if(args.length < 2){
            System.out.println("Too low number of arguments");
            exit(-1);
        }
        String port = args[0];
        String[] task = new String[args.length-1];
        arraycopy(args,1,task,0,args.length-1);

        Executor executor = null;
        try{
            executor = new Executor(port,task);
        } catch(Exception e){
            e.printStackTrace();
        }
        Executor finalExecutor = executor;
        Thread t = new Thread(()->{
            if(finalExecutor != null)
                finalExecutor.comandPrompt();
        });
        t.start();
        executor.run();
    }
}
