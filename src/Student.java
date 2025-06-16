public class Student {
    private String name;
    private int tamil;
    private int english;
    private int maths;
    private int science;
    private int social;
    private double average;
    private char grade;

    // Constructor
    public Student(String name, int tamil, int english, int maths, int science, int social, double average, char grade) {
        this.name = name;
        this.tamil = tamil;
        this.english = english;
        this.maths = maths;
        this.science = science;
        this.social = social;
        this.average = average;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }


    public int getTamil() {
        return tamil;
    }


    public int getEnglish() {
        return english;
    }


    public int getMaths() {
        return maths;
    }


    public int getScience() {
        return science;
    }


    public int getSocial() {
        return social;
    }


    public double getAverage() {
        return average;
    }


    public char getGrade() {
        return grade;
    }

}
