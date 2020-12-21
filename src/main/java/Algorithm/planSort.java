package Algorithm;

import MysqlTools.MysqlConnect;
import MysqlTools.Project_info;

import java.util.*;

public class planSort {
    Map<Integer,Integer>layers=new HashMap<>();
    Map<Integer,ArrayList<Integer>>nextPos=new HashMap<>();
    Map<Integer,ArrayList<Integer>>layer_plan=new HashMap<>();
    Map<Integer,ArrayList<Integer>>preWork=new HashMap<>();
    public planSort(int[][]edges) {
        this.sort(edges);
        Set<Integer>key=layers.keySet();
        for(int x:key){
            int layer=layers.get(x);
            if(!layer_plan.containsKey(layer)){
                layer_plan.put(layer,new ArrayList<>());
            }
            layer_plan.get(layer).add(x);
        }
        key=nextPos.keySet();
        for(int x:key){
            for(int y:nextPos.get(x)){
                if(!preWork.containsKey(y)){
                    preWork.put(y,new ArrayList<>());
                }
                preWork.get(y).add(x);
            }
        }
    }
    public  void sort(int[][]edges){
        Map<Integer, Queue<Integer>>map_out=new HashMap<Integer, Queue<Integer>>();
        Map<Integer,Integer>map_in=new HashMap<Integer, Integer>();
        Queue<Integer>queue=new LinkedList<>();
        for(int[]edge:edges){
            if(!map_out.containsKey(edge[0]))
                map_out.put(edge[0],new LinkedList<Integer>());
            map_out.get(edge[0]).add(edge[1]);
            map_in.put(edge[1],map_in.getOrDefault(edge[1],0)+1 );
        }
        Set<Integer>set=map_out.keySet();
        for(int x:set){
            if(!map_in.containsKey(x))
                queue.add(x);
        }
        int layer=1;
        while(!queue.isEmpty()){
            int size=queue.size();
            for(int i=0;i<size;i++){
                int pos=queue.poll();
                layers.put(pos,layer);
                Queue<Integer>temp=map_out.get(pos);
                while(temp!=null&&!temp.isEmpty()){
                    int next=temp.poll();
                    int restEdge=map_in.get(next);
                    restEdge--;
                    if(restEdge==0){
                        queue.add(next);
                        map_in.remove(next);
                    }else{
                        map_in.put(next,restEdge);
                    }
                }
            }
            layer++;
        }
        for(int[]x:edges){
            if(layers.get(x[1])-layers.get(x[0])==1){
                if(!nextPos.containsKey(x[0]))
                    nextPos.put(x[0],new ArrayList<>());
                nextPos.get(x[0]).add(x[1]);
            }
        }
    }
    public String parseJson(){
        StringBuilder jsonMerge=new StringBuilder("[");
        Set<Integer>set=layers.keySet();
        for(int x:set){
            int len=nextPos.containsKey(x)?nextPos.get(x).size():0;
            int[]next=new int[len];
            for(int i=0;i<len;i++){
                next[i]=nextPos.get(x).get(i);
            }
            String array=Arrays.toString(next);
            array=array.replace("{","[").replace("}","]");
            String json="{"+"\"work_id\":\""+x+"\","+"\"layer\":\""+layers.get(x)+"\","+"\"nextArray\":"+
                    array+"}";
            jsonMerge.append(json).append(",");
        }
        jsonMerge.delete(jsonMerge.length()-1,jsonMerge.length());
        jsonMerge.append("]");
        return jsonMerge.toString().equals("]")?"":jsonMerge.toString();
    }

    public Map<Integer,int[]>getArgs(Project_info project, MysqlConnect connect){
        int i=1;
        Map<Integer,int[]>planArgs=new HashMap<>();
        Map<Integer,Integer>planTime=connect.getPlanTime(project);
        //找最早时间
        while (layer_plan.containsKey(i)){
            if(i==1) {
                for (int x : layer_plan.get(i)){
                    int[]args=new int[6];
                    int time=planTime.get(x);
                    args[0]=0;
                    args[1]=time;
                    planArgs.put(x,args);
                }
            }else{
                for (int x : layer_plan.get(i)){
                    int[]args=new int[6];
                    int maxEnd=0;
                   for(int y:preWork.get(x)){
                       if(planArgs.containsKey(y))
                           maxEnd=Math.max(planArgs.get(y)[1],maxEnd);
                   }
                   int time=planTime.get(x);
                   args[0]=maxEnd;
                   args[1]=maxEnd+time;
                   planArgs.put(x,args);
                }
            }
            i++;
        }
        i--;
        //找最晚时间
        while(i>0){
            for(int x:layer_plan.get(i)){
                if(nextPos.containsKey(x)){
                    int minStartLate=Integer.MAX_VALUE;
                    int minStartEarly=Integer.MIN_VALUE;
                    for(int y:nextPos.get(x)){
                        minStartLate=Math.min(planArgs.get(y)[2],minStartLate);
                        minStartEarly=Math.min(planArgs.get(y)[0],minStartLate);
                    }
                    int time=planTime.get(x);
                    planArgs.get(x)[3]=minStartLate;
                    planArgs.get(x)[2]=minStartLate-time;
                    //总时差
                    planArgs.get(x)[4]=planArgs.get(x)[2]-planArgs.get(x)[0];
                    //自由时差
                    planArgs.get(x)[5]=minStartEarly-planArgs.get(x)[1];
                }else{
                    planArgs.get(x)[2]=planArgs.get(x)[0];
                    planArgs.get(x)[3]=planArgs.get(x)[1];
                }
            }
            i--;
        }
        return planArgs;
    }
    public String parseArgs(Map<Integer,int[]>args){
        StringBuilder jsonMerge=new StringBuilder("[");
        Set<Integer>keys=args.keySet();
        for(int key:keys){
            int[]arg=args.get(key);
            String argStr= Arrays.toString(arg).replace("{","[").replace("}","]");
            String jason="{"+"\"work_id\":\""+key+"\","+"\"args\":"+argStr+"}";
            jsonMerge.append(jason).append(",");
        }
        jsonMerge.delete(jsonMerge.length()-1,jsonMerge.length()).append("]");
        return jsonMerge.toString().equals("]")?"":jsonMerge.toString();
    }
}
