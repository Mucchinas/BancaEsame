import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Operation implements Serializable {

    private final String name, code;
    private final float amount;
    private final Account user;
    private final Account target;

    private final LocalDate date = LocalDate.now();
    private final LocalTime time = LocalTime.now();

    private final char opType;

    Operation(String name, float amount, Account user){

        this.name = name;
        this.amount = amount;
        this.user = user;
        this.target = null;
        this.code = RandomString.getAlphaNumericString();
        this.opType = 'a';


    }

    Operation(String name, float amount, Account user, Account target){

        this.name = name;
        this.amount = amount;
        this.user = user;
        this.target = target;
        this.code = RandomString.getAlphaNumericString();
        this.opType = 'b';

    }

    Operation(String name, float amount, Account user, Account target, String code){

        this.name = name;
        this.amount = amount;
        this.user = user;
        this.target = target;
        this.code = code;
        this.opType = 'c';

    }

    public String getCode(){

        return this.code;

    }

    @Override
    public String toString(){

        String pad = "\n" + "************";

        if (opType == 'a'){

            return pad + "\n" + name + " on " + date + "\n" +
                    "User: " + user.getIban() + "\n" +
                    "Time: " + time.toString().substring(0,8) + "\n" +
                    "Amount " + amount + "\n" +
                    "Operation code: " + code;

        }

        else if (opType == 'b'){

            return pad + "\n" + name + " on " + date + "\n" +
                    "From: " + user.getIban() + "\n" +
                    "To: " + target.getIban() + "\n" +
                    "Time: " + time.toString().substring(0,8) + "\n" +
                    "Amount " + amount + "\n" +
                    "Operation code: " + code;

        }

        else if (opType == 'c'){

            return pad + "\n" + name + " on " + date + "\n" +
                    "From: " + target.getIban() + "\n" +
                    "To: " + user.getIban() + "\n" +
                    "Time: " + time.toString().substring(0,8) + "\n" +
                    "Amount " + amount + "\n" +
                    "Operation code: " + code;

        }
        else return "Invalid Operation!";
    }


}
