/**
 * This program allows a user to buy and sell stock. The user is able to see their
 * total gain and quit the program at any point. 
 *
 * @Tyler Melanson
 * @10/28/2025 
 */

import java.util.Scanner;

public class StockCalc
{
    //Initializes the stocks to be able to trade
    private DequeInterface<StockLot> stock;
    private double totalGain;
    
    //Makes the stocks use the LinkedDeque and also sets the total gain to 0
    public StockCalc(){
        stock = new LinkedDeque<>();
        totalGain = 0.0;
    }
    
    //Lists all of the options a user can select in order to buy,trade,see gain, or quit
    public void stockMenu(){
        //Lets the options reask after each task unless the user quits
        boolean on = true;
        //Allows the user to reply to the options
        Scanner menu = new Scanner(System.in);
        
        //Loops menu
        while (on == true){
            //All options available to the user
            System.out.println("\nOptions: ");
            System.out.println("1: Buy");
            System.out.println("2: Sell");
            System.out.println("3: View Total Realized Capital Gain");
            System.out.println("4: Quit");
            System.out.println("Enter a number to run the corresponding action: ");

            //Gets users option choice
            int choice = menu.nextInt();
            
            //Buys shares if user selects 1
            if(choice == 1){
                System.out.println("\nEnter number of shares that you want to buy: ");
                int boughtShares = menu.nextInt();
                
                System.out.println("How much do they each cost? ");
                double buyPrice = menu.nextDouble();
                
                //Saves the purchace in StockLot
                buy(boughtShares, buyPrice);
                //Informs the user of the transaction
                System.out.println("Bought " + boughtShares + " shares at $" + buyPrice + " a share.");
            //Sells shares if user selects 2
            } else if(choice == 2){
                System.out.println("\nHow many shares to you want to sell? ");
                int sellShares = menu.nextInt();
                
                System.out.println("How much does one of these shares cost? ");
                double sellPrice = menu.nextDouble();
                
                //Processes the sale
                sell(sellShares, sellPrice);
            //Provides total gain
            } else if(choice == 3){
                System.out.printf("\nYour total gain is: $%.2f", totalGain);
            //Quits the program
            } else if(choice == 4){
                System.out.println("\nHave a good day! Hope you made a profit :)");
                on = false;
            //Informs the user if they entered something invalid and to try again
            } else{
                System.out.println("That isn't an option, please try a number 1-4.");
            }
        }
        //Closes the scanner
        menu.close();
    }
    
    //Buys shares by creating the purchace as a StockLot then adds it to the back of the queue
    public void buy(int shares, double price){
        StockLot lot = new StockLot(shares,price);
        stock.addToBack(lot);
    }
    
    //Sells shares and calculates gain
    public void sell(int sellShares, double sellPrice){
        int sharesSold = 0;
        //Sells until the requested shares are sold or if there are none to sell
        while(sharesSold < sellShares && !stock.isEmpty()){
            //Removes front share because due to FIFO structure of a queue
            StockLot lot = stock.removeFront();
            int available = lot.getShares();
            //Determines how many shares to sell
            int sharesThisLot = Math.min(available, sellShares - sharesSold);
            sharesSold += sharesThisLot;
            //Calculates gain for the transaction
            double gain = (sellPrice - lot.getBuyPrice()) * sharesThisLot;
            totalGain += gain;
            
            //Informs the user of the transaction that has unfolded
            System.out.printf("Sold %d shares bought at $%.2f for $%.2f each. Gain: $%.2f%n", sharesThisLot, lot.getBuyPrice(), sellPrice, gain);
            
            //Puts any remaining shares back in the front of the queue
            int remaining = available - sharesThisLot;
            if(remaining > 0){
                StockLot remainingLot = new StockLot(remaining, lot.getBuyPrice());
                stock.addToFront(remainingLot);
            }
        }
        //Informs the user that they dont have enough shares to complete the transaction they wanted
        if(sharesSold < sellShares){
            System.out.println("You dont have enough shares to sell");
        }
    }
}
