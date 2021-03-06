import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PA2 {
    public static void main(String[] args){

        try {
            Edge[][] adjacencyList = loadLen("Tiny2");
            //System.out.println(adjacencyList[0].toString());
        }
        catch(IOException e){
            System.err.println(e);
        }

    }

    public static void printAdjacencyList(Edge[][] adjacencyList){
        for(int i =0; i<adjacencyList.length; i++){
            for(int j =0; j<adjacencyList[i].length; j++)
                System.out.println(adjacencyList[i][j]);
        }
    }


    public static int dijkstra(Edge[][] graph, int source_in, int dest_in){
        PriorityQueue<node> open = new PriorityQueue<node>();
        List<node> closed=new ArrayList<node>();
        node curr;
        node source = new node(source_in);
        source.setDistance(0);
        open.setPriority(source, source.distance);

        while(open.getSize()>0){
            curr=open.getMinimumItem();
            open.deleteMinimum();
            closed.add(curr);
            if(curr.getIdentity()==dest_in)
                break;
            for(int i =0; i<graph[curr.getIdentity()].length; i++){
                if()
            }


        }

    }

    public static Edge[][] loadLen(String fileName) throws IOException {
        Edge[][] out;
        int[] lineInt;
        String stringIn="";
        int curr=0;
        List<Edge> allOfOne=new ArrayList<Edge>();

        BufferedReader inputFile = new BufferedReader(new FileReader("src/Data/"+fileName+".len"));
        lineInt=extractInts(2, inputFile.readLine());

        out=new Edge[lineInt[0]][0];

        while((stringIn = inputFile.readLine()) != null){
            lineInt=extractInts(3, stringIn);
            if(curr!=lineInt[0]){
                out[curr] = allOfOne.toArray(new Edge[allOfOne.size()]);
                allOfOne=new ArrayList<Edge>();
                curr=lineInt[0];
            }
            allOfOne.add(new Edge(lineInt[0], lineInt[1], lineInt[2]));
        }

        out[curr] = allOfOne.toArray(new Edge[allOfOne.size()]);
        allOfOne=new ArrayList<Edge>();



        return out;
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
        return out;
    }

}

class node{
    int identity;
    int distance;
    node parent;

    public node(int identity) {
        this.identity = identity;
        this.distance=-1;
        this.parent=null;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
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
}

class Edge {
    int source;
    int destination;
    int length;


    public Edge(int source, int destination, int length) {
        this.source = source;
        this.destination = destination;
        this.length = length;
    }

    public String toString(){
        return "Source: "+source+"; Detination: "+destination+"; Length: "+length+";";
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

}

//Algorithm info:
//P2pShortestPath.pdf from canvas for this course

//Java basic stuff:
//https://www.guru99.com/buffered-reader-in-java.html
//https://stackoverflow.com/questions/17678862/reading-lines-with-bufferedreader-and-checking-for-end-of-file
