package com.example.Baloot5Backend.model;

public class Provider {
    private int id;
    private String name;
    private String registryDate;
    private String image;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getRegistryDate() {return registryDate;}
    public void setRegistryDate(String registryDate) {this.registryDate = registryDate;}

    public String getImage() {return image;}
    public void setImage(String image) {this.image = image;}

    @Override
    public String toString() {
        return "Provider{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", registryDate='" + registryDate + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
