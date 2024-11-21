import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

public class Eliza 
{
    public static void main(String[] args) 
    {
        // Initialize the log file writer
        FileWriter logFile = null;
        try 
        {
            logFile = new FileWriter("eliza_chat_log.txt");
        } 
        catch (IOException e) 
        {
            System.out.println("Error creating the log file.");
            e.printStackTrace();
        }

        // Scanner object to read user input
        Scanner scanner = new Scanner(System.in);
        
        // Greet the user
        System.out.println("ELIZA: Hello, I am ELIZA. How can I help you today?");
        
        // Infinite loop for conversation, exits when user types "exit"
        while (true) 
        {
            // Prompt the user for input
            System.out.print("You: ");
            String userInput = scanner.nextLine().toLowerCase();
            
            // Save user input to log
            try 
            {
                logFile.write("You: " + userInput + "\n");
                logFile.flush();  // Flush to ensure immediate writing
            } 
            catch (IOException e) 
            {
                System.out.println("Error writing to the log file.");
                e.printStackTrace();
            }

            // Check if user wants to exit the conversation
            if (userInput.equals("exit")) 
            {
                break; // Exit the loop and end the program
            }
            
            // Generate ELIZA's response and print it
            String response = generateResponse(userInput);
            System.out.println("ELIZA: " + response);

            // Save ELIZA's response to log
            try 
            {
                logFile.write("ELIZA: " + response + "\n");
                logFile.flush();  // Flush to ensure immediate writing
            } 
            catch (IOException e) 
            {
                System.out.println("Error writing to the log file.");
                e.printStackTrace();
            }
        }

        // Close scanner when done
        scanner.close();

        // Close the log file when done
        try 
        {
            if (logFile != null) 
            {
                logFile.close();
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error closing the log file.");
            e.printStackTrace();
        }
    }

    // Method to generate ELIZA's response based on user input
    private static String generateResponse(String userInput) 
    {
        // Initialize Random object for choosing responses
        Random random = new Random();

        // Rule 1: Respond to "yes" in user input
        if (userInput.contains("yes")) 
        {
            return randomResponse(new String[]{
                "Are you sure?", 
                "You sound positive."
            });
        }
        // Rule 2: Respond to "no" in user input
        else if (userInput.contains("no")) 
        {
            return randomResponse(new String[]{
                "Are you certain?", 
                "Why the negative tone?"
            });
        }
        // Rule 3: Respond to phrases starting with "I am"
        else if (userInput.startsWith("i am")) 
        {
            String response = userInput.replace("i am", "you are");
            return randomResponse(new String[]{
                "Why do you think " + response + "?",
                "Did you come to me because " + response + "?"
            });
        }
        // Rule 4: Respond to phrases with "your"
        else if (userInput.contains("your")) 
        {
            String[] splitInput = userInput.split("your", 2);
            if (splitInput.length > 1) 
            {
                return "Why are you concerned about my " + splitInput[1] + "?";
            }
        }
        // Rule 5: Respond to "perhaps" or "maybe"
        else if (userInput.contains("perhaps") || userInput.contains("maybe")) 
        {
            return "Why the uncertain tone?";
        }
        // Rule 6: Respond to short input (less than 15 characters)
        else if (userInput.length() < 15) 
        {
            return "Tell me more.";
        }
        // Rule 7: Respond to "you"
        else if (userInput.contains("you")) 
        {
            return randomResponse(new String[]{
                "We were discussing you, not me.",
                "You’re not really talking about me, are you?"
            });
        }
        // Rule 8: Respond to "sorry"
        else if (userInput.contains("sorry")) 
        {
            return randomResponse(new String[]{
                "Please don’t apologize.",
                "Apologies are not necessary."
            });
        }
        // Rule 9: Respond to "you are X" / "are you X"
        else if (userInput.contains("you are") || userInput.contains("are you")) 
        {
            return randomResponse(new String[]{
                "Why are you interested in whether or not I am X?",
                "What makes you think I am X?"
            });
        }
        // Rule 10: Respond to "I need X" / "I want X"
        else if (userInput.contains("i need") || userInput.contains("i want")) 
        {
            String[] splitInput = userInput.split("i (need|want)", 2);
            if (splitInput.length > 1) 
            {
                return "What would it mean if you got " + splitInput[1].trim() + "?";
            }
        }
        // Rule 11: Respond to family terms
        else if (userInput.contains("mother") || userInput.contains("father") ||
                 userInput.contains("sister") || userInput.contains("brother")) 
        {
            return "Tell me more about your family.";
        }
        // Rule 12: Respond to "You are like X"
        else if (userInput.contains("you are like")) 
        {
            String[] splitInput = userInput.split("you are like", 2);
            if (splitInput.length > 1) 
            {
                return "What resemblance do you see?";
            }
        }
        // Rule 13: Respond to "X is Y"
        else if (userInput.contains(" is ")) 
        {
            String[] splitInput = userInput.split(" is ", 2);
            if (splitInput.length > 1) 
            {
                return "What else comes to your mind when you think of " + splitInput[0].trim() + "?";
            }
        }
        // Rule 14: Respond to anything ending with "?"
        else if (userInput.endsWith("?")) 
        {
            return randomResponse(new String[]{
                "Have you asked such questions before?",
                "What is it you really want to know?"
            });
        }
        // Rule 15: Respond to "I don’t X"
        else if (userInput.contains("i don’t")) 
        {
            String[] splitInput = userInput.split("i don’t", 2);
            if (splitInput.length > 1) 
            {
                return "Do you wish to " + splitInput[1].trim() + "?";
            }
        }
        // Rule 16: Respond to obscenities
        else if (userInput.contains("darn") || userInput.contains("gosh")) 
        {
            return "Does it make you feel strong to use that kind of language?";
        }
        // Rule 17: Respond to "yes" and "no" in the same sentence
        else if (userInput.contains("yes") && userInput.contains("no")) 
        {
            return "You seem uncertain.";
        }
        // Rule 18: Respond to "name" or "names"
        else if (userInput.contains("name") || userInput.contains("names")) 
        {
            return "I am not interested in names.";
        }

        // Default fallback responses if no specific rule applies
        return randomResponse(new String[]{
            "Interesting. Do go on.",
            "How do you feel about that?",
            "Please, please, elucidate your thoughts."
        });
    }

    // method that returns a random response from a set of choices
    private static String randomResponse(String[] responses) 
    {
        // Create a Random object to pick random response
        Random random = new Random();
        // Generate random index to pick a response
        int index = random.nextInt(responses.length);
        // Return the randomly selected response
        return responses[index];
    }
}
