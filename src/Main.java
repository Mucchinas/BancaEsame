import prog.io.*;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {

        File f = new File("Accounts.txt");
        List<Account> accounts;

        if (f.isFile()){

            accounts = Account.LoadAccounts();

        } else accounts = new ArrayList<>();

        ConsoleInputManager in = new ConsoleInputManager();
        Account current = null;

        while (current == null) {
            System.out.println("Welcome to the banking software");
            System.out.println("""

                    l) Login
                    r) Register""");
            String choice = in.readLine("\nSelect operation: ");

            switch (choice){

                case "l":
                    current = Account.Login(accounts);
                    break;

                case "r":
                    current = Account.Register(accounts);
                    break;
                default:
                    System.out.println("Invalid operation!");
            }
            Account.SaveAccounts(accounts);

        }

        while (true){

            System.out.println("""

                    a) Deposit
                    b) Withdraw
                    c) Check operations
                    d) Transfer
                    e) Exit""");

            String choice = in.readLine("\nSelect Operation: ");

            switch (choice){

                case "a":
                    current.Deposit((float)in.readDouble("Insert amount: "));
                    break;
                case "b":
                    current.Withdraw((float)in.readDouble("Insert amount: "));
                    break;
                case "c":
                    current.DisplayOperations();
                    break;
                case "d":
                    String target = in.readLine("Type IBAN of the recipient: ");
                    boolean done = false;
                    for (Account a : accounts){

                        if (a.getIban().equals(target)){

                            float amount = in.readInt("Insert amount: ");
                            current.Transfer(a,amount);
                            done = true;
                            break;

                        }

                    }

                    if (!done) System.out.println("Recipient not found!");
                    break;
                case "e":
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid operation!");
            }

            Account.SaveAccounts(accounts);

        }

    }

}

