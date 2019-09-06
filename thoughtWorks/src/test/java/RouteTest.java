import core.DefaultSearch;
import core.Digraph;
import entity.DirectedTrip;
import entity.Town;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

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
    
    
    @Test
    public void testCase2() {
        Town from = new Town("A");
        Town to = new Town("C");
        DefaultSearch search = new DefaultSearch(digraph, from);
    
        Collection<DirectedTrip> path = search.pathTo(to);
        System.out.println(String.format("A - > D distance %.2f Path -> %s ",search.distTo(to),path));
    }
}
