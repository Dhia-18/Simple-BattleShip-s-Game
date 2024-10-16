import java.util.Scanner;

public class Player {
    private String[][] locationBoard = {{"-","-","-","-","-"},
                                        {"-","-","-","-","-"},
                                        {"-","-","-","-","-"},
                                        {"-","-","-","-","-"},
                                        {"-","-","-","-","-"}
                                        };
    
    private String[][] targetHistoryBoard = {{"-","-","-","-","-"},
                                        {"-","-","-","-","-"},
                                        {"-","-","-","-","-"},
                                        {"-","-","-","-","-"},
                                        {"-","-","-","-","-"}
                                        };
    
    private int numberShipsHit=0;

    public String[][] getLocationBoard(){
        return(locationBoard);
    }

    public void setLocationBoard(int line, int column, String status){
        locationBoard[line][column]=status;
    }
    
    public int getNumberShipHit(){
        return(numberShipsHit);
    }

    public void displayBoard(String[][] board){
        System.out.println("  0 1 2 3 4");
        for(int line=0;line<5;line++){
            System.out.print(line);
            for(int column=0;column<5;column++){
                System.out.print(" "+board[line][column]);
            }
            System.out.println();
        }
    }

    public void displayShips(Scanner input){
        for (int numShip=1;numShip<=5;numShip++){
            int x=-1,y=-1;
            boolean validInput = false;

            do {
                System.out.println("Enter ship " + numShip + " location:");
                if(input.hasNextInt()){ // Checking if the two inputs are in the correct format.
                    x=input.nextInt();
                    if(input.hasNextInt()){
                        y=input.nextInt();
                        if(x>=0 && x<= 4 && y>=0 && y<=4){
                            if(locationBoard[x][y].equals("@")){ // Checking if there's already a ship in tat spot.
                            System.out.println("You already have a ship there. Choose different coordinates.");
                            }
                            else{
                                validInput=true;
                            }
                        }
                        else{
                            System.out.println("Invalid coordinates. Choose different coordinates.");
                        }
                    }
                    else{
                        System.out.println("Invalid coordinates. Choose different coordinates.");
                        input.next();
                    }
                }
                else{
                    System.out.println("Invalid coordinates. Choose different coordinates.");
                    input.next();
                }
            } while (!validInput);

            locationBoard[x][y]="@";
        }
        displayBoard(locationBoard);
    }

    public void hit(Scanner input, String numPlayer, Player enemy){
        int x=-1,y=-1;
        boolean validInput=false;
        do{
            System.out.println("Player "+ numPlayer +", enter hit row/column:");
            if(input.hasNextInt()){
                x=input.nextInt();
                if(input.hasNextInt()){
                    y=input.nextInt();
                    if(x>=0 && x<=4 && y>=0 && y<=4){
                        if(targetHistoryBoard[x][y].equals("O") || targetHistoryBoard[x][y].equals("X")){
                            System.out.println("You already fired on this spot. Choose different coordinates.");
                        }
                        else if(enemy.getLocationBoard()[x][y].equals("@")){
                            System.out.println("PLAYER "+numPlayer+" HIT PLAYER "+(numPlayer.equals("1")? "2":"1")+"'s SHIP!");
                            targetHistoryBoard[x][y]="X";
                            enemy.setLocationBoard(x,y,"X");

                            numberShipsHit++; // Increment the number of ships hit by the player
                            displayBoard(targetHistoryBoard);
                            validInput=true;
                        }
                        else{
                            System.out.println("PLAYER "+numPlayer+" MISSED!");
                            targetHistoryBoard[x][y]="O";
                            enemy.setLocationBoard(x, y, "O");

                            displayBoard(targetHistoryBoard);
                            validInput=true;
                        }
                    }
                    else{
                        System.out.println("Invalid coordinates. Choose different coordinates.");
                    }
                }
                else{
                    System.out.println("Invalid coordinates. Choose different coordinates.");
                    input.next();
                }
            }
            else{
                System.out.println("Invalid coordinates. Choose different coordinates.");
                input.next();
            }
        }while(!validInput);
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Battleship!\n");

        Scanner input = new Scanner(System.in);
        Player player1 = new Player();
        Player player2 = new Player();

        System.out.println("PLAYER 1, ENTER YOUR SHIPS' COORDINATES.");
        player1.displayShips(input);
        System.out.println("PLAYER 2, ENTER YOUR SHIPS' COORDINATES.");
        player2.displayShips(input);

        // Starting the game!
        boolean gameEnded=false;
        while(!gameEnded){
            if(player2.getNumberShipHit()<5){
                player1.hit(input,"1",player2);
            }
            else{
                gameEnded=true;
            }
            if(player1.getNumberShipHit()<5){
                player2.hit(input,"2",player1);
            }
            else{
                gameEnded=true;
            }
        }

        if(player1.getNumberShipHit()==5){
            System.out.println("PLAYER 1 WINS! YOU SUNK ALL OF YOUR OPPONENT'S SHIPS!\n");
        }
        else{
            System.out.println("PLAYER 2 WINS! YOU SUNK ALL OF YOUR OPPONENT'S SHIPS!\n");
        }
        System.out.println("Final boards:\n");
        player1.displayBoard(player1.getLocationBoard());
        System.out.println();
        player2.displayBoard(player2.getLocationBoard());
    }
}
