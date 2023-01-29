''' project module containing:
        	outFile function
        	class Verify
            	class Date
            	class Time	'''

#FUNCTION TO WRITE OUT FILE==============================================================
def outFile(date, time):
  oFile = open("appointment.txt", 'w')
  oFile.write("**********************************************************\n")
  oFile.write("You have an appointment at Andrea's Tutoring Service on: \n")
  oFile.write(date)
  oFile.write(' at ')
  oFile.write(time)
  oFile.close()
 
#CLASS TO VERIFY DATES, TIMES, AND APPOINTMENTS==========================================
class Verify():
  def validateMonth(self,  user_month):
    try:
      if user_month < 1 or user_month > 12:
    	print('Whoops! You did not enter a valid month!\nRemember to use numbers 1-12')
      else:
    	return True
    except ValueError:    #i dont think this part is working
      print('Whoops! You did not enter a number!')
  #----------------------------------------------------------------------------------------
  def validateDay(self, user_day):
    try:
      if user_day < 1 or user_day > 31:
    	print('Whoops! You did not enter a valid day!\nRemember to use numbers 1-31')
      else:
    	return True
    except ValueError:
      print('Whoops! You did not enter a number!')
  #----------------------------------------------------------------------------------------
  def validateYear(self, user_year):
    try:
      if user_year != 2020 and user_year != 2021:
    	print('Whoops! You did not enter a valid year!')
      else:
    	return True
    except ValueError:
      print('Whoops! You did not enter a number!')
  #----------------------------------------------------------------------------------------
  def validateHour(self, user_hour):
    try:
      if user_hour < 9 or user_hour > 16:
    	print('Whoops! You did not enter a valid hour!')
      else:
    	return True
    except ValueError:
      print('Whoops! You did not enter a number!')
  #----------------------------------------------------------------------------------------
  def validateMinute(self, user_min):
    try:
      if user_min != 0 and user_min != 50:
    	print('Whoops! You did not enter a valid minute!')
      else:
    	return True
    except ValueError:
      print('Whoops! You did not enter a number!')
  #----------------------------------------------------------------------------------------
  def confirmAppoint(self, month, day, year, hour, mi):
    date = '{}/{}/{}'.format(month, day, year)
    time = '{}:{}'.format(hour, mi)
    print("Confirm appointment: ", date, ' at ', time)
    answer = input('To confirm enter Y or N: ').upper()
    if answer == 'Y':
      outFile(date, time)
    else:
      return 1
 
#CLASS TO RECEIVE DATE FROM MAIN=========================================================
class Date(Verify):
  def __init__(self, month, day, year):
    self.month = month
    self.day = day
    self.year = year
    super().__init__()
  #calls the functions in Verify class to check user input-------------------------------
  def getDate(self):
    if Verify().validateMonth(self.month) == True:
      if Verify().validateDay(self.day) == True:
    	if Verify().validateYear(self.year) == True:
      	print()
    	else:
      	return False
      else:
    	return False
    else:
      return False
 
#CLASS TO RECEIVE TIME FROM MAIN=========================================================
class Time(Verify):
  def __init__(self, hour, minute):
    self.hour = hour
    self.minute = minute
    super().__init__()
  #calls the functions in Verify class to check user input-------------------------------
  def getTime(self):
    if Verify().validateHour(self.hour) == True:
      if Verify().validateMinute(self.minute) == True:
    	print()
      else:
    	return False
    else:
  	return False
''' project main '''

import final_project_mod as mod
import calendar   
 
#BRIEF INTRO-------------------------------------------------------------
intro = '''Hello! Welcome to Andrea's Tutoring Program!\n
This python program is to set up tutoring appointments up to 1
year in advance and provide the user's selection in a text file. '''
 
print(intro)
print()
wait = input("Press Enter to continue.")
print(calendar.calendar(2021))
print()
 
#GET DATE FOR APPOINTMENT------------------------------------------------
while True:
  month = int(input('Enter a month (1-12): '))
  day = int(input('Enter a day (1-31): '))
  year = int(input('Enter a year: '))
  date = mod.Date(month, day, year)
  print()
 
  if date.getDate() == False:
    continue
  else:
    print("Awesome!")
    break
 
#DISPLAY AVAILABLE TIMES--------------------------------------------------
print('Now pick a time.\nNote, options are in military time\nFor example: 1350 is 1:30\n')
appointments = [900, 950, 1000, 1050,
            	1100, 1150, 1200, 1250,
            	1300, 1350, 1400, 1450,
            	1500, 1550, 1600, 1650]
print(appointments[:4])
print(appointments[4:8])
print(appointments[8:12])
print(appointments[12:])
print()
 
#GET TIME FOR APPOINTMENT-------------------------------------------------
while True:
  hour = int(input('Enter the hour you want (9 - 16): '))
  mi = int(input('Enter the minute you want (00 or 50): '))
  time = mod.Time(hour, mi)
  print()
 
  if time.getTime() == False:
    continue
  else:
    break
 
#CONFIRM APPOINTMENT------------------------------------------------------
mod.Verify().confirmAppoint(month, day, year, hour, mi)
 
print()
print('A text file has been created with your appointment information.')
print('Thank you and have a good day!')

