#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <unistd.h>
#include <time.h>

/*
 * A very simple shell that supports only commands 'exit', 'help', and 'today'.
 */

#define MAX_BUF 160
#define MAX_TOKS 100

int main() {
	int ch;
	char *pos;
	char s[MAX_BUF+2];   // 2 extra for the newline and ending '\0'
	static const char prompt[] = "msh> ";
	char *toks[MAX_TOKS];

	while (1) {
		// prompt for input if input from terminal
		if (isatty(fileno(stdin))) {
			printf(prompt);
		}

		// read input
		char *status = fgets(s, MAX_BUF+2, stdin);

		// exit if ^d entered
		if (status == NULL) {
			printf("\n");
			break;
		}

		// input is too long if last character is not newline 
		if ((pos = strchr(s, '\n')) == NULL) {
			printf("error: input too long\n");
			// clear the input buffer
			while ((ch = getchar()) != '\n' && ch != EOF) ;
			continue; 
		}

		// remove trailing newline
		*pos = '\0';

		//
		// YOUR CODE HERE-----------------------------------------------------------
		char *token;
		const char delim[2] = " ";
		token = strtok(s, delim);
		
		if(token == NULL){	//prompt for more input if there are no tokens
			printf("enter 'help', 'today', or 'exit' to quit\n");
		}
		else if(strcmp(token,"help") == 0){	//print options
			printf("enter 'help', 'today', or 'exit' to quit\n");
		} 
		else if(strcmp(token,"exit") == 0){	//exit program
			printf("\n");
			break;
		} 
		else if(strcmp(token,"today") == 0){	//print todays date in mm/dd/yy format
			time_t seconds;
			time( &seconds );	//gets seconds since 01/01/1970, raw data
			struct tm *info;	//creates struct that stores seconds as readable data  
			info = localtime( &seconds );	//localtime will turn it into readable data
			printf("%02d/%02d/%02d\n", info->tm_mon+1, info->tm_mday-1, info->tm_year-100);
		} 	
		else{	
			// print tokens
			while (token != NULL){
				printf("token: \'%s\'\n", token);
				token = strtok(NULL, delim);
			}
		}	
		//--------------------------------------------------------------------------

	}
	exit(EXIT_SUCCESS);
}
