import java.util.*;
import java.text.DecimalFormat;

public class StudentGradeTracker {
    static class Student {
        String name;
        double score;
        Student(String name, double score) { this.name = name; this.score = score; }
        public String toString() { return name + " - " + fmt(score); }
    }

    static DecimalFormat df = new DecimalFormat("#.##");
    static String fmt(double v) { return df.format(v); }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
        while (true) {
            System.out.println("\n--- Student Grade Tracker ---");
            System.out.println("1) Add student");
            System.out.println("2) Edit student");
            System.out.println("3) Remove student");
            System.out.println("4) Show all students");
            System.out.println("5) Show summary (average, highest, lowest)");
            System.out.println("6) Export as CSV (prints to screen)");
            System.out.println("0) Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": addStudent(sc, students); break;
                case "2": editStudent(sc, students); break;
                case "3": removeStudent(sc, students); break;
                case "4": listStudents(students); break;
                case "5": showSummary(students); break;
                case "6": exportCSV(students); break;
                case "0": System.out.println("Goodbye."); sc.close(); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    static void addStudent(Scanner sc, ArrayList<Student> students) {
        System.out.print("Student name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Name cannot be empty."); return; }
        Double score = askForScore(sc);
        if (score == null) return;
        students.add(new Student(name, score));
        System.out.println("Added: " + name + " (" + fmt(score) + ")");
    }

    static Double askForScore(Scanner sc) {
        while (true) {
            System.out.print("Score (0 - 100): ");
            String s = sc.nextLine().trim();
            try {
                double v = Double.parseDouble(s);
                if (v < 0 || v > 100) { System.out.println("Score must be 0–100."); continue; }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    static void listStudents(ArrayList<Student> students) {
        if (students.isEmpty()) { System.out.println("No students yet."); return; }
        System.out.println("\nStudents:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i+1) + ") " + students.get(i));
        }
    }

    static void editStudent(Scanner sc, ArrayList<Student> students) {
        if (students.isEmpty()) { System.out.println("No students to edit."); return; }
        listStudents(students);
        System.out.print("Enter number to edit: ");
        String s = sc.nextLine().trim();
        try {
            int idx = Integer.parseInt(s) - 1;
            if (idx < 0 || idx >= students.size()) { System.out.println("Invalid index."); return; }
            Student st = students.get(idx);
            System.out.print("New name (leave empty to keep '" + st.name + "'): ");
            String newName = sc.nextLine().trim();
            if (!newName.isEmpty()) st.name = newName;
            System.out.print("Change score? (y/N): ");
            String c = sc.nextLine().trim().toLowerCase();
            if (c.equals("y") || c.equals("yes")) {
                Double ns = askForScore(sc);
                if (ns != null) st.score = ns;
            }
            System.out.println("Updated: " + st);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    static void removeStudent(Scanner sc, ArrayList<Student> students) {
        if (students.isEmpty()) { System.out.println("No students to remove."); return; }
        listStudents(students);
        System.out.print("Enter number to remove: ");
        String s = sc.nextLine().trim();
        try {
            int idx = Integer.parseInt(s) - 1;
            if (idx < 0 || idx >= students.size()) { System.out.println("Invalid index."); return; }
            Student removed = students.remove(idx);
            System.out.println("Removed: " + removed);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    static void showSummary(ArrayList<Student> students) {
        if (students.isEmpty()) { System.out.println("No students yet."); return; }
        double sum = 0;
        Student high = students.get(0), low = students.get(0);
        for (Student s : students) {
            sum += s.score;
            if (s.score > high.score) high = s;
            if (s.score < low.score) low = s;
        }
        double avg = sum / students.size();
        System.out.println("\n--- Summary ---");
        System.out.println("Count: " + students.size());
        System.out.println("Average: " + fmt(avg));
        System.out.println("Highest: " + high.name + " (" + fmt(high.score) + ")");
        System.out.println("Lowest: " + low.name + " (" + fmt(low.score) + ")");
    }

    static void exportCSV(ArrayList<Student> students) {
        if (students.isEmpty()) { System.out.println("No students to export."); return; }
        System.out.println("\nname,score");
        for (Student s : students) {
            System.out.println("\"" + s.name.replace("\"", "\"\"") + "\"," + s.score);
        }
        System.out.println("\n(Copy the above CSV output if you want to save it.)");
    }
  }
