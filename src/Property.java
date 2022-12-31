public class Property {
    private static final String[] PROPERTY_TYPE = {"Regular apartment", "Penthouse", "Private building"};
    private static final String[] RENT_OR_SALE = {"for rent", "for sale"};
    private String cityName;
    private String streetName;
    private String houseNumber;  // may be 1A
    private int propertyType;
    private int floorNumber;
    private int roomsNumber;
    private User user;
    private int price;
    private int rentOrSale;

    // The time complexity of the current constructor is O(1).
    public Property(String inCityName, String inStreetName, String inHouseNumber, int inPropertyType,
                    int inFloorNumber, int inRoomsNumber, int inPrice, int inRentOrSale, User inUser) {
        user = inUser;
        cityName = inCityName;
        streetName = inStreetName;
        houseNumber = inHouseNumber;
        propertyType = inPropertyType;
        roomsNumber = inRoomsNumber;
        floorNumber = inFloorNumber;
        price = inPrice;
        rentOrSale = inRentOrSale;
    }

    // The time complexity of the current function is O(1).
    public boolean ifSameUser(String userName) {
        return userName.equals(user.getUserName());
    }

    // The time complexity of the current function is O(1).
    public String toString() {
        String output = "";
        output += cityName + " - " + streetName + " " + houseNumber + ".\n";
        output += PROPERTY_TYPE[propertyType - 1] + " - " + RENT_OR_SALE[rentOrSale - 1] + ": " + roomsNumber +
                " rooms";
        if (propertyType != 3)
            output += ", floor " + floorNumber + ".\n";
        else output += ".\n";
        output += "Price: " + toPrintMany(price) + "$.\n";
        output += "Contact info: ";
        output += user.toString();

        return output;
    }

    // The time complexity of the current function is O(1).
    public boolean forSearchCheck(int rentOrSaleIn, int typePrIn, int roomsIn, int priceMinIn, int priceMaxIn) {
        if (rentOrSaleIn != -999 && rentOrSaleIn != rentOrSale)
            return false;
        if (typePrIn != -999 && typePrIn != propertyType)
            return false;
        if (roomsIn != -999 && roomsIn != roomsNumber)
            return false;
        if (priceMinIn != -999 && priceMinIn > price)
            return false;
        if (priceMaxIn != -999 && priceMaxIn < price)
            return false;
        return true;
    }

    // The time complexity of the current function is O(1).
    private String toPrintMany(int manyIn) {
        String out = "";
        String many = Integer.toString(manyIn);
        do {
            if (many.length() > 3) {
                out = "," + many.substring(many.length() - 3) + out;
                many = many.substring(0, many.length() - 3);
            } else {
                out = many + out;
                many = "";
            }
        } while (many.length() > 0);
        return out;
    }
}