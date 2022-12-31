public class City {
    private String cityName;
    private String districtName;
    private String[] streetsNames;

    // The time complexity of the current constructor is O(n).
    public City(String districtIn, String cityIn, String[] streetsIn) {
        districtName = districtIn;
        cityName = cityIn;
        streetsNames = new String[streetsIn.length];
        for (int i = 0; i < streetsNames.length; i++)
            streetsNames[i] = streetsIn[i];
    }

    // The time complexity of the current function is O(n).
    public String toString() {
        String output = "";
        output += "District " + districtName + "; " + cityName + "\nStreets:";
        for (int i = 0; i < streetsNames.length; i++) {
            output += "  " + streetsNames[i] + ",";
        }
        output += "\n";
        return output;
    }

    // The time complexity of the current function is O(1).
    public String[] getStreets() {
        return streetsNames;
    }

    // The time complexity of the current function is O(1).
    public String getCityName() {
        return cityName;
    }

    // The time complexity of the current function is O(1).
    public boolean isSameCity(String inCityName) {
        if (inCityName.equals(cityName))
            return true;
        else return false;

    }
}