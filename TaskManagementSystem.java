import java.io.*;
import java.util.*;

class Task {
    private String title;
    private String description;
    private int priority;
    private boolean isCompleted;

    public Task(String title, String description, int priority) {
        this.title = title;
        this.description = description;sss
        this.priority = priority;
        this.isCompleted = false;
    }

    public String getTitle() {
        return title;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return title + ";" + description + ";" + priority + ";" + isCompleted;
    }

    public static Task fromString(String taskString) {
        String[] parts = taskString.split(";");
        Task task = new Task(parts[0], parts[1], Integer.parseInt(parts[2]));
        task.isCompleted = Boolean.parseBoolean(parts[3]);
        return task;
    }
}

class TaskManager {
    private List<Task> tasks = new ArrayList<>();
    private final String fileName = "tasks.txt";

    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Task added successfully.");
    }

    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to display.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(tasks.get(i).toString().replace(";", " | "));
            }
        }
    }

    public void saveTasksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < tasks.size(); i++) {
                writer.write(tasks.get(i).toString());
                writer.newLine();
            }
            System.out.println("Tasks saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }

    public void markTaskAsCompleted(String title) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTitle().equalsIgnoreCase(title)) {
                tasks.get(i).markAsCompleted();
                System.out.println("Task marked as completed.");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    public void sortTasks() {
        for (int i = 0; i < tasks.size() - 1; i++) {
            for (int j = 0; j < tasks.size() - i - 1; j++) {
                if (tasks.get(j).getPriority() > tasks.get(j + 1).getPriority()) {
                    Task temp = tasks.get(j);
                    tasks.set(j, tasks.get(j + 1));
                    tasks.set(j + 1, temp);
                }
            }
        }
        System.out.println("Tasks sorted by priority.");
    }
}

public class TaskManagementSystem {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nTask Management System");
            System.out.println("1. Add Task");
            System.out.println("2. List Tasks");
            System.out.println("3. Save Tasks to File");
            System.out.println("4. Mark Task as Completed");
            System.out.println("5. Sort Tasks by Priority");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        System.out.print("Enter task title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter task description: ");
                        String description = scanner.nextLine();
                        System.out.print("Enter task priority (1-5): ");
                        int priority = Integer.parseInt(scanner.nextLine());
                        taskManager.addTask(new Task(title, description, priority));
                        break;
                    case 2:
                        taskManager.listTasks();
                        break;
                    case 3:
                        taskManager.saveTasksToFile();
                        break;
                    case 4:
                        System.out.print("Enter the title of the task to mark as completed: ");
                        String taskTitle = scanner.nextLine();
                        taskManager.markTaskAsCompleted(taskTitle);
                        break;
                    case 5:
                        taskManager.sortTasks();
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
