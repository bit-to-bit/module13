import java.io.UnsupportedEncodingException;

public class FirstHttpClientTest {
    public static void main(String[] args) throws UnsupportedEncodingException {

        // Test 1-1 POST User
        System.out.println("\nTest 1-1 POST User:\n-------------------");
        FirstHttpClient.postUser();

        // Test 1-2 PUT User
        System.out.println("\nTest 1-2 PUT User:\n------------------");
        FirstHttpClient.putUser();

        // Test 1-3 DELETE User
        System.out.println("\nTest 1-3 DELETE User:\n---------------------");
        FirstHttpClient.deleteUser();

        // Test 1-4 GET All User
        System.out.println("\nTest 1-4 GET All User:\n----------------------");
        FirstHttpClient.getAllUser();

        // Test 1-5 GET User By ID
        System.out.println("\nTest 1-5 GET User By ID:\n------------------------");
        FirstHttpClient.getUserByID();

        // Test 1-6 GET User by UserName
        System.out.println("\nTest 1-6 GET User By UserName:\n------------------------------");
        FirstHttpClient.getUserByUserName();

        // Test 2-1 GET All Comments Of MAX Post To JSON
        System.out.println("\nTest 2-1 GET All Comments Of MAX Post To JSON:\n----------------------------------------------");
        FirstHttpClient.getAllCommentsToJson(2);
        FirstHttpClient.getAllCommentsToJson(6);

        // Test 3-1 GET All User Open Tasks
        System.out.println("\nTest 3-1 GET All User Open Tasks:\n----------------------------------------------");
        FirstHttpClient.getAllOpenTasks(10);

    }

}
