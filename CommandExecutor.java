 class CommandExecutor {
    private char[][] fieldmap;      //input site map
    private char[][] tracker;       //to track cleared squares
    private int commandCount=0;     //number of commands
    private int fuelcost=0;         //cost for fuel
    private int paintPenalty=0;     //penalty for paint damage when crossing 't'
    private int protectedTreePenalty=0;  // penalty for crossing protected tree
    private int row=-1;             //current row position
    private int column=-1;          //current column position
    private int dir =1;             //current facing direction
    private int maxRow = 0;         //maximum row length
    private int maxColumn=0;        //maximum column length
    private int protectedTreeCount=0; // number of protected tree
    private StringBuffer commands=new StringBuffer();  // concatenated input command string
    /* dir :
     *   0 = north facing
     *   1 = east facing
     *   2 = west facing
     *   3 = south facing
     *   */
    public CommandExecutor(char[][] fieldmap)
    {
        this.fieldmap=fieldmap;
        this.tracker=new char[fieldmap.length][fieldmap[0].length];
        maxRow=fieldmap.length;
        maxColumn=fieldmap[0].length;
        countProtectedTree();
    }

    private void countProtectedTree() {
        for (char[] chars : fieldmap) {
            for (int j = 0; j < fieldmap[0].length; j++) {
                if (chars[j] == 'T') {
                    protectedTreeCount++;
                }
            }
        }
    }

     /**
      * @param input String input from the command line provided by user at runtime
      */
    public void executeCommand(String input)
    {
        if(input.equals("l"))
        {
            commandCount++;
            commands.append("turn left, ");
            switch(dir)
            {
                case 0: //north to west
                    dir=2;
                    break;
                case 1: // east to north
                    dir=0;
                    break;
                case 2: // west to south
                    dir=3;
                    break;
                case 3:  //south to east
                    dir=1;
                    break;
                default:
                    break;
            }
            ConstructionSiteSimulator.startStimulator();
        }
        else if(input.equals("r"))
        {
            commandCount++;
            commands.append("turn right, ");
            switch(dir) {
                case 0: //north to east
                    dir = 1; break;
                case 1: //east to south
                    dir = 3; break;
                case 2: //west to north
                    dir = 0; break;
                case 3: // south to west;
                    dir = 2; break;
                default:
                    break;
            }
            ConstructionSiteSimulator.startStimulator();
        }
        else if(input.startsWith("a"))
        {   commandCount++;
            try {
                int steps = Integer.parseInt(input.substring(input.indexOf(" ") + 1));
                commands.append("advance ").append(steps).append(", ");
                navigate(steps);
            }
            catch (NumberFormatException exception)
            {
                System.out.println("Please enter valid command");
                ConstructionSiteSimulator.startStimulator();
            }

        }
        else if(input.equals("q"))
            {
                commands.append("quit");
                calculateTotal();
            }

        else{
            System.out.println("Please enter valid command");
            ConstructionSiteSimulator.startStimulator();
        }
    }
    private int countRemainingBlocks(){
        int result=0;
        for (char[] chars : tracker) {
            for (int j = 0; j < tracker[0].length; j++) {
                if (chars[j] == 1) {
                    result++;
                }
            }
        }
        return maxRow*maxColumn-result-protectedTreeCount;
    }

    /**
     * Description  : Calculates and prints the output for simulator when quit command is executed.
     */
    private void calculateTotal() {
        System.out.println("The simulation has ended at your request. These are the commands you issued:");
       System.out.println(commands.toString());
       int remainingBlockCount=countRemainingBlocks();
        System.out.println("The costs for this land clearing operation were:");
        System.out.println("Items                                       Quantity        Cost");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("communication overhead                        "+commandCount+"            "+commandCount );
        System.out.println("fuel usage                                    "+fuelcost+"            "+fuelcost);
        System.out.println("uncleared squares                             "+remainingBlockCount+"         "+(remainingBlockCount*3));
        System.out.println("destruction of protected tree                 "+protectedTreePenalty+"            "+(protectedTreePenalty*10));
        System.out.println("paint damage to bulldozer                     "+paintPenalty+"            "+(paintPenalty*2));
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Total                                                   "+(commandCount+fuelcost+(protectedTreePenalty*10)+(paintPenalty*2)+(remainingBlockCount*3)));
        System.out.println("Thank you for using the Aconex site clearing simulator.");
    }

     /**
      * @param steps Moves the bulldozer by given steps in current direction
      */
    private void navigate(int steps) {
        switch(dir) {
            case 0: // moving in north
            {
                if(column==-1)
                    column=0;
                if (row-steps>=0) {
                    while (steps > 0) {
                        row--;
                        costCalculator(steps);
                        if(protectedTreePenalty>0)
                        return;
                        tracker[row][column] = 1;
                        steps--;
                    }
                }
                else {
                    System.out.println("Input command argument outside the site boundary. Quitting the simulator.");
                    executeCommand("q");
                    return;
                }
                break;
            }
            case 1: // moving in east
            {
                if(row==-1)
                    row=0;
                if(column+steps<maxColumn) {
                    while (steps > 0) {
                        column++;
                       costCalculator(steps);
                        if(protectedTreePenalty>0)
                       return;
                        tracker[row][column] = 1;
                        steps--;
                    }
                }
                else {
                    System.out.println("Input command argument outside the site boundary. Quitting the simulator.");
                    executeCommand("q");
                    return;
                }
                break;
            }
            case 2: // moving in west
            {
                if(row==-1)
                    row=0;
                if(column-steps>=0) {
                    while (steps > 0) {
                        column--;
                        costCalculator(steps);
                        if(protectedTreePenalty>0)
                        return;
                        tracker[row][column] = 1;
                        steps--;
                    }
                }
                else {
                    System.out.println("Input command argument outside the site boundary. Quitting the simulator.");
                    executeCommand("q");
                    return;
                }
                break;
            }
            case 3: // moving in south
            {
                if(column==-1)
                    column=0;
                if(row+steps<maxRow) {
                    while (steps > 0) {
                        row++;
                        costCalculator(steps);
                        if(protectedTreePenalty>0)
                            return;
                        tracker[row][column] = 1;
                        steps--;
                    }
                }
                else
                    {
                        System.out.println("Input command argument outside the site boundary. Quitting the simulator.");
                        executeCommand("q");
                        return;
                    }

                break;
            }
        }
        ConstructionSiteSimulator.startStimulator();
    }


     /**
      * Calculates cost for each block based on current row and column position
      */
    private void costCalculator(int steps){
        if (fieldmap[row][column] == 'o')
            fuelcost = fuelcost + 1;
        else if (fieldmap[row][column] == 'r') {
            fuelcost = fuelcost + 2;
            fieldmap[row][column] = 'o';
        } else if (fieldmap[row][column] == 't') {
            fuelcost = fuelcost + 2;
            fieldmap[row][column] = 'o';
            if (steps != 1) {
                paintPenalty++;
            }
        } else if (fieldmap[row][column] == 'T') {
            protectedTreePenalty++;
            System.out.println("Protected tree damaged. Quitting the simulator.");
            executeCommand("q");
            return;
        }
    }
}
