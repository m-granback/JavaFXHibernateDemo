package se.verran.javafxhibernatedemo.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "mobile_phones")
public class MobilePhone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "brand", nullable = false)
    private String brand;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Customer owner;

    public MobilePhone() {
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public MobilePhone(String brand) {
        this.brand = brand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
