import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.dbtrends.Entity;
import org.dbtrends.Knowledgebase;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by esteves on 04.01.17.
 */
public class DBTrends {

    private List<Entity> trends = new ArrayList<Entity>();
    private static final String dbpediafiles = "/home/esteves/github/FactBench/files/dbpedia/";
    private static final String dbpediafiles_output = dbpediafiles + "complete/";
    private CSVReader reader;

    /**
     * Parsing through CSV-File, get through each line the ResourceIn/OutDegree of DBTrends and write in new CSV
     * @return List of Entities
     * @throws UnsupportedEncodingException
     * @throws MalformedURLException
     * @throws URISyntaxException
     * @throws Exception
     */
    public List<Entity> getDbins(File f) throws Exception {
        System.out.print(f);
        List<String[]> infos = this.csvreader(f.getPath());
        List<String> entries = new ArrayList<String>();
        int counter = 0;
        int empty = 0;
        for (String[] info : infos) {
            String temp = "";
            for (int i=0; i<info.length; i++){
                temp += info[i] + ",";
            }
            if(counter > 0){
                try{
                    URL url= new URL(info[0]);
                    URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
                    System.out.println("Resource: " + uri.toASCIIString());
                    Entity e = Knowledgebase.DBpedia39.getEntity(uri.toASCIIString());
                    if(e != null){
                        trends.add(e);
                        entries.add(temp + e.getResourceInDegree() + "," + e.getResourceOutDegree());
                    }else{
                        entries.add(temp + "-1,-1");
                        empty++;
                    }
                }
                catch (Exception e){
                    System.out.print(e.toString());
                }
            }else{
                // First line of CSV still need for new CSV
                entries.add(temp + "dbin,dbout");
            }
            counter++;
        }
        System.out.println("Counts of Emptiness: "+empty);
        this.writecsv("out_" + f.getName(), entries);
        return trends;
    }

    public List<String[]> csvreader(String csv){
        List<String[]> lines = new ArrayList<String[]>();
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
                    dbpediafiles_output+csv), ',');

            List<String[]> toWrite = new ArrayList<String[]>();
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

    public DBTrends(){

    }

    public static void main(String[] args) throws Exception{
        File directory = new File(dbpediafiles);
        Collection<File> files =
                FileUtils.listFiles(directory, new WildcardFileFilter("*.csv"), null);
        DBTrends db = new DBTrends();
        for (File f: files){
            db.getDbins(f);
        }
        System.out.println("done");

    }

}
