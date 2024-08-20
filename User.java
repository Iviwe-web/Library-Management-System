package Library;

import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String name;
    protected String email;
    protected String phonenumber;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String email, String phonenumber) {
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phonenumber;
    }

    public void logout() {
        System.out.println("Logging out...");
        // This method will be called in the context of the session, so it might be just a placeholder
    }

    public void menu() {
    }
}


