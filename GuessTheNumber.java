import java.util.*;

class GuessTheNumber {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        
        int minRange = 1;
        int maxRange = 100;
        int maxAttempts = 5;
        int round = 1;
        int score = 0;

        System.out.println("----- Guess the Number ------");
        System.out.println("You have " + maxAttempts + " attempts to guess the number between " + minRange + " and " + maxRange + ".");
        System.out.println("Let's begin!");

        boolean playAgain = true;
        while (playAgain) {
            int secretNumber = random.nextInt(maxRange - minRange + 1) + minRange;
            int attempts = 0;
            boolean guessedCorrectly = false;

            while (attempts < maxAttempts && !guessedCorrectly) {
                System.out.print("Round " + round + ", Attempt " + (attempts + 1) + ": Enter your guess: ");
                int guess = sc.nextInt();

                if (guess == secretNumber) {
                    System.out.println("Congratulations! You guessed the correct number.");
                    guessedCorrectly = true;
                    score += (maxAttempts - attempts) * 10;
                } 
                else if (guess < secretNumber) {
                    System.out.println("Your guess is too low.");
                } 
                else {
                    System.out.println("Your guess is too high.");
                }

                attempts++;
            }

            if (!guessedCorrectly) {
                System.out.println("Out of attempts! The correct number was: " + secretNumber);
            }

            System.out.println("Score: " + score);
            round++;

            System.out.print("Do you want to play again? (yes/no): ");
            String playAgainInput = sc.next();

            if (playAgainInput.equalsIgnoreCase("no")) {
                playAgain = false;
                System.out.println("Thanks for playing! Final score: " + score);
            }

            System.out.println();
        }

        sc.close();
    }
}
