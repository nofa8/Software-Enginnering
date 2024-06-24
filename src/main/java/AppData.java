public class AppData {
    private static AppData instance;

    // Your data fields here
    private String someData;
    private int someNumber;
    // Add more fields as needed

    private AppData() {
        // Private constructor to prevent instantiation
    }

    public static synchronized AppData getInstance() {
        if (instance == null) {
            instance = new AppData();
        }
        return instance;
    }


}