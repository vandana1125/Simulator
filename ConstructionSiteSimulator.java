import java.util.Scanner;


/**
 * Simulator main() class
 */
public class ConstructionSiteSimulator {
    private static CommandExecutor commandExecutor;
    public static void main(String[] args)
    {
        try {
            FileUtils fu=new FileUtils();
            char[][] fieldmap=fu.readFile(args [0]);
            if(fieldmap==null)
                return;
            commandExecutor=new CommandExecutor(fieldmap);
            startStimulator();
        }
        catch(Exception e)
        {
            System.out.println("Enter valid file!!");
        }
    }

    /**
     * Provides command line options for input and reads the input command.
     */
     static void startStimulator()
    {
        System.out.println();
        System.out.print("(l)eft, (r)ight, (a)dvance <n>, (q)uit:");
        Scanner sc=new Scanner(System.in);
        String input=sc.nextLine();
        commandExecutor.executeCommand(input);
    }

}
