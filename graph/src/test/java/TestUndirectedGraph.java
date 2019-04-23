import org.junit.Test;

import java.io.InputStream;

public class TestUndirectedGraph {


    @Test
    public void TestName() throws Exception{
        InputStream inputStream = this.getClass().getResourceAsStream("tinyG.txt");

        UndirectedGraph graph = new UndirectedGraph(inputStream);

        System.out.println(graph);
    }


    @Test
    public void TestDepthSearch() throws Exception{
        InputStream inputStream = this.getClass().getResourceAsStream("tinyG.txt");

        UndirectedGraph graph = new UndirectedGraph(inputStream);

        DepthFirstSearch search = new DepthFirstSearch(graph, 0);

        System.out.println("search.marked(1)---> " + search.marked(1));
        System.out.println("search.marked(2)---> " + search.marked(2));
        System.out.println("search.marked(3)---> " + search.marked(3));
        System.out.println("search.marked(4)---> " + search.marked(4));
        System.out.println("search.marked(7)---> " + search.marked(7));

    }

}
