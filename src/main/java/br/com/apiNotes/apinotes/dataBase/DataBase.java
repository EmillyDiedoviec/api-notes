package br.com.apiNotes.apinotes.dataBase;

import br.com.apiNotes.apinotes.dtos.TasksDetail;
import br.com.apiNotes.apinotes.models.Task;
import br.com.apiNotes.apinotes.models.User;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public abstract class DataBase {
    private static ArrayList<User> users = new ArrayList<>();

    public static void addUser(User user){
        if(user.getEmail() == null){
            throw new RuntimeException("Usuário inválido");
        }

        users.add(user);
    }

    public static boolean userExitsByEmail(String email){
        var userFiltered = users.stream().filter((user) -> user.getEmail().equalsIgnoreCase(email)).findAny();
        return userFiltered.isPresent();
    }

    public static boolean passwordUser(String password, String repassword){
        if(password.equals(repassword) ){
            return true;
        }else{
            return false;
        }
    }

    public static User getUserByEmail(String email){
        var userFiltered = users.stream().filter(user -> user.getEmail().equals(email)).findAny();

        if(userFiltered.isEmpty()){
            return null;
        }

        return userFiltered.get();
    }

    public  static User getEmailUserTeste(String email){

        return DataBase.users.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst().orElseThrow();

    }

    public static List<TasksDetail> getAllTasks(String email){
        var user = getUserByEmail(email);
        var tasks = user.getTasks();

        if(tasks.isEmpty()){
            return null;
        }

        return tasks.stream().map(TasksDetail::new).toList();
    }

    public static Task getTaskByID(UUID idTask, String email){
        var user = getUserByEmail(email);
        var taskFiltered = user.getTasks().stream().filter(id -> id.getId().equals(idTask)).findAny();

        return taskFiltered.get();
    }
    public static User getEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("User not found for email: " + email));
    }

    public static List<User> getAllUsers(){
        return users;
    }

}
