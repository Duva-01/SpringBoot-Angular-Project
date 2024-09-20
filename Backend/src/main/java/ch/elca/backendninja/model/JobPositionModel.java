package ch.elca.backendninja.model;

public class JobPositionModel {

    private Integer id;
    private String name;

    public JobPositionModel() {}

    public JobPositionModel(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
