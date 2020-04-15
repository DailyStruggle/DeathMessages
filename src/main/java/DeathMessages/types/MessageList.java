package DeathMessages.types;

import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

public class MessageList extends HashMap<Integer,Message>{
    private Map<Integer, Double[]> chanceRanges = new HashMap<Integer, Double[]>();
    private Map<EntityType, HashSet<Integer>> typeLookup = new HashMap<EntityType, HashSet<Integer>>();
    private Map<String, HashSet<Integer>> nameLookup = new HashMap<String,HashSet<Integer>>();

    public <T> void put(T t){
        Message[] messages;
        if(t instanceof Message){
            messages = new Message[1];
            messages[0] = (Message) t;
        } else if(t instanceof Message[]){
            messages = (Message[]) t;
        } else{
            throw new IllegalArgumentException("bad type passed to MessageList.put(m)");
        }

        for (Message m : messages) {
            Integer idx = this.size();
            this.put(idx, m);

            Double[] thisChanceRange = {0.0, m.chance};
            if (idx > 0) {
                Double incrementAmt = ((Double[]) this.chanceRanges.get(idx - 1))[1];
                thisChanceRange[0] += incrementAmt;
                thisChanceRange[1] += incrementAmt;
            }
            this.chanceRanges.put(idx, thisChanceRange);

            if(m.names != null){
                for (String name : m.names){
                    this.nameLookup.putIfAbsent(name, new HashSet<Integer>());
                    this.nameLookup.get(name).add(idx);
                }
            }

            if(m.types != null){
                for (String type : m.types){
                    this.nameLookup.putIfAbsent(type, new HashSet<Integer>());
                    this.nameLookup.get(type).add(idx);
                }
            }
        }
    }

    public Message getRandom(EntityType type, String name) {
        if(this.size() == 0) return null;
        if(this.size() == 1) return this.get(0);

        MessageList messageList = this.reduceMessageList(type,name);

        Double r = new Random().nextDouble() * this.chanceRanges.get(this.chanceRanges.size()-1)[1];
        Integer high = this.chanceRanges.size()-1;
        Integer low = 0;
        Integer idx = (high-low)/2;
        while(true){
            if(r > messageList.chanceRanges.get(idx)[1]) high = idx;
            else if(r <= messageList.chanceRanges.get(idx)[0]) low = idx;
            else break;
            idx = (high-low)/2 + low;
        }
        return this.get(idx);
    }

    private MessageList reduceMessageList(){
        MessageList res = new MessageList();

        for(Integer idx : this.keySet()){
            if(this.get(idx).names.size() > 0) continue;
            if(this.get(idx).types.size() > 0) continue;
            res.put(idx, this.get(idx));
        }

        return res;
    }

    private MessageList reduceMessageList(EntityType type){
        //if mob type isn't in any message requirement, get all the untyped messages
        if(type == null || !this.typeLookup.containsKey(type)) return this.reduceMessageList();

        MessageList res = new MessageList();

        for (Integer idx : this.typeLookup.get(type)) {
            if (this.get(idx).names.size() > 0) continue;
            res.putIfAbsent(idx, this.get(idx));
        }

        return res;
    }

    private MessageList reduceMessageList(EntityType type, String name){
        //if this mob name isn't in any message requirement, filter only by type
        if(name == null || !this.nameLookup.containsKey(name)) return this.reduceMessageList(type);

        MessageList res = new MessageList();

        for (Integer idx : this.nameLookup.get(name)) {
            if(!this.typeLookup.containsKey(idx) || !this.typeLookup.get(idx).contains(name)) continue;
            res.putIfAbsent(idx, this.get(idx));
        }

        return res;
    }
}
