import core.Digraph;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class RouteTest {
    
    public static Digraph digraph;

    @BeforeClass
    public static void testBefore(){
        try(InputStream inputStream = RouteTest.class.getResourceAsStream("GraphTest.txt")){
            digraph = new Digraph(inputStream);
        }catch (IOException exp){
            exp.printStackTrace();
            throw new RuntimeException(String.format(" init failed %s !",exp.getMessage()));
        }
    }


    @Test
    public void testCase1(){
        System.out.println(digraph);
    }
}
