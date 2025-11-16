import org.json.JSONObject;

public class TestJSON {
    public static void main(String[] args) {
        JSONObject obj = new JSONObject();
        obj.put("name", "Rodina");
        obj.put("role", "Student");
        System.out.println(obj.toString());
    }
}
