import java.util.Scanner;

public class RealEstate {
    private static final String[] DISTRICTS = {"Negev", "South", "Center", "Sharon", "North"};
    private static final String[] CITIES = {"Bersheva", "Arad", "Ashdod", "Ashkelon", "Rehovot",
            "Tel-aviv", "Netania", "Kfar-Saba", "Haifa", "Maalot"};
    private static final int[][] CITY_INDEX = {{0, 1}, {2, 3}, {4, 5, 6}, {7}, {8, 9}};
    private static final String[] STREETS = {"Herzel", "Ben Gurion", "Rabin", "Menahem Begin", "Sokolov", "Tishrey"};
    private static final int[][] STREET_INDEX = {{1, 2, 3}, {1, 2, 4}, {1, 3, 5}, {2, 5}, {0, 1, 2, 4}, {0, 1, 2},
            {2, 3, 5}, {3, 4}, {0, 4, 5}, {0, 1}};
    private static final String[] SYMBOLS = {"$", "%", "_"};
    private static final String[] DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private User[] allUsers;
    private City[] allCities;
    private Property[] allProperties;

    // The time complexity of the current constructor is O(n^2).
    public RealEstate() {
        for (int i = 0; i < DISTRICTS.length; i++) {
            String district = DISTRICTS[i];
            for (int j = CITY_INDEX[i][0]; j <= CITY_INDEX[i][CITY_INDEX[i].length - 1]; j++) {
                String city = CITIES[j];
                int streetsLength = STREET_INDEX[j].length;
                String[] streets = new String[streetsLength];
                for (int k = 0; k < streetsLength; k++) {
                    streets[k] = STREETS[STREET_INDEX[j][k]];

                }
                City curCity = new City(district, city, streets);
                addCityToAllCities(curCity);
                //  System.out.println(allCities[allCities.length - 1].toString()); // If you like, you can use this print
                // to see the relation between the districts to the cities to the streets in every city. notice that
                // there might be the same street address in different cities while there is no connection between them.
            }
        }
    }

    // The time complexity of the current function is O(1).
    public boolean postNewProperty(User user) {
        String userName = user.getUserName();
        int limit = user.getLimit();

        if (!ifDuplicateUserCheck(userName, limit)) return false;
        int cityIndex = enterCityIndex();
        if (cityIndex < 0) return false;
        String city = allCities[cityIndex].getCityName();

        System.out.println("Streets list in " + city + ":");
        String street = enterStreet(cityIndex);
        if (street.isEmpty()) return false;

        int typePr = enterApartmentType(false);
        if (typePr == 4) return false;

        int floor = enterFloor(typePr);
        int rooms = enterRoomsNumber(false);
        String building = enterBuildingNumber();

        int rentOrSale = enterRentOrSale(false);
        if (rentOrSale == 3) return false;

        int price = enterPrice(false);

        Property newProperty = new Property(city, street, building, typePr,
                floor, rooms, price, rentOrSale, user);

        addPropertyToAllProperties(newProperty);
        return true;
    }

    // The time complexity of the current function is O(1).
    public void removeProperty(User user) {
        int allPropertiesNumber;
        int userPropertiesNumber = 0;
        if (allProperties != null) {
            String userName = user.getUserName();
            allPropertiesNumber = allProperties.length;
            int[] userPropertiesIndex = new int[allPropertiesNumber];
            for (int i = 0; i < allPropertiesNumber; i++) {
                Property currentProperty = allProperties[i];
                if (currentProperty.ifSameUser(userName)) {
                    userPropertiesIndex[userPropertiesNumber++] = i;
                    System.out.println(userPropertiesNumber + ". - " + currentProperty);
                }
            }
            if (userPropertiesNumber == 0) {
                System.out.println("User Properties list is empty. This procedure is not possible");
                return;
            }
            System.out.println("Insert property order number to delete");
            int deleteNumber = checkDigAnswer(1, userPropertiesNumber);
            if (deleteNumber == userPropertiesNumber + 1) {
                System.out.println("Invalid order number. Property not deleted");
            } else {
                removeFromAllProperties(userPropertiesIndex[deleteNumber - 1]);
                System.out.println("Property was successfully deleted");
            }
        } else
            System.out.println("All Properties list is empty. This procedure is not possible");
    }

    // The time complexity of the current function is O(n), because the "for" loops are seperated from each other.
    private void removeFromAllProperties(int inIndex) {
        int arraySize = allProperties.length;
        if (arraySize == 1) {
            allProperties = null;
            return;
        }
        Property[] newAllProperties = new Property[arraySize - 1];
        if (inIndex != 0) {
            for (int i = 0; i < inIndex; i++)
                newAllProperties[i] = allProperties[i];
        }
        if (inIndex != arraySize - 1) {
            for (int i = inIndex + 1; i < arraySize; i++)
                newAllProperties[i - 1] = allProperties[i];
        }
        allProperties = newAllProperties;
    }

    // The time complexity of the current function is O(n).
    public void printAllProperties() {
        if (allProperties == null)
            return;
        for (int i = 0; i < allProperties.length; i++) {
            System.out.println(allProperties[i].toString());
        }
    }

    // The time complexity of the current function is O(n).
    public void printProperties(User user) {
        if (allProperties == null)
            return;
        String userName = user.getUserName();
        for (int i = 0; i < allProperties.length; i++) {
            Property currentProperty = allProperties[i];
            if (currentProperty.ifSameUser(userName))
                System.out.println(currentProperty);
        }
    }

    // The time complexity of the current function is O(n).
    public User logIn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your user name:");
        String userName = scanner.nextLine();
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();
        if (allUsers != null) {
            for (int i = 0; i < allUsers.length; i++) {
                User user = allUsers[i];
                if (user.ifTheSame(userName, password))
                    return user;
            }
        }
        return null;
    }

    // The time complexity of the current function is O(1).
    public void createUser() {
        String username = enterUserName();
        String password = enterPassword();
        String phoneNumber = enterPhoneNumber();
        int userType = enterUserType();
        User curUser = new User(username, password, phoneNumber, userType);
        addUserToAllUsers(curUser);
        System.out.println(allUsers[allUsers.length - 1].toString());
    }

    // The time complexity of the current function is O(n).
    public Property[] search() {
        if (allProperties == null)
            return null;
        Property[] outProperty = new Property[allProperties.length];
        int selectedPropNumber = 0;
        int rentOrSale = enterRentOrSale(true);
        int typePr = enterApartmentType(true);
        int rooms = enterRoomsNumber(true);

        System.out.println("$ minimum price");
        int priceMin = enterPrice(true);
        System.out.println("$ maximum price");
        int priceMax = enterPrice(true);

        for (int i = 0; i < allProperties.length; i++) {
            Property curProperty = allProperties[i];
            if (curProperty.forSearchCheck(rentOrSale, typePr, rooms, priceMin, priceMax)) {
                outProperty[selectedPropNumber++] = curProperty;
            }
        }
        if (selectedPropNumber == 0) {
            System.out.println("Their is no result for your search");
            return null;
        }
        Property[] outProperty2 = new Property[selectedPropNumber];
        System.arraycopy(outProperty, 0, outProperty2, 0, selectedPropNumber);
        for (int i = 0; i < outProperty2.length; i++) {
            System.out.println((i + 1) + ". " + outProperty2[i].toString());
        }
        return outProperty2;
    }

    // The time complexity of the current function is O(1).
    // This method is public because Main class has to call for it.
    public int checkDigAnswer(int minA, int maxA) {
        Scanner scanner = new Scanner(System.in);
        boolean isInt = scanner.hasNextInt();
        int answer;
        if (!isInt) {
            scanner.nextLine();
            return maxA + 1;
        }
        answer = scanner.nextInt();
        if (answer < minA || answer > maxA)
            answer = maxA + 1;
        return answer;
    }

    // The time complexity of the current function is O(n).
    private void addUserToAllUsers(User curUser) {
        if (allUsers == null) {
            allUsers = new User[1];
            allUsers[0] = curUser;
        } else {

            int arraySize = allUsers.length;
            User[] newAllUsers = new User[arraySize + 1];
            for (int i = 0; i < arraySize; i++)
                newAllUsers[i] = allUsers[i];
            newAllUsers[arraySize] = curUser;
            allUsers = newAllUsers;
        }
    }

    // The time complexity of the current function is O(n).
    private void addCityToAllCities(City curCity) {
        if (allCities == null) {
            allCities = new City[1];
            allCities[0] = curCity;
        } else {

            int arraySize = allCities.length;
            City[] newAllCities = new City[arraySize + 1];
            for (int i = 0; i < arraySize; i++)
                newAllCities[i] = allCities[i];
            newAllCities[arraySize] = curCity;
            allCities = newAllCities;
        }
    }

    // The time complexity of the current function is O(n).
    private void addPropertyToAllProperties(Property currentProperty) {
        if (allProperties == null) {
            allProperties = new Property[1];
            allProperties[0] = currentProperty;
        } else {

            int arraySize = allProperties.length;
            Property[] newAllProperties = new Property[arraySize + 1];
            for (int i = 0; i < arraySize; i++)
                newAllProperties[i] = allProperties[i];
            newAllProperties[arraySize] = currentProperty;
            allProperties = newAllProperties;
        }
    }

    // The time complexity of the current function is O(n).
    private boolean ifDuplicateUserCheck(String userName, int limit) {
        int propertiesNumber = 0;
        if (allProperties != null) {
            for (int i = 0; i < allProperties.length; i++) {
                if (allProperties[i].ifSameUser(userName)) {
                    propertiesNumber++;
                    if (propertiesNumber > limit) {
                        System.out.println("the user properties limit has been exceeded");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // The time complexity of the current function is O(n).
    private int enterCityIndex() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Cities list:");
        for (int i = 0; i < allCities.length; i++) {
            System.out.print(" " + allCities[i].getCityName());
        }
        System.out.println(".");
        System.out.println("Insert valid City name");
        String city = scanner.nextLine();
        boolean fineCity = false;
        int cityIndex = 0;
        for (int i = 0; i < allCities.length; i++) {
            if (allCities[i].isSameCity(city)) {
                fineCity = true;
                cityIndex = i;
                break;
            }
        }
        if (!fineCity) {
            System.out.println("Invalid City name");
            cityIndex = -1;
        }
        return cityIndex;
    }

    // The time complexity of the current function is O(n), because the "for" loops are seperated from each other.
    private String enterStreet(int cityIndex) {
        Scanner scanner = new Scanner(System.in);
        String[] streets = allCities[cityIndex].getStreets();
        for (int i = 0; i < streets.length; i++) {
            System.out.print(" " + streets[i]);
        }
        System.out.println(".");
        System.out.println("Insert valid Street name");
        String street = scanner.nextLine();
        boolean fineStreet = false;
        for (int i = 0; i < streets.length; i++) {
            if (streets[i].equals(street)) {
                fineStreet = true;
                break;
            }
        }
        if (!fineStreet) {
            System.out.println("Invalid Street name");
            street = "";
        }
        return street;
    }

    // The time complexity of the current function is O(1).
    private int enterApartmentType(boolean survey) {
        System.out.println("1 - Regular apartment");
        System.out.println("2 - Penthouse");
        System.out.println("3 - Private building");
        System.out.println("Insert valid apartment type number");

        int checkMin;
        if (survey) checkMin = -999;
        else checkMin = 1;
        int typeOfProperty = checkDigAnswer(checkMin, 3);
        if (typeOfProperty == 4) {
            System.out.println("Invalid apartment type number");
        }
        return typeOfProperty;
    }

    // The time complexity of the current function is O(1).
    private int enterFloor(int typePr) {
        int floor = 0;
        if (typePr != 3) {
            System.out.println("Insert floor number");
            floor = checkDigAnswer(0, 100);
        }
        return floor;
    }

    // The time complexity of the current function is O(1).
    private int enterRoomsNumber(boolean survey) {
        System.out.println("Insert rooms number");

        int checkMin;
        if (survey) checkMin = -999;
        else checkMin = 1;
        return checkDigAnswer(checkMin, 100);
    }

    // The time complexity of the current function is O(1).
    private int enterRentOrSale(boolean survey) {
        System.out.println("1 - For rent");
        System.out.println("2 - For sale");
        System.out.println("Insert valid sale or rent number");

        int checkMin;
        if (survey) checkMin = -999;
        else checkMin = 1;
        int rentOrSale = checkDigAnswer(checkMin, 2);
        if (rentOrSale == 3) {
            System.out.println("Invalid sale or rent type number");
        }
        return rentOrSale;
    }

    // The time complexity of the current function is O(1).
    private String enterBuildingNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert building number");
        return scanner.nextLine();
    }

    // The time complexity of the current function is O(1).
    private int enterPrice(boolean survey) {
        System.out.println("Insert $ price");

        int checkMin;
        if (survey) checkMin = -999;
        else checkMin = 1;
        return checkDigAnswer(checkMin, 100000000);
    }

    // The time complexity of the current function is O(n).
    private boolean ifSymbolExist(String testedString, String[] constantString) {
        for (int i = 0; i < constantString.length; i++) {
            if (testedString.contains(constantString[i]))
                return true;
        }
        return false;
    }

    // The time complexity of the current function is O(n^2), because there is loop inside another loop ("n*n").
    private String enterUserName() {
        String username;
        boolean theSame;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Please enter your preferred user name:");
            username = scanner.nextLine();
            theSame = false;
            if (allUsers != null) {
                for (int i = 0; i < allUsers.length; i++) {
                    theSame = allUsers[i].ifTheSame(username, "");
                    if (theSame) {
                        System.out.println("This name already present, try again");
                        break;
                    }
                }
            }
        } while (theSame);
        return username;
    }

    // The time complexity of the current function is O(n), because do while loop performed "n" times, and depends on the
    // amount of times that the user tries to run it.
    private String enterPassword() {
        String password;
        boolean finePassword = false;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Please enter your preferred password:");
            System.out.println("(Note: Your password must contain at least 5" +
                    " characters, including one digit and at least one of those symbols: $,% or _ ). ");
            password = scanner.nextLine();

            if (password.length() >= 5) {
                finePassword = ifSymbolExist(password, SYMBOLS) && ifSymbolExist(password, DIGITS);
            }
            if (!finePassword)
                System.out.println("Invalid password, Try again!");
        } while (!finePassword);
        return password;
    }

    // The time complexity of the current function is O(n),(as explained before).
    private String enterPhoneNumber() {
        String phoneNumber;
        boolean isValidPhone = false;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Please enter your valid IL mobile phone number:");
            if (!scanner.hasNextInt())
                phoneNumber = scanner.nextLine();
            else {
                phoneNumber = scanner.nextLine();
                if (phoneNumber.length() == 10 && phoneNumber.charAt(0) == '0' && phoneNumber.charAt(1) == '5')
                    isValidPhone = true;
            }
            if (!isValidPhone)
                System.out.println("Invalid PhoneNumber. Try again.");
        } while (!isValidPhone);
        return phoneNumber;
    }

    // The time complexity of the current function is O(n),(as explained before).
    private int enterUserType() {
        int outUserType;
        boolean isValidUsertype;
        do {
            System.out.println("Please type:");
            System.out.println("1: If you are a real estate broker.");
            System.out.println("2: if you are a private user.");
            outUserType = checkDigAnswer(1, 2);
            isValidUsertype = outUserType < 1 || outUserType > 2;
            if (isValidUsertype)
                System.out.println("Invalid user type. Try again.");
        } while (isValidUsertype);
        return outUserType;

    }
}
