import prog.io.ConsoleInputManager;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.lang.*;

public class Account implements Serializable{

    private final String name, surname, password, iban;
    private float balance;

    private final List<Operation> operations = new ArrayList<>();


    Account(String name, String surname, String password) throws NoSuchAlgorithmException {


        this.name = name;
        this.surname = surname;
        this.password = Hasher.getHash(password);
        this.iban = GenerateIBAN.CreateIBAN();
        this.balance = 0;

    }

    public void Deposit(float amount){

        this.balance += amount;
        System.out.println("\n" + amount + " Euros have been deposited on " + name + " " +
                surname + "'s account, new balance: " + balance + " euros.");

        operations.add(new Operation("Deposit", amount, this));

    }

    public void Withdraw(float amount){

        if (balance >= amount) {

            this.balance -= amount;
            System.out.println("\n" + amount + " Euros have been withdrawn from " + name + " " +
                    surname + "'s account, new balance: " + balance + " euros.");

            operations.add(new Operation("Withdraw", amount*(-1), this));

        } else {

            System.out.println(name + " " + surname + " has insufficient balance!");

        }

    }

    public void Transfer(Account recipient, float amount){

        if (balance >= amount){

            this.balance -= amount;
            recipient.balance += amount;
            System.out.println("\n" + name + " " + surname + " has tranferred " + amount + " euros to " + recipient.name + " " + recipient.surname + ".");

            Operation sent = new Operation("Payment sent", amount*(-1), this, recipient);
            operations.add(sent);

            recipient.operations.add(new Operation("Payment received", amount, recipient, this, sent.getCode()));


        } else System.out.println(name + " " + surname + " has insufficient balance!");

    }

    public void Display(){

        System.out.println("\n" + name + " " + surname + "\n" +
                iban + "\n" +
                "Balance: " + balance);

    }

    public void DisplayOperations(){

        System.out.println("-----------------------");
        System.out.println(name + " " + surname + "'s operations: ");

        System.out.println(operations);
        System.out.println("************");
        System.out.println("Current balance: " + balance);
        System.out.println("-----------------------");

    }

    public String getIban() {
        return iban;
    }

    @Override
    public String toString(){

        return "\n" + name + " " + surname + "\n" +
                iban;

    }

    public static void SaveAccounts(List<Account> accounts) throws IOException{

        String fileName= "Accounts.txt";
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(accounts);
        oos.close();

    }

    public static List<Account> LoadAccounts() throws IOException, ClassNotFoundException {

        String fileName= "Accounts.txt";
        FileInputStream fin = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fin);
        @SuppressWarnings("unchecked")
        List<Account> accounts = (List<Account>) ois.readObject();
        ois.close();
        return accounts;

    }

    public static Account Login(List<Account> accounts) throws NoSuchAlgorithmException {

        Account current = null;
        ConsoleInputManager in = new ConsoleInputManager();
        String username = in.readLine("Insert name and surname: ");
        String[] split = username.split("\\s+");
        if (split.length != 2) {

            System.out.println("No accounts found!");
            return null;

        }
        for (Account acc : accounts){

            if (split[0].equals(acc.name) && split[1].equals(acc.surname)){

                current = acc;
                break;

            }

        }
        if (current == null){

            System.out.println("No accounts found!");
            return null;
        }

        String pass = in.readLine("Welcome " + username + ", insert your password: ");
        if (Hasher.getHash(pass).equals(current.password)){

            System.out.println("Password correct!");
            current.Display();
            return current;

        } else {

            System.out.println("Wrong password!");
            return null;

        }

    }

    public static Account Register(List<Account> accounts) throws NoSuchAlgorithmException {

        ConsoleInputManager in = new ConsoleInputManager();
        String username = in.readLine("Insert name and surname: ");
        String[] split = username.split("\\s+");
        if (split.length != 2) {

            System.out.println("Not a valid username!");
            return null;

        }
        for (Account acc : accounts){

            if (split[0].equals(acc.name) && split[1].equals(acc.surname)){

                System.out.println("Username already exists!");
                return null;

            }

        }

        String pass = in.readLine("Welcome " + username + ", choose a password: ");
        Account current = new Account(split[0], split[1], pass);
        System.out.println("Account created succesfully!");
        current.Display();
        accounts.add(current);
        return current;

    }
}
