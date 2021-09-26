package zw.co.malvern.integration;

public class ApiConstants {
    public static String buildUrl(int port,String otherPart){
        return "http://localhost:"+port+otherPart;
    }

}
