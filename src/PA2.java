import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class PA2 {
    public static void main(String[] args){
        node[] adjacencyList={};
        try {
            adjacencyList = loadLen("NewYork");
        }
        catch(IOException e){
            System.err.println(e);
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

        /*
        int[][] res = new int[7][7];
        for(int i =0; i<7; i++) {
            for (int j = 0; j < 7; j++) {
                for (node x : adjacencyList)
                    x.reset();
                res[i][j] = dijkstra(adjacencyList, i, j)[0];
            }
        }
        printRes(res);*/
    }

    public static void printRes(int[][] res){
        for(int i =0; i<res.length; i++){
            System.out.print("[ ");
            for(int j =0; j<res[i].length; j++){
                if(res[i][j]==-1)
                    System.out.print("infty");
                else
                    System.out.print(res[i][j]);
                if(j!=res[i].length-1)
                    System.out.print(", ");
            }
            System.out.println("]");
        }
    }


    public static int[] aStarEuclidean(node[] graph, int source_in, int dest_in){
        PriorityQueue<node> open = new PriorityQueue<node>();
        graph[source_in].setDistance(0);
        open.setPriority(graph[source_in], euclidean(graph[source_in], graph[dest_in]));
        node curr;
        node v;
        int relaxCount=0;
        int len;
        while(open.getSize()>0){
            curr = open.getMinimumItem();
            open.deleteMinimum();
            if(curr.getIdentity()==dest_in)
                return new int[] {curr.getDistance(), relaxCount};
            for(int j =0; j<curr.getAdjacentArr().length; j++){
                v=curr.getAdjacentArr()[j];
                len=curr.getDistance()+curr.getDistArr()[j];
                if(len<v.getDistance() || v.getDistance() <0){
                    relaxCount++;
                    v.setDistance(len);
                    open.setPriority(v, v.distance+euclidean(v, graph[dest_in]));
                }
            }
        }
        return new int[]{-1, relaxCount};
    }

    public static int euclidean(node n1, node n2){
        //casting to int is the same as math.floor which will ensure that the heuristic returns <=
        // and not greater
        return (int) Math.sqrt(Math.pow((n1.getX()-n2.getX()),2)+Math.pow((n1.getY()-n2.getY()),2));
    }


    public static int[] dijkstra(node[] graph, int source_in, int dest_in){
        PriorityQueue<node> open = new PriorityQueue<node>();
        List<node> closed=new ArrayList<node>();
        graph[source_in].setDistance(0);
        open.setPriority(graph[source_in], 0);
        node curr;
        node v;
        int relaxCount=0;
        int len;
        while(open.getSize()>0){
            curr = open.getMinimumItem();
            open.deleteMinimum();
            closed.add(curr);
            if(curr.getIdentity()==dest_in)
                return new int[] {curr.getDistance(), relaxCount};
            for(int j =0; j<curr.getAdjacentArr().length; j++){
                v=curr.getAdjacentArr()[j];
                if(closed.contains(v))
                    continue;
                len=curr.getDistance()+curr.getDistArr()[j];
                if(len<v.getDistance() || v.getDistance() <0){
                    relaxCount++;
                    v.setDistance(len);
                    //v.parent=curr;
                    open.setPriority(v, v.distance);
                }
            }
        }
        return new int[] {-1, relaxCount};
    }

    public static int[] biDijkstra(node[] graph, int source_in, int dest_in) {
        PriorityQueue<node> open = new PriorityQueue<node>();
        PriorityQueue<node> openR = new PriorityQueue<node>();
        List<node> closed=new ArrayList<node>();
        List<node> closedR=new ArrayList<node>();

        open.setPriority(graph[source_in], 0);
        openR.setPriority(graph[dest_in], 0);

        int relaxCount=0;

        node min, minR, v;

        int mu = Integer.MAX_VALUE;
        int len;

        while(open.getSize()>0 && openR.getSize()>0){
            minR = openR.getMinimumItem();
            min = open.getMinimumItem();
            if((min.getDistance()+minR.getDistanceR()>=mu || min.getDistance()<0 || minR.getDistance()<0)&& mu != Integer.MAX_VALUE){
                return new int[] {mu, relaxCount};
            }
            open.deleteMinimum();
            closed.add(min);

            for(int j =0; j<min.getAdjacentArr().length; j++){
                v=min.getAdjacentArr()[j];
                if(closed.contains(v))
                    continue;
                len=min.getDistance()+min.getDistArr()[j];
                if(len<v.getDistance() || v.getDistance() <0){
                    relaxCount++;
                    v.setDistance(len);
                    //v.parent=curr;
                    open.setPriority(v, v.distance);
                    if(len+v.getDistanceR()<mu) {
                        mu = len+v.getDistanceR();
                    }
                }


            }

            openR.deleteMinimum();
            closedR.add(min);

            for(int j =0; j<minR.getAdjacentArrR().length; j++) {
                v = minR.getAdjacentArrR()[j];
                if (closedR.contains(v))
                    continue;
                len = min.getDistanceR() + minR.getDistArrR()[j];
                if (len < v.getDistanceR() || v.getDistanceR() < 0) {
                    relaxCount++;
                    v.setDistanceR(len);
                    //v.parent=curr;
                    openR.setPriority(v, v.distance);
                    if (len + v.getDistanceR() < mu){
                        mu = len + v.getDistanceR();
                    }
                }
            }
        }

        return new int[]{-1, relaxCount};
    }

    public static node[] loadLen(String fileName) throws IOException {
        return loadLen(fileName, true);
    }

    public static node[] loadLen(String fileName, boolean loadCo) throws IOException {
        node[] node_out;
        int[] lineInt;
        double[] lineDouble;
        String stringIn="";
        int curr=0;
        List<node> allOfOne=new ArrayList<node>();

        BufferedReader inputFile = new BufferedReader(new FileReader("src/Data/"+fileName+".len"));
        lineInt=extractInts(2, inputFile.readLine());

        node_out = new node[lineInt[0]];

        for(int i =0; i<lineInt[0]; i++)
            node_out[i] = new node(i);

        while((stringIn = inputFile.readLine()) != null){
            lineInt=extractInts(3, stringIn);

            node_out[lineInt[0]].addAdjacent(node_out[lineInt[1]], lineInt[2]);
            node_out[lineInt[1]].addAdjacentR(node_out[lineInt[0]], lineInt[2]);
            //node_out[lineInt[1]].addAdjacent(node_out[lineInt[0]], lineInt[2]);
        }
        for (node curr_n: node_out){
            curr_n.finalizeAdjacent();
        }

        if(loadCo) {
            inputFile = new BufferedReader(new FileReader("src/Data/" + fileName + ".co"));
            lineInt = extractInts(2, inputFile.readLine());


            while ((stringIn = inputFile.readLine()) != null) {
                lineDouble = extractDoubles(3, stringIn);
                node_out[(int) lineDouble[0]].setX(lineDouble[1]);
                node_out[(int) lineDouble[0]].setY(lineDouble[2]);
                //node_out[lineInt[1]].addAdjacent(node_out[lineInt[0]], lineInt[2]);
            }
        }



        return node_out;
    }


    public static int[] extractInts(int numToExtract, String input){
        int[] out = new int[numToExtract];
        Scanner scan = new Scanner(input);
        int count=0;
        while(scan.hasNext() && count<numToExtract){
            if(scan.hasNextInt()){
                out[count]=scan.nextInt();
                count++;
            }
            else
                scan.next();

        }
        scan.close();
        return out;
    }


    public static double[] extractDoubles(int numToExtract, String input){
        double[] out = new double[numToExtract];
        Scanner scan = new Scanner(input);
        int count=0;
        while(scan.hasNext() && count<numToExtract){
            if(scan.hasNextDouble()){
                out[count]=scan.nextDouble();
                count++;
            }
            else
                scan.next();

        }
        scan.close();
        return out;
    }


}

class node{
    int identity;
    int distance;
    int distanceR;
    double x;
    double y;
    node parent;
    List<node> adjacentList;
    List<Integer> distToAdjacentList;
    node[] adjacentArr;
    Integer[] distArr;

    List<node> adjacentListR;
    List<Integer> distToAdjacentListR;
    node[] adjacentArrR;

    Integer[] distArrR;

    boolean finalized;



    public node(int identity) {
        this.identity = identity;
        this.distance=-1;
        this.distanceR=-1;
        this.parent=null;
        this.adjacentList=new ArrayList<node>();
        this.distToAdjacentList=new ArrayList<Integer>();

        this.adjacentListR=new ArrayList<node>();
        this.distToAdjacentListR=new ArrayList<Integer>();
        this.finalized=false;
    }

    public void reset(){
        this.distance=-1;
        this.distanceR=-1;
        this.parent=null;
    }
    public node[] getAdjacentArr() {
        return adjacentArr;
    }

    public Integer[] getDistArr() {
        return distArr;
    }

    public node(){

    }

    public void addAdjacent(node in, int intIn){
        this.adjacentList.add(in);
        this.distToAdjacentList.add(intIn);
    }

    public void addAdjacentR(node in, int intIn){
        this.adjacentListR.add(in);
        this.distToAdjacentListR.add(intIn);
    }

    public void finalizeAdjacent(){
        if(!finalized){
            this.adjacentArr=adjacentList.toArray(new node[this.adjacentList.size()]);
            this.adjacentList=null;
            this.distArr=this.distToAdjacentList.toArray(new Integer[this.distToAdjacentList.size()]);
            this.distToAdjacentList=null;
            this.adjacentArrR=adjacentListR.toArray(new node[this.adjacentListR.size()]);
            this.adjacentListR=null;
            this.distArrR=this.distToAdjacentListR.toArray(new Integer[this.distToAdjacentListR.size()]);
            this.distToAdjacentListR=null;
            this.finalized=true;
        }
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public void setDistanceR(int distanceR) {
        this.distanceR = distanceR;
    }


    public int getDistanceR() {
        return distanceR;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public node getParent() {
        return parent;
    }

    public void setParent(node parent) {
        this.parent = parent;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }


    public List<node> getAdjacentListR() {
        return adjacentListR;
    }

    public void setAdjacentListR(List<node> adjacentListR) {
        this.adjacentListR = adjacentListR;
    }

    public List<Integer> getDistToAdjacentListR() {
        return distToAdjacentListR;
    }

    public void setDistToAdjacentListR(List<Integer> distToAdjacentListR) {
        this.distToAdjacentListR = distToAdjacentListR;
    }

    public node[] getAdjacentArrR() {
        return adjacentArrR;
    }

    public void setAdjacentArrR(node[] adjacentArrR) {
        this.adjacentArrR = adjacentArrR;
    }

    public Integer[] getDistArrR() {
        return distArrR;
    }

    public void setDistArrR(Integer[] distArrR) {
        this.distArrR = distArrR;
    }


}



//Algorithm info:
//P2pShortestPath.pdf from canvas for this course

//Java basic stuff:
//https://www.guru99.com/buffered-reader-in-java.html
//https://stackoverflow.com/questions/17678862/reading-lines-with-bufferedreader-and-checking-for-end-of-file
//https://stackoverflow.com/questions/9331837/returning-an-array-without-assign-to-a-variable
//https://www.baeldung.com/java-generating-random-numbers-in-range
