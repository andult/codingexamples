package AppointmentDate;
import java.io.*;
import java.util.Scanner;

//--------------------------------------------------------------------------------------------
class PersonalInfo
{
   String firstName;
   String lastName;
   String phone;

   PersonalInfo (String f, String l, String p){
       firstName = f;
       lastName = l;
       phone = p;
   }
}
//--------------------------------------------------------------------------------------------
class Time
{
   int hour;
   int minute;

   public Time(int hour, int minute)
   {
       this.hour = hour;
       this.minute = minute;
   }
}
//------------------------------------------------------------------
class Date extends Time
{
   int day;
   int month;
   //----------------------------------------------------------
   Date (int d, int m, int hour, int min)
   {
       super(hour, min);
       day = d;
       month = m;
   }
   //----------------------------------------------------------
   public void VerifyHour(boolean isWeekend)
   {
       if(!isWeekend)
       {
           if (hour >= 8 && hour <= 20)    VerifyMin();
           else {
               System.out.println("You did not enter a valid time.");
               System.exit(0);
           }
       }
       else
           if (hour >= 8 && hour <= 17)    VerifyMin();
           else {
               System.out.println("You did not enter a valid time.");
               System.exit(0);
           }
   }
   //----------------------------------------------------------
    public void VerifyMin() {
       if (minute == 0 || minute == 50)    System.out.println();
       else {
           System.out.println("You did not enter a valid time.");
           System.exit(0);
       }

   }
}
//--------------------------------------------------------------------------------------------
class Choice{
   String[] donations = {"Money", "Seeds", "Soil", "Fertilizer", "Volunteer", "Tools", "None"};
   int choice;
   String c;
   //----------------------------------------------------------
   void DisplayMenu()
   {
       System.out.println("---OPTIONS MENU---");
       for (int i = 0; i < donations.length; i++)
       {
           System.out.println((i+1) + ". " + donations[i]);
       }
   }
   //----------------------------------------------------------
   String getChoice()
   {
       Scanner scanner = new Scanner(System.in);

       System.out.print("\nPick a number 1-7: ");
       choice = scanner.nextInt();
       scanner.close();

       switch (choice)
       {
           case 1:
           case 2:
           case 3:
           case 4:
           case 5:
           case 6:
           case 7: {
               System.out.println("You picked " + donations[choice - 1] + "!");
               c = donations[choice - 1];
               break;
           }
           default: {
               System.out.println("You did not enter a valid number.");
               System.exit(0);
           }
       }
       return c;
   }
}

