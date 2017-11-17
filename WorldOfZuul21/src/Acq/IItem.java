package Acq;

import Business.Spawnable;

public interface IItem extends Spawnable {
    String getName();
    boolean isKey();
}
