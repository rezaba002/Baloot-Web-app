package com.example.baloot6backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.Length;

import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "providers")
public class Provider {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private String name;
    private String registryDate;
    @Column(length = 400)
    private String image;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

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
