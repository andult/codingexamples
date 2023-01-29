package AppointmentDate;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ComGarden {
   public static void main(String[] args) {
       //DECLARE VARIABLES
       Scanner user_input = new Scanner(System.in);
       String filename = "AppointmentData.txt";
       String first, last, phone, donate;
       int month, day, hour, minute, year = 2021;
       boolean isWeekend;
       char answer;

       //ASK FOR PERSONAL INFO
       System.out.println("----Welcome to Moreno Valley Community Garden----");
       System.out.print("Please enter your first name: ");
       first = user_input.next();
       System.out.print("Please enter your last name: ");
       last = user_input.next();
       System.out.print("Please enter your phone number (###-###-####): ");
       phone = user_input.next();
       PersonalInfo user = new PersonalInfo(first, last, phone);

       //ASK FOR APPOINTMENT DATE
       System.out.println("\nYou can visit the garden anytime from 01/01/2021 to 12/31/2021------------------");
       System.out.print("Please enter a month (January = 1, February = 2, etc): ");
       month = user_input.nextInt();
       System.out.print("Please enter a day: ");
       day = user_input.nextInt();
       System.out.print("Is this a weekend? (Y/N): ");
       answer = user_input.next().charAt(0);                           //works here
           if (answer == 'Y' || answer == 'y') isWeekend = true;
           else isWeekend = false;

       //ASK FOR APPOINTMENT TIME
       System.out.println("\nNow pick a time, time will be in military time---------------------------------");
       System.out.print("Please enter the hour: ");
       hour = user_input.nextInt();
       System.out.print("Please enter the minute: ");
       minute = user_input.nextInt();
       Date app = new Date(day, month, hour, minute);
       app.VerifyHour(isWeekend);

       //ASK FOR DONATION
       System.out.println("\nThank you for scheduling an appointment with us!-------------------------------");
       System.out.print("Before you go please select a donation type: ");
       System.out.println("\nHere is the list of options!");
           Choice pick = new Choice();
           pick.DisplayMenu();
           donate = pick.getChoice();
           System.out.println();
/*
       //DISPLAY APPOINTMENT
       System.out.println(user.firstName + "\n" + user.lastName + "\n" + user.phone);
       System.out.print(app.month + "/" + app.day + "/" + year);
       System.out.println(" at " + app.hour + ":" + app.minute);
       System.out.println("Donation: " + donate);

       //APPOINTMENT CONFIRMATION
       System.out.print("Is the information above correct? (Y/N): ");
       answer = user_input.next().charAt(0);                       //but not here, unsure why
       if (answer == 'Y' || answer == 'y')
       {
           System.out.print("\nAwesome!");
           WriteToFile(filename, first, last, phone, month, day, hour, minute, donate);
           System.out.println("Thank you so much for using our services!" +
                   "\nWe hope too see you again soon!");
       }
       else
           System.out.println("Oh no! Please run the program again.");
*/
       WriteToFile(filename, first, last, phone, month, day, hour, minute, donate);
       System.out.println("Thank you so much for using our services!" +
               "\nWe hope too see you again soon!");
   }



   //WRITES DATA TO FILE--------------------------------------------------------------------
   public static void WriteToFile(String filename, String f, String l, String p, int mo, int d, int h, int mi, String don) {
       try {
           FileWriter file = new FileWriter(filename);
           file.write("**********************************************************\n");
           file.write("\tHere is your appointment info for\n" +
                           "\tMoreno Valley College Community Garden\n");
           file.write("First name: " + f + "\n");
           file.write("Last name: " + l + "\n");
           file.write("Phone number: " + p + "\n");
           file.write("Date: " + mo + "/" + d + "/" + 2021 + "\n");
           file.write("Time: " + h + ":" + mi + "\n");
           file.write("Donation: " + don + "\n");
           file.close();
           System.out.println("Your appointment information has now been saved to a file.");
       } catch (IOException e) {
           System.out.println("An error occurred!");
       }
   }
}

