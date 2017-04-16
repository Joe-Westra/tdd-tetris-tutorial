package tetris;

import java.util.*;

/**
 * Created by jdub on 16/04/17.
 * <p>
 * This class represents a Scrabble bag.  Items are first added, and then randomly selected for removal.
 * Once all items have been removed (bag is empty) the next call for an item will refill the bag with
 * it's original contents, where the process is repeated.  This is actually LESS RANDOM than a RNG selector,
 * as all elements from the original additions must be removed before repetitious elements are returned.
 */
public class ShuffleBag<T> {

    ArrayList<T> bagContents;
    int current;
    Random random;

    ShuffleBag() {
        bagContents = new ArrayList<>();
        random = new Random();
    }

    public void add(T type) {
        add(type, 1);
    }

    public void add(T type, int amount) {
        for (int i = 0; i < amount; i++) {
            bagContents.add(type);
            current++;
        }
    }

    public T getNext() {
        if (current <= 0) {
            current = bagContents.size();
        }
        int selection = random.nextInt(current);
        return removeFromBag(selection);
    }

    private T removeFromBag(int selection) {
        T temp = bagContents.get(selection);
        bagContents.remove(selection);
        bagContents.add(temp);
        current--;
        return temp;
    }
}
