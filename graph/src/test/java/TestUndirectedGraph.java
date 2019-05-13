import common.Bag;
import org.junit.Test;
import undriect.BreadthFirstPaths;
import undriect.CC;
import undriect.Cycle;
import undriect.DepthFirstPaths;
import undriect.DepthFirstSearch;
import undriect.UndirectedGraph;

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
        System.out.println("search.marked(12)---> " + search.marked(12));

    }

    @Test
    public void TestDepthPaths() throws Exception{
        InputStream inputStream = this.getClass().getResourceAsStream("tinyCG.txt");

        UndirectedGraph graph = new UndirectedGraph(inputStream);

        System.out.println(graph);

        DepthFirstPaths paths = new DepthFirstPaths(graph, 0);

        System.out.println("paths.pathTo(1)---> " + paths.pathTo(1));
        System.out.println("paths.pathTo(2)---> " + paths.pathTo(2));
        System.out.println("paths.pathTo(3)---> " + paths.pathTo(3));
        System.out.println("paths.pathTo(4)---> " + paths.pathTo(4));
        System.out.println("paths.pathTo(5)---> " + paths.pathTo(5));
    }

    @Test
    public void TestBreadthDepthPaths() throws Exception{
        InputStream inputStream = this.getClass().getResourceAsStream("tinyCG.txt");

        UndirectedGraph graph = new UndirectedGraph(inputStream);

        System.out.println(graph);

        BreadthFirstPaths paths = new BreadthFirstPaths(graph, 0);

        System.out.println("paths.pathTo(1)---> " + paths.pathTo(1));
        System.out.println("paths.pathTo(2)---> " + paths.pathTo(2));
        System.out.println("paths.pathTo(3)---> " + paths.pathTo(3));
        System.out.println("paths.pathTo(4)---> " + paths.pathTo(4));
        System.out.println("paths.pathTo(5)---> " + paths.pathTo(5));
    }

    @Test
    public void TestCC() throws Exception{
        InputStream inputStream = this.getClass().getResourceAsStream("tinyG.txt");

        UndirectedGraph graph = new UndirectedGraph(inputStream);

        System.out.println(graph);

        CC cc = new CC(graph);

        int count = cc.count();
        System.out.println(count + "components");
        // 表示每一个连通顶点
        Bag<Integer>[] bags = new Bag[count];
        for (int i = 0; i < bags.length; i++) {
            bags[i] = new Bag<>();
        }
        // 赋值
        for (int i = 0; i < graph.V(); i++) {
            bags[cc.id(i)].addFirst(i);
        }
        // 显示输出
        for (int i = 0; i < bags.length; i++) {
            System.out.println("bags："+i+" --- > " + bags[i]);
        }
    }

    @Test
    public void TestCycle() throws Exception{
        InputStream inputStream = this.getClass().getResourceAsStream("tinyCG.txt");

        UndirectedGraph graph = new UndirectedGraph(inputStream);

        System.out.println(graph);

        Cycle cycle = new Cycle(graph);

        System.out.println("cycle isHasCycle---> " + cycle.isHasCycle());
    }

}
