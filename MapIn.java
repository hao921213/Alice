import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MapIn {
    ArrayList<ArrayList<String>> map;
    ArrayList<ArrayList<String>> big_map;
    ArrayList<ArrayList<String>> ghost_map;
    ArrayList<ArrayList<String>> other_map;
    
    ArrayList<String> tempList = new ArrayList<>();
    public MapIn() throws IOException {
        map=new ArrayList<>();
        big_map=new ArrayList<>();
        ghost_map=new ArrayList<>();
        other_map=new ArrayList<>();
        readMapFile("map/map.txt");
        readGhostFile("map/ghost_map.txt");
    }
    
    private void readMapFile(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tempArray = line.split("\\s+");
                tempList.addAll(Arrays.asList(tempArray));
            }
        }
         for(int i=0;i<10;i++){
            for(int j=0;j<40;j++){
                ArrayList<String> temp=new ArrayList<>();
                for(int k=0;k<10;k++){
                    for(int l=0;l<40;l++){
                        temp.add(tempList.get(10*i+k));
                    }
                }
                big_map.add(temp);
            }
        }
        for(int i=0;i<10;i++){
            ArrayList<String> temp=new ArrayList<>();
            for(int k=0;k<10;k++){
                temp.add(tempList.get(10*i+k));
            }
            map.add(temp);
        }

        tempList.clear();
    }

    private void readGhostFile(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tempArray = line.split("\\s+");
                for (String element : tempArray) {
                    tempList.add(element);
                }
            }
        }
        
         for(int i=0;i<10;i++){
            ArrayList<String> temp=new ArrayList<>();
            for(int k=0;k<10;k++){
                temp.add(tempList.get(10*i+k));
            }
            ghost_map.add(temp);
        }

        for(int i=0;i<10;i++){
            for(int j=0;j<40;j++){
                ArrayList<String> temp=new ArrayList<>();
                for(int k=0;k<10;k++){
                    for(int l=0;l<40;l++){
                        temp.add(tempList.get(10*i+k));
                    }
                }
                other_map.add(temp);
            }
        }
    }

    public ArrayList<ArrayList<String>> getMap() {
        return map;
    }

    public ArrayList<ArrayList<String>> getBig_Map(){
        return big_map;
    }

    public ArrayList<ArrayList<String>> getGhost_Map(){
        return ghost_map;
    }

    public ArrayList<ArrayList<String>> getOther_Map(){
        return other_map;
    }

}
