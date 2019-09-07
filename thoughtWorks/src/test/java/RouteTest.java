import core.DefaultSearch;
import core.Digraph;
import core.DijkstraSearch;
import entity.DirectedTrip;
import entity.Town;
import entity.Trip;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class RouteTest {
    
    public static Digraph digraph;

    @BeforeClass
    public static void testBefore(){
        try(InputStream inputStream = RouteTest.class.getResourceAsStream("GraphTest.txt")){
            digraph = new Digraph(inputStream);
            // System.out.println(digraph);
        }catch (IOException exp){
            exp.printStackTrace();
            throw new RuntimeException(String.format(" init failed %s !",exp.getMessage()));
        }
    }


    @Test
    public void testCase1(){
        List<Town> towns = Arrays.asList(
                Town.builder().withSign("A").build(),
                Town.builder().withSign("B").build(),
                Town.builder().withSign("C").build());
        Collection<DirectedTrip> trips = digraph.routeTrips(towns);
        System.out.println(trips);
    }

    @Test
    public void testCase2(){
        List<Town> towns = Arrays.asList(
                Town.builder().withSign("A").build(),
                Town.builder().withSign("D").build());
        Collection<DirectedTrip> trips = digraph.routeTrips(towns);
        System.out.println(trips);
    }

    @Test
    public void testCase3(){
        List<Town> towns = Arrays.asList(
                Town.builder().withSign("A").build(),
                Town.builder().withSign("D").build(),
                Town.builder().withSign("C").build());
        Collection<DirectedTrip> trips = digraph.routeTrips(towns);
        System.out.println(trips);
    }


    @Test
    public void testCase4(){
        long start = System.currentTimeMillis();
        List<Town> towns = Arrays.asList(
                Town.builder().withSign("A").build(),
                Town.builder().withSign("E").build(),
                Town.builder().withSign("B").build(),
                Town.builder().withSign("C").build(),
                Town.builder().withSign("D").build());
        Collection<DirectedTrip> trips = digraph.routeTrips(towns);
        System.out.println(trips);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }


    @Test
    public void testCase5(){
        List<Town> towns = Arrays.asList(
                Town.builder().withSign("A").build(),
                Town.builder().withSign("E").build(),
                Town.builder().withSign("D").build());
        Collection<DirectedTrip> trips = digraph.routeTrips(towns);
        System.out.println(trips);
    }

    @Test
    public void testCase6(){
        Town start = Town.builder().withSign("C").build();
        Town end = Town.builder().withSign("C").build();
        Collection<Trip> trips = digraph.routeTrips(start, end, trip -> trip.getCount() > 3, trip -> trip.getCount() <= 3);
        System.out.println(trips);
    }

    @Test
    public void testCase7() {
        Town from = new Town("C");
        Town to = new Town("C");
        DijkstraSearch search = new DijkstraSearch(digraph, from);

        Collection<DirectedTrip> path = search.pathTo(to);
        System.out.println(String.format("C - > C distance %.2f Path -> %s ",search.distTo(to),path));
    }

    @Test
    public void testCase8() {
        Town from = new Town("A");
        Town to = new Town("C");
        DijkstraSearch search = new DijkstraSearch(digraph, from);
    
        Collection<DirectedTrip> path = search.pathTo(to);
        System.out.println(String.format("A - > C distance %.2f Path -> %s ",search.distTo(to),path));
    }

    @Test
    public void testCase9() {
        Town from = new Town("B");
        Town to = new Town("B");
        DijkstraSearch search = new DijkstraSearch(digraph, from);

        Collection<DirectedTrip> path = search.pathTo(to);
        System.out.println(String.format("B - > B distance %.2f Path -> %s ",search.distTo(to),path));
    }
}
