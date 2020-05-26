package controller.instance;

public interface Instance {

    /**
     * The type of instance
     * @return Instance type
     */
    String getType();

    /**
     * Name of instance
     * @return name
     */
    String getName();

    /**
     * Index of instance by type
     * @return id
     */
    int getId();

}
