package kindergarten;
/**
 * This class represents a Classroom, with:
 * - an SNode instance variable for students in line,
 * - an SNode instance variable for musical chairs, pointing to the last student in the list,
 * - a boolean array for seating availability (eg. can a student sit in a given seat), and
 * - a Student array parallel to seatingAvailability to show students filed into seats 
 * --- (more formally, seatingAvailability[i][j] also refers to the same seat in studentsSitting[i][j])
 * 
 */
public class Classroom {
    private SNode studentsInLine;         // when students are in line: references the FIRST student in the LL
    private SNode musicalChairs;          // when students are in musical chairs: references the LAST student in the CLL
    private boolean[][] seatingLocation;  // represents the classroom seats that are available to students
    private Student[][] studentsSitting;  // when students are sitting in the classroom: contains the students

    /**
     * Constructor for classrooms. Do not edit.
     * @param l passes in students in line
     * @param m passes in musical chairs
     * @param a passes in availability
     * @param s passes in students sitting
     */
    public Classroom ( SNode l, SNode m, boolean[][] a, Student[][] s ) {
		studentsInLine  = l;
        musicalChairs   = m;
		seatingLocation = a;
        studentsSitting = s;
	}
    /**
     * Default constructor starts an empty classroom. Do not edit.
     */
    public Classroom() {
        this(null, null, null, null);
    }

    /**
     * This method simulates students standing in line and coming into the classroom (not leaving the line).
     * 
     * It does this by reading students from input file and inserting these students studentsInLine singly linked list.
     * 
     * 1. Open the file using StdIn.setFile(filename);
     * 
     * 2. For each line of the input file:
     *     1. read a student from the file
     *     2. create an object of type Student with the student information
     *     3. insert the Student object at the FRONT of the linked list
     * 
     * Input file has:
     * 1) one line containing an integer representing the number of students in the file, say x
     * 2) x lines containing one student per line. Each line has the following student 
     * information separated by spaces: FirstName LastName Height
     * 
     * To read a string using StdIn use StdIn.readString()
     * 
     * The input file has Students in REVERSE alphabetical order. So, at the end of this
     * method all students are in line in alphabetical order.
     * 
     * DO NOT implement a sorting method, PRACTICE add to front.
     * 
     * @param filename the student information input file
     */
    public void enterClassroom ( String filename ) {


            //opens file 

            StdIn.setFile(filename);

            int numberofstudents = StdIn.readInt();
            
        for(int i=0; i<numberofstudents; i++){
        
            String firstName = StdIn.readString();
            String lastName = StdIn.readString();
            int height = StdIn.readInt();
    
            
            Student new_Student = new Student(firstName, lastName, height);

            // Node points to studentsinline (first)
            SNode new_node = new SNode(new_Student, studentsInLine);
            // new_node becomes first node
            studentsInLine = new_node;
        }
    

    }

    

    /**
     * 
     * This method creates and initializes the seatingAvailability (2D array) of 
     * available seats inside the classroom. Imagine that unavailable seats are broken and cannot be used.
     *      
     * 1. Open the file using StdIn.setFile(seatingChart);
     * 
     * 2. You will read the seating chart input file with the format:
     * An integer representing the number of rows in the classroom, say r
     * An integer representing the number of columns in the classroom, say c
     * Number of r lines, each containing c true or false values (true represents that a 
     * seat is present in that column)
     * 
     * 3. Initialize seatingLocation and studentsSitting arrays with r rows and c columns
     * 
     * 4. Update seatingLocation with the boolean values read from the input file
     * 
     * This method does not seat students on the seats.
     * 
     * @param seatingChart the seating chart input file
     */
    public void setupSeats(String seatingChart) {

        StdIn.setFile(seatingChart);

        int rows = StdIn.readInt();
        int cols = StdIn.readInt();

        seatingLocation = new boolean[rows][cols];
        studentsSitting = new Student[rows][cols];

        for(int r = 0; r <rows; r++){
            for(int c = 0; c<cols; c++){
                    seatingLocation[r][c] = StdIn.readBoolean();
            }

        }   

    }

    /**
     * 
     * This method simulates students standing inline and then taking their seats in the classroom.
     * 
     * 1. Starting from the front of the studentsInLine singly linked list
     * 2. Remove one student at a time from the list and insert the student into studentsSitting according to
     *    seatingLocations
     * 
     * studentsInLine will then be empty
     * 
     * If the students just played musical chairs, the winner of musical chairs is seated separately 
     * by seatMusicalChairsWinner().
     */
    public void seatStudents () {


        // studentsSitting[0][0] = studentsInLine.getStudent();

        // while (studentsInLine.getNext() != null){

        //         Student stud = studentsInLine.getStudent();

                 for(int r = 0; r<seatingLocation.length; r++){
                     for(int c = 0; c<seatingLocation[r].length; c++){
                         if(seatingLocation[r][c] == true){
                            if(studentsInLine != null){
                                studentsSitting[r][c] = studentsInLine.getStudent();
                                studentsInLine = studentsInLine.getNext();
                            }
                         }
                        }
                     }

             }


    /**
     * Traverses studentsSitting row-wise (starting at row 0) removing a seated
     * student and adding that student to the end of the musicalChairs list.
     * 
     * row-wise: starts at index [0][0] traverses the entire first row and then moves
     * into second row.
     */
    public void insertMusicalChairs () {
        
        //musicalChairs = new Snode


        for(int i = 0; i<studentsSitting.length; i++){
            for(int j = 0; j< studentsSitting[i].length; j++){
                if(studentsSitting[i][j] != null){
                    
                    SNode temp = new SNode(studentsSitting[i][j], null);
                    if(musicalChairs == null){
                        musicalChairs = temp; 
                        musicalChairs.setNext(musicalChairs);
                        studentsSitting[i][j] = null;
                    }
                    else{
                        temp.setNext(musicalChairs.getNext());
                        musicalChairs.setNext(temp);
                        musicalChairs = temp;
                        studentsSitting[i][j] = null;

                    }

                }
            }
        }


    }

    /**
     * 
     * Removes a random student from the musicalChairs.
     * 
     * @param size represents the number of students currently sitting in musicalChairs
     * 
     * 1. Select a random student to be removed from the musicalChairs CLL.
     * 2. Searches for the selected student, deletes that student from musicalChairs
     * 3. Calls insertByHeight to insert the deleted student into studentsInLine list.
     * 
     * Requires the use of StdRandom.uniform(int b) to select a random student to remove,
     * where b is the number of students currently sitting in the musicalChairs.
     * 
     * The random value denotes the refers to the position of the student to be removed
     * in the musicalChairs. 0 is the first student
     */
    public void moveStudentFromChairsToLine(int size) {
        //musical chairs is last node
        int random = StdRandom.uniform(size);
        // SNode tail = musicalChairs;
        SNode head = musicalChairs.getNext();
      
        System.out.println(random);
    
    // for(int i = 0; i<size; i++) {  
        if(random == 0){
            SNode temp = head;

            System.out.println("temp: " + temp.getStudent().print());
            System.out.println("head: " + head.getStudent().print());

            temp = temp.getNext();                               
            insertByHeight(head.getStudent());
            head = temp;
            musicalChairs.setNext(head);

            System.out.println("temp: " + temp.getStudent().print());
            System.out.println("head: " + head.getStudent().print());

            //break;
        }
        else if(random == size-1){
            SNode temp1 = head;
            SNode ptr  = temp1.getNext();

            System.out.println("temp1: " + temp1.getStudent().print());
            System.out.println("ptr: " + ptr.getStudent().print());


            while(ptr.getNext()!=head){
                temp1 = ptr;
                ptr = ptr.getNext();
            System.out.println("temp1: " + temp1.getStudent().print());
            System.out.println("ptr: " + ptr.getStudent().print());
            }
            insertByHeight(ptr.getStudent());
            // temp1.setNext(musicalChairs);
            musicalChairs = temp1;
            System.out.println("temp1: " + temp1.getStudent().print());
            System.out.println("ptr: " + ptr.getStudent().print());

            musicalChairs.setNext(head);
            //break;
            
        }
        else{   
            SNode temp2 = head;
            SNode ptr1 = temp2.getNext();

            System.out.println("temp2: " + temp2.getStudent().print());
            System.out.println("ptr1: " + ptr1.getStudent().print());

            for(int j = 0; j<random-1; j++){
                temp2 = ptr1;
                ptr1=ptr1.getNext();
                System.out.println("temp2: " + temp2.getStudent().print());
                System.out.println("ptr1: " + ptr1.getStudent().print());

            }
            insertByHeight(temp2.getNext().getStudent());
            temp2.setNext(ptr1.getNext());
            System.out.println("temp2: " + temp2.getStudent().print());
            System.out.println("ptr1: " + temp2.getNext().getStudent().print());

            //break;

        }

    }

        //+ " " + front.getStudent().getHeight()

        // for(int i = 0; i<=size; i++){
        //     System.out.println("i " + i);
        //     System.out.println("Previous: " + previous.getStudent().print());
        //     System.out.println("Front: " + front.getStudent().print());
        //     //breaks when the index is equal to i.
            
        //     if(i==size){
        //         previous.setNext(front.getNext());
        //             System.out.println("prev. next " + previous.getNext().getStudent().print());
        //             System.out.println("mc " + musicalChairs.getStudent().print());
        //             System.out.println("mc next " + musicalChairs.getNext().getStudent().print());
    
        //             insertByHeight(front.getStudent());
        //             front.setNext(null);
        //             musicalChairs = previous;
        //             front = musicalChairs.getNext();

        //     }
        //     else if(random == i){
        //         //deletes the node
                    
        //             // System.out.println("before");
        //             // System.out.println(front.getStudent().print());
        //             // System.out.println(previous.getStudent().print());
        //             System.out.println(front.getNext().getStudent().print());



        //             printMusicalChairs();

        //             previous.setNext(front.getNext());
        //             System.out.println(previous.getNext().getStudent().print());
        //             System.out.println("mc " + musicalChairs.getStudent().print());
        //             System.out.println("mc next " + musicalChairs.getNext().getStudent().print());

        //             insertByHeight(front.getStudent());
        //             front.setNext(null);
        //             front = previous.getNext();

                    
        //             // // System.out.println(front.getNext().getStudent().print());

        //             // // System.out.println("after");
        //             // // System.out.println(front.getStudent().print());
        //             // // System.out.println(previous.getStudent().print());
        //             printMusicalChairs();

    
        //         break;


                
        //     }
        //     else{
        //         //keeps traversing the list
        //         // System.out.println("before traverse");
        //         // System.out.println(front.getStudent().print());
        //         // System.out.println(previous.getStudent().print());
                
        //         previous = previous.getNext();        
        //         front = front.getNext();
            
                    
        //     //     System.out.println("after traverse");
        //     //         System.out.println(front.getStudent().print());
        //     //         System.out.println(previous.getStudent().print());
            //  }
            // }
           




        // for(int i = 0; i< random; i++){
        //         previous = current;
        //         current = current.getNext();
        //         if(previous == null){
        //             musicalChairs.setNext(current.getNext());
        //         }
        //         else{
        //             previous.setNext(current.getNext());
        //         }



        //     }


    
    /**
     * Inserts a single student, eliminated from musical chairs, to the studentsInLine list.
     * The student is inserted in ascending order by height (shortest to tallest).
     * 
     * @param studentToInsert the student eliminated from chairs to be inserted into studentsInLine
     */
    public void insertByHeight(Student studentToInsert) {
        
        //checks is first node is empty or if node is smaller than first node

        System.out.println("Student to insert: " + studentToInsert.print());

        if (studentsInLine == null || studentsInLine.getStudent().getHeight() > studentToInsert.getHeight()){
            

            SNode student = new SNode(studentToInsert, studentsInLine);
            studentsInLine = student;
            
            System.out.println("in line: " + studentsInLine.getStudent().print());

            printStudentsInLine();

        }
        else{
           
        
        
        SNode ptr = studentsInLine;

        while (ptr.getNext() != null && ptr.getNext().getStudent().getHeight() <= studentToInsert.getHeight()) {
            //traverses until .next = target

            System.out.println("ptr");
            System.out.println(ptr.getStudent().print());

            ptr = ptr.getNext();


        }

        // adds node to students in line

        printStudentsInLine();

        SNode student = new SNode(studentToInsert, ptr.getNext());
        ptr.setNext(student);

        System.out.println("ptr");
        System.out.println(ptr.getStudent().print());

        printStudentsInLine();


        }
    }


    //     else if(studentsInLine.getStudent().getHeight() > studentToInsert.getHeight()){
    //             SNode insert = new SNode(studentToInsert, studentsInLine);
    //             studentsInLine = insert;
    //         }
    //         else if(studentsInLine.getStudent().getHeight() < studentToInsert.getHeight()){
    //             SNode insert = new SNode(studentToInsert, null);
    //             studentsInLine.setNext(insert);
    //         }
    //         else { 
    //             SNode insert = new SNode(studentToInsert, null);
    //             studentsInLine.setNext(insert);
    //         }

        
    //     }
    // }

    /**
     * 
     * Removes eliminated students from the musicalChairs and inserts those students 
     * back in studentsInLine in ascending height order (shortest to tallest).
     * 
     * At the end of this method, all students will be in studentsInLine besides 
     * the winner, who will be in musicalChairs alone.
     * 
     * 1. Find the number of students currently on musicalChairs
     * 2. While there are more than 1 student in musicalChairs, call moveRandomStudentFromChairsToLine()
     */
    public void eliminateLosingStudents() {
        
        SNode ptr = musicalChairs.getNext();
        int size = 1;
        // do {
        //     ptr = ptr.getNext();
        //     size = size + 1;
        // } while(ptr != musicalChairs.getNext());
            
        while(ptr != musicalChairs){
        ptr = ptr.getNext();
        size = size + 1;
        }

        System.out.println("size: " + size);
       
        //ptr.getNext() != ptr

        while(size > 1){

            moveStudentFromChairsToLine(size);
            size = size-1;

        }
    //    }
       
    }       



    /* 
     * If musicalChairs (circular linked list) contains ONLY ONE student (the winner), 
     * this method removes the winner from musicalChairs and inserts that student 
     * into the first available seat in studentsSitting. musicChairs will then be empty.
     * 
     * This only happens when the musical chairs was just played.
     * 
     * This methods does nothing if there is more than one student in musicalChairs 
     * or if musicalChairs is empty.
     */
    public void seatMusicalChairsWinner () {

        
        System.out.println(musicalChairs.getStudent().print());
            for(int i = 0; i<studentsSitting.length;i++){
                if(musicalChairs == null){
                    break;
                }
                for(int j = 0; j<studentsSitting[i].length; j++){
                    if(seatingLocation[i][j] == true){
                       System.out.println(musicalChairs.getStudent().print());
                       studentsSitting[i][j] = musicalChairs.getStudent();    
                       musicalChairs = null;
                       seatingLocation[i][j] = false;
                       break;
                    }
                }
            }   

        }


    /**
     * 
     * This method represents the game of musical chairs!
     * 
     * This method calls previously written methods to repeatedly remove students from 
     * the musicalChairs until there is only one student (the winner), seats the winner
     * and seat the students from the studentsInline.
     * 
     * *****DO NOT****** UPDATE THIS METHOD
     */
    public void playMusicalChairs() {

	/* DO NOT UPDATE THIS METHOD */
        eliminateLosingStudents();
        seatMusicalChairsWinner();
        seatStudents();
    } 

    /**
     * Used by driver to display students in line
     * DO NOT edit.
     */
    public void printStudentsInLine () {

        //Print studentsInLine
        StdOut.println ( "Students in Line:" );
        if ( studentsInLine == null ) { StdOut.println("EMPTY"); }

        for ( SNode ptr = studentsInLine; ptr != null; ptr = ptr.getNext() ) {
            StdOut.print ( ptr.getStudent().print());
            if ( ptr.getNext() != null ) { StdOut.print ( " -> " ); }
        }
        StdOut.println();
        StdOut.println();
    }

    /**
     * Prints the seated students; can use this method to debug.
     * DO NOT edit.
     */
    public void printSeatedStudents () {

        StdOut.println("Sitting Students:");

        if ( studentsSitting != null ) {
        
            for ( int i = 0; i < studentsSitting.length; i++ ) {
                for ( int j = 0; j < studentsSitting[i].length; j++ ) {

                    String stringToPrint = "";
                    if ( studentsSitting[i][j] == null ) {

                        if (seatingLocation[i][j] == false) {stringToPrint = "X";}
                        else { stringToPrint = "EMPTY"; }

                    } else { stringToPrint = studentsSitting[i][j].print();}

                    StdOut.print ( stringToPrint );
                    
                    for ( int o = 0; o < (10 - stringToPrint.length()); o++ ) {
                        StdOut.print (" ");
                    }
                }
                StdOut.println();
            }
        } else {
            StdOut.println("EMPTY");
        }
        StdOut.println();
    }

    /**
     * Prints the musical chairs; can use this method to debug.
     * DO NOT edit.
     */
    public void printMusicalChairs () {
        StdOut.println ( "Students in Musical Chairs:" );

        if ( musicalChairs == null ) {
            StdOut.println("EMPTY");
            StdOut.println();
            return;
        }
        SNode ptr;
        //ptr = musicalChairs.getNext();
        //if(ptr != null){
        for ( ptr = musicalChairs.getNext(); ptr != musicalChairs; ptr = ptr.getNext() ) {
            StdOut.print(ptr.getStudent().print() + " -> ");
        }
        if ( ptr == musicalChairs) {
            StdOut.print(musicalChairs.getStudent().print() + " - POINTS TO FRONT");
        }
        StdOut.println();
    }

    /**
     * Prints the state of the classroom; can use this method to debug.
     * DO NOT edit.
     */
    public void printClassroom() {
        printStudentsInLine();
        printSeatedStudents();
        printMusicalChairs();
    }

    /**
     * Used to get and set objects.
     * DO NOT edit.
     */

    public SNode getStudentsInLine() { return studentsInLine; }
    public void setStudentsInLine(SNode l) { studentsInLine = l; }

    public SNode getMusicalChairs() { return musicalChairs; }
    public void setMusicalChairs(SNode m) { musicalChairs = m; }

    public boolean[][] getSeatingLocation() { return seatingLocation; }
    public void setSeatingLocation(boolean[][] a) { seatingLocation = a; }

    public Student[][] getStudentsSitting() { return studentsSitting; }
    public void setStudentsSitting(Student[][] s) { studentsSitting = s; }

}
