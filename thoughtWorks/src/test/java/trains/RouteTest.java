package trains;

import command.CommandContext;
import common.Constant;
import core.Digraph;
import entity.SearchCondition;
import entity.Town;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.CollectionUtils;
import utils.StrUtils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.fail;

public class RouteTest {

    public static Digraph digraph;

    @BeforeClass
    public static void testBefore(){
        try(InputStream inputStream = RouteTest.class.getResourceAsStream("/GraphTest.txt")){
            digraph = new Digraph(inputStream);
            // System.out.println(digraph);
        }catch (IOException ex){
            fail(String.format(" init failed %s !",ex.getMessage()));
        }
    }

    @Test
    public void testCase1(){
        // 1.The distance of the route A-B-C
        String route = "A-B-C";
        List<String> routeList = StrUtils.splitStr(route, "-");
        List<Town> towns = routeList.stream()
                .map((sign) -> Town.builder().withSign(sign).build())
                .collect(Collectors.toList());
        CommandContext context = new CommandContext(digraph).CommandContext(Constant.DISTANCE);
        System.out.println(context.execute(towns));
    }

    @Test
    public void testCase2(){
        // 2.The distance of the route A-D
        String route = "A-D";
        List<String> routeList = StrUtils.splitStr(route, "-");
        List<Town> towns = routeList.stream()
                .map((sign) -> Town.builder().withSign(sign).build())
                .collect(Collectors.toList());
        CommandContext context = new CommandContext(digraph).CommandContext(Constant.DISTANCE);
        System.out.println(context.execute(towns));
    }

    @Test
    public void testCase3(){
        // 3.The distance of the route A-D-C
        String route = "A-D-C";
        List<String> routeList = StrUtils.splitStr(route, "-");
        List<Town> towns = routeList.stream()
                .map((sign) -> Town.builder().withSign(sign).build())
                .collect(Collectors.toList());
        CommandContext context = new CommandContext(digraph).CommandContext(Constant.DISTANCE);
        System.out.println(context.execute(towns));
    }


    @Test
    public void testCase4(){
        // 4.The distance of the route A-E-B-C-D
        String route = "A-E-B-C-D";
        List<String> routeList = StrUtils.splitStr(route, "-");
        List<Town> towns = routeList.stream()
                .map((sign) -> Town.builder().withSign(sign).build())
                .collect(Collectors.toList());
        CommandContext context = new CommandContext(digraph).CommandContext(Constant.DISTANCE);
        System.out.println(context.execute(towns));
    }


    @Test
    public void testCase5(){
        // 5.The distance of the route A-E-D
        String route = "A-E-D";
        List<String> routeList = StrUtils.splitStr(route, "-");
        List<Town> towns = routeList.stream()
                .map((sign) -> Town.builder().withSign(sign).build())
                .collect(Collectors.toList());
        CommandContext context = new CommandContext(digraph).CommandContext(Constant.DISTANCE);
        System.out.println(context.execute(towns));
    }

    @Test
    public void testCase6(){
        // 6.The number of trips starting at C and ending at C with a maximum of 3 stops.
        // In the sample data below, there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops)

        // 测试路线起点和终点
        String route = "C-C";
        // 旅行次数最多3次
        Integer minCount = 0,maxCount = 3;
        List<String> routeList = StrUtils.splitStr(route, "-");
        List<Town> towns = routeList.stream()
                .map((sign) -> Town.builder().withSign(sign).build())
                .collect(Collectors.toList());
        Town start = CollectionUtils.findFirst(towns);
        Town end = CollectionUtils.findLast(towns);
        SearchCondition searchCondition = SearchCondition.builder()
                .withFrom(start)
                .withTo(end)
                .withStopCondition(trip -> trip.getCount() > maxCount)
                .withReturnCondition(trip -> trip.getCount() <= maxCount && trip.getCount() >= minCount)
                .build();
        CommandContext context = new CommandContext(digraph).CommandContext(Constant.TRIPS);
        System.out.println(context.execute(searchCondition));
    }

    @Test
    public void testCase7() {
        // 7.The number of trips starting at A and ending at C with exactly 4 stops.
        // In the sample data below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B)

        // 测试路线起点和终点
        String route = "A-C";
        // 旅行次数恰好是4次 即最大和最小都是4次
        Integer minCount = 4,maxCount = 4;
        List<String> routeList = StrUtils.splitStr(route, "-");
        List<Town> towns = routeList.stream()
                .map((sign) -> Town.builder().withSign(sign).build())
                .collect(Collectors.toList());
        Town start = CollectionUtils.findFirst(towns);
        Town end = CollectionUtils.findLast(towns);
        SearchCondition searchCondition = SearchCondition.builder()
                .withFrom(start)
                .withTo(end)
                .withStopCondition(trip -> trip.getCount() > maxCount)
                .withReturnCondition(trip -> trip.getCount() <= maxCount && trip.getCount() >= minCount)
                .build();
        CommandContext context = new CommandContext(digraph).CommandContext(Constant.TRIPS);
        System.out.println(context.execute(searchCondition));
    }

    @Test
    public void testCase8() {
        // 8.The length of the shortest route (in terms of distance to travel) from A to C.

        // 测试路线起点和终点
        String route = "A-C";
        List<String> routeList = StrUtils.splitStr(route, "-");
        List<Town> towns = routeList.stream()
                .map((sign) -> Town.builder().withSign(sign).build())
                .collect(Collectors.toList());
        Town start = CollectionUtils.findFirst(towns);
        Town end = CollectionUtils.findLast(towns);
        SearchCondition searchCondition = SearchCondition.builder()
                .withFrom(start)
                .withTo(end)
                .build();
        CommandContext context = new CommandContext(digraph).CommandContext(Constant.SHORTEST);
        System.out.println(context.execute(searchCondition));
    }

    @Test
    public void testCase9() {
        // 9.The length of the shortest route (in terms of distance to travel) from B to B
        // 测试路线起点和终点
        String route = "B-B";
        List<String> routeList = StrUtils.splitStr(route, "-");
        List<Town> towns = routeList.stream()
                .map((sign) -> Town.builder().withSign(sign).build())
                .collect(Collectors.toList());
        Town start = CollectionUtils.findFirst(towns);
        Town end = CollectionUtils.findLast(towns);
        SearchCondition searchCondition = SearchCondition.builder()
                .withFrom(start)
                .withTo(end)
                .build();
        CommandContext context = new CommandContext(digraph).CommandContext(Constant.SHORTEST);
        System.out.println(context.execute(searchCondition));
    }

    @Test
    public void testCase10() {
        // 10.The number of different routes from C to C with a distance of less than 30.
        // In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC

        // 测试路线起点和终点
        String route = "C-C";
        // 旅行距离小于30
        Integer maxDistance = 30;
        List<String> routeList = StrUtils.splitStr(route, "-");
        List<Town> towns = routeList.stream()
                .map((sign) -> Town.builder().withSign(sign).build())
                .collect(Collectors.toList());
        Town start = CollectionUtils.findFirst(towns);
        Town end = CollectionUtils.findLast(towns);
        BigDecimal maxRoute = new BigDecimal(maxDistance);
        SearchCondition searchCondition = SearchCondition.builder()
                .withFrom(start)
                .withTo(end)
                .withStopCondition(trip -> trip.sumDist().compareTo(maxRoute) >= 0)
                .withReturnCondition(trip -> trip.sumDist().compareTo(maxRoute) < 0)
                .build();
        CommandContext context = new CommandContext(digraph).CommandContext(Constant.TRIPS);
        System.out.println(context.execute(searchCondition));
    }
}
