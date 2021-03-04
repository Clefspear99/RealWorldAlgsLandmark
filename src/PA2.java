import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class PA2 {
    public static void main(String[] args){

        int[] res = extractInts(2, "7 4");
        System.out.println(res[0]+" "+res[1]);

    }


    public static Edge[][] load(String fileName) throws IOException {
        Edge[][] out;
        int[] lineInt;
        String stringIn="";
        int curr=0;

        BufferedReader inputFile = new BufferedReader(new FileReader("src/Data/"+fileName+".len"));
        lineInt=extractInts(2, inputFile.readLine());

        out=new Edge[lineInt[0]][];

        while((stringIn = inputFile.readLine()) != null){
            lineInt=extractInts(3, stringIn);
            if(curr==lin)
        }


        return ;
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

class Edge{
    int source;
    int destination;
    int length;

    public Edge(int source, int destination, int length) {
        this.source = source;
        this.destination = destination;
        this.length = length;
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


//Java basic stuff:
//https://www.guru99.com/buffered-reader-in-java.html
https://stackoverflow.com/questions/17678862/reading-lines-with-bufferedreader-and-checking-for-end-of-file