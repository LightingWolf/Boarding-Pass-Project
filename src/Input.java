import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Input {


    /*
    My thought process was get the user input and use that to create the user object
    Need to figure out a method to select the available flights
     */
    public static User getInformation() {
        Scanner in = new Scanner(System.in);

        System.out.println("Please enter your name: ");
        String name = InputValidation.checkName(in);

        int age = getUserAge(in);

        System.out.println("Please choose your gender: ");
        // user input for gender
        User.Gender gender = InputValidation.checkGender(in);

        System.out.println("Please enter your email: ");
        String email = InputValidation.checkEmail(in);

        System.out.println("Please Enter your phone number: ");
        String phoneNumber = in.nextLine();

        while (!InputValidation.checkNumber(phoneNumber)) {
            System.out.println("Please enter a valid phone number: ");
            phoneNumber = in.nextLine();
        }

        System.out.println("Please enter your current location: ");
        String origin = InputValidation.checkDestination(in, "");

        // creating a Date object that stores the current time of creation
        Date date = new Date();

        System.out.println("Enter choose your destination: ");
        String destination = InputValidation.checkDestination(in, origin);

        // creating a new Date object that takes the String in the
        // format Month/Day/Year and then splits it and creates the date object
        //ClearInputLine(in);


        Date departureTime = getDepartureTime(in);

        return new User(name, email, gender, phoneNumber, date, destination, departureTime, origin, age);
    }


    protected static Date getDepartureTime(Scanner in) {
        System.out.println("Enter your Departure Date: ");
        System.out.println("Expected Format: Month/Day/Year(xxxx)");
        String line = InputValidation.dateValidation(in);
        String[] dateFormatter = line.split("/");

        String[] timeLine = InputValidation.timeValidate(in).split(":");

        /*Calendar calendar = new Calendar.Builder().setCalendarType("gregorian")
                .setDate(Integer.parseInt(dateFormatter[2]), Integer.parseInt(dateFormatter[1]),
                        Integer.parseInt(dateFormatter[0])).setTimeOfDay(Integer.parseInt(timeLine[0]), Integer.parseInt(timeLine[1]), 0).build();
       return calendar.getTime();*/

/*        LocalDate ld = LocalDate.of(
                Integer.parseInt(dateFormatter[2]),
                Integer.parseInt(dateFormatter[1]),
                Integer.parseInt(dateFormatter[0]));*/
        //LocalTime lt = LocalTime.of(Integer.parseInt(timeLine[0]),Integer.parseInt(timeLine[1]));
        Date date = new Date();
        date.setYear(Integer.parseInt(dateFormatter[2])-1900);
        date.setMonth(Integer.parseInt(dateFormatter[0])-1);
        date.setDate(Integer.parseInt(dateFormatter[1]));
        date.setHours(Integer.parseInt(timeLine[0]));
        date.setMinutes(Integer.parseInt(timeLine[1]));
        return date;
    }

    private static int getUserAge(Scanner in) {
        int age = -1;
        do {
            System.out.println("Please enter your age: ");
            try {
                String line = in.nextLine();
                age = Integer.parseInt(line);
            } catch (NumberFormatException nfe){
                System.out.println("Please enter a number");
            }
        } while (age < 0);
        return age;
    }

}
