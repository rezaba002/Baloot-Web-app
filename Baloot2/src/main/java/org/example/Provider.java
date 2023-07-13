package org.example;

public class Provider {
    private int id;
    private String name;
    private String registryDate;


    public Provider (int id, String name, String registryDate) {
        this.id = id;
        this.name = name;
        this.registryDate = registryDate;
    }


    public int getId() {return id;}
    public String getName() {return name;}
    public String getRegistryDate() {return registryDate;}


    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setRegistryDate(String registryDate) {this.registryDate = registryDate;}


    @Override
    public String toString() {
        return "Provider{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", registryDate='" + registryDate + '\'' +
                '}';
    }
}