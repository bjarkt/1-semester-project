package Acq;

public interface IRoom {
    /**
     * Get the visual description of this room.
     *
     * @return visual description.
     */
    String getVisualDescription();

    /**
     * Get the location of this room.
     *
     * @return a location.
     */
    ILocation getLocation();
}
