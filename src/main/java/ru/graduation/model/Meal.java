package ru.graduation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.graduation.View;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "meals")
public class Meal extends AbstractNamedEntity {
    //    https://stackoverflow.com/questions/8148684/what-data-type-to-use-for-money-in-java/43051227#43051227
    @Column(name = "price", nullable = false)
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    @NotNull(groups = View.Persist.class)
    private Restaurant restaurant;

    @Column(name = "date", nullable = false)
    @NotNull(groups = View.Persist.class)
    private LocalDate date;

    public Meal() {
    }

    public Meal(String name, Long price, LocalDate date) {
        this(null, name, price, date);
    }

    public Meal(Integer id, String name, Long price) {
        super(id, name);
        this.price = price;
    }

    public Meal(Integer id, String name, Long price, LocalDate date) {
        this(id, name, price);
        this.date = date;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", id=" + id +
                '}';
    }
}

