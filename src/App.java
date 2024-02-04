public class App {
    
    public static int i = 0;
    public static String hints[] = new String[5];
    public static int score = 0;
    public static void main(String[] args) throws Exception {
        int flag = 0;
        DatabaseManager db = new DatabaseManager();
        db.createDB();
        db.createTable();
        Register register = new Register();
        new Rules();
        Minesweeper minesweeper1 = new Minesweeper(1);
        minesweeper1.playGame();
        if (minesweeper1.win() == 1){
            score += 2;
            TicTacToe tictactoe = new TicTacToe();
            if (tictactoe.win() == 1){
                score += 2;
                hints[i] = "The first digit is greater than 5.";
                i++;
            }
            Minesweeper minesweeper2 = new Minesweeper(2);
            minesweeper2.playGame();
            if (minesweeper2.win() == 1){
                score += 4;
                SimonSays simonsays = new SimonSays();
                if(simonsays.win() == 1){
                    score += 2;
                    hints[i] = "The second digit is an odd number.";
                    i++;
                }
                Minesweeper minesweeper3 = new Minesweeper(3);
                minesweeper3.playGame();
                if (minesweeper3.win() == 1){
                    score += 8;
                    WhacAMole whacamole = new WhacAMole();
                    if(whacamole.win() == 1){
                        score += 2;
                        hints[i] = "The sum of all digits is 20.";
                        i++;
                    }
                    Minesweeper minesweeper4 = new Minesweeper(4);
                    minesweeper4.playGame();
                    if (minesweeper4.win() == 1){
                        score += 16;
                        DurgeshHangmanGUI hangman = new DurgeshHangmanGUI();
                        if(hangman.win() == 1){
                            score += 2;
                            hints[i] = "The last digit is one less than the third digit.";
                            i++;
                        }
                        Minesweeper minesweeper5 = new Minesweeper(5);
                        minesweeper5.playGame();
                        if (minesweeper5.win() == 1){
                            score += 32;
                            UnlockTreasure unlocktreasure = new UnlockTreasure(hints);
                            unlocktreasure.showFrame();
                            if (unlocktreasure.win()==1){
                                score += 100;
                                flag = 1;
                            }
                        }
                        // else{

                        // }
                    }
                    // else{

                    // }
                }
                // else{

                // }
            }
            // else{

            // }
        }
        // else{

        // }
        db.updateScores(register.name, score);
        new LeaderBoard(flag);
    }
}
