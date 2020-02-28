package sample.GuiPart.WorkClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

public class PosList<E> extends LinkedHashSet<E> {
    @Override
    public boolean add(E i) {
        if(this.contains(i)){
            this.remove(i);
            return super.add(i);
        }
        return super.add(i);
    }
}
