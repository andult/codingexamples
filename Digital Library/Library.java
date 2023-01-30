import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Library {
    //DECLARE VARIABLES--------------------------------------------------------
    public final static int LENDING_LIMIT = 5;
    private String name;                            //name of library
    private static int libraryCard;                 //current max library card num
    private List<Reader> readers;                   //readers registered to the library
    private HashMap<String, Shelf> shelves;         //subject, shelf object
    private HashMap<Book, Integer> books;           //book objects registered to the library & count

    //CONSTRUCTOR--------------------------------------------------------------
    public Library(String name) {
        this.name = name;
        this.libraryCard = 0;
        this.readers = new ArrayList<>();
        this.shelves = new HashMap<>();
        this.books = new HashMap<>();
    }

    //INIT FUNCTIONS-----------------------------------------------------------
    public Code init(String filename) {
        //parses the csv files into a collection of objects, calls other methods for aid
        File f = new File(filename);
        Scanner scan;

        //searches for file---
        try {
            scan = new Scanner(f);
        }
        catch(FileNotFoundException e) {
            return Code.FILE_NOT_FOUND_ERROR;
        }

        //reads data from csv---
        int bookCount = convertInt(scan.nextLine(), Code.BOOK_COUNT_ERROR);    //converts object string to num
        if (bookCount < 0) { return errorCode(bookCount); }
        initBooks(bookCount, scan);                 //sends object number and scanner position to function
        listBooks();

        int shelfCount = convertInt(scan.nextLine(), Code.SHELF_NUMBER_PARSE_ERROR);
        if (shelfCount < 0) { return errorCode(shelfCount); }
        initShelves(shelfCount, scan);

        int readerCount = convertInt(scan.nextLine(), Code.READER_COUNT_ERROR);
        if (readerCount < 0) { return errorCode(readerCount); }
        initReader(readerCount, scan);

        System.out.println("SUCCESS FROM INIT");
        return Code.SUCCESS;
    }

    private Code initBooks(int bookCount, Scanner scan) {
        if(bookCount < 1) {
            return Code.LIBRARY_ERROR;
        }

        System.out.println("Parsing " + bookCount + " books");
        int count = 0;
        while(scan.hasNextLine() && count < bookCount) {    //should iterate for every line in file until bookCount is reached
            //store data from a line in csv---
            String fileLine = scan.nextLine();              //reads line in from file
            String[] bookData = fileLine.split(",");  //splits string and stores it in array
            String isbnCSV = bookData[Book.ISBN_];
            String titleCSV = bookData[Book.TITLE_];
            String subjectCSV = bookData[Book.SUBJECT_];
            String pageCountCSV = bookData[Book.PAGE_COUNT_];
            String authorCSV = bookData[Book.AUTHOR_];
            String dueDateCSV = bookData[Book.DUE_DATE_];

            //convert data types and check for errors---
            int intPgCtCSV = convertInt(pageCountCSV, Code.PAGE_COUNT_ERROR);
            LocalDate intDuDaCSV = convertDate(dueDateCSV, Code.DATE_CONVERSION_ERROR);
            if (intPgCtCSV <= 0) {
                return Code.PAGE_COUNT_ERROR;
            }
            else if(intDuDaCSV == null) {
                return Code.DATE_CONVERSION_ERROR;
            }

            //creates a new book and sends to addBook()---
            Book book = new Book(isbnCSV, titleCSV, subjectCSV, intPgCtCSV, authorCSV, intDuDaCSV);
            addBook(book);
            count++;        //keeps track of book objects made
        }
        System.out.println("SUCCESS FROM INIT BOOKS");
        return Code.SUCCESS;

    }

    private Code initShelves(int shelfCount, Scanner scan) {
        if (shelfCount < 1) {               //checks for errors
            return Code.SHELF_COUNT_ERROR;
        }

        //loop to parse through csv---
        System.out.println("\nparsing " + shelfCount + " shelves");
        int count = 0;
        while(scan.hasNextLine() && count < shelfCount) {
            //store shelf data from csv---
            String fileLine = scan.nextLine();
            String[] shelfData = fileLine.split(",");
            String shelfNumCSV = shelfData[Shelf.SHELF_NUMBER_];
            String subjectCSV = shelfData[Shelf.SUBJECT];

            //convert data types---
            int intShelfNumCSV = convertInt(shelfNumCSV, Code.SHELF_NUMBER_PARSE_ERROR);
            if (intShelfNumCSV <= 0) {
                return Code.SHELF_NUMBER_PARSE_ERROR;
            }

            //adds shelf to the hashmap---
            Shelf shelf = new Shelf();
            shelf.setSubject(subjectCSV);
            shelf.setShelfNumber(intShelfNumCSV);
            addShelf(shelf);    //calls addBookToShelf()

            count++;        //keeps track of shelf objects made

        }

        //checks for errors in parsing (too many/little shelves made)
        if(shelves.size() != shelfCount) {
            System.out.println("Number of shelves doesn't match expected");
            return Code.SHELF_NUMBER_PARSE_ERROR;
        }

        System.out.println("SUCCESS FROM INIT SHELVES");
        return Code.SUCCESS;

    }

    private Code initReader(int readerCount, Scanner scan) {
        //checks for file errors---
        if(readerCount <= 0) {
            return Code.READER_COUNT_ERROR;
        }

        //loops through csv to get data---
        System.out.println("\nparsing " + readerCount + " readers");

        int count = 0;
        while(scan.hasNextLine() && count < readerCount) {
            //store shelf data from csv---
            String fileLine = scan.nextLine();
            String[] readerData = fileLine.split(",");
            String cardNumCSV = readerData[Reader.CARD_NUMBER_];
            String nameCSV = readerData[Reader.NAME_];
            String phoneCSV = readerData[Reader.PHONE_];
            String bcountCSV = readerData[Reader.BOOK_COUNT_];  //indicates how books in list
//            String bstartCSV = readerData[Reader.BOOK_START_];    //never used

            //convert data types---
            int intcardNum = convertInt(cardNumCSV, Code.SHELF_NUMBER_PARSE_ERROR);
            int intBCount = convertInt(bcountCSV, Code.BOOK_COUNT_ERROR);

            //add reader to list---
            Reader read = new Reader(intcardNum, nameCSV, phoneCSV);
            addReader(read);

            //add corresponding books to reader---
            for (int i = Reader.BOOK_START_; i < (intBCount*2+4); i+=2) {   //loops through books
                Book book = getBookByISBN(readerData[i]);   //check if book exists
                if(book == null) {
                    System.out.println("ERROR");
                }

                //if found, convert & set due date---
                String date = readerData[i+1];
                LocalDate dueDate = convertDate(date, Code.DATE_CONVERSION_ERROR);
                book.setDueDate(dueDate);   //TODO: is this the method we're supposed to use
                checkOutBook(read, book);   //checkout book
            }
            count++;        //keeps track of shelf objects made
        }
        System.out.println("SUCCESS FROM INIT READER");
        return Code.SUCCESS;
    }

    //ADD FUNCTIONS------------------------------------------------------------
    public Code addBook(Book newBook) {
        //*hidden due to plagiarism*
    }

    public Code addShelf(String shelfSubject) {
        //creates and sets shelf objects bc we cant 'new Shelf(subject, num)'
        //used when called from main
        Shelf shelf = new Shelf();
        shelf.setShelfNumber(shelves.size() + 1);   //create a new shelf *previous shelf was 2 new one is 3*
        shelf.setSubject(shelfSubject);             //assign subject
         return addShelf(shelf);
    }

    public Code addShelf(Shelf shelf) {
       //*hidden due to plagiarism*
    }

    private Code addBookToShelf(Book book, Shelf shelf) {
        //*hidden due to plagiarism*
    }

    public Code addReader(Reader reader) {
       //*hidden due to plagiarism*
    }

    //RETURN & REMOVE FUNCTIONS------------------------------------------------
    public Code returnBook(Reader reader, Book book) {
            //*hidden due to plagiarism*
    }

    public Code returnBook(Book book) {
           //*hidden due to plagiarism*
    }

    public Code removeReader(Reader reader) {
            //*hidden due to plagiarism*

    }

    //LIST FUNCTIONS-----------------------------------------------------------
    public int listBooks() {
            //*hidden due to plagiarism*
    }

    public Code listShelves(boolean showbooks) {
        //*hidden due to plagiarism*
    }

    public int listReaders() {
        //*hidden due to plagiarism*
    }

    public int listReaders(boolean showBooks) {
        //*hidden due to plagiarism*
    }

    //CONVERTER FUNCTIONS------------------------------------------------------
    public static int convertInt(String recordCountString, Code code) {
        try {
            int num = Integer.parseInt(recordCountString);      //turns string "3" into an int 3
            return num;
        }
        catch (NumberFormatException e) {
            System.out.println("Value which caused the error: " + recordCountString);
            System.out.println("Error message: " + code.getMessage());
            return code.getCode();
        }
    }
    
    public static LocalDate convertDate(String date, Code errorCode) {
        //*hidden due to plagiarism*
    }

    //GETTER FUNCTIONS---------------------------------------------------------
    public Reader getReaderByCard(int cardNumber) {
        //looks for reader by cardNumber---
        for (Reader read : readers) {
            if(read.getCardNumber() == cardNumber)
                return read;
        }

        System.out.println("Could not find a reader with card #" + cardNumber);
        return null;
    }

    public Shelf getShelf(Integer shelfNumber) {
        //returns shelf with matching shelfNumber from map---
        for (Shelf shelf:shelves.values()) {
            if (shelf.getShelfNumber() == shelfNumber) {
                return shelf;
            }
        }

        System.out.println("No shelf number " + shelfNumber + " found");
        return null;
    }

    public Shelf getShelf(String subject) {
        //returns shelf with matching subject from map---
        if (shelves.containsKey(subject)) {
            return shelves.get(subject);
        }

        System.out.println("No shelf for " + subject + " books");
        return null;
    }

    public static int getLibraryCard() {
        return libraryCard++;
    }

    public Book getBookByISBN(String isbn) {
        //looks for book in map by isbn---
        for (Book book: books.keySet()) {       //loops through map
            if (book.getISBN().equals(isbn)){
                return book;
            }
        }

        System.out.println("ERROR: Could not find a book with isbn: " + isbn);
        return null;
    }

    //OTHER FUNCTIONS----------------------------------------------------------
    public Code checkOutBook(Reader reader, Book book) {
        //*hidden due to plagiarism*
    }

    private Code errorCode(int codeNumber) {
        //loops through enum objects and finds matching pair---
        for (Code code: Code.values()) {
            if (code.getCode() == codeNumber) {
                return code;
            }
        }
        return Code.UNKNOWN_ERROR;
    }
}
