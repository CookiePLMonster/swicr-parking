package swicr.logic;

import java.util.ArrayList;

/**
 * Created by Jarek on 2017-05-28.
 */
public class Fifo {
    private ArrayList<Task> toDo;

    public Fifo(){
        this.toDo = new ArrayList<Task>();
    }

    public void put(Task t){
        toDo.add(t);
    }

    public Task get(){
        try {
            Task toReturn = toDo.get(0);
            toDo.remove(0);
            return toReturn;
        } catch ( NullPointerException e){
            return null;
        }
    }
}
