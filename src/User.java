public class User {
    private static final String[] USERTYPE = {"real estate broker", "private user"};
    private String userName;
    private String password;
    private String telNumber;
    private int userType;

    // The time complexity of the current constructor is O(1).
    public User(String inUserName, String inPassword, String inTelNUmber, int inUserType) {
        userName = inUserName;
        password = inPassword;
        telNumber = inTelNUmber;
        userType = inUserType;
    }

    // The time complexity of the current function is O(1).
    public boolean ifTheSame(String inName, String inPassword) {
        return inName.equals(userName) && (inPassword.isEmpty() || inPassword.equals(password));
    }

    // The time complexity of the current function is O(1).
    public String getUserName() {
        return userName;
    }

    // The time complexity of the current function is O(1).
    public int getLimit() {
        if (userType == 1) return 5;
        else return 2;
    }

    // The time complexity of the current function is O(1).
    public String toString() {
        String outputStr = "";
        outputStr += userName + " " + telNumber + " (" + USERTYPE[userType - 1] + ").\n";
        return outputStr;
    }
}
