/**
 *This program runs the StockCalc class which allows for a user to trade stock.
 *The user selects 4 options to either buy, sell, view gain, or quit.
 *
 * @Tyler Melanson 
 * @10/28/2025
 */
public class Driver
{
   public static void main(String[] args){
       //Intitializes the StockCalc class
       StockCalc stocking = new StockCalc();
       stocking.stockMenu();
   }
}
