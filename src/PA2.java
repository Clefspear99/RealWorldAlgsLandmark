import javax.swing.text.html.HTMLDocument;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PA2 {

    public static void main(String[] args) {
        //String loc = "NewYork";
        String loc = "NewYork";
        Object[] graph = new Object[1];
        Object[] reverseGraph = new Object[1];
        Object[] revRev = new Object[1];
        try {
            graph = loadIntArr(loc, false);
            reverseGraph = reverseGraph(graph);
            revRev = reverseGraph(reverseGraph);
            //adjacencyList=loadLen("Tiny2", false);
            //adjacencyList=loadLen(loc);
            System.out.println("loaded");

        } catch (IOException e) {
            System.err.println(e);
        }

        Object[] rand = randomLandmarks(graph, reverseGraph, 4);
        System.out.print(loc);
        //This next dijkstra call should return 27; instead its return 15
        verify(graph, rand);

        /*
        int[][] res = new int[7][7];
        for(int i =0; i<7; i++) {
            for (int j = 0; j < 7; j++) {
                res[i][j] = dijkstra(graph, i, j)[0];
            }
        }
        printRes(res);*/





    /*
    public static void main(String[] args){
        node[] adjacencyList={};

        //String[] fileNames = {"California", "Central", "Colorado", "East", "NewYork","NorthWest", "West", "USA"};
          String[] fileNames={"NewYork"};
        int aSpeed=0, aRelax=0,dSpeed=0, aTime = 0, dTime=0, dRelax=0, start, end;
        int resA[];
        int resD[];
        int resB[];


        long time = -1;



        Random rand = new Random();
        for(String loc:fileNames) {
            try {
                adjacencyList = loadLen(loc);
                //adjacencyList=loadLen("Tiny2", false);
                //adjacencyList=loadLen(loc);
                System.out.println("Loaded!");
            } catch (IOException e) {
                System.err.println(e);
            }

            for (int i = 0; i < 2; i++) {

                start=rand.nextInt(adjacencyList.length);
                end=rand.nextInt(adjacencyList.length);
                start=0;
                end=11;

                /*
                for (node x : adjacencyList)
                    x.reset();
                time = System.currentTimeMillis() - time;
                resA = aStarEuclidean(adjacencyList, start, end);
                aTime += System.currentTimeMillis() - time;
                aRelax+=resA[1];



                for (node x : adjacencyList)
                    x.reset();
                time=System.currentTimeMillis();
                resD = dijkstra(adjacencyList, start, end);
                dTime+= System.currentTimeMillis() - time;
                dRelax+=resD[1];




            }
            System.out.println("for "+loc+"; Dijkstra average time "+(dTime/1.0)+" ms; Average edges relaxed: "+(dRelax/1000.0));
            System.out.println("for "+loc+"; A* euclidean average time "+(aTime/1.0)+" ms  ; Average edges relaxed: "+(aRelax/1000.0));
        }
        }



        Random rand = new Random();
        int start;
        int end;
        int resA[];
        int resD[];
        int resB[];
        for(int i =0; i<50; i++){
            start=rand.nextInt(adjacencyList.length);
            end=rand.nextInt(adjacencyList.length);


            for (node x : adjacencyList)
                x.reset();
            resD=dijkstra(adjacencyList, start, end);

            for (node x : adjacencyList)
                x.reset();
            resB=biDijkstra(adjacencyList, start, end);


            for (node x : adjacencyList)
                x.reset();
            resA=aStarEuclidean(adjacencyList, start, end);

            System.out.println("run: "+i+" Dijsktra: "+ resD[0]+ ", "+resD[1]+";  BiD: "+resB[0]+", "+resB[1]+";  A*: "+resA[0]+", "+resA[1]);

        }


        int[][] res = new int[7][7];
        for(int i =0; i<7; i++) {
            for (int j = 0; j < 7; j++) {
                for (node x : adjacencyList)
                    x.reset();
                res[i][j] = dijkstra(adjacencyList, i, j)[0];
            }
        }
        printRes(res);
    }*/

    }

    public static boolean verify(Object[] graph, Object[] landM) {
        Random rand = new Random();
        int start, end;
        int[][] arr = (int[][]) graph[0];
        int len = arr.length;

        int resA, resD;

        for (int i = 0; i < 50; i++) {
            start = rand.nextInt(len);
            end = rand.nextInt(len);
            start = 0;
            end = 2;
            resA = aStarLandmark(graph, landM, start, end)[0];
            resD = dijkstra(graph, start, end)[0];
            if (resA != resD) {
                System.out.println("Test " + i + " Failed! Start: " + start + " end: " + end);
                System.out.print("Abborting!");
                return false;
            }

            System.out.println("Finished test: " + i + " All Good!");
        }

        return false;
    }


    public static void printRes(int[][] res) {
        for (int i = 0; i < res.length; i++) {
            System.out.print("[ ");
            for (int j = 0; j < res[i].length; j++) {
                if (res[i][j] == -1)
                    System.out.print("infty");
                else
                    System.out.print(res[i][j]);
                if (j != res[i].length - 1)
                    System.out.print(", ");
            }
            System.out.println("]");
        }
    }

    public static int[] aStarEuclidean(Object[] graph, int source_in, int dest_in) {
        int[][] addList = (int[][]) graph[0];
        int[][] addDist = (int[][]) graph[1];
        double[][] cordinates = (double[][]) graph[2];
        int[] distance = new int[addList.length];
        int relaxCount = 0;
        int curr, v, len;

        for (int i = 0; i < distance.length; i++)
            distance[i] = -1;
        distance[source_in] = 0;


        PriorityQueue<Integer> open = new PriorityQueue<Integer>();
        open.setPriority(source_in, 0);
        while (open.getSize() > 0) {
            curr = open.getMinimumItem();
            open.deleteMinimum();
            if (curr == dest_in)
                return new int[]{distance[curr], relaxCount};
            for (int i = 0; i < addList[curr].length; i++) {
                v = addList[curr][i];

                len = distance[curr] + addDist[curr][i];
                if (len < distance[v] || distance[v] == -1) {
                    relaxCount++;
                    distance[v] = len;
                    open.setPriority(v, (len + euclidean(cordinates[v][0], cordinates[v][1], cordinates[dest_in][0], cordinates[dest_in][0])));
                }

            }
        }
        return new int[]{-1, relaxCount};
    }

    public static int euclidean(double x1, double y1, double x2, double y2) {
        //casting to int is the same as math.floor which will ensure that the heuristic returns <=
        // and not greater
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }


    public static int[] aStarLandmark(Object[] graph, Object[] landmarks, int source_in, int dest_in) {
        int[][] addList = (int[][]) graph[0];
        int[][] addDist = (int[][]) graph[1];
        double[][] cordinates = (double[][]) graph[2];
        int[] distance = new int[addList.length];
        int relaxCount = 0;
        int curr, v, len, heuristic;

        for (int i = 0; i < distance.length; i++)
            distance[i] = -1;
        distance[source_in] = 0;


        PriorityQueue<Integer> open = new PriorityQueue<Integer>();
        open.setPriority(source_in, 0);
        while (open.getSize() > 0) {
            curr = open.getMinimumItem();
            open.deleteMinimum();
            if (curr == dest_in)
                return new int[]{distance[curr], relaxCount};
            for (int i = 0; i < addList[curr].length; i++) {
                v = addList[curr][i];

                len = distance[curr] + addDist[curr][i];
                if (len < distance[v] || distance[v] == -1) {
                    relaxCount++;
                    distance[v] = len;
                    heuristic = landmarkHeuristic(graph, landmarks, v, dest_in);
                    open.setPriority(v, (len + heuristic));
                }

            }
        }
        return new int[]{-1, relaxCount};
    }


    public static int landmarkHeuristic(Object[] graph, Object[] landmarks, int v, int dest_in) {
        int[] landmarkLocs = (int[]) landmarks[0];
        int[][] toLandmark = (int[][]) landmarks[1];
        int[][] fromLandmark = (int[][]) landmarks[2];
        int out = 0;
        int len;

        for (int i = 0; i < landmarkLocs.length; i++) {
            //how do we handle infinity?
            //consider path 1 -> 2 with landmark: 6
            if (toLandmark[i][v] != -1 && toLandmark[i][dest_in] != -1) {
                len = toLandmark[i][v] - toLandmark[i][dest_in];
                if (len > out)
                    out = len;
            }
            if (fromLandmark[i][v] != -1 && fromLandmark[i][dest_in] != -1) {
                len = fromLandmark[i][dest_in] - fromLandmark[i][v];
                if (len > out)
                    out = len;
            }
        }


        return out;
    }

    public static Object[] randomLandmarks(Object[] graph, Object[] reverseGraph, int num) {
        Random rand = new Random();
        int len = ((int[][]) graph[0]).length;
        int[][] outToLandmark = new int[num][len];
        int[][] outFromLandmark = new int[num][len];
        int[] landmarkLocs = new int[num];
        for (int i = 0; i < num; i++) {
            landmarkLocs[i] = rand.nextInt(len);
            for (int j = 0; j < len; j++) {
                outToLandmark[i][j] = dijkstra(graph, j, landmarkLocs[i])[0];
                outFromLandmark[i][j] = dijkstra(reverseGraph, landmarkLocs[i], j)[0];

            }
            System.out.println("Finished random landmark gen: " + i);
        }

        return new Object[]{landmarkLocs, outToLandmark, outFromLandmark};
    }

    public static Object[] gridLandmarks(Object[] graph, Object[] reverseGraph, int num) {
        Random rand = new Random();
        int len = ((int[][]) graph[0]).length;
        int[][] outToLandmark = new int[num][len];
        int[][] outFromLandmark = new int[num][len];
        int[] landmarkLocs = getGridLandmarks(graph[3], num);

        for (int i = 0; i < num; i++) {
            for (int j = 0; j < len; j++) {
                outFromLandmark[i][j] = dijkstra(reverseGraph, landmarkLocs[i], j)[0];
                outToLandmark[i][j] = dijkstra(graph, j, landmarkLocs[i])[0];
            }
            System.out.println("Finished random landmark gen: " + i);
        }

        return new Object[]{landmarkLocs, outToLandmark, outFromLandmark};
    }

    public static int[] getGridLandmarks(Object coordinates, int num){
        int out[] = new int[num];
        for(int i =0; i<out.length; i++)
            out[i]=0;
        double[][] goal = new double[num][];
        double[][] cor = (double[][]) coordinates;

        double maxX=Integer.MIN_VALUE;
        double minX = Integer.MAX_VALUE;
        double maxY=Integer.MIN_VALUE;
        double minY = Integer.MAX_VALUE;

        for(double[] node: cor){
            if(node[0] < minX)
                minX=node[0];
            if(node[0] > maxX)
                minX=node[0];
            if(node[1] < minY)
                minX=node[1];
            if(node[1] > maxY)
                minX=node[1];

        }

        for(int i =1; i<=num; i++){
            goal[i-1][0]=((maxX-minX)/num)*i*.5;
            goal[i-1][1]=(((maxY-minY)/num)*i)-(((maxY-minY)/num)*.5);
        }
        double[] g;
        for(int i =0; i<cor.length; i++){
            for (int j=0; j<goal.length; j++) {
                g=goal[j];
                if (euclidean(cor[i][0], cor[i][1], g[0],g[1])<euclidean(cor[out[j]][0], cor[out[j]][1], g[0],g[1])) {
                    out[j]=i;
                }
            }
        }




        return out;
    }


    public static int[] dijkstra(Object[] graph, int source_in, int dest_in) {
        int[][] addList = (int[][]) graph[0];
        int[][] addDist = (int[][]) graph[1];
        boolean[] closed = new boolean[addList.length];
        int[] distance = new int[addList.length];
        int relaxCount = 0;
        int curr, v, len;

        for (int i = 0; i < distance.length; i++)
            distance[i] = -1;
        distance[source_in] = 0;


        PriorityQueue<Integer> open = new PriorityQueue<Integer>();
        open.setPriority(source_in, 0);
        while (open.getSize() > 0) {
            curr = open.getMinimumItem();
            open.deleteMinimum();
            closed[curr] = true;
            if (curr == dest_in)
                return new int[]{distance[curr], relaxCount};
            for (int i = 0; i < addList[curr].length; i++) {
                v = addList[curr][i];
                if (closed[v])
                    continue;

                len = distance[curr] +
                        addDist[curr][i];
                if (distance[v] == -1 || len < distance[v]) {
                    relaxCount++;
                    distance[v] = len;
                    open.setPriority(v, len);
                }

            }
        }
        return new int[]{-1, relaxCount};
    }


    public static int[][] returnSize(String fileName) throws IOException {
        int[] lineInt;
        int out[][];
        int vCount;

        BufferedReader inputFile = new BufferedReader(new FileReader("src/Data/" + fileName + ".deg"));
        vCount = extractInts(1, inputFile.readLine())[0];

        out = new int[vCount][];
        for (int i = 0; i < vCount; i++) {
            lineInt = extractInts(2, inputFile.readLine());
            out[lineInt[0]] = new int[lineInt[1]];
        }
        return out;
    }


    public static Object[] loadIntArr(String fileName, boolean loadCo) throws IOException {
        int[] lineInt;
        double[] lineDouble;
        int[][] out = returnSize(fileName);
        int[][] lenOut = returnSize(fileName);
        double[][] coOut = new double[lenOut.length][2];


        String stringIn = "";
        int curr = -1;
        int addCount = 0;


        BufferedReader lenFile = new BufferedReader(new FileReader("src/Data/" + fileName + ".len"));
        lineInt = extractInts(2, lenFile.readLine());

        System.out.println("Loading " + fileName + " Vertices: " + lineInt[0] + " Edges: " + lineInt[1]);


        while ((stringIn = lenFile.readLine()) != null) {
            lineInt = extractInts(3, stringIn);
            if (curr != lineInt[0]) {
                curr = lineInt[0];
                addCount = 0;
            }

            out[lineInt[0]][addCount] = lineInt[1];
            lenOut[lineInt[0]][addCount] = lineInt[2];
            addCount++;

            //node_out[lineInt[1]].addAdjacent(node_out[lineInt[0]], lineInt[2]);
        }

        System.out.println("read CO");
        if (loadCo) {
            BufferedReader inputFile = new BufferedReader(new FileReader("src/Data/" + fileName + ".co"));
            lineInt = extractInts(2, inputFile.readLine());


            while ((stringIn = inputFile.readLine()) != null) {
                lineDouble = extractDoubles(3, stringIn);
                coOut[(int) lineDouble[0]][0] = lineDouble[1];
                coOut[(int) lineDouble[0]][1] = lineDouble[2];
                //node_out[lineInt[1]].addAdjacent(node_out[lineInt[0]], lineInt[2]);
            }
        }

        return new Object[]{out, lenOut, coOut};
    }

    public static Object[] reverseGraph(Object[] graph) {
        int[][] adjacencyList = (int[][]) graph[0];
        int[][] lenList = (int[][]) graph[1];

        int[][] reversedAdjacencyList = new int[adjacencyList.length][];
        int[][] lenListReverse = new int[adjacencyList.length][];

        List<Integer>[] tempAL = new ArrayList[adjacencyList.length];
        List<Integer>[] tempLL = new ArrayList[adjacencyList.length];

        for (int x = 0; x < adjacencyList.length; x++) {
            tempAL[x] = new ArrayList<Integer>();
            tempLL[x] = new ArrayList<Integer>();
        }


        int val;
        for (int i = 0; i < adjacencyList.length; i++) {
            for (int j = 0; j < adjacencyList[i].length; j++) {
                val = adjacencyList[i][j];
                tempAL[val].add(i);
                //reversedAdjacencyList[j][i]=adjacencyList[i][j];
                //lenListReverse[j][i]=lenList[i][j];
                tempLL[val].add(lenList[i][j]);
            }
        }


        for (int i = 0; i < adjacencyList.length; i++) {

            reversedAdjacencyList[i] = convertToInt(tempAL[i]);
            lenListReverse[i] = convertToInt(tempLL[i]);
        }

        return new Object[]{reversedAdjacencyList, lenListReverse};
    }

    public static int[] convertToInt(List in) {
        int[] out = new int[in.size()];

        for (int i = 0; i < out.length; i++) {
            out[i] = (int) in.get(i);
        }
        return out;
    }

    public static int[] extractInts(int numToExtract, String input) {
        int[] out = new int[numToExtract];
        Scanner scan = new Scanner(input);
        int count = 0;
        while (scan.hasNext() && count < numToExtract) {
            if (scan.hasNextInt()) {
                out[count] = scan.nextInt();
                count++;
            } else
                scan.next();

        }
        scan.close();
        return out;
    }


    public static double[] extractDoubles(int numToExtract, String input) {
        double[] out = new double[numToExtract];
        Scanner scan = new Scanner(input);
        int count = 0;
        while (scan.hasNext() && count < numToExtract) {
            if (scan.hasNextDouble()) {
                out[count] = scan.nextDouble();
                count++;
            } else
                scan.next();

        }
        scan.close();
        return out;
    }


}


//Algorithm info:
//P2pShortestPath.pdf from canvas for this course

//Java basic stuff:
//https://www.guru99.com/buffered-reader-in-java.html
//https://stackoverflow.com/questions/17678862/reading-lines-with-bufferedreader-and-checking-for-end-of-file
//https://stackoverflow.com/questions/9331837/returning-an-array-without-assign-to-a-variable
//https://www.baeldung.com/java-generating-random-numbers-in-range
//https://stackoverflow.com/questions/26827480/do-i-need-to-seed-manually-when-using-random-in-java
//https://www.geeksforgeeks.org/array-of-arraylist-in-java/
//https://www.geeksforgeeks.org/compare-two-arrays-java/
