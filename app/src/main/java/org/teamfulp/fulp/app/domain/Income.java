package org.teamfulp.fulp.app.domain;

/**
 * Created by royfokker on 02-04-14.
 */
public class Income {

    private String name;
    private String start;
    private String end;
    private String interval;
    private double amount;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.isEmpty()){
            throw new IllegalArgumentException("Omschrijving mag niet leeg zijn");
        }
        else
            this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        if(this.start.isEmpty())
            throw new IllegalArgumentException("Startdatum mag niet leeg zijn");
        else
            this.end = end;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        if(interval.isEmpty())
            throw new IllegalArgumentException("Interval mag niet leeg zijn");
        else
            this.interval = interval;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if(amount == 0.0)
            throw new IllegalArgumentException("Bedrag moet een waarde hebben");
        else
            this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if(type.isEmpty())
            throw new IllegalArgumentException("Type mag niet leeg zijn");
        else
            this.type = type;
    }
}
