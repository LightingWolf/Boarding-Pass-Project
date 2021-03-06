import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;


public class InputValidation {
    public static boolean checkNumber(String phoneNumber) {
        if (phoneNumber.length() >= 9) {
            //(\+?(1[\s+-]?))? means   '1' (preceded optionally by a '+') followed by either a space, no space, '+', '-' ; OR no '1' with any of that.
            //for country code '1'


            // validate phone numbers of format "1234567890"
            if (phoneNumber.matches("(\\+?(1[\\s+-]?))?\\d{10}")) {
                return true;
            }
            // validating phone number with -, . or spaces
            //[-\s]? separate by space '-' or no space
        /*    else if (phoneNumber.matches("(\\+?(1[\\s+-]?))?\\d{3}[-.\\s]?\\d{3}[-.\\s]?\\d{4}")) {
                return true;
            }*/
            // validating phone number with extension length from 3 to 5
            //(\s(x|(ext))\d{3,5})? checks for extension
            else if (phoneNumber.matches("(\\+?(1[\\s+-]?))?\\d{3}[-\\s]?\\d{3}[-\\s]?\\d{4}(\\s(x|(ext))[-+\\s]?\\d{3,5})?")) {
                return true;
            }
            // validating phone number where area code is in braces ()
            else if (phoneNumber.matches("(\\+?(1[\\s+-]?))?\\(\\d{3}\\)[\\s-]?\\d{3}[-\\s]?\\d{4}(\\s(x|(ext))[-+\\s]?\\d{3,5})?")) {
                return true;
            }
     /*       else if (phoneNumber.matches("(\\+?(1[\\s+-]?))?\\(\\d{3}\\) \\d{3}-\\d{4}")) {
                return true;
            }*/
            // Validation for India numbers
            else if (phoneNumber.matches("\\d{4}[-.\\s]\\d{3}[-.\\s]\\d{3}")) {
                return true;
            } else // return false if nothing matches the input
                if (phoneNumber.matches("\\(\\d{5}\\)-\\d{3}-\\d{3}")) {
                    return true;
                } else return phoneNumber.matches("\\(\\d{4}\\)-\\d{3}-\\d{3}");
        }
        return false;
    }

    public static User.Gender checkGender(Scanner in) {
        System.out.println("1. Male \n2. Female \n3. Prefer not to say");
        String temp = in.nextLine();
        User.Gender gender;
        // only take in one number, no whitespace, no chars
        while (!temp.matches("(?<!\\S)[123](?!\\S)")) {
            System.out.println("Please choose a gender:  \n1. Male \n2. Female \n3. Prefer not to say");
            temp = in.nextLine();
        }

        switch (Integer.parseInt(temp)) {
            case 1:
                gender = User.Gender.M;
                break;
            case 2:
                gender = User.Gender.F;
                break;
            default:
                gender = User.Gender.X;
        }
        return gender;
    }

    public static String checkEmail(Scanner in) {
        String temp = in.nextLine();
        // checks to make sure email at lest has an '@' and a '.'
        while (!temp.matches("[a-zA-Z\\d]+@[a-zA-Z\\d]+\\.[a-zA-Z\\d]+$")) {
            System.out.println("Please enter a valid email: ");
            System.out.println("Format expected: gernericemail@xxxx.com");
            temp = in.nextLine();
        }
        return temp;
    }

    public static String checkName(Scanner in) {
        String tempName = in.nextLine();
        // only takes in characters and spaces
        while (!tempName.matches("[a-zA-Z\\s]+")) {
            System.out.println("Please enter a valid name (only characters and spaces): ");
            tempName = in.nextLine();
        }
        return tempName;
    }

    public static String checkDestination(Scanner in, String originDestination) {

        var airports = Airport.getAirports().keySet().stream().filter(a -> !a.equals(originDestination)).collect(Collectors.toCollection(ArrayList::new));

        String assignDestination;
        boolean firstTry = true;
        var end = String.valueOf(airports.size());
        do {
            System.out.println("Please choose a" + (firstTry ? "n" : " valid") + " option: ");
            firstTry = false;
            int count = 0;
            for (var a : airports) {
                System.out.println(++count + ". " + a);
            }
            assignDestination = in.nextLine();

        }while (!assignDestination.matches("[1-" + end + "]"));

        var index = Integer.parseInt(assignDestination)-1;
        return airports.get(index);
    }


    public static String dateValidation(Scanner in) {
        String tempDate = in.nextLine();
        // makes sure date follows format (xx/xx/xxxx)
        // and ensures months will never be out of range 1-12
        // and dates will never be higher than 31
        // also only takes years 20xx
        while (!tempDate.matches("^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](20)\\d\\d$"))
        {
            System.out.println("Please enter a valid date with the expected format: ");
            System.out.println("Example: Month/Day/Year(xxxx) -> 12/24/2019");
            tempDate = in.nextLine();
        }
        return tempDate;
    }

    public static String timeValidate(Scanner in) {
        String tempTime;

        System.out.println("Enter your Departure Time: ");
        System.out.println("HH:mm AM/PM");
        tempTime = in.nextLine();

        while (!timeRegexMatch(tempTime)){
            System.out.println("Please use this format: HH:mm AM/PM");
            tempTime = in.nextLine();
        }
        var temp = tempTime.split("[:\\s)]");
        int hour = Integer.parseInt(temp[0]);
        if (temp.length !=3) {
            temp[1] = temp[1].replace('p','P');
            temp[1] = temp[1].replace('a','A');
            var letter = ' ';
            int index =   temp[1].indexOf("P");
            if (index == -1) {
                letter = 'A';
            }
            else{
                letter = 'P';
            }
            var temp2 = temp[1].split("[AP]");
            var temp3 = temp[0];
            temp = new String[3];
            temp[0] = temp3;
            temp[1] = temp2[0];
            temp2[1] = letter +  temp2[1];
            temp[2] = temp2[1];
        }
        if (Character.toUpperCase(temp[2].charAt(0)) == 'P') {
            if (hour != 12) {
                hour += 12;
            }
        } else if (Character.toUpperCase(temp[2].charAt(0)) == 'A') {
            if (hour == 12) {
                hour = 0;
            }
        }
        return hour + ":" + temp[1];
    }

    public static boolean timeRegexMatch(String tempTime) {
        return tempTime.matches("(1[0-2]|0[1-9]|[1-9]):([0-5]\\d)\\s?([aApP])([mM])");
    }
}
