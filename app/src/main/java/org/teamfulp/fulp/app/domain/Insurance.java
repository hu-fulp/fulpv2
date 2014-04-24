package org.teamfulp.fulp.app.domain;

/**
 * Created by royfokker on 02-04-14.
 */
public class Insurance {

    private String name;
    private String category;
    private String end;
    private double amount;
    private String start;
    private String interval;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.isEmpty()){
            throw new IllegalArgumentException("Naam mag niet leeg zijn");
        }
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if(category.isEmpty()){
            throw new IllegalArgumentException("Categorie mag niet leeg zijn");
        }
        this.category = category;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        if(start.isEmpty()){
            throw new IllegalArgumentException("Start mag niet leeg zijn");
        }
        this.end = end;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if(amount == 0.0){
            throw new IllegalArgumentException("Bedrag mag niet leeg zijn");
        }
        this.amount = amount;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        if(interval.isEmpty()){
            throw new IllegalArgumentException("Interval mag niet leeg zijn");
        }
        this.interval = interval;
    }
}
