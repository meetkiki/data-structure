import core.DefaultSearch;
import core.Digraph;
import entity.DirectedTrip;
import entity.Town;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
        List<Town> towns = Arrays.asList(Town.builder().withFrom("A").build(), Town.builder().withFrom("B").build(), Town.builder().withFrom("C").build());
        List<DirectedTrip> trips = digraph.routeTrips(towns);
        System.out.println(trips);
    }

    @Test
    public void testCase2(){
        List<Town> towns = Arrays.asList(Town.builder().withFrom("A").build(), Town.builder().withFrom("D").build());
        List<DirectedTrip> trips = digraph.routeTrips(towns);
        System.out.println(trips);
    }

    @Test
    public void testCase3(){
        List<Town> towns = Arrays.asList(Town.builder().withFrom("A").build(), Town.builder().withFrom("D").build(),Town.builder().withFrom("C").build());
        List<DirectedTrip> trips = digraph.routeTrips(towns);
        System.out.println(trips);
    }


    @Test
    public void testCase4(){
        long start = System.currentTimeMillis();
        List<Town> towns = Arrays.asList(
                Town.builder().withFrom("A").build(),
                Town.builder().withFrom("E").build(),
                Town.builder().withFrom("B").build(),
                Town.builder().withFrom("C").build(),
                Town.builder().withFrom("D").build());
        List<DirectedTrip> trips = digraph.routeTrips(towns);
        System.out.println(trips);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }


    @Test
    public void testCase5(){
        List<Town> towns = Arrays.asList(
                Town.builder().withFrom("A").build(),
                Town.builder().withFrom("E").build(),
                Town.builder().withFrom("D").build());
        List<DirectedTrip> trips = digraph.routeTrips(towns);
        System.out.println(trips);
    }


    @Test
    public void testCase6() {
        Town from = new Town("A");
        Town to = new Town("C");
        DefaultSearch search = new DefaultSearch(digraph, from);
    
        Collection<DirectedTrip> path = search.pathTo(to);
        System.out.println(String.format("A - > C distance %.2f Path -> %s ",search.distTo(to),path));
    }
}
