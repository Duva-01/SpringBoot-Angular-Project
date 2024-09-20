package ch.elca.backendninja.model;

public class GenderModel {

    private Integer id;
    private String name;

    public GenderModel(){}

    public GenderModel(Integer id, String name) {
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
