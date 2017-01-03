package main.java;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.dbtrends.Entity;
import org.dbtrends.Knowledgebase;

/**
 * Created by johnyscrazy on 04/01/17.
 */
public class DBTrendsSupport {

    private List<Entity> trends = new ArrayList<>();
    private static final String dbpedia1 = "/Users/media/NetBeansProjects/factbench_defacto/src/main/webapp/dbpedia/dbpedia_001.csv";
    private static final String dbpedia2 = "/Users/media/NetBeansProjects/factbench_defacto/src/main/webapp/dbpedia/dbpedia_002.csv";
    private static final String dbpedia3 = "/Users/media/NetBeansProjects/factbench_defacto/src/main/webapp/dbpedia/dbpedia_003.csv";
    private static final String dbpedia4 = "/Users/media/NetBeansProjects/factbench_defacto/src/main/webapp/dbpedia/dbpedia_004.csv";
    private static final String dbpedia5 = "/Users/media/NetBeansProjects/factbench_defacto/src/main/webapp/dbpedia/dbpedia_005.csv";
    private CSVReader reader;

    /**
     * Parsing through CSV-File, get through each line the ResourceIn/OutDegree of DBTrends and write in new CSV
     * @return List of Entities
     * @throws UnsupportedEncodingException
     * @throws MalformedURLException
     * @throws URISyntaxException
     * @throws Exception
     */
    public List<Entity> getDbins() throws UnsupportedEncodingException, MalformedURLException, URISyntaxException, Exception {
        List<String[]> infos = this.csvreader(dbpedia5);
        List<String> entries = new ArrayList<>();
        int counter = 0;
        int empty = 0;
        for (String[] info : infos) {
            if(counter > 0){
                URL url= new URL(info[0]);
                URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
                //System.out.println("Resource: " + uri.toASCIIString());
                Entity e = Knowledgebase.DBpedia39.getEntity(uri.toASCIIString());
                if(e != null){
                    trends.add(e);
                    entries.add(info[0]+","+info[1]+","+info[2]+","+e.getResourceInDegree()+","+e.getResourceOutDegree());
                }else{
                    entries.add(info[0]+","+info[1]+","+info[2]+",-1,-1");
                    empty++;
                }
            }else{
                // First line of CSV still need for new CSV
                entries.add(info[0]+","+info[1]+","+info[2]+",dbin,dbout");
            }
            counter++;
        }
        System.out.println("Counts of Emptiness: "+empty);
        this.writecsv("dbpedia_005_dbtrends.csv", entries);
        return trends;
    }

    public List<String[]> csvreader(String csv){
        List<String[]> lines = new ArrayList<>();
        try{
            reader = new CSVReader(new FileReader(csv));
            lines = reader.readAll();
        }catch(IOException e){
        }
        return lines;
    }

    public void writecsv(String csv, List<String> entries) throws IOException{
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(
                    "/Users/media/NetBeansProjects/factbench_defacto/src/main/webapp/dbpedia/"+csv), ',');

            List<String[]> toWrite = new ArrayList<>();
            //String[] array = new String[entries.size()];
            //writer.writeNext(entries.toArray(array));


            for(String e : entries){
                //System.out.println(e);
                toWrite.add(e.split(","));
            }
            writer.writeAll(toWrite);
            writer.close();
            System.out.println("Hopefully created new CSV: "+csv);
        } catch(Exception e){

        }

    }

    /**
     * Setter Trends
     * @param trends List of Entities
     */
    public void setTrends(List<Entity> trends) {
        this.trends = trends;
    }

    /**
     * Creates a new instance of DBTrends
     */
    public DBTrendsSupport() {
    }
}
