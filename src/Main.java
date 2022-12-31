public class Main {
    // The time complexity of the current function is O(n^2).
    public static void main(String[] args) {
        RealEstate other = new RealEstate();
        System.out.println("Welcome to the real estate bulletin board!");
        int num, num1;
        User user;
        do {
            System.out.println("Please choose one of the options below:");
            System.out.println("Type 1 to create an account.");
            System.out.println("Type 2 to connect to an existing account.");
            System.out.println("Type 3 to finish the program.");
            num = other.checkDigAnswer(1, 3);
            switch (num) {
                case 1 -> other.createUser();
                case 2 -> {
                    user = other.logIn();
                    if (user == null) {
                        System.out.println("Invalid user or password");
                    } else {
                        boolean switchContinue = true;
                        do {
                            System.out.println("Please choose one of the options below:");
                            System.out.println("Type 1 to publish a new property.");
                            System.out.println("Type 2 to remove advertising on a property.");
                            System.out.println("Type 3 to display all properties in the system.");
                            System.out.println("Type 4 to show all properties published by the user.");
                            System.out.println("Type 5 to Search for a property by parameters.");
                            System.out.println("Type 6 to log out and return to the main menu.");

                            num1 = other.checkDigAnswer(1, 6);
                            switch (num1) {
                                case 1 -> {
                                    if (other.postNewProperty(user))
                                        System.out.println("New property saved successfully");
                                    else
                                        System.out.println("New property  is not saved");
                                }
                                case 2 -> other.removeProperty(user);
                                case 3 -> other.printAllProperties();
                                case 4 -> other.printProperties(user);
                                case 5 -> {
                                    Property[] survey = other.search(); // survey is never used, because I don't need to
                                    // use it by the instructions, it just gives a name to other.search().
                                }
                                case 6 -> switchContinue = false;
                            }
                        } while (switchContinue);
                    }
                }
                case 3 -> System.out.println("Finish the program!");
                default -> System.out.println("Invalid option number!");
            }
        } while (num != 3);
    }
}