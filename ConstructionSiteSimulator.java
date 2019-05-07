import java.util.Scanner;


public class ConstructionSiteSimulator {
    private static CommandExecutor commandExecutor;
    public static void main(String args[])
    {
        try {
            FileUtils fu=new FileUtils();
            char[][] fieldmap=fu.readFile(args [0]);
            commandExecutor=new CommandExecutor(fieldmap);
            startStimulator();
        }
        catch(Exception e)
        {
            System.out.println("Enter valid file!!");
        }
    }

    /**
     * 
     */
    public static void startStimulator()
    {
        System.out.println();
        System.out.print("(l)eft, (r)ight, (a)dvance <n>, (q)uit:");
        Scanner sc=new Scanner(System.in);
        String input=sc.nextLine();
        commandExecutor.executeCommand(input);
    }

}
